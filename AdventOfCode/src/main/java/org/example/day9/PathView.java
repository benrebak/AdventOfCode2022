package org.example.day9;

import lombok.Data;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.IntSummaryStatistics;
import java.util.Set;

import static java.util.stream.Collectors.summarizingInt;

@Data
class PathView {
    public static final int SIZE = 380;
    private final char[][] characterMap = new char[SIZE][SIZE];
    int xLowerBound, xUpperBound, yLowerBound, yUpperBound = 0;

    private final Set<Point> visitedByTail;
    private final Deque<Knot> rope;

    public void updateBounds(Point p) {
        xLowerBound = Math.min(xLowerBound, p.x());
        xUpperBound = Math.max(xUpperBound, p.x());
        yLowerBound = Math.min(yLowerBound, p.y());
        yUpperBound = Math.max(yUpperBound, p.y());

        if (xUpperBound - xLowerBound > SIZE || yUpperBound - yLowerBound > SIZE) {
            throw new RuntimeException("Need a bigger grid!");
        }
    }

    private void reset(char[][] array) {
        for (char[] row : array) {
            Arrays.fill(row, '.');
        }
    }

    @SuppressWarnings("unused")
    void draw(PrintStream writer) {
        reset(characterMap);
        visitedByTail.forEach(point -> setAt(point, '#'));
        drawRope();
        writeTo(writer, characterMap);
    }

    private void writeTo(PrintStream writer, char[][] content) {
        for (char[] row : content) {
            writer.println(new String(row));
        }
    }

    @SuppressWarnings("unused")
    public void drawRopeShape(PrintStream output) {
        IntSummaryStatistics xBounds = rope.stream()
                .map(Knot::getPosition)
                .collect(summarizingInt(Point::x));
        IntSummaryStatistics yBounds = rope.stream()
                .map(Knot::getPosition)
                .collect(summarizingInt(Point::y));

        final char[][] characterMap = new char[yBounds.getMax() - yBounds.getMin() + 1][xBounds.getMax() - xBounds.getMin() + 1];

        reset(characterMap);
        rope.descendingIterator()
                .forEachRemaining(knot -> {
                    Point point = knot.getPosition();
                    characterMap[yBounds.getMax() - point.y()][xBounds.getMax() - point.x()] = getId(knot);
                });
        writeTo(output, characterMap);
    }

    private void drawRope() {
        rope.descendingIterator().forEachRemaining(knot -> draw(knot, getId(knot)));
        Knot head = rope.getFirst();
        draw(head, 'H');
    }

    private void draw(Knot knot, char id) {
        System.out.println("Setting " + knot.getPosition() + " in grid to " + id);
        setAt(knot.getPosition(), id);
    }

    private static char getId(Knot knot) {
        return String.valueOf(knot.getId()).charAt(0);
    }

    private void setAt(Point point, char newChar) {
        characterMap[point.y() - yLowerBound][point.x() - xLowerBound] = newChar;
    }
}
