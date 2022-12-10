package org.example.day7.fileSystem;

import com.google.common.base.Strings;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class FileSystemEntity {
    protected final String name;
    @Getter
    @Nullable
    private final Directory parent;

    protected FileSystemEntity(@NonNull String name,
                               @Nullable Directory parent) {
        this.parent = parent;
        this.name = name;
    }

    abstract int getSize();

    protected void print(int depth) {
        System.out.println(" ".repeat(depth * 2) + "- " + Strings.padEnd(name, 40 - (depth * 2), ' ') + "| " + getSize());
    }
}
