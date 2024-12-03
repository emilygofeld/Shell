package commands;

import java.util.Scanner;

public class HandleCommands {
    public static String directCommand(final String userInput) {
        final Scanner parserScanner = new Scanner(userInput);
        final String command = parserScanner.next();
        final String data = parserScanner.nextLine().substring(1);

        final Command cmdType = getCommandType(command);

        switch(cmdType) {
            case ECHO:
                return data;
            case TYPE:
                return typeCmd(data);
            case EXIT:
                return "";
            default:
                return (userInput + ": command not found\n");
        }
    }

    private static String typeCmd(final String data) {
        final Command type = getCommandType(data.toUpperCase());

        if (type != null)
            return (type + " is a shell builtin\n");
        else
            return (data + ": not found\n"); 
    }

    private static Command getCommandType(final String command) {
        try {
            return Command.valueOf(command);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    enum Command {
        ECHO,
        TYPE,
        EXIT
    }
}