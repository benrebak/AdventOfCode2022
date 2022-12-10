package org.example.day7;

import lombok.RequiredArgsConstructor;
import org.example.day7.fileSystem.Directory;

import java.util.NavigableSet;
import java.util.TreeSet;

@RequiredArgsConstructor
public class Part2Tracker {
    private final int totalUsed;
    private final NavigableSet<Integer> directorySizes = new TreeSet<>();

    public void accept(Directory directory) {
        directorySizes.add(directory.getSize());
        directory.getSubDirectories().values()
                .forEach(this::accept);
    }

    public void print() {
        System.out.println("\n\n");
        final int totalDiskSize = 70_000_000;
        final int spaceRequired = 30_000_000;
        System.out.println("Total: " + totalDiskSize);
        System.out.println("Required: " + spaceRequired);
        int spaceAvailable = totalDiskSize - totalUsed;
        int additionalSpaceRequired = spaceRequired - spaceAvailable;
        System.out.println("In use: " + totalUsed);
        System.out.println("Free: " + spaceAvailable);
        System.out.println("Additional required: " + additionalSpaceRequired);
        System.out.println(directorySizes.ceiling(additionalSpaceRequired));
    }
}
