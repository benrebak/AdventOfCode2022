package org.example.day7.commandLine;

import org.example.day7.fileSystem.Directory;
import org.example.day7.fileSystem.FileSystem;

public class Cd implements Command {
    private final String line;
    private final FileSystem fileTree;

    public Cd(String line, FileSystem fileSystem) {
        this.line = line;
        this.fileTree = fileSystem;
    }

    @Override
    public void execute() {
        String destination = line.split("cd ")[1];
        Directory current = fileTree.getWorkingDirectory();
        if (destination.equals("/")) {
            fileTree.setWorkingDirectory(fileTree.getRoot());
        } else if (destination.equals("..") && !current.isRoot()) {
            fileTree.setWorkingDirectory(current.getParent());
        } else {
            fileTree.setWorkingDirectory(current.getSubDirectories().get(destination));
        }
    }
}
