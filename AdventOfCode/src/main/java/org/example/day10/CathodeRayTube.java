package org.example.day10;

import java.io.PrintStream;
import java.util.Arrays;

import static java.lang.Math.min;

public class CathodeRayTube {
    private static final int SCREEN_WIDTH = 40;
    private final char[] screen = new char[240];
    private final Clock clock;
    private final Register register;

    public CathodeRayTube(Clock clock, Register register) {
        this.clock = clock;
        this.register = register;
        Arrays.fill(screen, ' ');
    }

    public void drawTo(PrintStream writer) {
        writer.println("\n\n");
        int pixelToDraw = clock.getCycle() - 1;
        screen[pixelToDraw] = register.isSpritePixel(pixelToDraw % SCREEN_WIDTH) ? '#' : '.';
        writeTo(writer);
    }

    private void writeTo(PrintStream writer) {
        String screenString = new String(screen);
        for (int i = 0; i < screenString.length(); i+= SCREEN_WIDTH) {
            writer.println(screenString.substring(i, min(i + SCREEN_WIDTH, screenString.length())));
        }
    }
}
