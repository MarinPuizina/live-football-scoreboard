package com.marin.scoreboard.core;

import java.util.StringJoiner;

public class Match {

    private final String id;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(final String id,
                 final String homeTeam,
                 final String awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public String getId() {
        return id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void updateScore(final int homeScore, final int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Match.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("homeTeam='" + homeTeam + "'")
                .add("awayTeam='" + awayTeam + "'")
                .add("homeScore=" + homeScore)
                .add("awayScore=" + awayScore)
                .toString();
    }
}
