package ui;
import chess.ChessGame;
import model.*;

import java.util.Objects;

public class InGameUI {
    public static void draw(String... params){

    }
    public static String leaveGame(String...params) throws ResponseException {
        int gameID = PostLoginUI.wideGameID;
        ChessGame.TeamColor playerColor = PostLoginUI.currentPlayerColor;

        if (playerColor == ChessGame.TeamColor.WHITE) {
            JoinData joinSend = new JoinData("WHITE", gameID);
            SuccessJoin joinResult = ServerFacade.joinGame(joinSend);

            PreLoginUI.state = UIState.IN_GAME;

            return "Successfully joined as White Player\n";
        }
    }
    public static void makeMove(String... params){

    }
    public static void resignGame(String... params){

    }
    public static void highlightMoves(String... params){

    }
}
