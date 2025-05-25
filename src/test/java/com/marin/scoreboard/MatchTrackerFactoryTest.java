package com.marin.scoreboard;

import com.marin.scoreboard.core.MatchTracker;
import com.marin.scoreboard.sport.football.FootballMatchTracker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTrackerFactoryTest {

    @Nested
    @DisplayName("createFootballMatchTracker()")
    class CreateFootballMatchTrackerTests {
        @Test
        void should_return_FootballMatchTracker_instance() {
            MatchTracker footballMatchTracker = MatchTrackerFactory.createFootballMatchTracker();
            assertNotNull(footballMatchTracker);
            assertInstanceOf(FootballMatchTracker.class, footballMatchTracker);
        }
    }
}