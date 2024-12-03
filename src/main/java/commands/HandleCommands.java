package commands;


public class HandleCommands {

    enum Command {
        echo,
        type,
        exit
    }


    public static String directCommand(String userInput) {
        String[] parts = userInput.split(" ", 2); 
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : "";

        Command cmdType = getCommandType(command);

        switch(cmdType) {
            case echo:
                return data;
            case type:
                return typeCmd(data);
            case exit:
                return "";
            default:
                return (userInput + ": command not found\n");
        }
    }

    private static String typeCmd(String data) {
        Command type = getCommandType(data);

        if (type != null)
            return (type + " is a shell builtin\n");
        else
            return (data + ": not found\n"); 
    }

    private static Command getCommandType(String command) {
        try {
            return Command.valueOf(command);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}