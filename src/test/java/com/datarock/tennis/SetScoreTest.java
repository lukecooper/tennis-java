package com.datarock.tennis;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SetScoreTest {

    private final List<String> players = List.of("player 1", "player 2");

    private SetScore createWithGameScore(List<String> players, List<Integer> scores, List<Integer> gameScores) {
        return SetScore.Create(players, scores, GameScore.Create(players, gameScores));
    }

    private SetScore createWithTieBreakScore(List<String> players, List<Integer> scores, List<Integer> gameScores) {
        return SetScore.Create(players, scores, TieBreakScore.Create(players, gameScores));
    }

    @Test
    public void testTieBreakCheck() {
        assertFalse(createWithGameScore(players, List.of(0, 0), List.of(0, 0)).isTieBreak());
        assertFalse(createWithGameScore(players, List.of(3, 2), List.of(0, 0)).isTieBreak());
        assertFalse(createWithGameScore(players, List.of(6, 4), List.of(0, 0)).isTieBreak());
        assertTrue(createWithGameScore(players, List.of(6, 6), List.of(0, 0)).isTieBreak());
        assertFalse(createWithGameScore(players, List.of(7, 6), List.of(0, 0)).isTieBreak());
        assertFalse(createWithGameScore(players, List.of(8, 6), List.of(0, 0)).isTieBreak());
    }

    @Test
    public void testSetScoreAddPoint() {
        assertEquals(List.of(1, 0), createWithGameScore(players, List.of(0, 0), List.of(0, 0))
                        .addPoint(players, "player 1")
                        .getNestedScore()
                        .getScores(players),
                "Point is added to game within");
        assertEquals(List.of(1, 0), createWithGameScore(players, List.of(0, 0), List.of(4, 3))
                        .addPoint(players, "player 1")
                        .getScores(players),
                "Winner of game wins set point");
        assertEquals(List.of(0, 1), createWithGameScore(players, List.of(0, 0), List.of(4, 5))
                        .addPoint(players, "player 2")
                        .getScores(players),
                "Player 2 wins set point");
        assertEquals(List.of(6, 6), createWithGameScore(players, List.of(6, 5), List.of(4, 5))
                        .addPoint(players, "player 2")
                        .getScores(players),
                "Player 2 wins game point forcing tiebreak");
        assertInstanceOf(TieBreakScore.class, createWithGameScore(players, List.of(6, 5), List.of(4, 5))
                        .addPoint(players, "player 2")
                        .getNestedScore(),
                "Forced tiebreak results in correct game type");
    }

    @Test
    public void testSetScoreIsComplete() {
        assertFalse(createWithGameScore(players, List.of(0, 0), List.of(0, 0)).isComplete(),
                "Not complete");
        assertFalse(createWithGameScore(players, List.of(5, 3), List.of(0, 0)).isComplete(),
                "Less than 6 games won");
        assertFalse(createWithGameScore(players, List.of(6, 6), List.of(0, 0)).isComplete(),
                "6 games won each");
        assertFalse(createWithGameScore(players, List.of(7, 6), List.of(0, 0)).isComplete(),
                "Tiebreak 1 point ahead");
        assertTrue(createWithGameScore(players, List.of(6, 4), List.of(0, 0)).isComplete(),
                "Player 1 won by 2 points");
        assertTrue(createWithGameScore(players, List.of(2, 6), List.of(0, 0)).isComplete(),
                "Player 2 won by 4 points");
        assertFalse(createWithGameScore(players, List.of(7, 6), List.of(0, 0)).isComplete(),
                "No winner if incorrect game score");
        assertFalse(createWithTieBreakScore(players, List.of(7, 6), List.of(5, 4)).isComplete(),
                "No winner if tiebreak not yet won");
        assertTrue(createWithTieBreakScore(players, List.of(7, 6), List.of(7, 5)).isComplete(),
                "Player 1 wins tie break");
        assertTrue(createWithTieBreakScore(players, List.of(6, 7), List.of(42, 45)).isComplete(),
                "Player 2 wins marathon tie break");
    }

    @Test
    public void testSetScoreDisplayScore() {
        assertEquals("0-0", createWithGameScore(players, List.of(0, 0), List.of(0, 0)).displayScore(players),
                "Zero score stills display score");
        assertEquals("4-5", createWithGameScore(players, List.of(4, 5), List.of(0, 0)).displayScore(players),
                "Score is displayed");
        assertEquals("5-4", createWithGameScore(players, List.of(5, 4), List.of(0, 0)).displayScore(players),
                "Score order is preserved");
        assertEquals("5-4", createWithGameScore(players, List.of(4, 5), List.of(0, 0)).displayScore(List.of("player 2", "player 1")),
                "Reversing player order reverses score order");
    }
}
