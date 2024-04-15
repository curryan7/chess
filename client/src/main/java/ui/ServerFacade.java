package ui;
import com.google.gson.Gson;
import model.*;
import java.io.*;
import java.net.*;


public class ServerFacade {
    public static String serverURL;

    public ServerFacade(String url){
        serverURL = url;
    }

    public static RegisterResult register(UserData req) throws ResponseException {
        var path = "/user";
        return makeRequest("POST", path, req, RegisterResult.class, false);
    }

    public static LoginResult login(UserData req) throws ResponseException {
        var path = "/session";
        return makeRequest("POST", path, req, LoginResult.class, false);
    }

    public static void logout(String... params) throws ResponseException {
        var path = "/session";
        makeRequest("DELETE", path, null, LogoutResult.class, true);
    }

    public static GameList listgames(String... params) throws ResponseException {
        var path = "/game";
        return makeRequest("GET", path, null, GameList.class, true);
    }

    public static SuccessJoin joinGame(JoinData req) throws ResponseException {
        var path = "/game";
        return makeRequest("PUT", path, req, SuccessJoin.class, true);
    }

    public static GameCreationResult createGame(GameData req) throws ResponseException {
        var path = "/game";
        return makeRequest("POST", path, req, GameCreationResult.class, true);
    }

    private static <T> T makeRequest(String method, String path, Object req, Class<T> responseClass, Boolean needToken) throws ResponseException {
        try{
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            if (needToken){
                http.addRequestProperty("authorization", ChessClient.authToken);
            }

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
