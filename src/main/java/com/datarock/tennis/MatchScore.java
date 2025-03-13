package com.datarock.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A modified scoring system for a whole tennis match. A match is usually played with
 * multiple sets, each with multiple games. In this implementation, the first player
 * to win a set wins the match.
 */
public class MatchScore implements Score {

    private final Map<String, Integer> score = new HashMap<>();
    private Score setScore;

    public static MatchScore Create(List<String> players, List<Integer> scores, Score setScore) {
        MatchScore matchScore = new MatchScore(players);
        for (int i = 0; i < players.size() && i < scores.size(); i++) {
            matchScore.score.put(players.get(i), scores.get(i));
        }
        matchScore.setScore = setScore;
        return matchScore;
    }

    public MatchScore(List<String> players) {
        players.forEach(p -> score.put(p, 0));
        setScore = new SetScore(players);
    }

    @Override
    public List<Integer> getScores(List<String> players) {
        List<Integer> scores = new ArrayList<>();
        players.forEach(p -> scores.add(score.getOrDefault(p, 0)));
        return scores;
    }

    @Override
    public Score getNestedScore() {
        return setScore;
    }

    @Override
    public Score addPoint(List<String> players, String pointScoredBy) {
        setScore.addPoint(players, pointScoredBy);
        if (setScore.isComplete()) {

            // add sets points
            if (score.containsKey(pointScoredBy)) {
                score.put(pointScoredBy, score.get(pointScoredBy) + 1);
            }
        }

        return this;
    }

    @Override
    public boolean isComplete() {
        return score.values().stream().anyMatch(n -> n > 0);
    }

    @Override
    public String displayScore(List<String> players) {
        Score gameScore = setScore.getNestedScore();
        String gameScoreDisplay = gameScore.displayScore(players);

        if (gameScoreDisplay == null) {
            return setScore.displayScore(players);
        }
        return String.format("%s, %s", setScore.displayScore(players), gameScoreDisplay);
    }
}
