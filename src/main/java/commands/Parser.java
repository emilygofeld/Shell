package main.java.commands;

import main.java.commands.HandleCommands.Command;

import java.util.Scanner;

public class Parser {

    public static String parseCommand(final String userInput) {
        final Scanner parserScanner = new Scanner(userInput);
        final String command = parserScanner.next();
        String data = "";
        if (parserScanner.hasNext())
            data = parserScanner.nextLine().substring(1);

        final Command cmdType = getCommandType(command);

        return switch (cmdType) {
            case ECHO -> data + "\n";
            case TYPE -> typeCmd(data);
            case EXIT -> "";
            case null -> (userInput + ": command not found\n");
        };
    }

    private static String typeCmd(final String data) {
        final Command type = getCommandType(data);

        if (type != null)
            return (type.toString().toLowerCase() + " is a shell builtin\n");
        else
            return PathHandler.pathType(data);
    }

    private static Command getCommandType(final String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
