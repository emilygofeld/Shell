import java.util.Scanner;

import commands.HandleCommands;

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

            if (input.equals(exitCmd))
                break;

            System.out.print(HandleCommands.directCommand(input));
        }
        scanner.close();
    }
}
