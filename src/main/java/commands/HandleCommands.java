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

            String[] fullCommand = new String[] {path, data};
            System.out.printf("\nCommand: %s, Data: %s, path: %s, Full path: %s",
                    command,
                    data,
                    path,
                    Arrays.toString(fullCommand));

            // Execute the command
            Process cmdProcess = Runtime.getRuntime().exec(fullCommand);

            // Read the output
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