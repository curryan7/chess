package dataAccess;
import model.UserData;
import model.AuthData;
import model.GameData;

import java.util.*;
import java.util.ArrayList;

public class dataAccessFunctions {
    private static Map<String, UserData> users = new HashMap<>();
    private static Map<String, AuthData> authTokens = new HashMap<>();
    private static Map<String, GameData> games = new HashMap<>();

    public static void clearoff() throws DataAccessException {
        users.clear();
        authTokens.clear();
        games.clear();
    }

    public static Object grabUser(String username) throws DataAccessException {
        return users.get(username);
    }

    public static Object grabPassword(String username, String password) throws DataAccessException {
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

    public static Object createAuthToken(String username) {
        AuthData authToken = new AuthData(username, UUID.randomUUID().toString());
        authTokens.put(username, authToken);
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

    public static ArrayList<GameData> getGamesList() throws DataAccessException{
        ArrayList<GameData> gameList = new ArrayList<>();
        gameList.addAll(games.values());
        return gameList;
    }
}
