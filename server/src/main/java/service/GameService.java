package service;

import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.GameData;
import model.JoinData;
import model.GameCreationResult;
import model.*;

import java.sql.SQLException;

public class GameService {
    public static Boolean verifyToken(String token) {
        return MySqlDataAccess.verifyAuthToken(token);
    }

    public static Boolean verifyData(JoinData data){
        return MySqlDataAccess.verifyPlayerData(data);
    }

    public static GameList listGames(String token) {
        try {
            if (verifyToken(token)) {
                return new GameList(MySqlDataAccess.getGamesList(), null);
            }
            else {
                throw new DataAccessException("Error: unauthorized");
            }
        }
        catch (DataAccessException e){
            return new GameList(null, "Error: unauthorized");
        }
    }

    public static GameCreationResult createGame(String token, GameData gameData) {
        try {
            if (gameData.gameName() != null) {
                if (verifyToken(token)) {
                    GameData gameStuff = MySqlDataAccess.createGame(gameData);
                    return new GameCreationResult(gameStuff.gameID(), null);
                }
                else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }
            else {
                throw new DataAccessException("Error: bad request");
                }
        }
        catch (DataAccessException | SQLException e) {
            if (e.getMessage().equals("Error: unauthorized")){
                return new GameCreationResult(0, "Error: unauthorized");
            }
            else if(e.getMessage().equals("Error: bad request")){
                return new GameCreationResult(0,"Error: bad request");
            }
            else{
                return new GameCreationResult(0, "Error: description");
            }
            }
        }
    public static void joinGame(JoinData joinData, String token) throws DataAccessException, SQLException {
        if (verifyToken(token)) {
            if(verifyData(joinData)) {
                if (MySqlDataAccess.verifyGameID(joinData.gameID())) {
                    MySqlDataAccess.joinGame(joinData, token);
                }
                else {
                    throw new DataAccessException("Error: bad request");
                }
            }
            else {
                throw new DataAccessException("Error: bad request");
            }
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}

