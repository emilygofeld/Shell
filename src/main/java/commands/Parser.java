package main.java.commands;

import main.java.commands.CommandHandler.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.java.commands.CommandHandler.*;

public class Parser {

    public static String parseCommand(final String userInput) {

        String[] inputs = getInput(userInput);
        final String command = inputs[0], data = inputs[1];

        System.out.printf("command = %s, data = %s\n", command, data);

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

    public static List<String> extractFilePaths(final String data) {
        List<String> filePaths = new ArrayList<>();
        StringBuilder currentPath = new StringBuilder();
        boolean insideSingleQuotes = false;
        boolean insideDoubleQuotes = false;

        for (int i = 0; i < data.length(); i++) {
            char current = data.charAt(i);

            if (current == '\'' && !insideDoubleQuotes) {
                insideSingleQuotes = !insideSingleQuotes;
                currentPath.append(current);

            } else if (current == '"' && !insideSingleQuotes) {
                insideDoubleQuotes = !insideDoubleQuotes;
                currentPath.append(current);

            } else if (current == '\\' && insideDoubleQuotes) {
                currentPath.append(current);
                if (i + 1 < data.length()) {
                    currentPath.append(data.charAt(i + 1));
                    i++;
                }

            } else if (Character.isWhitespace(current) && !insideSingleQuotes && !insideDoubleQuotes) {
                if (!currentPath.isEmpty()) {
                    filePaths.add(currentPath.toString());
                    currentPath.setLength(0);
                }

            } else {
                currentPath.append(current);
            }
        }

        if (!currentPath.isEmpty()) {
            filePaths.add(currentPath.toString());
        }

        return filePaths;
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
        final Scanner parserScanner = new Scanner(userInput);
        String enclosingChar = String.valueOf(userInput.charAt(0));
        boolean isInQuotes = enclosingChar.equals("'") || enclosingChar.equals("\"");

        if (!isInQuotes) {
            final String cmd = parserScanner.next();
            final String data = (parserScanner.hasNext()) ? parserScanner.nextLine().substring(1) : "";
            return new String[] { cmd, data };
        }

        StringBuilder cmd = new StringBuilder();
        StringBuilder data = new StringBuilder();

        while (parserScanner.hasNext()) {
            String next = parserScanner.next();

            if (!next.contains(enclosingChar))
                cmd.append(next).append(" ");
            else
                data.append(parserScanner.nextLine());
        }

        return new String[] { cmd.toString().trim(), data.toString().trim() };
    }
}

