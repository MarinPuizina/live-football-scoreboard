package com.marin.scoreboard.core;

import com.marin.scoreboard.sport.football.FootballMatch;

import java.util.Map;

public interface MatchTracker {
    void startMatch(String homeTeam, String awayTeam);
    void updateScore(Match match, String team, int score);
    void endMatch(Match match);
    Map<String, FootballMatch> getSummaryOfMatches();
}
