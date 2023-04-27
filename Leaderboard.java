import java.util.*;
import java.util.stream.Stream;

class User {
    private String name;
    private final String email;
    private String country;
    private int score;

    public User(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", score=" + score +
                '}';
    }
}


public class Leaderboard {
        private final Map<String, User> usersByEmail = new HashMap<>();
        private final TreeSet<User> topUsers = new TreeSet<>(Comparator.comparing(User::getScore).reversed().thenComparing(User::getEmail));
        private final Map<String, TreeSet<User>> usersByCountry = new HashMap<>();

        public void upsertUser(String name, String email, String country) {
            User user = usersByEmail.get(email);
            if (user == null) {
                user = new User(name, email, country);
                usersByEmail.put(email, user);
                usersByCountry.computeIfAbsent(country, k -> new TreeSet<>(Comparator.comparing(User::getScore).reversed().thenComparing(User::getEmail)))
                        .add(user);
            }
            else{
                user = new User(name, email, country);
                usersByEmail.put(email, user);
                if(!(user.getCountry().equals(country))){
                    user.setCountry(country);
                }
                usersByCountry.computeIfAbsent(country, k -> new TreeSet<>(Comparator.comparing(User::getScore).reversed().thenComparing(User::getEmail)))
                        .add(user);
            }
            topUsers.remove(user);
            topUsers.add(user);
        }


        public void upsertScore(String email, int score) {
            User user = usersByEmail.get(email);
            if (user == null) {
                throw new IllegalArgumentException("User with email " + email + " not found");
            }
            topUsers.remove(user);
            user.setScore(score);
            topUsers.add(user);
        }

        public List<User> getTop(int k, String country) {
            if (country == null) {
                return new ArrayList<>(topUsers.stream().limit(k).toList());
            } else {
                TreeSet<User> users = usersByCountry.get(country);
                if (users == null) {
                    return Collections.emptyList();
                } else {
                    return new ArrayList<>(users.stream().limit(k).toList());
                }
            }
        }

        public List<User> search(int score, String country) {
            Stream<User> stream = usersByEmail.values().stream();
            if (country != null) {
                TreeSet<User> users = usersByCountry.get(country);
                if (users == null) {
                    return Collections.emptyList();
                } else {
                    stream = users.stream();
                }
            }
            return stream.filter(user -> user.getScore() == score).toList();
        }

        public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard();

        leaderboard.upsertUser("Nikhil", "nikhil@flipkart.com", "India");
        leaderboard.upsertUser("Rahul", "rahul@flipkart.com", "India");
        leaderboard.upsertScore("rahul@flipkart.com", 1);
        leaderboard.upsertScore("nikhil@flipkart.com", 5);
        leaderboard.upsertUser("Karan", "karan@flipkart.com", "Argentina");
        leaderboard.upsertUser("Karan", "karan@flipkart.com", "china");
        leaderboard.upsertScore("karan@flipkart.com", 1);
        leaderboard.upsertScore("karan@flipkart.com", 10);

        int k1=3, k2=2, k3=10;
        String country2="India", country3="china";
        System.out.println("Top 3 global users: " + leaderboard.getTop(k1, null));
        System.out.println("Top 2 users in " + country2 +": "  + leaderboard.getTop(k2, country2));

        System.out.println("Users with score 1 in "+ country3 +": " + leaderboard.search(k3, country3));

    }

}





