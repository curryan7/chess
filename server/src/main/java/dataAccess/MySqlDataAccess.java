package dataAccess;
import model.*;
import java.util.*;
import java.sql.*;
import dataAccess.DatabaseManager;

public class MySqlDataAccess {
    public MySqlDataAccess() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        try(var conn=DatabaseManager.getConnection()){
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            conn.setCatalog("chess");

            var createUserTable = """
                    CREATE TABLE IF NOT EXISTS Users (
                        username INT,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        PRIMARY KEY (username)
                        )
                    """;

            try(var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }
    public static void clearoff() throws SQLException, DataAccessException {
        try(var conn = DatabaseManager.getConnection()){
            conn.setCatalog("Users");
        }
        var statement = "TRUNCATE Users";
//        var statement2 = "TRUNCATE authTokens";
//        var statement3 = "TRUNCATE games";
    }

    public UserData grabUser(String username) {
        var statement = "SELECT username FROM users";
        return null;
    }
}
