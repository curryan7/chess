package webSocketMessages.serverMessages.ServerMessageModels;

import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGame extends ServerMessage {
    ChessGame game;

    public ChessGame getGame() {
        return game;
    }

    public LoadGame(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game;
    }
}
