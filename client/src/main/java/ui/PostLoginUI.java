package ui;

import chess.ChessGame;
import model.*;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Objects;

public class PostLoginUI {
    public static String authToken = ChessClient.authToken;
    public static ArrayList<GameData> wideListOfGames;
    public static int wideGameID;
    public static ChessGame.TeamColor widePlayerColor;


    public static UIState state = PreLoginUI.state;
    public static String logout(String... params) throws ResponseException {
        if(params.length==0){
            ServerFacade.logout();
            ChessClient.authToken = null;
            PreLoginUI.state = UIState.PRE_LOGIN;
            return "logout successful\n";
        }
        throw new ResponseException(400, "Extra Fields\n");
    }
    public static String createGame(String... params) throws ResponseException {
        if(params.length == 1){
            String gameName = params[0];

            GameData createSend = new GameData(0, null, null, gameName, null);
            GameCreationResult createFinish = ServerFacade.createGame(createSend);
            int gameID = createFinish.gameID();
            return "Game successfully created. Here is your gameID: "+ gameID + "\n";
        }
        throw new ResponseException(400, "Error: Fields are incorrect\n");
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
            return "--end of games list--\n";

        }
        throw new ResponseException(400, "Bad Fields\n");
    }
    public static String joinGame (String... params) throws Exception {
        if (params.length == 2) {
            String playerColor = params[0];
            String sGameNum = params[1];
            int gameNum = Integer.parseInt(sGameNum) - 1;
            GameData subjectGame = wideListOfGames.get(gameNum);
            int gameID = subjectGame.gameID();

            if (Objects.equals(playerColor, "white")) {
                JoinData joinSend = new JoinData("WHITE", gameID);
                ServerFacade.joinGame(joinSend);

                wideGameID = gameID;
                widePlayerColor = ChessGame.TeamColor.WHITE;
                PreLoginUI.state = UIState.IN_GAME;

                WSFacade.joinGame(PostLoginUI.authToken, wideGameID, widePlayerColor);
                return "\n";

            } else if (Objects.equals(playerColor, ".")) {
                JoinData joinSend = new JoinData(null, gameID);
                ServerFacade.joinGame(joinSend);
                PreLoginUI.state = UIState.IN_GAME;
                wideGameID = gameID;
                WSFacade.joinGame(PostLoginUI.authToken,gameID, null);
                return "Successfully joined as Observer\n";
            }
            else {
                JoinData joinSend = new JoinData("BLACK", gameID);
                ServerFacade.joinGame(joinSend);

                PreLoginUI.state = UIState.IN_GAME;
                wideGameID = gameID;
                widePlayerColor = ChessGame.TeamColor.BLACK;

                WSFacade.joinGame(PostLoginUI.authToken, wideGameID, widePlayerColor);
                return "Successfully joined as Black\n";
            }
        }
            throw new ResponseException(400, "Bad Request\n");
    }
}
