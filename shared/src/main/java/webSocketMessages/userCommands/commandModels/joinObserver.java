package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class joinObserver extends UserGameCommand {
    int gameID;

    public joinObserver(String auth, int gameID) {
        super(auth);
        this.gameID = gameID;
    }
}
