package org.example.day9;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.createDirectories;

public class MapWriter {
    private final Path folder;
    private final PathView pathView;

    @SneakyThrows
    public MapWriter(PathView pathView) {
        this.pathView = pathView;
        folder = Paths.get("src", "main", "resources")
                .resolve(Paths.get("day9", "debug"));
        createDirectories(folder);
    }

    @SneakyThrows
    void write(@SuppressWarnings("SameParameterValue") String fileName) {
        try (FileOutputStream out = new FileOutputStream(folder.resolve(fileName).toFile())) {
            pathView.draw(new PrintStream(out));
        }
    }
}
