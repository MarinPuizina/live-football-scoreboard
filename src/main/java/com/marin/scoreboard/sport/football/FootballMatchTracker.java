package com.marin.scoreboard.sport.football;

import com.marin.scoreboard.core.Match;
import com.marin.scoreboard.core.MatchTracker;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class FootballMatchTracker implements MatchTracker {

    private final Map<String, FootballMatch> startedMatches = new LinkedHashMap<>();

    /**
     * Starts a new football match between the given teams.
     * Provided team names cannot be blank and cannot be the same.
     *
     * @param homeTeamName must ba a non-blank String
     * @param awayTeamName must ba a non-blank String
     */
    public void startMatch(final String homeTeamName, final String awayTeamName) throws IllegalArgumentException {
        validateTeamNames(homeTeamName, awayTeamName);

        final String matchId = createMatchId(homeTeamName, awayTeamName);

        if (startedMatches.containsKey(matchId)) {
            throw new IllegalArgumentException("The match is already in progress.");
        }

        final FootballMatch footballMatch = new FootballMatch(matchId, homeTeamName, awayTeamName);
        startedMatches.put(matchId, footballMatch);
    }

    String createMatchId(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase().trim() + " vs " + awayTeam.toLowerCase().trim();
    }

    void validateTeamNames(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be blank.");
        }
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same.");
        }
    }

    public void updateScore(final Match match, final String team, final int score) {
    }

    public void endMatch(final Match match) {
    }

    public Map<String, FootballMatch> getSummaryOfMatches() {
        return startedMatches;
    }
}
