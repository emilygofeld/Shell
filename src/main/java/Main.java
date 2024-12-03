import java.util.Scanner;

import commands.HandleCommands;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("$ ");
            input = scanner.nextLine();

            String command = input.split(" ", 2)[0];

            if (input.equals("exit 0"))
                break;

            if (!HandleCommands.directCommand(command)) {
                System.out.printf("%s: command not found\n", input);
            }
        }

        scanner.close();
    }
}
