package webSocket;

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

public class SessionManager {
    Session wideSession;
    public final ConcurrentHashMap<Integer, Vector<Session>> connections = new ConcurrentHashMap<>();
    public void add(String auth, Session session, int gameID, String color) throws SQLException, DataAccessException, IOException {
//        Connection connection = new Connection(auth, session);
        if (connections.get(gameID)!=null){
            Vector<Session> edit = connections.get(gameID);
            edit.add(session);
        }
        else{
            Vector<Session> edit = new Vector<Session>( 200);
            edit.add(session);
            connections.put(gameID, edit);
        }
        wideSession = session;

        String username = MySqlDataAccess.getUsername(auth);
        GameData gameObject = MySqlDataAccess.grabGameByID(gameID);
        assert gameObject != null;

        Notification addMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has joined as " + color);
        announce(addMessage, gameID);

    }

    public void bounce(String auth, Session session, int gameID, String color) throws IOException {
//        Connection connection = new Connection(auth, session);
        Vector<Session> edit = new Vector<Session>( 200);
        edit.add(session);
        connections.put(gameID, edit);
        Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR, "ERROR: Color already taken");
        announce(errorMessage, gameID);
    }

    public void announce(ServerMessage message, int gameID) throws IOException {
        Vector<Session> connectionsList = connections.get(gameID);

        Gson gson = new Gson();
        String formattedMessage = gson.toJson(message);

        for (Session sesh:connectionsList){
            if (sesh != wideSession){
                sesh.getRemote().sendString(formattedMessage);
            }
            else if (message.getServerMessageType()==ServerMessage.ServerMessageType.LOAD_GAME){
                sesh.getRemote().sendString(formattedMessage);
            }
            else if (message.getServerMessageType()== ServerMessage.ServerMessageType.ERROR){
                if(sesh == wideSession){
                    sesh.getRemote().sendString(formattedMessage);
                }
            }
        }

    }
}
