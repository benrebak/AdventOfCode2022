package org.example.day10;

import lombok.Data;

import java.io.PrintStream;

@Data
public class Register {
    private int x = 1;

    public void increment(int i) {
        x += i;
    }

    public void drawTo(PrintStream out) {
        StringBuilder s = new StringBuilder("Sprite position: ");
        for (int i = 0; i < 40; i++) {
            s.append(isSpritePixel(i) ? '#' : '.');
        }
        out.println(s);
    }

    boolean isSpritePixel(int i) {
        return i == (x - 1) || i == x || i == (x + 1);
    }
}
