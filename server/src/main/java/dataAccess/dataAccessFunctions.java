package dataAccess;

import chess.ChessGame;
import model.JoinData;
import model.UserData;
import model.AuthData;
import model.GameData;
import java.util.Random;

import java.util.*;
import java.util.ArrayList;

public class dataAccessFunctions {
    static Random rand = new Random();
    private static Map<String, UserData> users = new HashMap<>();
    private static Map<String, AuthData> authTokens = new HashMap<>();
    private static Map<Integer, GameData> games = new HashMap<>();

    public static void clearoff() throws DataAccessException {
        users.clear();
        authTokens.clear();
        games.clear();
    }

    public static Object grabUser(String username) throws DataAccessException {
        return users.get(username);
    }

    public static String grabPassword(String username, String password) throws DataAccessException {
        UserData userinfo = users.get(username);
        return userinfo.password();
    }

    public static Boolean createUser(UserData userinfo) {
        String username = userinfo.username();
        if (users.containsKey(username)) {
            return false;
        } else {
            users.put(username, userinfo);
            return true;
        }
    }

    public static AuthData createAuthToken(String username) {
        String authName = UUID.randomUUID().toString();
        AuthData authToken = new AuthData(username, authName);
        authTokens.put(authName, authToken);
        return authToken;
    }

    public static Object getAuthToken(String username) {
        return authTokens.get(username);
    }

    public static Boolean verifyAuthToken(String token) {
        for(AuthData entry : authTokens.values()){
            if(Objects.equals(entry.authToken(), token)){
                return true;
            }
        }
        return false;
    }

    public static void deleteAuthToken(String token) {
        Iterator<Map.Entry<String, AuthData>> iterator = authTokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, AuthData> entry = iterator.next();
            if (entry.getValue().authToken().equals(token)) {
                iterator.remove();
                break;
            }
        }
    }

    public static ArrayList<GameData> getGamesList() {
        ArrayList<GameData> gameList = new ArrayList<>(games.values());
        return gameList;
    }

    public static GameData createGame(GameData gameData) {
        int gameID = rand.nextInt(500);
        GameData finalGameData = new GameData(gameID,gameData.whiteUsername(),gameData.blackUsername(),gameData.gameName(),gameData.game());
        games.put(gameID, finalGameData);
        return finalGameData;
    }

    public static Boolean verifyGameID(int gameID){
        return games.get(gameID) != null;
    }

    public static Boolean verifyPlayerData(JoinData data){
        return ((Objects.equals(data.playerColor(), "WHITE") || (Objects.equals(data.playerColor(), "BLACK") || data.playerColor()==null)));
    }

    public static void updateGame(JoinData joinData, String token) throws DataAccessException {
        int gameID = joinData.gameID();
        GameData gameData = games.get(gameID);
        String blackUser = gameData.blackUsername();
        String whiteUser = gameData.whiteUsername();
        String gameName = gameData.gameName();
        ChessGame chessGame = gameData.game();

        AuthData tokenInfo = authTokens.get(token);
        String playerName = tokenInfo.username();

        if(Objects.equals(joinData.playerColor(), "WHITE")){
            if (whiteUser==null){
                GameData updatedGame = new GameData(gameID, playerName, blackUser, gameName, chessGame);
                games.replace(gameID, updatedGame);
            }
            else{
                throw new DataAccessException("Error: already taken");
            }
        }
        else if (Objects.equals(joinData.playerColor(), "BLACK")){
            if (blackUser==null) {
                GameData updatedGame = new GameData(gameID, whiteUser, playerName, gameName, chessGame);
                games.replace(gameID, updatedGame);
            }
            else{
                throw new DataAccessException("Error: already taken");
            }
        }
        // add code here for spectators
    }
}
