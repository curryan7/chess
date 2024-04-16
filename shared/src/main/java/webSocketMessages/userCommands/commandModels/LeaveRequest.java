package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class LeaveRequest extends UserGameCommand {
    int gameID;

    public int getGameID() {
        return gameID;
    }


    public LeaveRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }
}
