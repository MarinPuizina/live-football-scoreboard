package com.marin.scoreboard.sport.football;

import com.marin.scoreboard.sport.football.FootballMatch;
import com.marin.scoreboard.sport.football.FootballMatchTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FootballMatchTrackerTest {

    private FootballMatchTracker matchTracker;

    @BeforeEach
    void setUp() {
        matchTracker = new FootballMatchTracker();
    }

    @Nested
    @DisplayName("startMatch()")
    class StartMatchTest {

        @Test
        void should_throw_exception_if_duplicate_team_names_are_provided() {
            matchTracker.startMatch("Team A", "Team B");

            IllegalArgumentException actualException =
                    assertThrows(IllegalArgumentException.class, () -> matchTracker.startMatch("Team A", "Team B"));

            assertEquals("The match is already in progress.", actualException.getMessage());
        }

        @Test
        void should_not_throw_exception() {
            assertDoesNotThrow(() -> matchTracker.startMatch("Team A", "Team B"));

            matchTracker.startMatch("Team C", "Team D");

            Map<String, FootballMatch> summaryOfMatches = matchTracker.getSummaryOfMatches();

            System.out.println(summaryOfMatches);
        }

    }

    @Nested
    @DisplayName("createMatchId()")
    class CreateMatchIdTest {

        static Stream<Arguments> createMatchIdTestCases() {
            return Stream.of(
                    Arguments.of(" Real Madrid ", "Barcelona  ", "real madrid vs barcelona"),
                    Arguments.of("MANCHESTER UNITED", "Liverpool", "manchester united vs liverpool"),
                    Arguments.of("  PSG  ", "  Bayern Munich  ", "psg vs bayern munich"),
                    Arguments.of("  Croatia  ", "  Norway  ", "croatia vs norway"),
                    Arguments.of("  Norway  ", "  Poland  ", "norway vs poland")
            );
        }

        @ParameterizedTest
        @MethodSource("createMatchIdTestCases")
        void should_create_match_id(String homeTeam, String awayTeam, String expectedMatchId) {
            String actualMatchId = matchTracker.createMatchId(homeTeam, awayTeam);
            assertEquals(expectedMatchId, actualMatchId);
        }


        static Stream<Arguments> createMatchIdNegativeTestCases() {
            return Stream.of(
                    Arguments.of(null, "Team B", NullPointerException.class),
                    Arguments.of("Team A", null, NullPointerException.class),
                    Arguments.of(null, null, NullPointerException.class)
            );
        }

        @ParameterizedTest
        @MethodSource("createMatchIdNegativeTestCases")
        void should_fail_and_throw_exception(String homeTeam, String awayTeam, Class<? extends Throwable> expectedException) {
            assertThrows(expectedException, () -> matchTracker.createMatchId(homeTeam, awayTeam));
        }
    }

    @Nested
    @DisplayName("validateTeamNames()")
    class ValidateTeamNamesTests {
        static Stream<Arguments> validTeamNamesCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team B"),
                    Arguments.of("Real Madrid", "Barcelona"),
                    Arguments.of("Manchester United", "Liverpool"),
                    Arguments.of("PSG", "Bayern Munich")
            );
        }

        @ParameterizedTest
        @MethodSource("validTeamNamesCases")
        void should_not_throw_exception(String homeTeam, String awayTeam) {
            assertDoesNotThrow(() -> matchTracker.validateTeamNames(homeTeam, awayTeam));
        }

        static Stream<Arguments> invalidTeamNamesCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team A", "Home and away teams cannot be the same."),
                    Arguments.of("team a", "TEAM A", "Home and away teams cannot be the same."),
                    Arguments.of("", "Team B", "Team names cannot be blank."),
                    Arguments.of("Team A", "", "Team names cannot be blank."),
                    Arguments.of(" ", "Team B", "Team names cannot be blank."),
                    Arguments.of("Team A", " ", "Team names cannot be blank."),
                    Arguments.of(null, "TeamB", "Team names cannot be blank."),
                    Arguments.of("TeamA", null, "Team names cannot be blank."),
                    Arguments.of("", "TeamB", "Team names cannot be blank."),
                    Arguments.of("TeamA", "", "Team names cannot be blank."),
                    Arguments.of(null, null, "Team names cannot be blank."),
                    Arguments.of("", "", "Team names cannot be blank.")
            );
        }

        @ParameterizedTest
        @MethodSource("invalidTeamNamesCases")
        void should_throw_exception_with_proper_message(String homeTeam, String awayTeam, String expectedExceptionMessage) {
            IllegalArgumentException actualException =
                    assertThrows(IllegalArgumentException.class, () -> matchTracker.validateTeamNames(homeTeam, awayTeam));

            assertEquals(expectedExceptionMessage, actualException.getMessage());
        }
    }
}