package webSocketMessages.userCommands.commandModels;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
    int gameID;

    public int getGameID() {
        return gameID;
    }


    public JoinObserver(String auth, int gameID) {
        super(auth);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }
}
