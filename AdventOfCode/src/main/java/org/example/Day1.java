package org.example;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.stream.IntStream.range;
import static org.example.FileReader.reading;

public class Day1 {
    public static void main(String[] args) {
        NavigableSet<Integer> calorieTotals = new TreeSet<>();
        AtomicInteger currentCalorieTotal = new AtomicInteger();
        reading("Day1.txt",
                lines -> lines.forEach(line -> {
                    if (line.isBlank()) {
                        calorieTotals.add(currentCalorieTotal.get());
                        currentCalorieTotal.set(0);
                    } else {
                        currentCalorieTotal.addAndGet(parseInt(line));
                    }
                }));
        out.println(range(0, 1)
                .map(i -> calorieTotals.pollLast())
                .sum());
    }
}