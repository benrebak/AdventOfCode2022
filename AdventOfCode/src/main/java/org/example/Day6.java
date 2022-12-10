package org.example;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.example.FileReader.reading;

public class Day6 {
    public static final int DISTINCT_CHARACTER_COUNT = 14;

    public static void main(String[] args) {
        reading("Day6.txt",
                lines -> {
                    String input = lines.collect(joining());
                    if (input.length() <= 4) {
                        throw new IllegalStateException();
                    }

                    Map<Character, Integer> counts = IntStream.range(0, DISTINCT_CHARACTER_COUNT)
                            .boxed()
                            .collect(toMap(input::charAt, i -> 1, Integer::sum));

                    int i = DISTINCT_CHARACTER_COUNT;
                    while (i < input.length()) {
                        counts.compute(input.charAt(i - DISTINCT_CHARACTER_COUNT), decrementing());
                        counts.merge(input.charAt(i), 1, Integer::sum);

                        log(counts);

                        if (counts.size() == DISTINCT_CHARACTER_COUNT) {
                            break;
                        } else {
                            i++;
                        }
                    }

                    System.out.println("\n\n" + (i + 1));
                    System.out.println(input.substring(i - DISTINCT_CHARACTER_COUNT, i));
                    log(counts);
                });
    }

    private static void log(Map<Character, Integer> counts) {
        System.out.println(
                counts.entrySet().stream()
                        .map(e -> "[" + e.getKey() + ": " + e.getValue() + "]")
                        .collect(joining(", ")));
    }

    private static BiFunction<Character, Integer, Integer> decrementing() {
        return (key, count) -> count == 1 ? null : count - 1;
    }
}
