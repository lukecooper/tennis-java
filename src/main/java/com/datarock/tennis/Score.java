package com.datarock.tennis;

import java.util.List;

/**
 * Score interface for tennis match scoring.
 */
public interface Score {

    /**
     * Provides the current scores for the given players, in the order that they
     * are provided in `players`.
     *
     * @param players the players whose scores are being queries
     * @return a list of scores
     */
    List<Integer> getScores(List<String> players);

    /**
     * Returns the nested score component, if one exists.
     *
     * @return the nested score or null
     */
    Score getNestedScore();

    /**
     * Adds a point scored by a player. Requires the list of players to record this.
     *
     * @param players The list of players in the match
     * @param pointScoredBy The player who scored the point
     * @return this
     */
    Score addPoint(List<String> players, String pointScoredBy);

    /**
     * Is this score component complete?
     *
     * @return true if complete, false otherwise
     */
    boolean isComplete();

    /**
     * Returns the score component as a string, or null if no score available.
     *
     * @param players The list of players in the match
     * @return the score as a string
     */
    String displayScore(List<String> players);
}
