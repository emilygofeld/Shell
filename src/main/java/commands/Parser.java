package main.java.commands;

import static main.java.commands.CommandHandler.*;

public class Parser {

    public static String parseCommand(final String userInput) {
        String[] inputs = getInput(userInput);
        final String command = inputs[0], data = inputs[1];

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

    public static String parseEchoCommand(String cmd) {
        cmd = cmd.replaceAll("\\s+", " ").trim();
        return cmd.replace("\\", "");
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

    private static String checkNonSpecificCommand(final String command) {
        String path = PathHandler.findFilePath(command);
        if (path != null)
            return path;

        return command + ": not found\n";
    }

    private static String[] getInput(final String userInput) {
        String enclosingChar = String.valueOf(userInput.charAt(0));
        String command, data;

        if (enclosingChar.equals("'") || enclosingChar.equals("\"")) {
            int lastQuoteIndex = userInput.lastIndexOf(enclosingChar);
            command = userInput.substring(0, lastQuoteIndex + 1).trim();
            data = userInput.substring(lastQuoteIndex + 1).trim();
        } else {
            int firstSpaceIndex = userInput.indexOf(' ');
            if (firstSpaceIndex == -1) {
                command = userInput;
                data = "";
            } else {
                command = userInput.substring(0, firstSpaceIndex).trim();
                data = userInput.substring(firstSpaceIndex + 1).trim();
            }
        }

        return new String[] { command, data };
    }
}

