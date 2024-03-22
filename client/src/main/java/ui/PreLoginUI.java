package ui;

import com.google.gson.Gson;
import model.*;
import ui.ResponseException;
import ui.UIState;

import java.util.Arrays;


public class PreLoginUI {

    public static UIState state;
    public static String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            String username = params[0];
            String password = params[1];
            UserData loginSend = new UserData(username, password, null);
            LoginResult loginFinish = ServerFacade.login(loginSend);
            String authToken = loginFinish.authToken();

            if(authToken != null){
                ChessClient.authToken = authToken;
                state = UIState.POST_LOGIN;
            }

            if(authToken == null){
                throw new ResponseException(400, "Missing Fields");
            }
        }
        return "you are now logged in";
    }

    public static String register(String... params) throws ResponseException {
        if (params.length>=2){
            String username = params[0];
            String password = params[1];
            String email = params[2];

            UserData registerSend = new UserData(username, password, email);
            RegisterResult registerFinish = ServerFacade.register(registerSend);

            String authToken = registerFinish.authToken();

            if(authToken != null){
                ChessClient.authToken = authToken;
                state = UIState.POST_LOGIN;
                return "you are now logged in as "+username;
            }
            else{
                throw new ResponseException(400, "Missing Fields");
            }
        }
        throw new ResponseException(400, "Missing Fields");
    }
}
