/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.utils;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.services.LibraryState;
import org.apache.commons.lang3.SerializationUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Static File operations
 */
public class FileHelper {
    private static final Path homePath = getPath();

    /**
     * @return the default Documents path of the operating system
     */
    private static Path getPath() {
        return Paths.get(
                FileSystemView
                        .getFileSystemView()
                        .getDefaultDirectory()
                        .getPath()
        );
    }

    private static final Path libraryFilePath = resolveLibraryFilePath();

    /**
     * Hides the constructor because the class is meant to be static
     */
    private FileHelper() {
    }

    /**
     * Loads the subject list from a csv resource file
     *
     * @param uri {@link String} the resource path
     * @return {@link Set}<{@link Subject}>
     * @throws FileNotFoundException if file is not found
     */
    public static Set<Subject> loadCsv(String uri) throws FileNotFoundException {
        try (
                InputStream stream = FileHelper.class.getResourceAsStream(uri);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
        ) {
            return bufferedReader.lines()
                    .collect(Collectors.toList())
                    .stream()
                    .map(line -> {
                        String[] strings = line.split(",");
                        return new Subject(strings[1], strings[0]);
                    })
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new FileNotFoundException();
    }

    /**
     * Loads and deserializes the Library from the disk
     *
     * @return {@link LibraryState} object
     * @throws FileNotFoundException if file is not found
     */
    public static LibraryState loadFromDisk() throws FileNotFoundException {
        LibraryState library = new LibraryState(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

        if (libraryFilePath != null) {
            if (Files.exists(libraryFilePath)) {
                try {
                    byte[] file = Files.readAllBytes(libraryFilePath);
                    library = SerializationUtils.deserialize(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new FileNotFoundException("Something went wrong while loading the file!");
        }

        return library;
    }

    /**
     * Serializes and save the Library to disk as a binary file
     *
     * @param libraryState {@link LibraryState}
     */
    public static void saveToDisk(LibraryState libraryState) {
        byte[] data = SerializationUtils.serialize(libraryState);

        try {
            if (libraryFilePath != null) {
                Files.write(libraryFilePath, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Concat and create the subdirectory of the application
     *
     * @return {@link Path} the final file path
     */
    private static Path resolveLibraryFilePath() {
        Path path = null;
        try {
            path = Files.createDirectories(homePath.resolve("Librarian/"));
            path = path.resolve("Library.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
