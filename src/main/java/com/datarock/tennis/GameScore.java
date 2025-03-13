package com.datarock.tennis;

import java.util.*;

/**
 * Scoring system for a single game in a tennis match. A game is won when
 * a player wins at least 4 points and is 2 points ahead of the opponent.
 */
public class GameScore implements Score {

    private final Map<String, Integer> score = new HashMap<>();

    public static GameScore Create(List<String> players, List<Integer> scores) {
        GameScore gameScore = new GameScore(players);
        for (int i = 0; i < players.size() && i < scores.size(); i++) {
            gameScore.score.put(players.get(i), scores.get(i));
        }
        return gameScore;
    }

    public GameScore(List<String> players) {
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
        return ((max >= 4) && ((max - min) >= 2));
    }

    @Override
    public String displayScore(List<String> players) {
        Integer p1Score = score.get(players.getFirst());
        Integer p2Score = score.get(players.getLast());
        Map<Integer, String> pointLookup = Map.of(0, "0", 1, "15", 2, "30", 3, "40");

        if ((p1Score == 0) && (p2Score == 0)) {
            return null;
        }
        if (p1Score < 3 || p2Score < 3) {
            return String.format("%s-%s", pointLookup.get(p1Score), pointLookup.get(p2Score));
        }
        if (p1Score > p2Score) {
            return String.format("Advantage %s", players.getFirst());
        }
        if (p1Score < p2Score) {
            return String.format("Advantage %s", players.getLast());
        }
        return "Deuce";
    }
}
