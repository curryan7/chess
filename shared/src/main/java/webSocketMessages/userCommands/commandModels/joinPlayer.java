package webSocketMessages.userCommands.commandModels;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class joinPlayer extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor playerColor;




    public joinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

//    public String getAuth() {
//        return auth;
//    }

    public int getGameID(){
        return this.gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
