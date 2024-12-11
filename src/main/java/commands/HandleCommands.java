package main.java.commands;

public class HandleCommands {

    public static String commandNotFound(String cmd) {
        return cmd + ": not found\n";
    }


    enum Command {
        ECHO,
        TYPE,
        EXIT
    }
}