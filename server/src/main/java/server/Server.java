package server;

import dataAccess.DataAccessException;
import model.*;
import spark.*;
import com.google.gson.Gson;

import service.clearService;
import service.userService;
import service.gameService;

public class Server {
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // clear database
        Spark.delete("/db", this::clear);
        // registration
        Spark.post("/user", this::register);
        // login
        Spark.post("/session", this::login);
//        logout
        Spark.delete("/session", this::logout);
        // list games
        Spark.get("/game", this::listGames);
        // create games
        Spark.post("/game", this::createGame);
        // join game
        Spark.put("/game", this::joinGame);


        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request req, Response res) {
        //http request, deserialize, and send to service
        // receive response back from service
        // serialize the response
        // send it back to request
        Gson gson = new Gson();
        try {
            clearService.clearOut();
            res.status(200);
            return gson.toJson(res.body());
        } catch (DataAccessException e) {
            res.status(500);
            res.body(gson.toJson(e.getMessage()));
            return res.body();
        }
    }
    private Object register(Request req, Response res) {
        Gson gson = new Gson();

        UserData userData = gson.fromJson(req.body(), UserData.class);
        registerResult authToken = userService.registerUser(userData);
        if(authToken.message()==null){
            res.status(200);
        }
        else if (authToken.message().equals("Error: bad request")) {
            res.status(400);
        }
        else if (authToken.message().equals("Error: already taken")) {
            res.status(403);
        }

        res.body(gson.toJson(authToken));
        return res.body();
    }
    private Object login(Request req, Response res) {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(req.body(), UserData.class);
        loginResult authToken = userService.loginUser(userData);
        if (authToken.message() == null) {
            res.status(200);
        }
        else if (authToken.message().equals("Error: unauthorized")) {
            res.status(401);
        }
        else{
            res.status(500);
        }

        res.body(gson.toJson(authToken));
        return res.body();
    }
    private Object logout(Request req, Response res) {
        Gson gson = new Gson();

        String authTokenHeader = req.headers("authorization");
        if (authTokenHeader == null || authTokenHeader.isEmpty()){
            res.status(401);
            res.body(gson.toJson(new noHeader("Error: Unauthorized")));
            return res.body();
        }

        logoutResult logoutResponse = userService.logoutUser(authTokenHeader);

        if (logoutResponse.message() == null){
            res.status(200);
        }
        else if (logoutResponse.message().equals("Error: unauthorized")){
            res.status(401);
        }

        res.body(gson.toJson(logoutResponse));
        return res.body();
    }
    private Object listGames(Request req, Response res) {
        Gson gson = new Gson();

        String authTokenHeader = req.headers("authorization");
        if (authTokenHeader == null || authTokenHeader.isEmpty()){
            res.status(401);
            res.body(gson.toJson(new noHeader("Error: Unauthorized")));
            return res.body();
        }

            gameList GameList = gameService.listGames(authTokenHeader);

        if (GameList.message()== null) {
            res.status(200);

        }
        else if(GameList.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else {
            res.status(500);
        }

        return gson.toJson(GameList);
    }
    private Object createGame(Request req, Response res) {
        Gson gson = new Gson();
        GameData gameData = gson.fromJson(req.body(),GameData.class);

        String authTokenHeader = req.headers("authorization");
        if (authTokenHeader == null || authTokenHeader.isEmpty()){
            res.status(401);
            res.body(gson.toJson(new noHeader("Error: Unauthorized")));
            return res.body();
        }

        gameCreationResult result = gameService.createGame(authTokenHeader, gameData);

        if (result.message()==null){
            res.status(200);
            res.body(gson.toJson(result));
        }
        else{
            gameCreationFailure failedCreation = new gameCreationFailure(null,result.message());
            if(result.message().equals("Error: unauthorized")){
                res.status(401);
            }
            else if(result.message().equals("Error: bad request")){
                res.status(400);
            }
            else {
                res.status(500);
            }
            res.body(gson.toJson(failedCreation));
        }
        return res.body();

    }
    private Object joinGame(Request req, Response res) {
        Gson gson = new Gson();
        JoinData joinData = gson.fromJson(req.body(),JoinData.class);

        String authTokenHeader = req.headers("authorization");
        if (authTokenHeader == null || authTokenHeader.isEmpty()){
            res.status(401);
            res.body(gson.toJson(new noHeader("Error: Unauthorized")));
            return res.body();
        }

        try {
            gameService.joinGame(joinData, authTokenHeader);
            res.status(200);
            SuccessJoin successJoin = new SuccessJoin(null);
            return gson.toJson(successJoin);
        }
        catch (DataAccessException e){
            if (e.getMessage().equals("Error: unauthorized")){
                res.status(401);
                return gson.toJson(new SuccessJoin("Error: unauthorized"));
            }
            else if (e.getMessage().equals("Error: already taken")){
                res.status(403);
                return gson.toJson(new SuccessJoin("Error: already taken"));
            }
            else if (e.getMessage().equals("Error: bad request")){
                res.status(400);
                return gson.toJson(new SuccessJoin("Error: bad request"));
            }
            else{
                res.status(500);
                return gson.toJson(new SuccessJoin("Error: description"));
            }
        }
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}