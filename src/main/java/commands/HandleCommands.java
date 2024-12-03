package commands;


public class HandleCommands {

    enum Command {
        echo,
        type,
        exit
    }


    public static boolean directCommand(String cmd) {
        String[] parts = cmd.split(" ", 2); // Split at the first space
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : "";

        switch(command) {
            case "echo":
                System.out.println(data);
                break;
            case "type":
                System.out.print(typeCmd(data));
                break;
            default:
                return false;
        }
        return true;
    }


    private static String typeCmd(String data) {
        if (Command.valueOf(data) != null) {
            return (data + " is a shell builtin\n");
        }
        return (data + ": not found");
    }
}