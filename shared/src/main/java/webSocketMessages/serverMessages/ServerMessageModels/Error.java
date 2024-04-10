package webSocketMessages.serverMessages.ServerMessageModels;

import webSocketMessages.serverMessages.ServerMessage;

public class Error extends ServerMessage {
    String errorMessage;

    public Error(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
    }
}
