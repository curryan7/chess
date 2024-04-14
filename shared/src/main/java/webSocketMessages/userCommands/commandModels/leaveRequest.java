package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class leaveRequest extends UserGameCommand {
    int gameID;
    CommandType command;

    public int getGameID() {
        return gameID;
    }

    public CommandType getCommand() {
        return command;
    }

    public leaveRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.command = CommandType.LEAVE;
    }
}
