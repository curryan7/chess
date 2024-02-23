package model;

import chess.ChessGame;

public record GameData() {
    public static int GameID;
    public static String whiteUsername;
    public static String blackUsername;
    public static String gameName;
    public static ChessGame game;
}
