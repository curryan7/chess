package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
    int gameID;
    CommandType command;

    public int getGameID() {
        return gameID;
    }

    public CommandType getCommand() {
        return command;
    }

    public JoinObserver(String auth, int gameID) {
        super(auth);
        this.gameID = gameID;
        this.command = CommandType.JOIN_OBSERVER;
    }
}
