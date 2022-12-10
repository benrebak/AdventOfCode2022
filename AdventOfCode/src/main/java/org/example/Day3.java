package org.example;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.partition;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.example.FileReader.reading;

public class Day3 {
    public static void main(String[] args) {
        Map<Character, Integer> priorities = buildPriorityMap();
        reading("Day3.txt", lines ->
                System.out.println(
                        partition(lines.collect(toList()), 3).stream()
                                .mapToInt(elves -> priorities.get(badge(elves)))
                                .sum()));
    }

    static Map<Character, Integer> buildPriorityMap() {
        Map<Character, Integer> priorities = new HashMap<>();
        int priority = 1;
        for (char v = 'a'; v <= 'z'; v++) {
            priorities.put(v, priority++);
        }
        for (char v = 'A'; v <= 'Z'; v++) {
            priorities.put(v, priority++);
        }
        return priorities;
    }

    private static char badge(List<String> elves) {
        return elves.stream()
                .map(Day3::createSet)
                .reduce(Sets::intersection)
                .flatMap(characters -> characters.stream()
                        .findFirst())
                .orElseThrow();
    }

    private static Set<Character> createSet(String contents) {
        return contents.chars()
                .mapToObj(e -> (char) e)
                .collect(toSet());
    }
}
