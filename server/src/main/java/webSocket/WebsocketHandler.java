package webSocket;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.loadGame;
import webSocketMessages.userCommands.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import webSocketMessages.userCommands.commandModels.*;


@WebSocket
public class WebsocketHandler {
    private static final SessionManager sessions = new SessionManager();
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
        String userName = MySqlDataAccess.getUsername(auth);

        //grab game data
        GameData gameStuff = MySqlDataAccess.grabGameByID(gameID);

        //check what color the user is
        if (MySqlDataAccess.grabGameByID(gameID)!=null){
            switch (color) {
                case ChessGame.TeamColor.WHITE:
                    // see if the username is matching
                    assert gameStuff != null;
                    if (Objects.equals(userName, gameStuff.whiteUsername())) {
                        // if it is matching

                        // let all the other players know that they are connecting/connected
                        sessions.add(auth, session, gameID, colorString);

                        // get the game that they are about to play on
                        loadGame(auth, gameID, session, colorString);
                    } else {
                        //if it is not matching, then we send an error message
                        sessions.bounce(auth, session, gameID, colorString);
                    }
                    break;

                case ChessGame.TeamColor.BLACK:
                    assert gameStuff != null;
                    if (Objects.equals(userName, gameStuff.blackUsername())) {
                        sessions.add(auth, session, gameID, colorString);
                        loadGame(auth, gameID, session, colorString);
                    } else {
                        sessions.bounce(auth, session, gameID, colorString);
                    }
                    break;
            }
        }
        else{
            sessions.bounce(auth, session, gameID, colorString);
        }
    }

    public static void joinObserver (Session session, String message) throws SQLException, DataAccessException, IOException {
        joinObserver focusObserver = new Gson().fromJson(message, joinObserver.class);
        int gameID = focusObserver.getGameID();
        String auth = focusObserver.getAuthString();


        if (MySqlDataAccess.grabGameByID(gameID)==null || !MySqlDataAccess.verifyAuthToken(auth)){
            sessions.bounce(auth, session, gameID, null);
        }
        else{
            sessions.add(auth, session, gameID, null);
            loadGame(auth, gameID,session, null);
        }

    }

    public static void loadGame (String auth, int gameID, Session session, String color) throws IOException, SQLException, DataAccessException {
        GameData gameDat = MySqlDataAccess.grabGameByID(gameID);
        assert gameDat != null;
        ChessGame gameObject = gameDat.game();
        loadGame gotGame = new loadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameObject);
        Gson gson = new Gson();
        String sendGame = gson.toJson(gotGame);
        session.getRemote().sendString(sendGame);
    }
}
