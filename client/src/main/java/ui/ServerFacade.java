package ui;
import com.google.gson.Gson;
import model.*;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.util.ArrayList;
import java.util.List;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(String url){
        serverURL = url;
    }

    public static RegisterResult register(UserData req) {
        Gson gson = new Gson();
        String registerSend = gson.toJson(req);
        var path = "/user";
        return makeRequest("POST", path, registerSend, RegisterResult.class);
    }

    public static LoginResult login(UserData req){
        Gson gson = new Gson();
        String loginSend = gson.toJson(req);
        var path = "/session";
        return makeRequest("POST", path, loginSend, LoginResult.class);
    }

    public LoginResult logout(String req){
        var path = "/session";
        return makeRequest("DELETE", path, req, LoginResult.class);
    }

    public GameList listgames(Request req){
        var path = "/game";
        return makeRequest("GET", path, req, GameList.class);
    }

    public SuccessJoin joinGame(Request req){
        var path = "/game";
        return makeRequest("PUT", path, req, SuccessJoin.class);
    }

    public GameCreationResult createGame(Request req){
        var path = "/game";
        return makeRequest("POST", path, req, GameCreationResult.class);
    }

    private static <T> T makeRequest(String method, String path, String req, Class<T> responseClass) {
        return null;
    }

}
