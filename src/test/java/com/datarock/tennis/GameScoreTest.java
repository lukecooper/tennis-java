package com.datarock.tennis;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameScoreTest {

    private final List<String> players = List.of("player 1", "player 2");

    @Test
    public void testGameScoreAddPoint() {
        assertEquals(List.of(1, 0), GameScore.Create(players, List.of(0, 0))
                .addPoint(players, "player 1")
                .getScores(players),
                "Simple add score");

        assertEquals(List.of(0, 1), GameScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 2")
                .getScores(players),
                "Players list is ignored");

        assertEquals(List.of(0, 0), GameScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 3")
                .getScores(players),
                "Ignore incorrect player point");

        assertEquals(List.of(2, 1), GameScore.Create(players, List.of(0, 0))
                .addPoint(null, "player 1")
                .addPoint(null, "player 1")
                .addPoint(null, "player 2")
                .getScores(players),
                "Successive adds are accumulated");
    }

    @Test
    public void testGameScoreIsComplete() {
        assertFalse(GameScore.Create(players, List.of(0, 1)).isComplete(),
                "Simple not complete");
        assertFalse(GameScore.Create(players, List.of(3, 0)).isComplete(),
                "Not complete under 4 points");
        assertFalse(GameScore.Create(players, List.of(4, 3)).isComplete(),
                "Not complete when difference under 2");
        assertFalse(GameScore.Create(players, List.of(5, 4)).isComplete(),
                "Not complete when at advantage");
        assertTrue(GameScore.Create(players, List.of(4, 2)).isComplete(),
                "Complete when game won no advantage");
        assertTrue(GameScore.Create(players, List.of(3, 5)).isComplete(),
                "Complete when game won after advantage");
    }

    @Test
    public void testGameScoreDisplayScore() {
        assertNull(GameScore.Create(players, List.of(0, 0)).displayScore(players),
                "No score when 0 all");
        assertEquals("15-30", GameScore.Create(players, List.of(1, 2)).displayScore(players),
                "Display game scores");
        assertEquals("40-15", GameScore.Create(players, List.of(3, 1)).displayScore(players),
                "Display game scores");
        assertEquals("Deuce", GameScore.Create(players, List.of(3, 3)).displayScore(players),
                "Display deuce");
        assertEquals("Deuce", GameScore.Create(players, List.of(4, 4)).displayScore(players),
                "Display deuce");
        assertEquals("Advantage player 1", GameScore.Create(players, List.of(4, 3)).displayScore(players),
                "Display player 1 advantage at 4-3");
        assertEquals("Advantage player 2", GameScore.Create(players, List.of(8, 9)).displayScore(players),
                "Display player 2 advantage at higher score");
    }
}
