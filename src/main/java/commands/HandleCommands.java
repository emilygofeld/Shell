package main.java.commands;

import java.io.IOException;
import java.util.Objects;

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
            final String cmd = (Objects.equals(data, ""))? command : command + " " + data;
            return commandNotFound(cmd);
        }
    }

    public static String commandNotFound(String cmd) {
        return cmd.trim() + ": command not found\n";
    }

    enum Command {
        ECHO,
        TYPE,
        PWD,
        EXIT
    }
}