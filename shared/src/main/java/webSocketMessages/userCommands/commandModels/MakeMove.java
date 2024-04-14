package webSocketMessages.userCommands.commandModels;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMove extends UserGameCommand {
    int gameID;
    ChessMove move;
    CommandType command;

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public CommandType getCommand() {
        return command;
    }

    public MakeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.command= CommandType.MAKE_MOVE;
    }
}
