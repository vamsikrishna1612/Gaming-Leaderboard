# Gaming-Leaderboard

An online gaming leaderboard with capabilities mentioned below. Sample command names to be used are enclosed in [].

Designing the leaderboard keeping all data in-memory.

[UPSERT_USER] Upsert user name along with email-id and country that he/she belongs to. Default score for a user is 0. Username, email-id should be unique.
[UPSERT_SCORE] Upsert score for a given user.
[GET_TOP] Get the top k rank holders at a given point of time taking k as input. Country is an optional argument. If a country is provided then return top k users of a country else at a global level.
[SEARCH] Implement a search capability where based on given certain filter parameters score and country - users matching the criteria are returned. For example, queries such as give me all users who are Indians and have a score equal to 4. [Exact search over the score]
