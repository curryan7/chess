package ui.commandModels;

import chess.ChessGame;

public record joinPlayer(int gameID, ChessGame.TeamColor playerColor) {
}
