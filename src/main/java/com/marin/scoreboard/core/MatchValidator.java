package com.marin.scoreboard.core;

import com.marin.scoreboard.constant.ErrorMessages;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public final class MatchValidator {
    private MatchValidator() {}

    private static final Pattern VALID_TEAM_NAME_PATTERN = Pattern.compile("^[\\p{L} '\\-]+$");

    public static void validateTeamNames(String homeTeam, String awayTeam) throws IllegalArgumentException {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_NAME_CANNOT_BE_BLANK);
        }

        final boolean isHomeTeamInvalid = !VALID_TEAM_NAME_PATTERN.matcher(homeTeam).matches();
        final boolean isAwayTeamInvalid = !VALID_TEAM_NAME_PATTERN.matcher(awayTeam).matches();
        if (isHomeTeamInvalid || isAwayTeamInvalid) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_TEAM_NAME_FORMAT);
        }

        if (homeTeam.trim().equalsIgnoreCase(awayTeam.trim())) {
            throw new IllegalArgumentException(ErrorMessages.TEAMS_CANNOT_BE_THE_SAME);
        }
    }

    public static void validateTeamScores(final int homeScore, final int awayScore) throws IllegalArgumentException {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_SCORES_CANNOT_BE_NEGATIVE);
        }
    }
}
