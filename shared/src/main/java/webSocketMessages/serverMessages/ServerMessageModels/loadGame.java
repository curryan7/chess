package webSocketMessages.serverMessages.ServerMessageModels;

import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class loadGame extends ServerMessage {
    ChessGame game;
    public loadGame(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game;
    }
}
