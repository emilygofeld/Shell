package main.java.commands;

import main.java.commands.CommandHandler.Command;

import java.util.Scanner;

import static main.java.commands.CommandHandler.*;

public class Parser {

    public static String parseCommand(final String userInput) {

        final Scanner parserScanner = new Scanner(userInput);
        final String command = parserScanner.next();

        String data = "";
        if (parserScanner.hasNext())
            data = parserScanner.nextLine().substring(1);

        final Command cmdType = getCommandType(command);

        return switch (cmdType) {
            case ECHO -> echoCommand(data) + "\n";
            case TYPE -> typeCmd(data);
            case PWD -> PathHandler.workingDir + "\n";
            case CD -> cdCommand(data);
            case EXIT -> "";
            case null -> runOtherFilesCommand(command, data); // check if user runs command from PATH
        };
    }

    private static String typeCmd(final String data) {
        final Command type = getCommandType(data);

        if (type != null)
            return (type.toString().toLowerCase() + " is a shell builtin\n");
        else
            return checkNonSpecificCommand(data);
    }

    private static Command getCommandType(final String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String checkNonSpecificCommand(String command) {
        String path = PathHandler.findFilePath(command);
        if (path != null)
            return path;

        return command + ": not found\n";
    }

    public static String parseEchoCommand(String cmd) {
        cmd = cmd.replaceAll("\\s+", " ").trim();
        return cmd.replace("\\", "");
    }

    public static String parsePathBackslashes(String path) {
        return path
                .replace("\\$", "$")
                .replace("\\\n", "\\n")
                .replace("\\`", "`")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}

