package org.example.day7.fileSystem;

import lombok.Getter;
import lombok.Setter;

public class FileSystem {
    @Getter
    private final Directory root;

    @Getter
    @Setter
    private Directory workingDirectory;

    public FileSystem() {
        root = new Directory("/", null);
        workingDirectory = root;
    }

    public void print() {
        root.print(0);
    }
}
