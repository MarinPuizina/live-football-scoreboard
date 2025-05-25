package com.marin.scoreboard.core;

import org.apache.commons.lang3.StringUtils;

public final class MatchValidator {

    private MatchValidator() {}

    public static void validateTeamNames(String homeTeam, String awayTeam) throws IllegalArgumentException {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be blank.");
        }
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same.");
        }
    }

    public static void validateTeamScores(final int homeScore, final int awayScore) throws IllegalArgumentException {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Team scores cannot be negative values.");
        }
    }
}
