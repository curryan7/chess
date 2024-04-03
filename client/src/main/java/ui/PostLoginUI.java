package ui;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;
import java.util.Objects;

public class PostLoginUI {
    public String authToken = ChessClient.authToken;
    public static ArrayList<GameData> wideListOfGames;
    public static int wideGameID;
    public static ChessGame.TeamColor currentPlayerColor;


    public static UIState state = PreLoginUI.state;
    public static String logout(String... params) throws ResponseException {
        if(params.length==0){
            ServerFacade.logout();
            ChessClient.authToken = null;
            PreLoginUI.state = UIState.PRE_LOGIN;
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
                System.out.println(i+")|| Game Name:" + name + "|| White:" + wName + " || Black: "+ bName +" || GameID: " + gameID +"\n");
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
                wideGameID = gameID;
                currentPlayerColor = ChessGame.TeamColor.WHITE;

                PreLoginUI.state = UIState.IN_GAME;

                return "Successfully joined as White Player\n" ;
            }

            else if(Objects.equals(playercolor, ".")){
                JoinData joinSend = new JoinData(null, gameID);
                SuccessJoin joinResult = ServerFacade.joinGame(joinSend);
                PreLoginUI.state = UIState.IN_GAME;
                wideGameID = gameID;
                return "Successfully joined as Observer\n";
            }

            else {
                JoinData joinSend = new JoinData("BLACK", gameID);
                SuccessJoin joinResult = ServerFacade.joinGame(joinSend);
                PreLoginUI.state = UIState.IN_GAME;
                wideGameID = gameID;
                return "Successfully joined as Black player\n";
            }

        }
        throw new ResponseException(400, "Bad Request");
    }
}
