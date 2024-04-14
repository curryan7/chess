package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.Error;
import webSocketMessages.serverMessages.ServerMessageModels.Notification;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import webSocketMessages.serverMessages.ServerMessageModels.LoadGame;
import webSocketMessages.userCommands.UserGameCommand;

public class SessionManager {
    public final ConcurrentHashMap<Integer, Vector<Session>> sessions = new ConcurrentHashMap<>();

    public void add(UserGameCommand.CommandType modelType, String auth, Session currentSession, int gameID, String color) throws SQLException, DataAccessException, IOException {

        if (sessions.get(gameID) != null) {
            Vector<Session> edit = sessions.get(gameID);
            edit.add(currentSession);
        } else {
            Vector<Session> edit = new Vector<Session>(200);
            edit.add(currentSession);
            sessions.put(gameID, edit);
        }

        String username = MySqlDataAccess.getUsername(auth);
        switch (modelType) {
            case JOIN_OBSERVER, JOIN_PLAYER:
                Notification addMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has joined as " + color);
                announce(currentSession, addMessage, gameID);
                break;
            case MAKE_MOVE:
                Notification moveMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has moved a piece");
                announce(currentSession, moveMessage, gameID);
                GameData gameDat = MySqlDataAccess.grabGameByID(gameID);
                assert gameDat != null;
                ChessGame gameObject = gameDat.game();
                LoadGame gotGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameObject);
                announce(currentSession, gotGame, gameID);
                break;
        }
    }

    public void delete(Session currentSession, int gameID){
            Vector<Session> sessionsVector = sessions.get(gameID);
            sessionsVector.remove(currentSession);
    }
    public void bounce(String auth, Session currentSession, int gameID, String color) throws IOException {
        Vector<Session> edit = new Vector<Session>(200);
        edit.add(currentSession);
        sessions.put(gameID, edit);
        Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR, "ERROR: Color already taken");
        announce(currentSession, errorMessage, gameID);

    }

    public void announce(Session session, ServerMessage message, int gameID) throws IOException {
        Vector<Session> sessionsVector = sessions.get(gameID);

        Gson gson = new Gson();
        String formattedMessage = gson.toJson(message);

        if (message.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            for (Session sesh : sessionsVector){
                if (sesh != session) {
                    sesh.getRemote().sendString(formattedMessage);
                }
            }
        }
        else if (message.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            String sendGame = gson.toJson(message);

            for (Session lSesh : sessionsVector) {
                try {
                    lSesh.getRemote().sendString(sendGame);
                }
                catch (IOException e){
                    System.out.println("error");
                }
            }
        }
        else if (message.getServerMessageType()== ServerMessage.ServerMessageType.ERROR){
                    session.getRemote().sendString(formattedMessage);
            }
        }

    }
