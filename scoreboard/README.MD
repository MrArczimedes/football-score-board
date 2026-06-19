**WELCOME**

This is score board application.
First, I did domain and logic in services, with all required tests. Next I will use java fx to present app.

**ASSUMPTIONS**
1. Match is only between 2 teams - away and home team.
2. Match always begin with score 0 for both teams.
3. Team's names are final.
4. Team name cannot have numbers and special characters.
5. Changing score is done only by 1. 
6. Cannot edit score for example from 0 -> 5
7. You can undo last goal. But u can undo only last action for one team.
8. When match is finished is not visible in scoreboard.
9. It is ordered by last added match on top
10. choosing walkover finishes match and set score to 3-0 
11. summary window display all matches by higher score


8th, 9th and 11th assumption will be done in javaFx controller.

**HOW TO RUN**

These are 2 ways:

1. In inteliji idea go to gradle toolbar -> PROJECT_NAME -> Tasks -> application -> run
2. .\gradlew run
