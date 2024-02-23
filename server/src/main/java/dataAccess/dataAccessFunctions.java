package dataAccess;
import java.util.HashMap;
import java.util.Map;
public class dataAccessFunctions {
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, String> authTokens = new HashMap<>();
    private static Map<String, String> games = new HashMap<>();
    public static void clearoff() throws DataAccessException{
        users.clear();
        authTokens.clear();
        games.clear();
    };
}
