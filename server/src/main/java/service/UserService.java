package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import dataAccess.DataAccessFunctions;
import model.LoginResult;
import model.LogoutResult;
import model.RegisterResult;

public class UserService {
    public static Object getUser(UserData user) throws DataAccessException{
        String username = user.username();
        return DataAccessFunctions.grabUser(username);
    }
    public static AuthData createUser(UserData user) throws DataAccessException{
        if(DataAccessFunctions.createUser(user)){
            return createAuth(user);
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
    }
    public static AuthData createAuth(UserData user) throws DataAccessException{
        String username = user.username();
        return DataAccessFunctions.createAuthToken(username);
    }
    public static Boolean verifyData(UserData user) throws DataAccessException {
        return user.username() != null && user.password() != null && user.email() != null;
    }
    public static RegisterResult registerUser(UserData user) {
        try {
            if (verifyData(user)) {
                if (getUser(user) == null) {
                    AuthData tokenData = createUser(user);
                    return new RegisterResult(tokenData.username(), tokenData.authToken(), null);
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
                return new RegisterResult(null, null, "Error: bad request");
            }
            else if (e.getMessage().equals("Error: already taken")){
                return new RegisterResult(null, null, "Error: already taken");
            }
            return new RegisterResult(null, null, "Error: description");
        }
    }
    public static LoginResult loginUser(UserData user){
        String username = user.username();
        String password = user.password();

        try {
            if(username != null || password != null){
                if (getUser(user) != null) {
                    String existingPassword = DataAccessFunctions.grabPassword(username, password);
                    if (existingPassword.equals(password)) {
                        return new LoginResult(username, createAuth(user).authToken(), null);
                    }
                    else{
                        throw new DataAccessException("Error: unauthorized");
                    }
                }
                else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }
            else {
                throw new DataAccessException("Error: bad request");
            }
        }
        catch(DataAccessException e){
            if(e.getMessage().equals("Error: unauthorized")) {
                return new LoginResult(null, null, "Error: unauthorized");
            }
            else if(e.getMessage().equals("Error: bad request")){
                return new LoginResult(null, null, "Error: bad request");
            }
            else {
                return new LoginResult(null,null,"Error: description");
            }
        }
    }
    public static LogoutResult logoutUser(String authToken) {
        try {
            if (DataAccessFunctions.verifyAuthToken(authToken)) {
                DataAccessFunctions.deleteAuthToken(authToken);
                return new LogoutResult(null);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        }
        catch(DataAccessException e){
            return new LogoutResult("Error: unauthorized");
        }
    }
}
