import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("$ ");
            input = scanner.nextLine();
            System.out.printf("%s: command not found\n", input);
        }
    }
}
