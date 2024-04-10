package webSocketMessages.userCommands.commandModels;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class makeMove extends UserGameCommand {
    int gameID;
    ChessMove move;
    CommandType command;

    public makeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.command= CommandType.MAKE_MOVE;
    }
}
