package main.java.commands;

import java.io.IOException;
import java.util.Arrays;

public class HandleCommands {

    public static String runOtherCommand(String command, String data) {
        try {
            String path = PathHandler.findFilePath(command);
            if (path == null) {
                return commandNotFound(command + " " + data);
            }

            String[] fullCommand = new String[] {command, data};
            Process cmdProcess = Runtime.getRuntime().exec(fullCommand);

            return new String(cmdProcess.getInputStream().readAllBytes());
        } catch (IOException e) {
            return commandNotFound(command + " " + data);
        }
    }

    public static String commandNotFound(String cmd) {
        return cmd + ": not found\n";
    }

    enum Command {
        ECHO,
        TYPE,
        EXIT
    }
}