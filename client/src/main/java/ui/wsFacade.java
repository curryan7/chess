package ui;
import chess.ChessGame;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.Session;
import java.net.URI;
import webSocketMessages.userCommands.commandModels.joinPlayer;

public class wsFacade extends Endpoint{
    // receive message from InGameUI
    // package the message properly into the correct datatype
    // convert the object into JSON
    // send to websocket handler
    // receive JSON from websocket handler
    // send string back into the InGameUI

    public Session session;

    public wsFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void joinGame (String auth, int gameID, ChessGame.TeamColor color) throws Exception {
        joinPlayer joinRequest = new joinPlayer(auth, gameID, color);
        Gson gson = new Gson();
        send(gson.toJson(joinRequest));

    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

}