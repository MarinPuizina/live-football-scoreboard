# Football World Cup - Live Scoreboard
___
## Assumptions:

### 1. Start a new match:
- We simply provide two team names as method parameters.
- The created object will have the default score 0–0.
- This method also needs to add this match to the scoreboard.

### 2. Update score:
- We must receive two int values for scores.
- Might add some match id to know for which match we want to update the score.
- Since we're updating the score of the football match, I would expect to update the score of only one team.
  However, we are required to accept the values for both of the teams. Also, I would expect
  that we're increasing a goal at the time, but we are required to accept the pair of absolute values.
  Therefore, I've decided only to do negative number validation.

### 3. Finish match:
- Remove the match from the scoreboard.

### 4. Get summary:
- I'm guessing we can return the data in any format, as long as the data is ordered correctly.

___
## Match Object:
- id:
  - using the "HomeTeamName vs AwayTeamName" format since teams can play only in one match, hence securing the uniqueness.
    the alternative would be generating UUID.
