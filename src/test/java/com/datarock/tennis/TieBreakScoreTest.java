package com.datarock.tennis;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TieBreakScoreTest {

    private final List<String> players = List.of("player 1", "player 2");

    @Test
    public void testTieBreakScoreAddPoint() {
        assertEquals(List.of(1, 0), TieBreakScore.Create(players, List.of(0, 0))
                .addPoint(players, "player 1")
                .getScores(players),
                "Simple add score");

        assertEquals(List.of(0, 1), TieBreakScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 2")
                .getScores(players),
                "Players list is ignored");

        assertEquals(List.of(0, 0), TieBreakScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 3")
                .getScores(players),
                "Ignore incorrect player point");

        assertEquals(List.of(2, 1), TieBreakScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 1")
                .addPoint(null, "player 1")
                .addPoint(null, "player 2")
                .getScores(players),
                "Successive adds are accumulated");
    }

    @Test
    public void testTieBreakScoreIsComplete() {
        assertFalse(TieBreakScore.Create(players, List.of(0, 1)).isComplete(),
                "Simple not complete");
        assertFalse(TieBreakScore.Create(players, List.of(6, 0)).isComplete(),
                "Not complete under 7 points");
        assertFalse(TieBreakScore.Create(players, List.of(9, 8)).isComplete(),
                "Not complete when difference under 2");
        assertFalse(TieBreakScore.Create(players, List.of(6, 7)).isComplete(),
                "Not complete when at advantage");
        assertFalse(TieBreakScore.Create(players, List.of(8, 7)).isComplete(),
                "Not complete when at advantage");
        assertTrue(TieBreakScore.Create(players, List.of(10, 8)).isComplete(),
                "Complete when game won no advantage");
        assertTrue(TieBreakScore.Create(players, List.of(7, 5)).isComplete(),
                "Complete when game won after advantage");
    }

    @Test
    public void testTieBreakScoreDisplayScore() {
        assertNull(TieBreakScore.Create(players, List.of(0, 0)).displayScore(players),
                "No score when 0 all");
        assertEquals("1-2", TieBreakScore.Create(players, List.of(1, 2)).displayScore(players),
                "Display game scores");
        assertEquals("10-8", TieBreakScore.Create(players, List.of(10, 8)).displayScore(players),
                "Display game scores");
    }
}
