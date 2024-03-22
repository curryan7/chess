package ui;
import com.google.gson.Gson;
import model.*;
import java.io.*;
import java.net.*;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.util.ArrayList;
import java.util.List;

public class ServerFacade {
    public static String serverURL;

    public ServerFacade(String url){
        this.serverURL = url;
        System.out.println(serverURL);
    }

    public static RegisterResult register(UserData req) throws ResponseException {
        var path = "/user";
        return makeRequest("POST", path, req, RegisterResult.class);
    }

    public static LoginResult login(UserData req) throws ResponseException {
//        Gson gson = new Gson();
//        String loginSend = gson.toJson(req);
        var path = "/session";
        return makeRequest("POST", path, req, LoginResult.class);
    }

    public LoginResult logout(String req) throws ResponseException {
        var path = "/session";
        return makeRequest("DELETE", path, req, LoginResult.class);
    }

    public GameList listgames(Request req) throws ResponseException {
        var path = "/game";
        return makeRequest("GET", path, req, GameList.class);
    }

    public SuccessJoin joinGame(Request req) throws ResponseException {
        var path = "/game";
        return makeRequest("PUT", path, req, SuccessJoin.class);
    }

    public GameCreationResult createGame(Request req) throws ResponseException {
        var path = "/game";
        return makeRequest("POST", path, req, GameCreationResult.class);
    }

    private static <T> T makeRequest(String method, String path, Object req, Class<T> responseClass) throws ResponseException {
        try{
            System.out.println(ServerFacade.serverURL);
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (req!= null){
                http.addRequestProperty("Content-Type", "application/json");
                String sendData = new Gson().toJson(req);
                try (OutputStream reqBody = http.getOutputStream()){
                    reqBody.write(sendData.getBytes());
                }
            }
            http.connect();
            return readBody(http, responseClass);

        } catch (Exception e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

}
