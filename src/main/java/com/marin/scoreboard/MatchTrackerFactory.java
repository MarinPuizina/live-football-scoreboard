package com.marin.scoreboard;

import com.marin.scoreboard.core.MatchTracker;
import com.marin.scoreboard.sport.football.FootballMatchTracker;

public final class MatchTrackerFactory {
    private MatchTrackerFactory() {}

    public static MatchTracker createFootballMatchTracker() {
        return new FootballMatchTracker();
    }
}
