package com.marin.scoreboard.core;

import java.util.List;

public interface MatchTracker {
    void startMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    void endMatch(Match match);

    List<Match> getSummary();
}
