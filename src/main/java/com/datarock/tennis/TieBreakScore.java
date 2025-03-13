package com.datarock.tennis;

import java.util.*;

/**
 * A special game scoring system for a tennis match that is played when a set
 * is tied at 6-6. A tiebreak is won when a player has won at least 7 points
 * and is 2 points ahead of the opponent.
 */
public class TieBreakScore implements Score {

    private final Map<String, Integer> score = new HashMap<>();

    public static TieBreakScore Create(List<String> players, List<Integer> scores) {
        TieBreakScore tieBreakScore = new TieBreakScore(players);
        for (int i = 0; i < players.size() && i < scores.size(); i++) {
            tieBreakScore.score.put(players.get(i), scores.get(i));
        }
        return tieBreakScore;
    }

    public TieBreakScore(List<String> players) {
        players.forEach(p -> score.put(p, 0));
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
        return null;
    }

    @Override
    public Score addPoint(List<String> players, String pointScoredBy) {
        if (score.containsKey(pointScoredBy)) {
            score.put(pointScoredBy, score.get(pointScoredBy) + 1);
        }

        return this;
    }

    @Override
    public boolean isComplete() {
        List<Integer> scores = score.values().stream().sorted().toList();
        Integer min = scores.getFirst();
        Integer max = scores.getLast();
        return ((max >= 7) && ((max - min) >= 2));
    }

    @Override
    public String displayScore(List<String> players) {
        Integer p1Score = score.get(players.getFirst());
        Integer p2Score = score.get(players.getLast());

        if ((p1Score == 0) && (p2Score == 0)) {
            return null;
        }
        return String.format("%d-%d", p1Score, p2Score);
    }
}
