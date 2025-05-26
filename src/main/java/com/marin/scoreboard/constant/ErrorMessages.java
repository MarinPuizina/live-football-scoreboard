package com.marin.scoreboard.constant;

public final class ErrorMessages {
    private ErrorMessages() {}

    public static final String TEAM_NAME_CANNOT_BE_BLANK = "Team names cannot be blank.";
    public static final String TEAMS_CANNOT_BE_THE_SAME = "Home and away teams cannot be the same.";
    public static final String TEAM_SCORES_CANNOT_BE_NEGATIVE = "Team scores cannot be negative values.";
    public static final String MATCH_NOT_FOUND = "Match not found.";
    public static final String TEAM_ALREADY_PLAYING = "Team: %s is already playing a match.";
    public static final String MATCH_NOT_FOUND_FAILED_ENDING = "Failed ending the match. The match does not exist.";
    public static final String INVALID_TEAM_NAME_FORMAT = "Team names must contain only letters, spaces, apostrophes, or dashes.";
}
