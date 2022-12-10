package org.example.day7.commandLine;

import lombok.SneakyThrows;
import org.example.day7.fileSystem.Directory;
import org.example.day7.fileSystem.File;

import java.util.Deque;

import static java.lang.Integer.parseInt;

public class Ls implements Command {
    private final Deque<String> reader;
    private final Directory currentDirectory;

    public Ls(Deque<String> reader, Directory currentDirectory) {
        this.reader = reader;
        this.currentDirectory = currentDirectory;
    }

    @Override
    @SneakyThrows
    public void execute() {
        String line;
        while (true) {
            line = reader.pollFirst();
            if (line == null) {
                return;
            } else if (line.startsWith("$ ")) {
                break;
            }
            String[] lineSplit = line.split(" ");
            String name = lineSplit[1];
            if (line.startsWith("dir")) {
                currentDirectory.addChild(new Directory(name, currentDirectory));
            } else {
                currentDirectory.addChild(new File(parseInt(lineSplit[0]), name, currentDirectory));
            }
        }

        reader.addFirst(line); // re-add the next command
    }
}
