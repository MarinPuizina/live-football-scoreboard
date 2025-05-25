package com.marin.scoreboard.sport.football;

import com.marin.scoreboard.core.Match;
import com.marin.scoreboard.core.MatchTracker;
import com.marin.scoreboard.core.MatchValidator;
import org.apache.commons.lang3.StringUtils;

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
     */
    public void startMatch(final String homeTeam, final String awayTeam) throws IllegalArgumentException {
        MatchValidator.validateTeamNames(homeTeam, awayTeam);

        final String matchId = createMatchId(homeTeam, awayTeam);

        if (startedMatches.containsKey(matchId)) {
            throw new IllegalArgumentException("The match is already in progress.");
        }

        final FootballMatch footballMatch = new FootballMatch(matchId, homeTeam, awayTeam);
        startedMatches.put(matchId, footballMatch);
    }

    // TODO: Move to FootballMatch class?
    public static String createMatchId(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase().trim() + " vs " + awayTeam.toLowerCase().trim();
    }

    /**
     * Updates the score of the given match between the given teams.
     *
     * @param homeTeam must ba a non-blank String
     * @param awayTeam must ba a non-blank String
     * @param homeScore must be a non-negative integer
     * @param awayScore must be a non-negative integer
     */
    public void updateScore(final String homeTeam,
                            final String awayTeam,
                            final int homeScore,
                            final int awayScore) {
        MatchValidator.validateTeamNames(homeTeam, awayTeam);
        MatchValidator.validateTeamScores(homeScore, awayScore);

        final Match match = findMatch(homeTeam, awayTeam);

        match.updateScore(homeScore, awayScore);
    }


    Match findMatch(String homeTeam, String awayTeam) throws IllegalArgumentException {
        final String matchId = createMatchId(homeTeam, awayTeam);
        final Match match = startedMatches.get(matchId);
        if (match == null) {
            throw new IllegalArgumentException("Match not found.");
        }
        return match;
    }

    public void endMatch(final Match match) {
    }

    public List<Match> getSummaryOfMatches() {
        List<Match> matchList = new ArrayList<>(startedMatches.values());
        Collections.reverse(matchList);
        matchList.sort(Comparator.comparingInt(Match::getTotalScore).reversed());

        return matchList;
    }
}
