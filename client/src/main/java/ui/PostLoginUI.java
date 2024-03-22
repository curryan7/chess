package ui;

import model.*;
import org.glassfish.grizzly.http.server.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostLoginUI {
    public String authToken = ChessClient.authToken;
    public static ArrayList<GameData> wideListOfGames;


    public static UIState state = PreLoginUI.state;
    public static String logout(String... params) throws ResponseException {
        if(params.length==0){
            PreLoginUI.state = UIState.PRE_LOGIN;
            ServerFacade.logout();
            ChessClient.authToken=null;
            return "logout successful";
        }
        throw new ResponseException(400, "Extra Fields");
    }
    public static String createGame(String... params) throws ResponseException {
        if(params.length == 1){
            String gamename = params[0];

            GameData createSend = new GameData(0, null, null, gamename, null);
            GameCreationResult createFinish = ServerFacade.createGame(createSend);
            int gameID = createFinish.gameID();
            return "Game successfully created. Here is your gameID: "+ gameID;
        }
        throw new ResponseException(400, "Error: Fields are incorrect");
    }
    public static String listGames (String... params) throws ResponseException {
        if(params.length==0){
            GameList gamelist = ServerFacade.listgames();
            ArrayList<GameData> listOfGames = gamelist.games();
            wideListOfGames = listOfGames;
            int i =0;
            for (GameData game:listOfGames){
                i++;
                String name = game.gameName();
                int gameID = game.gameID();
                String wName = game.whiteUsername();
                String bName = game.blackUsername();
                System.out.println(i+") White: "+ wName + " || Black: "+ bName + " || Game Name: " + name +" || GameID: " + gameID);
            }
            return "--end of games list--";

        }
        throw new ResponseException(400, "Bad Fields");
    }
    public static String joinGame (String... params) throws ResponseException{
        if(params.length == 2){
            String playercolor = params[0];
            String SgameNum = params[1];
            int gameNum = Integer.parseInt(SgameNum)-1;
            GameData subjectGame = wideListOfGames.get(gameNum);
            int gameID = subjectGame.gameID();

            if (Objects.equals(playercolor, "white")){
                JoinData joinSend = new JoinData("WHITE", gameID);
                SuccessJoin joinResult = ServerFacade.joinGame(joinSend);
                return "Successfully joined as White Player";
            }
            else if(Objects.equals(playercolor, ".")){
                JoinData joinSend = new JoinData(null, gameID);
                SuccessJoin joinResult = ServerFacade.joinGame(joinSend);
                return "Successfully joined as Observer";
            }
            else {
                JoinData joinSend = new JoinData("BLACK", gameID);
                SuccessJoin joinResult = ServerFacade.joinGame(joinSend);
                return "Successfully joined as Black player";
            }

        }
        throw new ResponseException(400, "Bad Request");
    }
}
