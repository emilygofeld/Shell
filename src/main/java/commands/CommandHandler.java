package main.java.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.java.commands.PathHandler.workingDir;

public class CommandHandler {

    public static String echoCommand(String data) {
        if (!(data.contains("'") || data.contains("\"")))
            return Parser.parseEchoCommand(data);

        return StringFormatter.format(data);
    }

    public static String runOtherFilesCommand(String command, String data) {
        try {
            String formattedCmd = StringFormatter.format(command);
            System.out.println(formattedCmd);
            String path = PathHandler.findFilePath(formattedCmd);
            if (path == null) {
                return commandNotFound(command + " " + data);
            }

            if (command.equals("cat"))
                return catCommand(data);

            return getProcessResult(formattedCmd, data);
        } catch (IOException e) {
            final String cmd = (Objects.equals(data, ""))? command : command + " " + data;
            return commandNotFound(cmd);
        }
    }

    public static String cdCommand(String data) {
        if (data.contains("../") || data.contains("./")) {
            if (cdSpecialPath(data))
                return "";
        }
        if (data.equals("~")) {
            workingDir = PathHandler.getHomeDir();
            return "";
        }

        if (Files.isDirectory(Path.of(data))) {
            PathHandler.workingDir = data;
            return "";
        }
        else if (cdSpecialPath(data))
            return "";

        return "cd: " + data + ": No such file or directory\n";
    }

    private static String catCommand(String data) {
        List<String> content = new ArrayList<>();
        String cat = "cat";

        try {
            List<String> filePaths = Parser.extractFilePaths(data);

            for (String path : filePaths) {
                String formattedPath = StringFormatter.format(path);
                content.add(getProcessResult(cat, formattedPath));
            }
        } catch (Exception e) {
            return commandNotFound(cat);
        }

        return String.join("", content);
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

    private static String getProcessResult(String command, String data) throws IOException {
        String[] fullCommand = new String[] {command, data};
        Process cmdProcess = Runtime.getRuntime().exec(fullCommand);

        return new String(cmdProcess.getInputStream().readAllBytes());
    }

    private static String commandNotFound(String cmd) {
        return cmd.trim() + ": command not found\n";
    }

    enum Command {
        ECHO,
        TYPE,
        PWD,
        CD,
        EXIT
    }
}