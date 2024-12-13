package main.java.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.commands.PathHandler.workingDir;

public class CommandHandler {

    public static String echoCommand (String data) {
        List<String> outputs = new ArrayList<>();

        // regex to match either single or double-quoted paths
        Matcher matcher = Pattern.compile("(['\"])(.*?)\\1").matcher(data);

        while (matcher.find()) {
            outputs.add(matcher.group(2).trim());
        }

        if (outputs.isEmpty())
            return Parser.parseEchoCommand(data);

        return String.join(" ", outputs);
    }

    public static String catCommand(String data) {
        List<String> content = new ArrayList<>();
        String cat = "cat";

        try {
            // regex to match either single or double-quoted paths
            Matcher matcher = Pattern.compile("(['\"])(.*?)\\1").matcher(data);

            while (matcher.find()) {
                String fileName = Parser.parsePathBackslashes(matcher.group(2));
                content.add(getProcessResult(cat, fileName));
            }
        } catch (Exception _) {
            return commandNotFound(cat);
        }

        return String.join("", content);
    }


    public static String runOtherFilesCommand(String command, String data) {
        try {
            String path = PathHandler.findFilePath(command);
            if (path == null) {
                return commandNotFound(command + " " + data);
            }

            if (command.equals("cat"))
                return catCommand(data);

            return getProcessResult(command, data);
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