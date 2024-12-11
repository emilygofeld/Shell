import main.java.commands.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        runShell();
    }

    public static void runShell() {
        Scanner scanner = new Scanner(System.in);
        String input = "", exitCmd = "exit 0";

        while (!input.equals(exitCmd)) {
            System.out.print("$ ");
            input = scanner.nextLine();

            System.out.print(Parser.parseCommand(input));
        }
        scanner.close();
    }
}
