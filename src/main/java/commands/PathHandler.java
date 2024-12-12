package main.java.commands;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathHandler {

    public static String workingDir = getWorkingDir();

    public static String getPath() {
        return System.getenv("PATH");
    }

    public static String findFilePath(String file) {
        for (String path : getPath().split(":")) {
            Path filePath = Path.of(path, file);
            if (Files.isRegularFile(filePath)) {
                return filePath + "\n";
            }
        }
        return null;
    }

    public static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    public static String getParentDir(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }
}
