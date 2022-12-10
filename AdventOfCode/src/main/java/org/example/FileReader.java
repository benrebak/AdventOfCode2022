package org.example;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Path.of;

public class FileReader {
    public static void reading(String fileName, Consumer<Stream<String>> consumer) {
        try (Stream<String> stream = lines(of("src/main/resources/" + fileName))) {
            consumer.accept(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
