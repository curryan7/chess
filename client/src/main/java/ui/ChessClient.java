package ui;

import java.util.Arrays;
import static ui.Repl.help;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private UIState state = UIState.PRE_LOGIN;

    public ChessClient(String serverUrl){
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public static String readInput(String input){
        try {
            var words = input.toLowerCase().split(" ");
            var cmd = (words.length>0) ? words[0] : "help";
            var params = Arrays.copyOfRange(words, 1, words.length);
            return switch (cmd){
                case "login"->PreLoginUI.login(params);
                case "register"-> PreLoginUI.register(params);
                case "quit" -> "quit";
                case "logout"-> PostLoginUI.logout(params);
                case "creategame"-> PostLoginUI.createGame(params);
                case "listgames"-> PostLoginUI.listGames(params);
                case "joingame"-> PostLoginUI.joinGame(params);
                case "joinobserver"->PostLoginUI.joinobserver(params);
                default -> help();
            };

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
