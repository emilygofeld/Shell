package main.java.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static main.java.commands.PathHandler.workingDir;

public class HandleCommands {

    public static String runOtherFilesCommand(String command, String data) {
        try {
            String path = PathHandler.findFilePath(command);
            if (path == null) {
                return commandNotFound(command + " " + data);
            }

            String[] fullCommand = new String[] {command, data};
            Process cmdProcess = Runtime.getRuntime().exec(fullCommand);

            return new String(cmdProcess.getInputStream().readAllBytes());
        } catch (IOException e) {
            final String cmd = (Objects.equals(data, ""))? command : command + " " + data;
            return commandNotFound(cmd);
        }
    }

    public static String commandNotFound(String cmd) {
        return cmd.trim() + ": command not found\n";
    }

    public static String cdCommand(String data) {
        if (data.contains("../") || data.contains("./")) {
            if (cdSpecialPath(data))
                return "";

        }
            if (Files.isDirectory(Path.of(data))) {
                PathHandler.workingDir = data;
                return "";
            } else if (cdSpecialPath(data))
                return "";

            return "cd: " + data + ": No such file or directory\n";
    }

    private static Boolean cdSpecialPath(String path) {
        Path currentPath = Path.of(workingDir);

        for (String dir : path.split("/")) {
            if (dir.equals("..")) {
                currentPath = currentPath.getParent();
            } else if (!dir.equals(".") && !dir.isEmpty()) {
                currentPath = currentPath.resolve(dir);
            }
        }

        if (Files.isDirectory(currentPath)) {
            PathHandler.workingDir = currentPath.toString();
            return true;
        }
        return false;
    }

    enum Command {
        ECHO,
        TYPE,
        PWD,
        CD,
        EXIT
    }
}