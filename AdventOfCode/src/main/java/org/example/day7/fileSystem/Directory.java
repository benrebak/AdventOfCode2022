package org.example.day7.fileSystem;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Directory extends FileSystemEntity {
    private final Map<String, File> children = new HashMap<>();
    @Getter
    private final Map<String, Directory> subDirectories = new HashMap<>();

    public Directory(String name, Directory parent) {
        super(name, parent);
    }

    public void addChild(Directory entity) {
        subDirectories.put(entity.name, entity);
    }

    public void addChild(File entity) {
        children.put(entity.name, entity);
    }

    @Override
    public int getSize() {
        return totalSizeOf(children) + totalSizeOf(subDirectories);
    }

    private <T extends FileSystemEntity> int totalSizeOf(Map<String, T> entities) {
        return entities.values().stream()
                .mapToInt(FileSystemEntity::getSize)
                .sum();
    }

    public void print(int depth) {
        super.print(depth);
        subDirectories.values()
                .forEach(node -> node.print(depth + 1));
        children.values()
                .forEach(node -> node.print(depth + 1));
    }

    public boolean isRoot() {
        return getParent() == null;
    }
}
