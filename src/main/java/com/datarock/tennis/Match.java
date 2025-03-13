package com.datarock.tennis;

import java.util.List;

/**
 * A wrapper class to provide a recognisable interface to the tennis scoring system.
 */
public class Match {

    private final List<String> players;
    private final MatchScore score;

    /**
     * Convenience function for playing out an entire tennis game, point by point. Provide two
     * player names and the list of points (won by player name) to see the score. Terminates
     * when the list of points is exhausted or the match is won (first to win a set).
     *
     * @param player1 player 1 name
     * @param player2 player 2 name
     * @param pointsWonBy list of player names who won points
     * @return the score as a string
     */
    public static String PlayMatch(String player1, String player2, List<String> pointsWonBy)  {
        assert(pointsWonBy.stream().allMatch(pointWonBy -> player1.equals(pointWonBy) || player2.equals(pointWonBy))) :
            "One or more points won by player not in the match";

        List<String> players = List.of(player1, player2);
        MatchScore match = new MatchScore(players);

        for (String point : pointsWonBy) {
            match.addPoint(players, point);
            if (match.isComplete()) {
                return match.displayScore(players);
            }
        }

        return match.displayScore(players);
    }

    public Match(String player1, String player2) {
        players = List.of(player1, player2);
        score = new MatchScore(players);
    }

    public void pointWonBy(String player) {
        score.addPoint(players, player);
    }

    public String score() {
        return score.displayScore(players);
    }
}
