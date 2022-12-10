package org.example.day9;

import com.google.common.base.Strings;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static java.lang.Integer.compare;

@Data
class Knot {
    private final int id;
    @NonNull
    private Point position;
    @Nullable
    private Knot next;

    void move(Direction direction) {
        moveTo(direction.applyTo(position));
    }

    void moveTo(Point newPosition) {
        setPosition(newPosition);
        if (next != null && movedOutOfRangeOf(next)) {
            next.moveTowards(newPosition);
        }
    }

    private boolean movedOutOfRangeOf(@NonNull Knot other) {
        return !withinOneX(getPosition(), other.getPosition())
                || !withinOneY(getPosition(), other.getPosition());
    }

    private static boolean withinOneX(Point p1, Point p2) {
        return Math.abs(p1.x() - p2.x()) <= 1;
    }

    private static boolean withinOneY(Point p1, Point p2) {
        return Math.abs(p1.y() - p2.y()) <= 1;
    }

    private void moveTowards(Point destination) {
        Point current = getPosition();
        moveTo(new Point(
                current.x() + compare(destination.x(), current.x()),
                current.y() + compare(destination.y(), current.y())));
    }

    @NonNull String chainedDebugString() {
        return Strings.padEnd("#" + id + " @ " + position, 24, ' ')
                + (next != null ? next.chainedDebugString() : "");
    }
}
