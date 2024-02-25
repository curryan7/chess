package service;

import dataAccess.DataAccessException;
import dataAccess.dataAccessFunctions;
public class gameService {
    public static Object listGames(String token) throws DataAccessException {
        if(dataAccessFunctions.verifyAuthToken(token)){
            return dataAccessFunctions.getGamesList();
        }
        else{
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
