package ui;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.Session;
import java.net.URI;

import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.Notification;
import webSocketMessages.serverMessages.ServerMessageModels.LoadGame;
import webSocketMessages.userCommands.commandModels.*;

public class WSFacade extends Endpoint{
    public static WSFacade wsConnect;

    static {
        try {
            wsConnect = new WSFacade();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // receive message from InGameUI
    // package the message properly into the correct datatype
    // convert the object into JSON
    // send to websocket handler
    // receive JSON from websocket handler
    // send string back into the InGameUI

    public static Session session;

    public WSFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);

        session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                Gson gson = new Gson();
                ServerMessage jump = gson.fromJson(message, ServerMessage.class);
                switch(jump.getServerMessageType()){
                    case NOTIFICATION:
                       Notification notification = gson.fromJson(message, Notification.class);
                       processNotification(notification);
                    case ERROR:
                        Error error = gson.fromJson(message, Error.class);
                        processError(error);
                    case LOAD_GAME:
                        LoadGame loader = gson.fromJson(message, LoadGame.class);
                        processLoad(loader);
                }
            }
        });
    }

    public static void joinGame(String auth, int gameID, ChessGame.TeamColor color) throws Exception {
        if(color == null){
            JoinObserver observerRequest = new JoinObserver(auth, gameID);
            Gson gson = new Gson();
            send(gson.toJson(observerRequest));
        }
        else {
            JoinPlayer joinRequest = new JoinPlayer(auth, gameID, color);
            Gson gson = new Gson();
            send(gson.toJson(joinRequest));
        }
        PreLoginUI.state = UIState.IN_GAME;
    }

    public static void leaveGame(String auth, int gameID) throws Exception {
        LeaveRequest leaver = new LeaveRequest(auth, gameID);
        Gson gson = new Gson();
        send(gson.toJson(leaver));
        PreLoginUI.state = UIState.POST_LOGIN;
    }

    public static void resignGame(String auth, int gameID) throws Exception {
        ResignRequest quitter = new ResignRequest(auth, gameID);
        Gson gson = new Gson();
        send(gson.toJson(quitter));
    }

    public static void makeMoves(String auth, int gameID, ChessMove moves) throws Exception {
        MakeMove mover = new MakeMove(auth,gameID, moves);
        send(new Gson().toJson(mover));
    }

    public static void redraw(){
        if (PostLoginUI.widePlayerColor == ChessGame.TeamColor.BLACK){
            int orientation = 1;
            ChessDesign.finalDraw(orientation);
        }
        else if (PostLoginUI.widePlayerColor == ChessGame.TeamColor.WHITE){
            int orientation = 2;
            ChessDesign.finalDraw(orientation);
        }
    }


    public static void processNotification(Notification message){
        String shoutout = message.getMessage();
        System.out.println(shoutout);
    }

    public static void processError(Error message){
        String error = message.getMessage();
        System.out.println(error);
    }

    public static void processLoad(LoadGame message){
        ChessGame game = message.getGame();
        ChessDesign.setGame(game);
        ChessDesign.game.setBoard(game.getBoard());
        if (PostLoginUI.widePlayerColor == ChessGame.TeamColor.BLACK){
            int orientation = 1;
            ChessDesign.finalDraw(orientation);
        }
        else if (PostLoginUI.widePlayerColor == ChessGame.TeamColor.WHITE){
            int orientation = 2;
            ChessDesign.finalDraw(orientation);
        }
        else {
            ChessGame.TeamColor color = game.getTeamTurn();
            if (color == ChessGame.TeamColor.WHITE){
                int orientation = 1;
                ChessDesign.finalDraw(orientation);
            }
            else if (color == ChessGame.TeamColor.BLACK){
                int orientation = 2;
                ChessDesign.finalDraw(orientation);
            }
        }
    }

    public static void send(String msg) throws Exception {
        session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

}