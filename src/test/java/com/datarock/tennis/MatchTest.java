package com.datarock.tennis;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTest {

    @Test
    public void testMatch() {
        Match match = new Match("player 1", "player 2");
        assertEquals("0-0", match.score(),
                "Initial score is zero");

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        assertEquals("0-0, 30-0", match.score(),
                "Successive wins are cumulative");

        Match newMatch = new Match("George", "Foreman");
        newMatch.pointWonBy("George");
        newMatch.pointWonBy("Foreman");
        newMatch.pointWonBy("George");
        assertEquals("0-0, 30-15", newMatch.score(),
                "Player names are matched");
    }

    @Test
    public void testExample() {
        Match match = new Match("player 1", "player 2");
        match.pointWonBy("player 1");
        match.pointWonBy("player 2");
        assertEquals("0-0, 15-15", match.score());

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        assertEquals("0-0, 40-15", match.score());

        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        assertEquals("0-0, Deuce", match.score());

        match.pointWonBy("player 1");
        assertEquals("0-0, Advantage player 1", match.score());

        match.pointWonBy("player 1");
        assertEquals("1-0", match.score());
    }

    @Test
    public void testPlayMatch() {
        assertEquals("0-0", Match.PlayMatch("p1", "p2", List.of()),
                "Initial score is zero");;
        assertEquals("1-0", Match.PlayMatch("p1", "p2", List.of("p1", "p1", "p1", "p1")),
                "Player 1 wins game");;
        assertEquals("0-0, Advantage Norris", Match.PlayMatch("Boris", "Norris",
                        List.of("Boris", "Norris", "Norris", "Boris", "Norris", "Boris", "Norris")),
                "Player 2 has advantage");;
        assertEquals("6-0", Match.PlayMatch("player 1", "player 2", Stream.generate(() -> "player 1").limit(100).collect(Collectors.toList())),
                "Player 1 wins game");;
    }
}
