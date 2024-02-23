package service;

import dataAccess.DataAccessException;
import model.UserData;
import spark.Request;
import spark.Response;
import dataAccess.dataAccessFunctions;

import javax.xml.crypto.Data;

public class registerService {
    public static Object getUser(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.grabUser(username);
    }

    public static Object createAuth(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.createAuthToken(username);
    }

    public static Object createUser(UserData user) throws DataAccessException{
        
        if(dataAccessFunctions.createUser(user)){
            return createAuth(user);
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
    }

    public static Object registerUser(UserData user) throws DataAccessException {
        // check if user exists
        if (getUser(user)==null){
            // if it does exist try to createUser
            return createUser(user);
        }
        else{
            throw new DataAccessException("Error: already taken");
        }
    }

}
