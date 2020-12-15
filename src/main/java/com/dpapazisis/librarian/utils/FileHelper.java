/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.utils;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.repository.LibraryState;
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

public class FileHelper {
    // In case that FileSystemView doesn't work on Linux
    //    private static final Path homePath = Paths.get(System.getProperty("user.home"));
    private static final Path homePath = Paths.get(
            FileSystemView
                    .getFileSystemView()
                    .getDefaultDirectory()
                    .getPath()
    );
    private static final Path libraryFilePath = resolveLibraryFilePath();

    private FileHelper() {
    }

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

//    TODO: Add all file operations

    public static LibraryState loadFromDisk() throws FileNotFoundException {
        LibraryState library = new LibraryState(new HashSet<>(), new HashSet<>(), new HashSet<>());

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
