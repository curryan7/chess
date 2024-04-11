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



public class WebsocketHandler {
    private static final ConnectionManager connections = new ConnectionManager();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, SQLException, DataAccessException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER:
                joinGameWS(session, message);
            case JOIN_OBSERVER:
                System.out.println("sad");
            case MAKE_MOVE:
                System.out.println("moved");
            case LEAVE:
                System.out.println("left");
            case RESIGN:
                System.out.println("resigned");
        }
    }

    public static void joinGameWS (Session session, String message) throws SQLException, DataAccessException, IOException {
        joinPlayer focusPlayer = new Gson().fromJson(message, joinPlayer.class);
        int gameID = focusPlayer.getGameID();
        ChessGame.TeamColor color = focusPlayer.getPlayerColor();

        String colorString = color.toString();
        String auth = focusPlayer.getAuthString();
        String gameString = MySqlDataAccess.grabGameByID(gameID);
        Gson gson = new Gson();
        ChessGame validGame = gson.fromJson(gameString, ChessGame.class);
        loadGame gotGame = new loadGame(ServerMessage.ServerMessageType.LOAD_GAME, validGame);
        connections.add(auth, session, gameID, colorString);
        session.getRemote().sendString(gson.toJson(gotGame));
    }


}
