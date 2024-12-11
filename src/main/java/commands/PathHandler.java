package main.java.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import static main.java.commands.HandleCommands.commandNotFound;

public class PathHandler {
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

//    public static String pathType(String fileName) {
//        String path = PathHandler.findFilePath(fileName);
//        if (path != null)
//            return path;
//        return commandNotFound(fileName);
//    }

}
