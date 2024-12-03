import java.util.Scanner;

import commands.HandleCommands;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("$ ");
            input = scanner.nextLine();

            if (input.equals("exit 0"))
                break;

            if (!HandleCommands.directCommand(input)) {
                System.out.printf("%s: command not found\n", input);
            }
        }

        scanner.close();
    }
}
