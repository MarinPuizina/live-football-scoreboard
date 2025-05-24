package com.marin.scoreboard.core;

import java.util.StringJoiner;

public class Match {

    private final String id;
    private final String homeTeamName;
    private final String awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;

    public Match(final String id,
                 final String homeTeamName,
                 final String awayTeamName) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    public String getId() {
        return id;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void updateScore(final int homeTeamScore, final int awayTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Match.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("homeTeamName='" + homeTeamName + "'")
                .add("awayTeamName='" + awayTeamName + "'")
                .add("homeTeamScore=" + homeTeamScore)
                .add("awayTeamScore=" + awayTeamScore)
                .toString();
    }
}
