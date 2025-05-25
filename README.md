# Football World Cup - Live Scoreboard
___
## Assumptions:

### 1. Start a new match:
- We simply provide two team names as method parameters.
- The created object will have the default score 0 – 0.
- This method also needs to add this match to the match tracker.

### 2. Update score:
- We must receive two int values for scores.
- I've decided to add team names to know for which match we want to update the score.
- Since we're updating the score of the football match, I would expect to update the score of only one team.
  However, we are required to accept the values for both of the teams. Also, I would expect
  that we're increasing by one goal at a time, but we are required to accept the pair of absolute values.
- The score values cannot be negative numbers.

### 3. Finish match:
- Will remove the match from the match tracker.
- I've decided to throw an exception if we're trying to remove a non-existent match.

### 4. Get summary:
- I'm guessing we can return the data in any format, as long as the data is ordered correctly.
  Therefore, I'm returning a map of FootballMatches. That way the user can present the data in the format they want.

___
## How to use the library:
- I've created a factory method for creating a FootballMatchTracker. I like this approach since it's straightforward to
  add another type of trackers and provide a new factory method.

Code: 

    MatchTracker tracker = MatchTrackerFactory.createFootballMatchTracker();
    footballMatchTracker.endMatch("Croatia", "Norway");
    footballMatchTracker.updateScore("Croatia", "Norway", 1, 0);
    footballMatchTracker.getSummary();
    footballMatchTracker.endMatch("Croatia", "Norway");


___
## Design:

    com.marin.scoreboard
    ├── core
    │   ├── Match.java
    │   ├── MatchTracker.java
    │   └── MatchValidator.java
    ├── sport
    │   └── football
    │       ├── FootballMatch.java
    │       └── FootballMatchTracker.java
    └── MatchTracerFactory.java


- `core`: Contains core interfaces and classes not specific to any sport.
  - `Match`: Represents a generic sports match.
  - `MatchTracker`: Defines the contract for managing matches.
  - `MatchValidator`: Validates match-related data.
- `sport`: Contains sport-specific implementations.
  - `FootballMatch`: Represents a football match.
  - `FootballMatchTracker`: Implements the MatchTracker interface for football matches.
- `MatchTrackerFactory`: The Main entry point, contains factory methods.

**Design Decisions:**
- If I had a Database, I would make uuid for a match object and timestamps. Then it's a matter of making simple queries
to make the getSummary method return a proper dataset.
However, I've decided to use data structures instead. LinkedHashMap works great in this case, since I get the benefit of
having a key value map. And being it linked, keeps track of the match start "time". Which made it easy to create the 
algorithm for getSummary method.
- I created the MatchTracker.java interface since we have a clearly defined behavior. Also, it makes it easy to add additional 
implementations at a later date.
- MatchTracerFactory.java is a factory class that provides the factory methods. If we add additional mach trackers, it will
be straightforward to add additional factory methods to support them.