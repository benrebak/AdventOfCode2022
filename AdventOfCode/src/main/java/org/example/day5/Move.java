package org.example.day5;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

class Move {
    private final int repetitions;
    private final String sourceStackId;
    private final String destinationStackId;

    Move(int repetitions, String sourceStackId, String destinationStackId) {
        this.repetitions = repetitions;
        this.sourceStackId = sourceStackId;
        this.destinationStackId = destinationStackId;
    }

    public void apply(Map<String, Deque<Character>> stacks) {
        Deque<Character> popped = popN(stacks.get(sourceStackId));
        Deque<Character> destination = stacks.get(destinationStackId);
        while (!popped.isEmpty()) {
            destination.push(popped.removeFirst());
        }
    }

    public void apply9001(Map<String, Deque<Character>> stacks) {
        Deque<Character> popped = popN(stacks.get(sourceStackId));
        Deque<Character> destination = stacks.get(destinationStackId);
        while (!popped.isEmpty()) {
            destination.push(popped.removeLast());
        }
    }

    private Deque<Character> popN(Deque<Character> source) {
        Deque<Character> popped = new ArrayDeque<>();
        for (int i = 0; i < repetitions; i++) {
            popped.addLast(source.pop());
        }
        return popped;
    }
}
