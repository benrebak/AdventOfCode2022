package org.example.day5;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.*;
import static org.example.FileReader.reading;

public class Day5 {
    public static void main(String[] args) {
        reading("Day5.txt", lines -> {
            List<String> fileContents = lines.toList();
            List<String> diagram = new ArrayList<>(fileContents.stream()
                    .takeWhile(s -> !s.isEmpty())
                    .toList());

            Map<String, Deque<Character>> stacks = buildStacksFrom(diagram);
            Map<String, Deque<Character>> stacks9001 = copy(stacks);
            logState(stacks);

            List<Move> moveSet = fileContents.stream()
                    .dropWhile(s -> !s.startsWith("move "))
                    .map(Day5::parseMove)
                    .toList();

            moveSet.forEach(instruction -> instruction.apply(stacks));
            logResult(stacks);

            moveSet.forEach(instruction -> instruction.apply9001(stacks9001));
            logResult(stacks9001);
        });
    }

    static Map<String, Deque<Character>> copy(Map<String, Deque<Character>> input) {
        return input.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, characters -> new ArrayDeque<>(characters.getValue())));
    }

    private static void logResult(Map<String, Deque<Character>> stacks) {
        System.out.println("\n");
        logState(stacks);
        System.out.println(stacks.values().stream()
                .map(crates -> String.valueOf(crates.peek()))
                .collect(joining()));
    }

    private static Map<String, Deque<Character>> buildStacksFrom(List<String> diagram) {
        String legend = diagram.remove(diagram.size() - 1);
        Map<String, Deque<Character>> stacks = new HashMap<>();
        Map<String, Integer> stackPositionsById = getStackPositions(legend);
        diagram.forEach(level ->
                stackPositionsById
                        .forEach((stackId, position) -> {
                            if (level.length() >= position) {
                                char c = level.charAt(position);
                                if (!valueOf(c).isBlank()) {
                                    stacks.computeIfAbsent(stackId, key -> new ArrayDeque<>())
                                            .addLast(c);
                                }
                            }
                        }));
        return stacks;
    }

    private static void logState(Map<String, Deque<Character>> stacks) {
        stacks.forEach((id, crates) -> System.out.println("ID " + id + ": [" + logStack(crates) + "]"));
    }

    private static String logStack(Deque<Character> crates) {
        return reversedStream(crates).map(String::valueOf).collect(joining(", "));
    }

    private static Stream<Character> reversedStream(Deque<Character> crates) {
        return stream(spliteratorUnknownSize(crates.descendingIterator(), Spliterator.ORDERED), false);
    }

    private static Move parseMove(String instruction) {
        String[] part1 = instruction.replace("move ", "").split(" from ");
        int count = Integer.parseInt(part1[0]);
        String[] srcDstPair = part1[1].split(" to ");
        return new Move(count, srcDstPair[0], srcDstPair[1]);
    }

    private static Map<String, Integer> getStackPositions(String legend) {
        return Arrays.stream(legend.split("\\s+"))
                .filter(not(String::isBlank))
                .collect(toMap(identity(), legend::indexOf));
    }
}
