package ui;

import com.google.gson.Gson;
import model.*;
import ui.ResponseException;
import ui.UIState;

import java.util.Arrays;


public class PreLoginUI {

    private static UIState state = UIState.PRE_LOGIN;
    public static String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = UIState.POST_LOGIN;
            String username = params[1];
            String password = params[2];
            UserData loginSend = new UserData(username, password, null);
            LoginResult loginFinish = ServerFacade.login(loginSend);
            String authToken = loginFinish.authToken();

            if(authToken != null){
                System.out.println("you are now logged in as "+username);
            }
            else{
                throw new ResponseException(400, "Missing Fields");
            }
        }
        throw new ResponseException(400, "Missing Fields ");
    }

    public static String register(String... params) throws ResponseException {
        if (params.length>=2){
            state = UIState.POST_LOGIN;
            String username = params[1];
            String password = params[2];
            String email = params[3];

            UserData registerSend = new UserData(username, password, email);
            RegisterResult registerFinish = ServerFacade.register(registerSend);
            String authToken = registerFinish.authToken();

            if(authToken != null){
                System.out.println("you are now logged in as "+username);
            }
            else{
                throw new ResponseException(400, "Missing Fields");
            }
        }
        throw new ResponseException(400, "Missing Fields");
    }
}
