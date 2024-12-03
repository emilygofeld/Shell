package commands;


public class HandleCommands {
    public static boolean directCommand(String cmd) {
        switch(cmd) {
            case "echo":
                System.out.println(cmd);
                break;
            default:
                return false;
        }
        return true;
    }
}