package webSocketMessages.userCommands.commandModels;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class joinPlayer extends UserGameCommand {
    static int gameID;
    ChessGame.TeamColor playerColor;
    String auth;



    public joinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.auth = authToken;
    }

    public String getAuth() {
        return auth;
    }

    public int getGameID(){
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
