package com.datarock.tennis;

import java.util.*;

/**
 * A scoring system for a single set in a tennis match. A set consists of multiple games
 * and a point is scored for each game won. A set is won when a player wins at least 6 points
 * and is 2 points ahead of the opponent.
 * <p>
 * If points are tied at 6-6 then a tiebreak game is played, with the winner of that game
 * winning the set.
 */
public class SetScore implements Score {

    private final Map<String, Integer> score = new HashMap<>();
    private Score gameScore;

    public static SetScore Create(List<String> players, List<Integer> scores, Score gameScore) {
        SetScore setScore = new SetScore(players);
        for (int i = 0; i < players.size() && i < scores.size(); i++) {
            setScore.score.put(players.get(i), scores.get(i));
        }
        setScore.gameScore = gameScore;
        return setScore;
    }

    public SetScore(List<String> players) {
        players.forEach(p -> score.put(p, 0));
        gameScore = new GameScore(players);
    }

    public boolean isTieBreak() {
        List<Integer> scores = score.values().stream().sorted().toList();
        Integer min = scores.getFirst();
        Integer max = scores.getLast();

        return (min == 6 && max == 6);
    }

    @Override
    public List<Integer> getScores(List<String> players) {
        List<Integer> scores = new ArrayList<>();
        for (String player : players) {
            scores.add(score.get(player));
        }
        return scores;
    }

    @Override
    public Score getNestedScore() {
        return gameScore;
    }

    @Override
    public Score addPoint(List<String> players, String pointScoredBy) {
        gameScore.addPoint(players, pointScoredBy);
        if (gameScore.isComplete()) {

            // add set pointScoredBy
            if (score.containsKey(pointScoredBy)) {
                score.put(pointScoredBy, score.get(pointScoredBy) + 1);
            }

            // create new game with zero score
            gameScore = isTieBreak() ? new TieBreakScore(players) : new GameScore(players);
        }

        return this;
    }

    @Override
    public boolean isComplete() {
        List<Integer> scores = score.values().stream().sorted().toList();
        Integer min = scores.getFirst();
        Integer max = scores.getLast();

        return (((max >= 6) && ((max - min) >= 2)) ||
                (max >= 7 && gameScore.isComplete()));
    }

    @Override
    public String displayScore(List<String> players) {
        return String.format("%d-%d", score.get(players.getFirst()), score.get(players.getLast()));
    }
}
