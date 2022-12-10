package org.example.day10;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.day10.instruction.AddX;
import org.example.day10.instruction.Instruction;
import org.example.day10.instruction.NoOp;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Integer.parseInt;
import static org.example.FileReader.reading;

public class Day10 {
    public static void main(String[] args) {
        reading("day10/full.txt",
                fileLines -> {
                    Clock clock = new Clock();
                    Register register = new Register();

                    Deque<Instruction> instructions = new ArrayDeque<>(fileLines
                            .map(s -> getInstruction(register, s))
                            .toList());

                    CathodeRayTube crt = new CathodeRayTube(clock, register);
                    Processor processor = new Processor(clock, register, crt);
                    processor.execute(instructions);
                    System.out.println(processor.getSignal());
                });
    }

    @NonNull
    private static Instruction getInstruction(Register register, String line) {
        return switch (line.substring(0, 4)) {
            case "noop" -> new NoOp();
            case "addx" -> new AddX(parseInt(line.split(" ")[1]), register);
            default -> throw new IllegalArgumentException("Unknown instruction " + line);
        };
    }
}
