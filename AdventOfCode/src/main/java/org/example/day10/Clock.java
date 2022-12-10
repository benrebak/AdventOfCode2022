package org.example.day10;

import lombok.Getter;

public class Clock {
    @Getter
    private int cycle = 1;
    public void tick() {
        cycle++;
    }
}
