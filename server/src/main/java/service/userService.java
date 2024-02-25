package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import dataAccess.dataAccessFunctions;

public class userService {
    public static Object getUser(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.grabUser(username);
    }
    public static Object createUser(UserData user) throws DataAccessException{
        if(dataAccessFunctions.createUser(user)){
            return createAuth(user);
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
    }
    public static Object createAuth(UserData user) throws DataAccessException{
        String username = user.username();
        return dataAccessFunctions.createAuthToken(username);
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

    public static Object loginUser(UserData user) throws DataAccessException {
        String username = user.username();
        String password = user.password();

        if(getUser(user)!=null){
            if(dataAccessFunctions.grabPassword(username, password)!= null){
                if(dataAccessFunctions.getAuthToken(username)==null){
                    return createAuth(user);
                }
                else{
                    return dataAccessFunctions.getAuthToken(username);
                }
            }
            else{
                throw new DataAccessException("Error: unauthorized");
            }
        }
        else{
            throw new DataAccessException("Error: description");
        }
    }

    public static void logoutUser(String authToken) throws DataAccessException {
        if(dataAccessFunctions.verifyAuthToken(authToken)){
            dataAccessFunctions.deleteAuthToken(authToken);
        }
        else{
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
