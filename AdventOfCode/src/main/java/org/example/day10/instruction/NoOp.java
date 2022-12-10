package org.example.day10.instruction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoOp implements Instruction {

    @Override
    public int getTimeToComplete() {
        return 1;
    }

    @Override
    public void onCompletion() {
    }
}
