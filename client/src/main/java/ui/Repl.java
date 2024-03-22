package ui;

import java.net.URL;
import java.util.Scanner;

public class Repl {
    private final String serverUrl;
    private static UIState state = UIState.PRE_LOGIN;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to Chess. Press help to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = ChessClient.readInput(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public static String help() {
        if (state == UIState.PRE_LOGIN) {
            return """
                    - signIn <yourname>
                    - quit
                    """;
        }
        return """
                - list
                - adopt <pet id>
                - rescue <name> <CAT|DOG|FROG|FISH>
                - adoptAll
                - signOut
                - quit
                """;
    }
    private void printPrompt() {
        System.out.print("\n>>> ");
    }
}
