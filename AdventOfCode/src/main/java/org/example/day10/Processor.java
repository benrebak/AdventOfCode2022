package org.example.day10;

import lombok.RequiredArgsConstructor;
import org.example.day10.instruction.Instruction;

import java.util.*;

import static java.lang.System.out;

@RequiredArgsConstructor
public class Processor {
    public static final Set<Integer> SIGNAL_SAMPLE_CYCLES = Set.of(20, 60, 100, 140, 180, 220);
    private final Clock clock;
    private final Register register;
    private final CathodeRayTube crt;

    NavigableMap<Integer, Integer> signalStrength = new TreeMap<>();

    public void execute(Deque<Instruction> instructionSet) {
        Instruction current = instructionSet.pollFirst();
        int start = clock.getCycle();
        while (current != null) {
            sample();
            crt.drawTo(out);
            clock.tick();
            if (clock.getCycle() >= start + current.getTimeToComplete()) {
                current.onCompletion();
                register.drawTo(out);
                current = instructionSet.pollFirst();
                start = clock.getCycle();
            }
        }
    }

    private void sample() {
        if (SIGNAL_SAMPLE_CYCLES.contains(clock.getCycle())) {
            sampleSignalStrength();
        }
    }

    int getSignal() {
        return signalStrength.entrySet().stream()
                .mapToInt(e -> {
                    int strength = e.getKey() * e.getValue();
                    out.println("at cycle " + e.getKey() + ", register = " + e.getValue() + ". Strength = " + strength);
                    return strength;
                })
                .sum();
    }

    private void sampleSignalStrength() {
        out.println("Sampling [cycle = " + clock.getCycle() + ", register = " + register.getX());
        signalStrength.put(clock.getCycle(), register.getX());
    }

}
