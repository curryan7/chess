package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class ResignRequest extends UserGameCommand {
    int gameID;


    public int getGameID() {
        return gameID;
    }


    public ResignRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }
}
