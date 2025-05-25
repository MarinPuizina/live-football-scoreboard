package com.marin.scoreboard.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatchValidatorTest {

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
            assertDoesNotThrow(() -> MatchValidator.validateTeamNames(homeTeam, awayTeam));
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
                    assertThrows(IllegalArgumentException.class, () -> MatchValidator.validateTeamNames(homeTeam, awayTeam));

            assertEquals(expectedExceptionMessage, actualException.getMessage());
        }
    }

    @Nested
    @DisplayName("validateTeamScores()")
    class ValidateTeamScoresTests {
        static Stream<Arguments> validScoresCases() {
            return Stream.of(
                    Arguments.of(0, 0),
                    Arguments.of(1, 0),
                    Arguments.of(0, 1),
                    Arguments.of(5, 3),
                    Arguments.of(10, 10)
            );
        }

        @ParameterizedTest
        @MethodSource("validScoresCases")
        void should_not_throw_exception(int homeScore, int awayScore) {
            assertDoesNotThrow(() -> MatchValidator.validateTeamScores(homeScore, awayScore));
        }

        static Stream<Arguments> invalidScoresCases() {
            return Stream.of(
                    Arguments.of(-1, 0, "Team scores cannot be negative values."),
                    Arguments.of(0, -1, "Team scores cannot be negative values."),
                    Arguments.of(-1, -1, "Team scores cannot be negative values.")
            );
        }

        @ParameterizedTest
        @MethodSource("invalidScoresCases")
        void should_throw_exception_with_proper_message(int homeScore, int awayScore, String expectedExceptionMessage) {
            IllegalArgumentException actualException =
                    assertThrows(IllegalArgumentException.class, () -> MatchValidator.validateTeamScores(homeScore, awayScore));

            assertEquals(expectedExceptionMessage, actualException.getMessage());
        }
    }
}