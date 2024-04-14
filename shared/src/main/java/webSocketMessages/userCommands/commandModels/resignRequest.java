package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class resignRequest extends UserGameCommand {
    int gameID;
    CommandType command;

    public int getGameID() {
        return gameID;
    }

    public CommandType getCommand() {
        return command;
    }

    public resignRequest(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.command = CommandType.RESIGN;
    }
}
