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
        else if(PreLoginUI.state == UIState.POST_LOGIN){
        return """
                - help
                - logout
                - creategame <gamename>
                - listgames 
                - joingame <playercolor:WHITE or BLACK> <Game Number>
                - joinobserver <type "."> <Game Number> 
                """;
        }
        else {
            return """
                - help
                - redraw
                - leave
                - makemove <Piece row #> <Piece col #> <Move row #> <Move col #>
                - resign
                - highlighmoves<piece row #><piece column #>
                    """;
        }
    }
}
