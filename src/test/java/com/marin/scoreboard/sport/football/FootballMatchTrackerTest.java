package com.marin.scoreboard.sport.football;

import com.marin.scoreboard.core.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
                    assertThrows(IllegalArgumentException.class, () -> matchTracker.startMatch(homeTeam, awayTeam));

            assertEquals(expectedExceptionMessage, actualException.getMessage());
        }

        @Test
        void should_throw_exception_if_duplicate_team_names_are_provided() {
            matchTracker.startMatch("Team A", "Team B");

            IllegalArgumentException actualException =
                    assertThrows(IllegalArgumentException.class, () -> matchTracker.startMatch("Team A", "Team B"));

            assertEquals("The match is already in progress.", actualException.getMessage());
        }

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
            assertDoesNotThrow(() -> matchTracker.startMatch(homeTeam, awayTeam));
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
    @DisplayName("updateScore()")
    class UpdateScoreTest {

        static Stream<Arguments> updateScoreNegativeTestCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team B", -1, 0, "Team scores cannot be negative values."),
                    Arguments.of("Team A", "Team B", 0, -1, "Team scores cannot be negative values."),
                    Arguments.of("Team A", "Team A", 1, 1, "Home and away teams cannot be the same."),
                    Arguments.of("", "Team B", 1, 1, "Team names cannot be blank."),
                    Arguments.of("Team A", "", 1, 1, "Team names cannot be blank."),
                    Arguments.of("Team C", "Team D", 1, 1, "Match not found.")
            );
        }

        @ParameterizedTest
        @MethodSource("updateScoreNegativeTestCases")
        void should_throw_exception_for_invalid_update_score(String homeTeam,
                                                             String awayTeam,
                                                             int homeScore,
                                                             int awayScore,
                                                             String expectedErrorMessage) {

            matchTracker.startMatch("Team A", "Team B");

            IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                    () -> matchTracker.updateScore(homeTeam, awayTeam, homeScore, awayScore));

            assertEquals(expectedErrorMessage, actualException.getMessage());
        }

        static Stream<Arguments> updateScoreTestCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team B", 2, 1),
                    Arguments.of("Real Madrid", "Barcelona", 3, 3),
                    Arguments.of("Manchester United", "Liverpool", 0, 2),
                    Arguments.of("PSG", "Bayern Munich", 1, 0)
            );
        }

        @ParameterizedTest
        @MethodSource("updateScoreTestCases")
        void should_update_score_for_valid_match(String homeTeam, String awayTeam, int homeScore, int awayScore) {
            matchTracker.startMatch(homeTeam, awayTeam);

            assertDoesNotThrow(() -> matchTracker.updateScore(homeTeam, awayTeam, homeScore, awayScore));

            assertEquals(homeScore, matchTracker.getSummary().getFirst().getHomeScore());
            assertEquals(awayScore, matchTracker.getSummary().getFirst().getAwayScore());
        }
    }

    @Nested
    @DisplayName("findMatch()")
    class FindMatchTest {

        static Stream<Arguments> findMatchNegativeTestCases() {
            return Stream.of(
                    Arguments.of("Team C", "Team D", "Match not found."),
                    Arguments.of("Non-existent", "Team", "Match not found."),
                    Arguments.of("Team A", "Non-existent", "Match not found.")
            );
        }

        @ParameterizedTest
        @MethodSource("findMatchNegativeTestCases")
        void should_throw_exception_when_match_not_found(String homeTeam, String awayTeam, String expectedErrorMessage) {
            matchTracker.startMatch("Team A", "Team B");

            IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                    () -> matchTracker.findMatch(homeTeam, awayTeam));

            assertEquals(expectedErrorMessage, actualException.getMessage());
        }

        static Stream<Arguments> findMatchPositiveTestCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team B"),
                    Arguments.of("Real Madrid", "Barcelona"),
                    Arguments.of("Manchester United", "Liverpool")
            );
        }

        @ParameterizedTest
        @MethodSource("findMatchPositiveTestCases")
        void should_find_match_for_valid_teams(String homeTeam, String awayTeam) {
            matchTracker.startMatch(homeTeam, awayTeam);

            assertDoesNotThrow(() -> matchTracker.findMatch(homeTeam, awayTeam));
        }
    }

    @Nested
    @DisplayName("getSummary()")
    class GetSummaryTest {
        @Test
        void should_return_results_sorted_by_high_score_and_creation() {
            matchTracker.startMatch("Mexico", "Canada");
            matchTracker.updateScore("Mexico", "Canada", 0, 5);
            matchTracker.startMatch("Spain", "Brazil");
            matchTracker.updateScore("Spain", "Brazil", 10, 2);
            matchTracker.startMatch("Germany", "France");
            matchTracker.updateScore("Germany", "France", 2, 2);
            matchTracker.startMatch("Uruguay", "Italy");
            matchTracker.updateScore("Uruguay", "Italy", 6, 6);
            matchTracker.startMatch("Argentina", "Australia");
            matchTracker.updateScore("Argentina", "Australia", 3, 1);
            matchTracker.startMatch("Croatia", "Norway");
            matchTracker.updateScore("Croatia", "Norway", 6, 6);

            List<Match> summary = matchTracker.getSummary();

            assertEquals(6, summary.size());

            assertEquals("Croatia", summary.get(0).getHomeTeam());
            assertEquals("Norway", summary.get(0).getAwayTeam());

            assertEquals("Uruguay", summary.get(1).getHomeTeam());
            assertEquals("Italy", summary.get(1).getAwayTeam());

            assertEquals("Spain", summary.get(2).getHomeTeam());
            assertEquals("Brazil", summary.get(2).getAwayTeam());

            assertEquals("Mexico", summary.get(3).getHomeTeam());
            assertEquals("Canada", summary.get(3).getAwayTeam());

            assertEquals("Argentina", summary.get(4).getHomeTeam());
            assertEquals("Australia", summary.get(4).getAwayTeam());

            assertEquals("Germany", summary.get(5).getHomeTeam());
            assertEquals("France", summary.get(5).getAwayTeam());
        }

        @Test
        void should_not_return_null() {
            assertNotNull(matchTracker.getSummary());
        }
    }

    @Nested
    @DisplayName("endMatch()")
    class EndMatchTest {

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
        void should_end_match_for_valid_teams(String homeTeam, String awayTeam) {
            matchTracker.startMatch(homeTeam, awayTeam);
            assertDoesNotThrow(() -> matchTracker.endMatch(homeTeam, awayTeam));
        }

        static Stream<Arguments> invalidTeamNamesCases() {
            return Stream.of(
                    Arguments.of("Team A", "Team A", "Home and away teams cannot be the same."),
                    Arguments.of("", "Team B", "Team names cannot be blank."),
                    Arguments.of("Team A", "", "Team names cannot be blank."),
                    Arguments.of(" ", "Team B", "Team names cannot be blank."),
                    Arguments.of("Team A", " ", "Team names cannot be blank."),
                    Arguments.of(null, "Team B", "Team names cannot be blank."),
                    Arguments.of("Team A", null, "Team names cannot be blank.")
            );
        }

        @ParameterizedTest
        @MethodSource("invalidTeamNamesCases")
        void should_throw_exception_for_invalid_team_names(String homeTeam, String awayTeam, String expectedErrorMessage) {
            IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                    () -> matchTracker.endMatch(homeTeam, awayTeam));

            assertEquals(expectedErrorMessage, actualException.getMessage());
        }

        @Test
        void should_throw_exception_when_ending_non_existent_match() {
            String homeTeam = "Non-existent Team A";
            String awayTeam = "Non-existent Team B";

            IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                    () -> matchTracker.endMatch(homeTeam, awayTeam));

            assertEquals("Failed ending the match. The match does not exist.", actualException.getMessage());
        }

        @Test
        void should_remove_match_from_summary() {
            matchTracker.startMatch("Team A", "Team B");
            matchTracker.endMatch("Team A", "Team B");
            List<Match> summary = matchTracker.getSummary();

            assertEquals(0, summary.size());
        }
    }
}