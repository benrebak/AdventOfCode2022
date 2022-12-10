package org.example.day7;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.day7.commandLine.Cd;
import org.example.day7.commandLine.Command;
import org.example.day7.commandLine.Ls;
import org.example.day7.fileSystem.Directory;
import org.example.day7.fileSystem.FileSystem;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.util.stream.Collectors.toCollection;
import static org.example.FileReader.reading;

public class Day7 {
    public static void main(String[] args) {
        reading("Day7.txt", stream -> {
            Deque<String> lines = stream.collect(toCollection(ArrayDeque::new));
            FileSystem fileTree = new FileSystem();
            while (!lines.isEmpty()) {
                determineCommand(lines.pollFirst(), lines, fileTree)
                        .execute();
            }
            fileTree.print();

            Directory root = fileTree.getRoot();
            Part2Tracker part2Tracker = new Part2Tracker(root.getSize());
            part2Tracker.accept(root);
            part2Tracker.print();
        });
    }

    private static @NonNull Command determineCommand(String line, Deque<String> reader, FileSystem fileTree) {
        String rawCommand = line.replace("$ ", "");
        if (rawCommand.startsWith("ls")) {
            return new Ls(reader, fileTree.getWorkingDirectory());
        }
        if (rawCommand.startsWith("cd")) {
            return new Cd(line, fileTree);
        }
        throw new IllegalStateException();
    }
}
