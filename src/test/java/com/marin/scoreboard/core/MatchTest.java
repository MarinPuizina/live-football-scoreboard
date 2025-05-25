package com.marin.scoreboard.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Nested
    @DisplayName("updateScore()")
    class UpdateScoreTests {
        @Test
        void should_update_home_and_away_scores() {
            Match match = new Match("", "Team A", "Team B");
            assertEquals(0, match.getHomeScore());
            assertEquals(0, match.getAwayScore());

            match.updateScore(2, 1);
            assertEquals(2, match.getHomeScore());
            assertEquals(1, match.getAwayScore());
        }
    }

}