package webSocket;
import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;

public class Connection {
    public String auth;
    public Session session;

    public Connection(String auth, Session session) {
        this.auth = auth;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
