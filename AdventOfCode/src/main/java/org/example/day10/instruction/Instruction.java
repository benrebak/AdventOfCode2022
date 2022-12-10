package org.example.day10.instruction;

public interface Instruction {
    int getTimeToComplete();

    void onCompletion();
}
