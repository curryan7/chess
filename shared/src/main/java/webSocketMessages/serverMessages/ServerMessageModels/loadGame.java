package webSocketMessages.serverMessages.ServerMessageModels;

import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class loadGame extends ServerMessage {
    ChessGame game;
    ServerMessageType message;
    public loadGame(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game;
        this.message=ServerMessageType.LOAD_GAME;
    }
}
