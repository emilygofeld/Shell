package main.java.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static main.java.commands.PathHandler.getParentDir;
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
        if (Files.isDirectory(Path.of(data))) {
            PathHandler.workingDir = data;
            return "";
        }
        else  {
            cdSpecialPath(data);
        }
        return "cd: " + data + ": No such file or directory\n";
    }

    private static void cdSpecialPath(String path) {
        String strPath = workingDir;
        for (String dir : path.split("/")) {
            if (dir.equals(cdDirs.getLast())) {
                strPath = getParentDir(strPath);
            }
            else if (!dir.equals(cdDirs.getFirst())) {
                strPath += dir + "/";
            }
        }
        strPath = strPath.substring(0, strPath.length()-2);
        if (Files.isDirectory(Path.of(strPath))) {
            PathHandler.workingDir = strPath;
        }
    }

    static final private List<String> cdDirs = Collections.unmodifiableList(
            List.of(".", "..")
    );

    enum Command {
        ECHO,
        TYPE,
        PWD,
        CD,
        EXIT
    }
}