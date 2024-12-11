package main.java.commands;

import java.io.IOException;

public class HandleCommands {

    public static String runOtherCommand(String command, String data) {
        try {
            String path = PathHandler.findFilePath(command);
            if (path == null) {
                System.out.println("Path not found");
                return commandNotFound(command + " " + data);
            }

            String[] fullCmdPath = (path + command + " " + data).split(" ");
            Process cmdProcess = Runtime.getRuntime().exec(fullCmdPath);
            return new String(cmdProcess.getInputStream().readAllBytes());
        } catch (IOException e) {
            System.out.println("Exception");
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