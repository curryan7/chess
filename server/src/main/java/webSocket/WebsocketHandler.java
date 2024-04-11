package webSocket;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.loadGame;
import webSocketMessages.userCommands.*;
import java.io.IOException;
import java.sql.SQLException;
import webSocketMessages.userCommands.commandModels.*;


@WebSocket
public class WebsocketHandler {
    private static final ConnectionManager connections = new ConnectionManager();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, SQLException, DataAccessException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER:
                joinGameWS(session, message);
                break;
            case JOIN_OBSERVER:
                joinObserver(session, message);
                break;
            case MAKE_MOVE:
                System.out.println("moved");
                break;
            case LEAVE:
                System.out.println("left");
                break;
            case RESIGN:
                System.out.println("resigned");
                break;
        }
    }

    public static void joinGameWS (Session session, String message) throws SQLException, DataAccessException, IOException {
        joinPlayer focusPlayer = new Gson().fromJson(message, joinPlayer.class);
        int gameID = focusPlayer.getGameID();
        ChessGame.TeamColor color = focusPlayer.getPlayerColor();

        String colorString = color.toString();
        String auth = focusPlayer.getAuthString();
        connections.add(auth, session, gameID, colorString);

        loadGame(gameID, session, colorString);
    }

    public static void joinObserver (Session session, String message) throws SQLException, DataAccessException, IOException {
        joinObserver focusObserver = new Gson().fromJson(message, joinObserver.class);
        int gameID = focusObserver.getGameID();
        String auth = focusObserver.getAuthString();
        connections.add(auth, session, gameID, null);
        loadGame(gameID,session, null);
    }

    public static void loadGame (int gameID, Session session, String color) throws IOException, SQLException, DataAccessException {
        String gameString = MySqlDataAccess.grabGameByID(gameID);
        Gson gson = new Gson();
        ChessGame validGame = gson.fromJson(gameString, ChessGame.class);
        loadGame gotGame = new loadGame(ServerMessage.ServerMessageType.LOAD_GAME, validGame);
        session.getRemote().sendString(gson.toJson(gotGame));
    }

}
