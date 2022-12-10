package org.example.day7.fileSystem;

import lombok.Getter;

public class File extends FileSystemEntity {
    @Getter
    private final int size;

    public File(int size, String name, Directory parent) {
        super(name, parent);
        this.size = size;
    }
}
