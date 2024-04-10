package ui;
import chess.ChessGame;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.Session;
import java.net.URI;

import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.Notification;
import webSocketMessages.userCommands.commandModels.joinPlayer;

public class wsFacade extends Endpoint{
    // receive message from InGameUI
    // package the message properly into the correct datatype
    // convert the object into JSON
    // send to websocket handler
    // receive JSON from websocket handler
    // send string back into the InGameUI

    public static Session session;

    public wsFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);

        session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                Gson gson = new Gson();
                ServerMessage jump = gson.fromJson(message, ServerMessage.class);
                switch(jump.getServerMessageType()){
                    case NOTIFICATION:
                       Notification notification = new Notification(jump.getServerMessageType(), Notification.class);
                    case ERROR:
                        System.out.println("sad");
                    case LOAD_GAME:
                        System.out.println("meh");
                }
            }
        });
    }

    public static void joinGame(String auth, int gameID, ChessGame.TeamColor color) throws Exception {
        joinPlayer joinRequest = new joinPlayer(auth, gameID, color);
        Gson gson = new Gson();
        send(gson.toJson(joinRequest));
    }

    public static String processNotification(String message){
        Notification newNotif = new Notification(message);
    }

    public static void send(String msg) throws Exception {
        session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }

}