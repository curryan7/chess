package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import dataAccess.dataAccessFunctions;
import model.loginResult;
import model.logoutResult;
import model.registerResult;

public class userService {
    public static Object getUser(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.grabUser(username);
    }
    public static AuthData createUser(UserData user) throws DataAccessException{
        if(dataAccessFunctions.createUser(user)){
            return createAuth(user);
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
    }
    public static AuthData createAuth(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.createAuthToken(username);
    }
    public static Boolean verifyData(UserData user) throws DataAccessException {
        return user.username() != null && user.password() != null && user.email() != null;
    }
    public static registerResult registerUser(UserData user) {
        try {
            if (verifyData(user)) {
                if (getUser(user) == null) {
                    AuthData tokenData = createUser(user);
                    return new registerResult(tokenData.username(), tokenData.authToken(), null);
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            }
            else {
                throw new DataAccessException("Error: bad request");
            }
        }
        catch (DataAccessException e){
            if (e.getMessage().equals("Error: bad request")){
                return new registerResult(null, null, "Error: bad request");
            }
            else if (e.getMessage().equals("Error: already taken")){
                return new registerResult(null, null, "Error: already taken");
            }
            return new registerResult(null, null, "Error: description");
        }
    }
    public static loginResult loginUser(UserData user){
        String username = user.username();
        String password = user.password();
        try {
            if (getUser(user) != null) {
                String existingPassword = dataAccessFunctions.grabPassword(username, password);
                if (existingPassword.equals(password)) {
                    return new loginResult(username, createAuth(user).authToken(), null);
                }
                else{
                    throw new DataAccessException("Error: unauthorized");
                }
            }
            else {
                throw new DataAccessException("Error: bad request");
            }
        }
        catch(DataAccessException e){
            if(e.getMessage().equals("Error: unauthorized")) {
                return new loginResult(null, null, "Error: unauthorized");
            }
            else if(e.getMessage().equals("Error: bad request")){
                return new loginResult(null, null, "Error: bad request");
            }
            else {
                return new loginResult(null,null,"Error: description");
            }
        }
    }
    public static logoutResult logoutUser(String authToken) {
        try {
            if (dataAccessFunctions.verifyAuthToken(authToken)) {
                dataAccessFunctions.deleteAuthToken(authToken);
                return new logoutResult(null);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        }
        catch(DataAccessException e){
            return new logoutResult("Error: unauthorized");
        }
    }
}
