package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class LeaveRequest extends UserGameCommand {
    int gameID;
    CommandType command;

    public int getGameID() {
        return gameID;
    }

    public CommandType getCommand() {
        return command;
    }

    public LeaveRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.command = CommandType.LEAVE;
    }
}
