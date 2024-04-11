package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.Notification;
import webSocketMessages.serverMessages.ServerMessageModels.loadGame;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    Connection wideConnection;
    public final ConcurrentHashMap<Integer, Vector<Connection>> connections = new ConcurrentHashMap<>();
    public void add(String auth, Session session, int gameID, String color) throws SQLException, DataAccessException, IOException {
        var connection = new Connection(auth, session);
        if (connections.get(gameID)!=null){
            Vector<Connection> edit = connections.get(gameID);
            edit.add(connection);
        }
        else{
            Connection nConnection = new Connection(auth, session);
            Vector<Connection> v = new Vector<Connection>( 200);
            v.add(nConnection);
            connections.put(gameID, v);
            v.remove(nConnection);
        }
        wideConnection = connection;

        String username = MySqlDataAccess.getUsername(auth);
        String gameString = MySqlDataAccess.grabGameByID(gameID);
        Gson gson = new Gson();
        ChessGame validGame = gson.fromJson(gameString, ChessGame.class);

        Notification addMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has joined as " + color);
        loadGame gotGame = new loadGame(ServerMessage.ServerMessageType.LOAD_GAME, validGame);
        announce(addMessage, gameID);
        announce(gotGame, gameID);
    }

    public void announce(ServerMessage message, int gameID) throws IOException {
        Vector<Connection> connectionsList = connections.get(gameID);

        Gson gson = new Gson();
        String formattedMessage = gson.toJson(message);

        for (Connection conn:connectionsList){
            if (conn != wideConnection){
                conn.session.getRemote().sendString(formattedMessage);
            }
        }

    }

}
