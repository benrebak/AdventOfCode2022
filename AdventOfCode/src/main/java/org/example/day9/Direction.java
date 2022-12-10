package org.example.day9;

import java.util.function.UnaryOperator;

enum Direction {
    UP(p -> new Point(p.x(), p.y() - 1)),
    DOWN(p -> new Point(p.x(), p.y() + 1)),
    LEFT(p -> new Point(p.x() - 1, p.y())),
    RIGHT(p -> new Point(p.x() + 1, p.y())),
    ;

    private final UnaryOperator<Point> movementFunction;

    Direction(UnaryOperator<Point> movementFunction) {
        this.movementFunction = movementFunction;
    }

    public static Direction fromLetter(char letter) {
        return switch (letter) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Unable to parse direction from " + letter);
        };
    }

    public Point applyTo(Point p) {
        return movementFunction.apply(p);
    }
}
