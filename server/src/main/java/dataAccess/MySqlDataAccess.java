package dataAccess;
import chess.ChessGame;
import com.google.gson.Gson;
import model.*;
import java.util.*;
import java.sql.*;



public class MySqlDataAccess {
    static Random rand = new Random();
    public MySqlDataAccess() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {

            var createUserTable = """
                    CREATE TABLE IF NOT EXISTS Users (
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        PRIMARY KEY (username)
                        )
                    """;
            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS Auths (
                        username VARCHAR(255) NOT NULL,
                        authToken VARCHAR(255) NOT NULL,
                        PRIMARY KEY (authToken)
                        )
                    """;
            var createGameTable = """
                    CREATE TABLE IF NOT EXISTS Games (
                        GameID INT NOT NULL,
                        whiteUserName VARCHAR(255),
                        blackUserName VARCHAR(255),
                        gameName VARCHAR(255),
                        game TEXT,
                        PRIMARY KEY (GameID)
                        )""";

            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }
    public static void clearoff() throws SQLException, DataAccessException {
        try(var conn = DatabaseManager.getConnection()){
            var clearUsersStatement = """
            TRUNCATE Users
            """;
            var clearGamesStatement = """
                    TRUNCATE Games""";
            var clearAuthsStatement = """
                    TRUNCATE Auths""";
            try (var updateUsersStatement = conn.prepareStatement(clearUsersStatement)){
                updateUsersStatement.executeUpdate();
            }
            try (var updateGamesStatement = conn.prepareStatement(clearGamesStatement)){
                updateGamesStatement.executeUpdate();
            }
            try (var updateAuthsStatement = conn.prepareStatement(clearAuthsStatement)){
                updateAuthsStatement.executeUpdate();
            }
        }
    }

    public static UserData grabUser(String username) throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = """
                    SELECT username,password,email FROM Users WHERE username=?
                    """;
            try (var grabUserStatement = conn.prepareStatement(statement)) {
                grabUserStatement.setString(1, username);
                try (ResultSet userResult = grabUserStatement.executeQuery()) {
                    if (userResult.next()) {
                        String uName = userResult.getString("username");
                        String password = userResult.getString("password");
                        String email = userResult.getString("email");
                        return new UserData(uName, password, email);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public static Boolean grabGame(String gamename) throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = """
                    SELECT GameID,whiteUserName,blackUsername,gameName,game FROM Games WHERE gameName=?
                    """;
            try (var grabGameStatement = conn.prepareStatement(statement)){
                grabGameStatement.setString(1, gamename);
                try (ResultSet gameResult = grabGameStatement.executeQuery()) {
                    //                        int gID = gameResult.getInt("GameID");
                    //                        String wUsername = gameResult.getString("whiteUsername");
                    //                        String bUsername = gameResult.getString("blackUsername");
                    //                        String gName = gameResult.getString("gameName");
                    //                        String cGame = gameResult.getString("game");
                    return !gameResult.next();
                }
            }
        }
    }

    public static Boolean createUser(UserData userinfo) throws DataAccessException {
        String uname = userinfo.username();
        String pword = userinfo.password();
        String mail = userinfo.email();

        try (var conn = DatabaseManager.getConnection()) {
            var createUserStatement = """
                            INSERT INTO Users (username, password, email) VALUES (?, ?, ?)
                    """;
            try (var UInsertStatement = conn.prepareStatement(createUserStatement, Statement.RETURN_GENERATED_KEYS)) {
                UInsertStatement.setString(1, uname);
                UInsertStatement.setString(2, pword);
                UInsertStatement.setString(3, mail);

                return UInsertStatement.executeUpdate() != 0;

            }

        } catch (SQLException e) {
            return null;
        }
    }

    public static AuthData createAuthToken(String username) throws DataAccessException {
        String authName = UUID.randomUUID().toString();
        try (var conn = DatabaseManager.getConnection()) {
            var createAuthStatement = """
                            INSERT INTO Auths (username, authToken) VALUES (?, ?)
                    """;
            try (var authInsertStatement = conn.prepareStatement(createAuthStatement)) {
                authInsertStatement.setString(1, username);
                authInsertStatement.setString(2, authName);

                authInsertStatement.executeUpdate();
                return new AuthData(username, authName);
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    public static String grabPassword(String username) throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            var createAuthStatement = """
                            SELECT password FROM Users WHERE username=?
                    """;
            try (var grabPasswordStatement = conn.prepareStatement(createAuthStatement)) {
                grabPasswordStatement.setString(1, username);
                ResultSet passwordResult = grabPasswordStatement.executeQuery();
                if(passwordResult.next()){
                    return passwordResult.getString("password");
                }
                else{
                    throw new DataAccessException("Error: bad request");
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    public static Boolean verifyAuthToken(String token) {
        try (var conn = DatabaseManager.getConnection()) {
            var grabAuthStatement = """
                            SELECT authToken FROM Auths WHERE authToken=?
                    """;
            try (var grabAStatement = conn.prepareStatement(grabAuthStatement)) {
                grabAStatement.setString(1, token);
                ResultSet authResult = grabAStatement.executeQuery();
                return authResult.next();
            }
        } catch (SQLException | DataAccessException e) {
            return false;
        }
    }

    public static void deleteAuthToken(String token) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var deleteAuthStatement = """
                            DELETE FROM Auths WHERE authToken=?;
                    """;
            try (var deleteStatement = conn.prepareStatement(deleteAuthStatement)) {
                deleteStatement.setString(1, token);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    public static ArrayList<GameData> getGamesList() throws DataAccessException {
        ArrayList<GameData> gameList = new ArrayList<>();

        try (var conn = DatabaseManager.getConnection()) {
            var createAuthStatement = """
                            SELECT GameID, whiteUserName, blackUserName, gameName, game FROM Games
                    """;
            try (var grabPasswordStatement = conn.prepareStatement(createAuthStatement)) {
                ResultSet gamesResult = grabPasswordStatement.executeQuery();
                while (gamesResult.next()) {
                    int GameID = gamesResult.getInt("GameID");
                    String whiteUsername = gamesResult.getString("whiteUserName");
                    String blackUsername = gamesResult.getString("blackUserName");
                    String gameName = gamesResult.getString("gameName");
                    String pregame = gamesResult.getString("game");
                    Gson gson = new Gson();
                    ChessGame game = gson.fromJson(pregame, ChessGame.class);
                    GameData gameObject = new GameData(GameID, whiteUsername, blackUsername, gameName, game);
                    gameList.add(gameObject);
                }
                return gameList;
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    public static GameData createGame(GameData gameData) throws DataAccessException, SQLException {
        String gameName = gameData.gameName();
        if(Boolean.TRUE.equals(grabGame(gameName))){
            int gameID = rand.nextInt(500);
            GameData finalGameData = new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game());
            Gson gson = new Gson();
            String gameString = gson.toJson(finalGameData.game());
            try (var conn = DatabaseManager.getConnection()) {
                var createUserStatement = """
                            INSERT INTO Games (GameID, whiteUserName, blackUserName, gameName, game) VALUES (?, ?, ?, ?, ?)
                    """;
                try (var GameInsertStatement = conn.prepareStatement(createUserStatement)) {
                    GameInsertStatement.setInt(1, gameID);
                    GameInsertStatement.setString(2, finalGameData.whiteUsername());
                    GameInsertStatement.setString(3, finalGameData.blackUsername());
                    GameInsertStatement.setString(4, finalGameData.gameName());
                    GameInsertStatement.setString(5, gameString);

                    GameInsertStatement.executeUpdate();
                    return finalGameData;
                }
            } catch (SQLException | DataAccessException e) {
                throw new DataAccessException("Error: bad request");
            }
        }
        else {
            throw new DataAccessException("Error: bad request");
        }


    }

    public static Boolean verifyGameID(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var grabGameIDStatement = """
                            SELECT GameID FROM Games WHERE GameID=?
                    """;
            try (var grabAStatement = conn.prepareStatement(grabGameIDStatement)) {
                grabAStatement.setInt(1, gameID);
                ResultSet authResult = grabAStatement.executeQuery();
                return authResult.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    public static Boolean verifyPlayerData(JoinData data){
        return Objects.equals(data.playerColor(), "WHITE") || (Objects.equals(data.playerColor(), "BLACK") || data.playerColor() == null);
    }

    public static void updateGame(JoinData joinData, String token) throws DataAccessException, SQLException {
        int gameID = joinData.gameID();
        try(var conn = DatabaseManager.getConnection()){
            var createGameStatement = """
                    SELECT whiteUserName, blackUserName, gameName, game FROM Games WHERE gameID=?
            """;
            var getAuthStatement = """
                    SELECT username FROM Auths WHERE authToken =?
            """;
            try(var grabGameStatement = conn.prepareStatement(createGameStatement)){
                grabGameStatement.setInt(1,gameID);
                ResultSet gameResult = grabGameStatement.executeQuery();
                if(gameResult.next()){
                    String blackUser = gameResult.getString("blackUserName");
                    String whiteUser = gameResult.getString("whiteUserName");

                    try(var grabAuthStatement = conn.prepareStatement(getAuthStatement)) {
                        grabAuthStatement.setString(1, token);
                        ResultSet authResult = grabAuthStatement.executeQuery();
                        if (authResult.next()) {
                            String username = authResult.getString("username");

                            if (Objects.equals(joinData.playerColor(), "WHITE")) {
                                if (whiteUser == null) {
                                    try (var preparedStatement = conn.prepareStatement(
                                            "UPDATE Games SET whiteUserName = ? WHERE GameID = ?")) {
                                        preparedStatement.setString(1, username);
                                        preparedStatement.setInt(2, gameID);
                                        preparedStatement.executeUpdate();
                                    }
                                }
                                else{
                                    throw new DataAccessException("Error: already taken");
                                }
                            } else if (Objects.equals(joinData.playerColor(), "BLACK")) {
                                if (blackUser == null) {
                                    try (var preparedStatement = conn.prepareStatement(
                                            "UPDATE Games SET blackUserName = ? WHERE GameID = ?")) {
                                        preparedStatement.setString(1, username);
                                        preparedStatement.setInt(2, gameID);
                                        preparedStatement.executeUpdate();
                                    }
                                }
                                else {
                                    throw new DataAccessException("Error: already taken");
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}


