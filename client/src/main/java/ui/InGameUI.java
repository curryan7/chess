package ui;
import com.google.gson.Gson;
import webSocketMessages.userCommands.commandModels.leaveRequest;

public class InGameUI {
    // initialize the server
    // we need to send a message to websocket
    // receive message from the server
    // print out the message to the user
    // public static void draw(String... params){
    public static String leaveGame(String...params) throws ResponseException {
        leaveRequest lRequest = new leaveRequest(PostLoginUI.authToken, PostLoginUI.wideGameID);
        Gson gson = new Gson();
        gson.toJson(lRequest);
        // ^ send to handler
        return "Successfully joined as White Player\n";
    }
    public static void makeMove(String... params){

//        makeMove moveRequest = new makeMove(PostLoginUI.authToken, PostLoginUI.wideGameID, )
    }
    public static void resignGame(String... params){

    }
    public static void highlightMoves(String... params){

    }
}
