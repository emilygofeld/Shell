package main.java;

import java.util.Scanner;

import main.java.commands.HandleCommands;
import main.java.commands.Parser;

public class Main {
    public static void main(String[] args) throws Exception {
        runShell();
    }

    public static void runShell() {
        Scanner scanner = new Scanner(System.in);
        String input = "", exitCmd = "exit 0";

        while (!input.equals(exitCmd)) {
            System.out.print("\n$ ");
            input = scanner.nextLine();

            System.out.print(Parser.parseCommand(input));
        }
        scanner.close();
    }
}
