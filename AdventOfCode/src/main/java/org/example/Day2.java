package org.example;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

import static org.example.Day2.Move.*;
import static org.example.Day2.Result.*;
import static org.example.FileReader.reading;

public class Day2 {
    private final Map<Character, Move> opponentMoveMap = Map.of('A', ROCK, 'B', PAPER, 'C', SCISSORS);
    private final Map<Character, Move> myMoveMap = Map.of('X', ROCK, 'Y', PAPER, 'Z', SCISSORS);
    private final Map<Character, Result> resultMap = Map.of('X', LOSS, 'Y', DRAW, 'Z', WIN);

    public static void main(String[] args) {
        AtomicInteger totalScore = new AtomicInteger();
        reading("Day2.txt",
                lines -> lines.mapToInt(new Day2().part2Sum())
                        .forEach(totalScore::addAndGet));
        System.out.println(totalScore.get());
    }

    @SuppressWarnings("unused")
    private ToIntFunction<String> part1Sum() {
        return s -> {
            String[] content = s.split(" ");
            Move theirs = opponentMoveMap.get(content[0].charAt(0));
            Move mine = myMoveMap.get(content[1].charAt(0));
            return Score.get(mine, theirs);
        };
    }

    @SuppressWarnings("unused")
    private ToIntFunction<String> part2Sum() {
        return s -> {
            String[] content = s.split(" ");
            Move theirs = opponentMoveMap.get(content[0].charAt(0));
            Result result = resultMap.get(content[1].charAt(0));
            Move mine = result.resolve(theirs);
            return Score.get(mine, theirs);
        };
    }

    static class Score {
        private static final Map<Move, Integer> scoreLookup = Map.of(ROCK, 1, PAPER, 2, SCISSORS, 3);

        public static int get(Move mine, Move theirs) {
            return scoreLookup.get(mine) + winLossResult(mine, theirs);
        }

        private static int winLossResult(Move mine, Move theirs) {
            return mine.beats(theirs) ? 6
                    : theirs.beats(mine) ? 0 : 3;
        }
    }

    enum Move {
        ROCK,
        PAPER,
        SCISSORS,
        ;

        public boolean beats(Move other) {
            return switch (this) {
                case ROCK -> other == SCISSORS;
                case PAPER -> other == ROCK;
                case SCISSORS -> other == PAPER;
            };
        }
    }

    enum Result {
        WIN,
        LOSS,
        DRAW,
        ;

        public Move resolve(Move theirs) {
            if (this == DRAW) {
                return theirs;
            }
            Move mine = winningMoveAgainst(theirs);
            return this == WIN ? mine : winningMoveAgainst(mine);
        }

        private static Move winningMoveAgainst(Move theirs) {
            return switch (theirs) {
                case ROCK -> PAPER;
                case PAPER -> SCISSORS;
                case SCISSORS -> ROCK;
            };
        }
    }
}
