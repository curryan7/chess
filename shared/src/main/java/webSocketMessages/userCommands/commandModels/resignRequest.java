package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class resignRequest extends UserGameCommand {
    int gameID;
    CommandType command;
    public resignRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.command = CommandType.RESIGN;
    }
}
