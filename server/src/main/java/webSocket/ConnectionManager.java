package webSocket;

import org.eclipse.jetty.websocket.api.Session;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Vector<Connection>> connections = new ConcurrentHashMap<>();
    public void add(String auth, Session session, int gameID) {
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
    }
}
