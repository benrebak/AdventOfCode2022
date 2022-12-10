package org.example.day9;

import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.FileReader;

import java.util.*;
import java.util.stream.Stream;

public class Day9 {
    public static void main(String[] args) {
        FileReader.reading("day9/full.txt", Day9::doPuzzle);
    }

    @SneakyThrows
    private static void doPuzzle(Stream<String> fileLines) {
        List<Instruction> instructions = createInstructionsFrom(fileLines);

        Deque<Knot> rope = createRope();
        Knot head = rope.getFirst();
        Knot tail = rope.getLast();

        Set<Point> points = new HashSet<>(List.of(tail.getPosition()));
        PathView pathView = new PathView(points, rope);
        MapWriter mapWriter = new MapWriter(pathView);
        for (int j = 0; j < instructions.size(); j++) {
            Instruction instruction = instructions.get(j);
            System.out.println("INSTRUCTION #" + j + ": " + instruction.direction() + " x" + instruction.steps());
            for (int i = 0; i < instruction.steps(); i++) {
                head.move(instruction.direction());
                points.add(tail.getPosition());
                pathView.updateBounds(head.getPosition());
                System.out.println(head.chainedDebugString());
            }
        }
        mapWriter.write("final.txt");

        System.out.println("Distinct points visited: " + points.size());
    }

    @NonNull
    private static List<Instruction> createInstructionsFrom(Stream<String> fileLines) {
        return fileLines
                .map(s -> s.split(" "))
                .map(s -> new Instruction(
                        Direction.fromLetter(s[0].charAt(0)),
                        Integer.parseInt(s[1])))
                .toList();
    }

    @NonNull
    private static Deque<Knot> createRope() {
        Point origin = new Point(0, 0);
        Knot head = new Knot(0, origin);
        Deque<Knot> knots = new ArrayDeque<>(10);
        knots.add(head);

        Knot current = head;
        for (int id = 1; id < 10; id++) {
            Knot next = new Knot(id, origin);
            knots.add(next);
            current.setNext(next);
            current = next;
        }
        return knots;
    }

    record Instruction(Direction direction, int steps) {
    }
}
