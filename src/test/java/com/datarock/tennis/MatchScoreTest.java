package com.datarock.tennis;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreTest {

    private final List<String> players = List.of("player 1", "player 2");

    private MatchScore createScore(List<String> players, List<Integer> scores, List<Integer> setScores, List<Integer> gameScores) {
        return MatchScore.Create(players, scores,
                SetScore.Create(players, setScores,
                        GameScore.Create(players, gameScores)));
    }

    @Test
    public void testMatchScoreAddPoint() {
        assertEquals(List.of(1, 0), createScore(players, List.of(0, 0), List.of(0, 0), List.of(0, 0))
                        .addPoint(players, "player 1")
                        .getNestedScore()
                        .getNestedScore()
                        .getScores(players),
                "Point is added to game within");
        assertEquals(List.of(1, 0), createScore(players, List.of(0, 0), List.of(0, 0), List.of(3, 2))
                        .addPoint(players, "player 1")
                        .getNestedScore()
                        .getScores(players),
                "Player 1 wins a set");
        assertEquals(List.of(0, 1), createScore(players, List.of(0, 0), List.of(4, 5), List.of(4, 5))
                        .addPoint(players, "player 2")
                        .getScores(players),
                "Player 2 wins a match");
    }

    @Test
    public void testMatchScoreIsComplete() {
        assertFalse(createScore(players, List.of(0, 0), List.of(0, 0), List.of(0, 0)).isComplete(),
                "No points scored");
        assertFalse(createScore(players, List.of(0, 0), List.of(0, 0), List.of(4, 2)).isComplete(),
                "Only game points scored");
        assertFalse(createScore(players, List.of(0, 0), List.of(4, 5), List.of(0, 0)).isComplete(),
                "Only set points scored");
        assertTrue(createScore(players, List.of(1, 0), List.of(4, 5), List.of(100, -1)).isComplete(),
                "Single match point scored");
    }

    @Test
    public void testMatchScoreDisplayScore() {
        assertEquals("4-5", createScore(players, List.of(0, 0), List.of(4, 5), List.of(0, 0)).displayScore(players),
                "No game score displayed when 0 game points");
        assertEquals("6-6, Advantage player 1", createScore(players, List.of(0, 0), List.of(6, 6), List.of(4, 3)).displayScore(players),
                "Normal score");
    }
}
