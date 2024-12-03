package commands;


public class HandleCommands {
    public static boolean directCommand(String cmd) {

        String[] parts = cmd.split(" ", 2); // Split at the first space
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : "";

        switch(command) {
            case "echo":
                System.out.println(data);
                break;
            default:
                return false;
        }
        return true;
    }
}