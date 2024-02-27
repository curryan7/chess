package service;

import dataAccess.DataAccessException;
import dataAccess.dataAccessFunctions;
import model.GameData;
import model.JoinData;
import model.gameCreationResult;
import model.gameList;

import java.util.ArrayList;
import java.util.List;

public class gameService {
    public static Boolean verifyToken(String token) {
        return dataAccessFunctions.verifyAuthToken(token);
    }

    public static Boolean verifyData(JoinData data){
        return dataAccessFunctions.verifyPlayerData(data);
    }

    public static gameList listGames(String token) throws DataAccessException {
        try {
            if (verifyToken(token)) {
                return new gameList(dataAccessFunctions.getGamesList(), null);
            }
            else {
                throw new DataAccessException("Error: unauthorized");
            }
        }
        catch (DataAccessException e){
            return new gameList(null, "Error: unauthorized");
        }
    }

    public static gameCreationResult createGame(String token, GameData gameData) {
        try {
            if (gameData.gameName() != null) {
                if (verifyToken(token)) {
                    GameData gameStuff = dataAccessFunctions.createGame(gameData);
                    return new gameCreationResult(gameStuff.gameID(), null);
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
                return new gameCreationResult(0, "Error: unauthorized");
            }
            else if(e.getMessage().equals("Error: bad request")){
                return new gameCreationResult(0,"Error: bad request");
            }
            else{
                return new gameCreationResult(0, "Error: description");
            }
            }
        }

    public static void joinGame(JoinData joinData, String token) {
        if (verifyToken(token) && verifyData(joinData)) {
            if (dataAccessFunctions.verifyGameID(joinData.gameID())) {
                dataAccessFunctions.updateGame(joinData);
            }
//            else {
//                throw new DataAccessException("Error: bad request");
//            }
//        }
//        else {
//            throw new DataAccessException("Error: bad request");
//        }
        }
    }
}

