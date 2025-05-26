package com.marin.scoreboard.sport.football;

import com.marin.scoreboard.constant.ErrorMessages;
import com.marin.scoreboard.core.Match;
import com.marin.scoreboard.core.MatchTracker;
import com.marin.scoreboard.core.MatchValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FootballMatchTracker implements MatchTracker {

    private final Map<String, FootballMatch> startedMatches = new LinkedHashMap<>();

    /**
     * Starts a new football match between the given teams.
     * Provided team names cannot be blank and cannot be the same.
     *
     * @param homeTeam must ba a non-blank String
     * @param awayTeam must ba a non-blank String
     * @throws IllegalArgumentException if arguments fail validation or if the match already exists.
     */
    public void startMatch(final String homeTeam, final String awayTeam) throws IllegalArgumentException {
        MatchValidator.validateTeamNames(homeTeam, awayTeam);

        final String matchId = checkIfTeamsAreAlreadyPlaying(homeTeam, awayTeam);

        final FootballMatch footballMatch = new FootballMatch(matchId, homeTeam, awayTeam);
        startedMatches.put(matchId, footballMatch);
    }

    /**
     * Updates the score of the given match between the given teams.
     *
     * @param homeTeam  must ba a non-blank String
     * @param awayTeam  must ba a non-blank String
     * @param homeScore must be a non-negative integer
     * @param awayScore must be a non-negative integer
     * @throws IllegalArgumentException if arguments fail validation or if the match does not exist.
     */
    public void updateScore(final String homeTeam,
                            final String awayTeam,
                            final int homeScore,
                            final int awayScore) throws IllegalArgumentException {
        MatchValidator.validateTeamNames(homeTeam, awayTeam);
        MatchValidator.validateTeamScores(homeScore, awayScore);

        final Match match = findMatch(homeTeam, awayTeam);

        match.updateScore(homeScore, awayScore);
    }

    /**
     * Ends the given match between the given teams. If the match does not exist, an IllegalArgumentException will be thrown.
     *
     * @param homeTeam must be a non-blank String
     * @param awayTeam must be a non-blank String
     * @throws IllegalArgumentException if arguments fail validation or if the match does not exist.
     */
    public void endMatch(final String homeTeam, final String awayTeam) throws IllegalArgumentException {
        MatchValidator.validateTeamNames(homeTeam, awayTeam);

        final String matchId = createMatchId(homeTeam, awayTeam);

        final FootballMatch removedMatch = startedMatches.remove(matchId);
        if (removedMatch == null) {
            throw new IllegalArgumentException(ErrorMessages.MATCH_NOT_FOUND_FAILED_ENDING);
        }
    }

    /**
     * @return a list of all started matches, sorted by total score and by time of creation.
     * If two matches have the same total score, the match that started last will come first in the list.
     */
    public List<Match> getSummary() {
        List<Match> matchList = new ArrayList<>(startedMatches.values());
        Collections.reverse(matchList);
        matchList.sort(Comparator.comparingInt(Match::getTotalScore).reversed());

        return matchList;
    }

    //____________________________________________________________________________________________________________________

    String createMatchId(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase().trim() + " vs " + awayTeam.toLowerCase().trim();
    }

    Match findMatch(String homeTeam, String awayTeam) throws IllegalArgumentException {
        final String matchId = createMatchId(homeTeam, awayTeam);
        final Match match = startedMatches.get(matchId);
        if (match == null) {
            throw new IllegalArgumentException(ErrorMessages.MATCH_NOT_FOUND);
        }
        return match;
    }

    String checkIfTeamsAreAlreadyPlaying(String homeTeam, String awayTeam) {
        final String matchId = createMatchId(homeTeam, awayTeam);
        final String reversedMatchId = createMatchId(awayTeam, homeTeam);

        if (startedMatches.containsKey(matchId) || startedMatches.containsKey(reversedMatchId)) {
            throw new IllegalArgumentException(ErrorMessages.MATCH_ALREADY_IN_PROGRESS);
        }

        return matchId;
    }

}
