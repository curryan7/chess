package ui;

import java.util.Scanner;

public class Repl {
    public String serverUrl;
    private static UIState state = UIState.PRE_LOGIN;
    private ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to Chess. Press help to start.");
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
        if (PreLoginUI.state == UIState.PRE_LOGIN | PreLoginUI.state == null) {
            return """
                    - help
                    - quit
                    - login <username> <password>
                    - register <username> <password> <email>
                    """;
        }
        return """
                - help
                - logout
                - creategame <gamename>
                - listgames 
                - joingame <playercolor:WHITE or BLACK> <gameID>
                - joinobserver <type "."> <gameID> 
                """;
    }

    private void printPrompt() {
        System.out.print("\n>>> ");
    }
}
