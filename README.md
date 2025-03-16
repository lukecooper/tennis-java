# tennis-app

A Java app implementing the DataRock Tennis programming challenge.

## Requirements

Java 21 - https://adoptium.net/temurin/releases/

Maven - https://maven.apache.org/install.html

## Usage

Run the sample app from the command line:
```
$ mvn package
$ java -cp target/tennis-app-1.0-SNAPSHOT.jar com.datarock.tennis.App
```

Run tests:
```
$ mvn test
```

From a test runner:

Use the `Match.PlayMatch` static member to play out a whole match, given the current players and the winner of successive points. Example:
```
Match.PlayMatch("player 1", "player 2", List.of("player 1", "player 1", "player 2", "player 1", "player 2", "player 2", "player 1")); 
=> "0-0, Advantage player 1"
```

## Notes

- I initially implemented this solution in Clojure then ported it to Java, so any perceived strangeness in the design probably comes from that.


- I chose to implement the score keeping as a map of `name -> point`, so that any player names could be used. This means passing the player names around quite a bit, but again this comes from a Clojure design perspective (where it is  much easier).


- The current implementation can be easily extended to score a match of multiple sets, by adding a history or set scores and checking the winner of each to determine the match winner (best of `n` sets).


- Much of the Score implementations (factory methods and accessors) were only required for testing.

## Issues
- There is some duplication across the Score implementation wrt how the scoring map (of player names to points) is handled. This could be solved using either object composition or an additional interface dependency, but this seemed a little excessive for this task.


- There is too much trusting that the player list has exactly 2 players, with not enough checking. This is enforced at the top level by the Match class but the Score implementations should have additional checks.