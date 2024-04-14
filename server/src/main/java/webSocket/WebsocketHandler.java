package webSocket;
import chess.*;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.ServerMessageModels.Notification;
import webSocketMessages.serverMessages.ServerMessageModels.LoadGame;
import webSocketMessages.userCommands.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import webSocketMessages.userCommands.commandModels.*;


@WebSocket
public class WebsocketHandler {
    private static final SessionManager sessions = new SessionManager();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, SQLException, DataAccessException, InvalidMoveException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER:
                joinGameWS(session, message);
                break;
            case JOIN_OBSERVER:
                joinObserver(session, message);
                break;
            case MAKE_MOVE:
                makeMove(session, message);
                break;
            case LEAVE:
                leave(session, message);
                break;
            case RESIGN:
                resign(session, message);
                break;
        }
    }

    static ChessGame.TeamColor wideColor;
    static ChessGame.TeamColor otherColor;
    static boolean gameFinished= false;
    static boolean resignToken = false;


    public static void setColor(String userName, GameData gameInfo){
        if (Objects.equals(userName, gameInfo.whiteUsername())) {
            wideColor = ChessGame.TeamColor.WHITE;
            otherColor = ChessGame.TeamColor.BLACK;
        }
        else {
            wideColor = ChessGame.TeamColor.BLACK;
            otherColor = ChessGame.TeamColor.WHITE;
        }
    }

    public static Boolean isObserver(String userName, GameData gameInfo){
        return !Objects.equals(userName, gameInfo.blackUsername()) || !Objects.equals(userName, gameInfo.whiteUsername());
    }

    public static void joinGameWS (Session session, String message) throws SQLException, DataAccessException, IOException {
        JoinPlayer focusPlayer = new Gson().fromJson(message, JoinPlayer.class);
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
                        sessions.add(focusPlayer.getCommandType(),auth, session, gameID, colorString);

                        // get the game that they are about to play on
                        loadNewGame(auth, gameID, session);
                        wideColor = ChessGame.TeamColor.WHITE;
                    } else {
                        //if it is not matching, then we send an error message
                        sessions.bounce(auth, session, gameID, colorString);
                    }
                    break;

                case ChessGame.TeamColor.BLACK:
                    assert gameStuff != null;
                    if (Objects.equals(userName, gameStuff.blackUsername())) {
                        sessions.add(focusPlayer.getCommandType(),auth, session, gameID, colorString);
                        loadNewGame(auth,gameID, session);
                        wideColor = ChessGame.TeamColor.BLACK;
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
        JoinObserver focusObserver = new Gson().fromJson(message, JoinObserver.class);
        int gameID = focusObserver.getGameID();
        String auth = focusObserver.getAuthString();


        if (MySqlDataAccess.grabGameByID(gameID)==null || !MySqlDataAccess.verifyAuthToken(auth)){
            sessions.bounce(auth, session, gameID, null);
        }
        else{
            sessions.add(focusObserver.getCommandType(),auth, session, gameID, null);
            loadNewGame(auth,gameID,session);
        }

    }

    public static void makeMove (Session session, String message) throws SQLException, DataAccessException, InvalidMoveException, IOException {


        MakeMove moveData = new Gson().fromJson(message, MakeMove.class);
        String auth = moveData.getAuthString();
        int gameID = moveData.getGameID();
        ChessMove madeMove = moveData.getMove();
        GameData gotGame = MySqlDataAccess.grabGameByID(moveData.getGameID());

        assert gotGame != null;
        ChessGame gameObject =gotGame.game();


        // to make a move
        // you need to make sure that the piece color and player color is the same x
        // make sure that the move is valid x
        // make sure that the game has not finished already x
        // make sure that the observer is not trying to move any pieces

            try {
                String userName = MySqlDataAccess.getUsername(auth);
                setColor(userName, gotGame);
                ChessGame.TeamColor turn = gotGame.game().getTeamTurn();
                ChessPosition startPosition = madeMove.getStartPosition();

                ChessGame.TeamColor pieceColor = gotGame.game().getBoard().getPiece(startPosition).getTeamColor();

                if (wideColor != pieceColor){
                    throw new InvalidMoveException();
                }

                if (!Objects.equals(userName, gotGame.whiteUsername()) && !Objects.equals(userName, gotGame.blackUsername().toString())){
                    throw new InvalidMoveException();
                }

                if (gameFinished){
                    throw new InvalidMoveException();
                }


                gameObject.makeMove(madeMove);
                if (gotGame.game().isInCheckmate(wideColor) || gotGame.game().isInCheckmate(otherColor)|| gotGame.game().isInStalemate(wideColor)|| gotGame.game().isInStalemate(otherColor)){
                    gameFinished = true;
                }

                Gson gson = new Gson();
                String gameString = gson.toJson(gameObject);

                MySqlDataAccess.updateBoard(gameString, gameID);

                loadGame(auth, gameID, session);
                Notification addMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, wideColor.toString() + "moved a piece");
                sessions.announce(session, addMessage, gameID);

            } catch (InvalidMoveException e) {
                sessions.bounce(auth, session, gameID, null);
            }
        }

    public static void loadNewGame(String auth, int gameID, Session session) throws IOException, SQLException, DataAccessException {
        GameData gameDat = MySqlDataAccess.grabGameByID(gameID);
        assert gameDat != null;
        ChessGame gameObject = gameDat.game();
        LoadGame gotGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameObject);
        Gson gson = new Gson();
        String sendGame = gson.toJson(gotGame);
        session.getRemote().sendString(sendGame);
    }

    public static void loadGame(String auth, int gameID, Session session) throws SQLException, DataAccessException, IOException {
        GameData gameDat = MySqlDataAccess.grabGameByID(gameID);
        assert gameDat != null;
        ChessGame gameObject = gameDat.game();
        LoadGame gotGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameObject);
        Gson gson = new Gson();
        String sendGame = gson.toJson(gotGame);
//        session.getRemote().sendString(sendGame);
        sessions.announce(session, gotGame,gameID);
//        sessions.announce(session, gotGame,gameID);

    }

    public static void resign(Session session, String message) throws SQLException, DataAccessException, IOException {

        ResignRequest resignationObject = new Gson().fromJson(message, ResignRequest.class);
        String auth = resignationObject.getAuthString();
        int gameID = resignationObject.getGameID();
        String username = MySqlDataAccess.getUsername(auth);
        GameData gameObject = MySqlDataAccess.grabGameByID(gameID);
        assert gameObject != null;
        setColor(username, gameObject);


        if (!resignToken){
            resignToken = true;

            Notification resignationMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has resigned");
            sessions.announce(session, resignationMessage,gameID);
            session.getRemote().sendString(new Gson().toJson(resignationMessage));
        }
        else {
            sessions.bounce(auth,session,gameID,null);
        }
    }

    public static void leave (Session session, String message) throws SQLException, DataAccessException, IOException {
        LeaveRequest leaver = new Gson().fromJson(message, LeaveRequest.class);
        String auth = leaver.getAuthString();
        int gameID = leaver.getGameID();
        String username = MySqlDataAccess.getUsername(auth);
        GameData gotGame = MySqlDataAccess.grabGameByID(gameID);

        assert gotGame != null;
        setColor(username, gotGame);
        try{
            MySqlDataAccess.leaveGame(wideColor,null, gameID);
            Notification leaveMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION,username + " has left the game.");
            sessions.announce(session,leaveMessage,gameID);
            sessions.delete(session,gameID);
        }
        catch (Exception e){
            Notification leaveMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION,username + " has left the game.");
            sessions.announce(session,leaveMessage,gameID);
        }






    }
}
