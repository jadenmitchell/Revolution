package org.mdev.revolution.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static String getFile(String dir, String file) throws IOException {
        Path path = Paths.get(dir + file);
        if (Files.notExists(path)) {
            throw new FileNotFoundException("File: '" + dir + "' does not exists");
        }
        byte[] buffer = Files.readAllBytes(path);
        return new String(buffer, Charset.defaultCharset());
    }

    public static String[] getFilesInDirectory(String dir, String ext) {
        File directory = new File(dir);
        FilenameFilter filter = (File folder, String fileName) -> fileName.endsWith("." + ext);
        File[] files = directory.listFiles(filter);
        List<String> names = new ArrayList<>();
        for (File file : files) {
            names.add(file.getName());
        }
        return names.stream().toArray(String[]::new);
    }

    public static String[] getFilesInDirectory(String dir) {
        return getFilesInDirectory(dir, "");
    }
}
