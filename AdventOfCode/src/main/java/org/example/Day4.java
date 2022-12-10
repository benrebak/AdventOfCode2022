package org.example;

import com.google.common.collect.Range;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import static org.example.FileReader.reading;

public class Day4 {
    public static void main(String[] args) {
        reading("Day4.txt",
                fileLines -> {
                    List<Pair> rangePairs = fileLines
                            .map(Day4::getPair)
                            .toList();
                    part1(rangePairs);
                    part2(rangePairs);
                });
    }

    private static void part1(List<Pair> lines) {
        System.out.println(lines.stream()
                .filter(pair -> pair.r1().encloses(pair.r2()) || pair.r2().encloses(pair.r1()))
                .count());
    }

    private static void part2(List<Pair> lines) {
        System.out.println(lines.stream()
                .filter(pair -> pair.r1().isConnected(pair.r2()))
                .count());
    }

    @NonNull
    private static Pair getPair(String line) {
        String[] assignments = line.split(",");
        Range<Integer> range1 = toRange(assignments[0]);
        Range<Integer> range2 = toRange(assignments[1]);
        return new Pair(range1, range2);
    }

    private static Range<Integer> toRange(String assignment) {
        String[] split = assignment.split("-");
        return Range.closed(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private record Pair(Range<Integer> r1, Range<Integer> r2) {
    }
}
