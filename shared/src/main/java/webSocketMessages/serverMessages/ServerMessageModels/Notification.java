package webSocketMessages.serverMessages.ServerMessageModels;

import webSocketMessages.serverMessages.ServerMessage;

public class Notification extends ServerMessage {
    String message;

    public Notification(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }
}
