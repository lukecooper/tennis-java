package com.datarock.tennis;

import java.util.List;

public class App {

    public static void main(String[] args) {

        // this runs the example given in the problem outline
        System.out.println("-- first match");
        Match match = new Match("player 1", "player 2");
        match.pointWonBy("player 1");
        match.pointWonBy("player 2");
        System.out.println(match.score());

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        System.out.println(match.score());

        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        System.out.println(match.score());

        match.pointWonBy("player 1");
        System.out.println(match.score());

        match.pointWonBy("player 1");
        System.out.println(match.score());

        // this runs using the match runner
        System.out.println("-- next match");
        System.out.println(
                Match.PlayMatch("player 1", "player 2",
                        List.of("player 1", "player 2", "player 1", "player 1", "player 2", "player 2", "player 1", "player 1")));
    }
}