package service;

import dataAccess.DataAccessException;
import dataAccess.DataAccessFunctions;
import model.GameData;
import model.JoinData;
import model.GameCreationResult;
import model.*;

public class GameService {
    public static Boolean verifyToken(String token) {
        return DataAccessFunctions.verifyAuthToken(token);
    }

    public static Boolean verifyData(JoinData data){
        return DataAccessFunctions.verifyPlayerData(data);
    }

    public static GameList listGames(String token) {
        try {
            if (verifyToken(token)) {
                return new GameList(DataAccessFunctions.getGamesList(), null);
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
                    GameData gameStuff = DataAccessFunctions.createGame(gameData);
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
        catch (DataAccessException e) {
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

    public static void joinGame(JoinData joinData, String token)throws DataAccessException{
        if (verifyToken(token)) {
            if(verifyData(joinData)) {
                if (DataAccessFunctions.verifyGameID(joinData.gameID())) {
                    DataAccessFunctions.updateGame(joinData, token);
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

