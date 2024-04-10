package ui;

import java.util.Arrays;

public class ChessClient {
    public static String authToken;

    public ChessClient(String serverUrl){
        ServerFacade server = new ServerFacade(serverUrl);
    }

    public static String readInput(String input){
        try {
            var words = input.toLowerCase().split(" ");
            var cmd = (words.length>0) ? words[0] : "help";
            var params = Arrays.copyOfRange(words, 1, words.length);
            if(PreLoginUI.state == UIState.PRE_LOGIN | PreLoginUI.state == null) {
                return switch (cmd) {
                    case "login" -> PreLoginUI.login(params);
                    case "register" -> PreLoginUI.register(params);
                    case "quit" -> "quit";
                    default -> Repl.help();
                };
            }
            else if (PreLoginUI.state == UIState.POST_LOGIN){
                return switch(cmd){
                    case "logout" -> PostLoginUI.logout(params);
                    case "creategame"-> PostLoginUI.createGame(params);
                    case "listgames"-> PostLoginUI.listGames(params);
                    case "joingame"-> PostLoginUI.joinGame(params);
                    case "joinobserver"->PostLoginUI.joinGame(params);
                    default -> Repl.help();
                };
            }
            else {
                return switch(cmd){
//                    case "redraw" -> InGameUI.draw(params);
//                    case "leave"-> InGameUI.leaveGame(params);
//                    case "makemove"-> InGameUI.makeMove(params);
//                    case "resign"-> InGameUI.resignGame(params);
//                    case "highlightmoves"-> InGameUI.highlightMoves(params);
                    default -> Repl.help();

                };
            }
        }
        catch (Exception ex) {
            return ex.getMessage();
        }

    }
}
