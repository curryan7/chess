package dataAccess;
import model.UserData;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class dataAccessFunctions {
    private static Map<String, UserData> users = new HashMap<>();
    private static Map<String, AuthData> authTokens = new HashMap<>();
    private static Map<String, GameData> games = new HashMap<>();
    public static void clearoff() throws DataAccessException{
        users.clear();
        authTokens.clear();
        games.clear();
    };
    public static Object grabUser(String username) throws DataAccessException{
        return users.get(username);
    }

    public static Boolean createUser(UserData userinfo){
        String username = userinfo.username();
        if(users.containsKey(username)){
            return false;
        }
        else {
            users.put(username, userinfo);
            return true;
        }
    }

    public static Object createAuthToken(String username){
        AuthData authToken = new AuthData(username, UUID.randomUUID().toString());
        authTokens.put(username, authToken);
        return authToken;
    }
}
