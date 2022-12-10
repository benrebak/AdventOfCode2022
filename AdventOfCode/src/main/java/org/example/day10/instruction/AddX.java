package org.example.day10.instruction;

import lombok.RequiredArgsConstructor;
import org.example.day10.Register;

@RequiredArgsConstructor
public class AddX implements Instruction {
    private final int toAdd;
    private final Register register;

    @Override
    public int getTimeToComplete() {
        return 2;
    }

    @Override
    public void onCompletion() {
        register.increment(toAdd);
    }
}
