package ui;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.GameData;
import model.GameList;
import webSocketMessages.userCommands.commandModels.LeaveRequest;
import webSocketMessages.userCommands.commandModels.ResignRequest;

import java.util.ArrayList;

public class InGameUI {
    // initialize the server
    // we need to send a message to websocket
    // receive message from the server
    // print out the message to the user
    // public static void draw(String... params){
    public static String leaveGame() throws Exception {
        int gameID = PostLoginUI.wideGameID;
        String auth = PostLoginUI.authToken;
        WSFacade.leaveGame(auth, gameID);

        GameList gamelist = ServerFacade.listgames();
        ArrayList<GameData> listOfGames = gamelist.games();
        return "you left the game";
    }

    public static String makeMove(String... params) throws Exception {
        if (params.length ==4) {
            int start1 = Integer.parseInt(params[0]);
            int start2 = Integer.parseInt(params[1]);
            int end1 = Integer.parseInt(params[2]);
            int end2 = Integer.parseInt(params[3]);

            ChessPosition startPosition = new ChessPosition(start1,start2);
            ChessPosition endPosition = new ChessPosition(end1, end2);

            ChessMove moves = new ChessMove(startPosition, endPosition, null);
            int gameID = PostLoginUI.wideGameID;
            String auth = PostLoginUI.authToken;
            WSFacade.makeMoves(auth, gameID, moves);

            return "next player's turn";
        }
        return "enter a properly formatted move";
    }
    public static String resignGame() throws Exception {
        int gameID = PostLoginUI.wideGameID;
        String auth = PostLoginUI.authToken;
        try {
            WSFacade.resignGame(auth, gameID);
            return "You have resigned";
        }
        catch (Exception e){
            throw new Exception("resign unsuccessful");
        }
    }
}
