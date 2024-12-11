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

            String[] args = data.split(" ");
            String[] fullCommand = new String[args.length + 1];
            fullCommand[0] = path + command;
            System.arraycopy(args, 0, fullCommand, 1, args.length);
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