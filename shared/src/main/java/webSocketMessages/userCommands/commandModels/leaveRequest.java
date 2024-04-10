package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class leaveRequest extends UserGameCommand {
    int gameID;

    public leaveRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
    }
}
