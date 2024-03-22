package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessDesign {
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    static ChessGame game;


//    public static void main(String[] args) {
//        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//
//        out.print(ERASE_SCREEN);
//        boolean running = true;
//
//        while(running) {
//            System.out.printf("Welcome to Chess. Type 'help' to get started%n>>> ");
//            Scanner scanner = new Scanner(System.in);
//            String line = scanner.nextLine();
//
//            var tokens = line.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens [0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//
//            if (cmd.equals("help")) {
//                System.out.print("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\nlogin <USERNAME> <PASSWORD> - to play chess\nquit - to exit the program\nhelp - to make this list reappear\n >>>");
//                line = scanner.nextLine();
//
//                switch(line){
//                    case "help":
//                        System.out.print("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\nlogin <USERNAME> <PASSWORD> - to play chess\nquit - to exit the program\nhelp - to make this list reappear\n >>>");
//                        break;
//                    case "quit":
//                        System.out.println("Exiting Chess. Goodbye!");
//                        running = false;
//                        break;
//                    case"login":
//
//                }
//            }
//            System.out.println("Invalid command. Type 'help' for available commands.");
//
//
//        }
//
//        drawThemHorizontalAxis(out,1);
//        drawChessBoard(out, 1, game);
//        drawThemHorizontalAxis(out, 1);
//
//
//        drawThemHorizontalAxis(out, 2);
//        drawChessBoard(out,2, game);
//        drawThemHorizontalAxis(out, 2);
//
//    }

    private static void drawThemHorizontalAxis(PrintStream out, int orientation) {
        if (orientation ==1 ) {
            String[] headers = {"   ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", "   "};
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                drawHorizontalAxis(out, headers[boardCol]);
            }
        }
        else
            if(orientation == 2){
                String[] headers = {"   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", "   "};
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    drawHorizontalAxis(out, headers[boardCol]);
                }
            }
        System.out.println(RESET_BG_COLOR);
    }

    private static void drawHorizontalAxis(PrintStream out, String headerText){
        printHeaderText(out, headerText);
    }

    private static void drawChessBoard(PrintStream out, int orientation, ChessGame game) {
        for (int boardRow = 0; boardRow <= 7; ++boardRow) {
            for (int boardCol = 0; boardCol <= 9; ++boardCol) {
                    if (boardCol == 0 || boardCol == 9) {
                        if (orientation == 1){
                            switch(boardRow){
                                case 0:
                                    printerMarginText(out, " 1 ");
                                    break;
                                case 1:
                                    printerMarginText(out, " 2 ");
                                    break;
                                case 2:
                                    printerMarginText(out, " 3 ");
                                    break;
                                case 3:
                                    printerMarginText(out, " 4 ");
                                    break;
                                case 4:
                                    printerMarginText(out, " 5 ");
                                    break;
                                case 5:
                                    printerMarginText(out, " 6 ");
                                    break;
                                case 6:
                                    printerMarginText(out, " 7 ");
                                    break;
                                case 7:
                                    printerMarginText(out, " 8 ");
                                    break;
                            }
                        }
                        else if (orientation == 2){
                            switch(boardRow){
                                case 0:
                                    printerMarginText(out, " 8 ");
                                    break;
                                case 1:
                                    printerMarginText(out, " 7 ");
                                    break;
                                case 2:
                                    printerMarginText(out, " 6 ");
                                    break;
                                case 3:
                                    printerMarginText(out, " 5 ");
                                    break;
                                case 4:
                                    printerMarginText(out, " 4 ");
                                    break;
                                case 5:
                                    printerMarginText(out, " 3 ");
                                    break;
                                case 6:
                                    printerMarginText(out, " 2 ");
                                    break;
                                case 7:
                                    printerMarginText(out, " 1 ");
                                    break;
                            }
                        }
                }

                else if ((boardRow + boardCol) % 2 == 0) {
                    setWhite(out);
                    switch (getPieceColor(game, boardRow, boardCol)){
                        case WHITE:
                            switch(getSquarePiece(game, boardRow, boardCol)){
                                case BISHOP:
                                    out.print(WHITE_BISHOP);
                                    break;
                                case ROOK:
                                    out.print(WHITE_ROOK);
                                    break;
                                case KNIGHT:
                                    out.print(WHITE_KNIGHT);
                                    break;
                                case KING:
                                    out.print(WHITE_KING);
                                    break;
                                case QUEEN:
                                    out.print(WHITE_QUEEN);
                                    break;
                                case PAWN:
                                    out.print(WHITE_PAWN);
                                    break;
                            }
                        case BLACK:
                            switch(getSquarePiece(game, boardRow, boardCol)){
                                case BISHOP:
                                    out.print(BLACK_BISHOP);
                                    break;
                                case ROOK:
                                    out.print(BLACK_ROOK);
                                    break;
                                case KNIGHT:
                                    out.print(BLACK_KNIGHT);
                                    break;
                                case KING:
                                    out.print(BLACK_KING);
                                    break;
                                case QUEEN:
                                    out.print(BLACK_QUEEN);
                                    break;
                                case PAWN:
                                    out.print(BLACK_PAWN);
                                    break;
                            }
                    }
                    out.print("   ");
                } else {
                    setBlack(out);
                        switch (getPieceColor(game, boardRow, boardCol)){
                            case WHITE:
                                switch(getSquarePiece(game, boardRow, boardCol)){
                                    case BISHOP:
                                        out.print(WHITE_BISHOP);
                                        break;
                                    case ROOK:
                                        out.print(WHITE_ROOK);
                                        break;
                                    case KNIGHT:
                                        out.print(WHITE_KNIGHT);
                                        break;
                                    case KING:
                                        out.print(WHITE_KING);
                                        break;
                                    case QUEEN:
                                        out.print(WHITE_QUEEN);
                                        break;
                                    case PAWN:
                                        out.print(WHITE_PAWN);
                                        break;
                                }

                            case BLACK:
                                switch(getSquarePiece(game, boardRow, boardCol)){
                                    case BISHOP:
                                        out.print(BLACK_BISHOP);
                                        break;
                                    case ROOK:
                                        out.print(BLACK_ROOK);
                                        break;
                                    case KNIGHT:
                                        out.print(BLACK_KNIGHT);
                                        break;
                                    case KING:
                                        out.print(BLACK_KING);
                                        break;
                                    case QUEEN:
                                        out.print(BLACK_QUEEN);
                                        break;
                                    case PAWN:
                                        out.print(BLACK_PAWN);
                                        break;
                                }
                        }
                    out.print("   ");
                }

                out.print(RESET_BG_COLOR);
            }
            out.println();
        }
    }

    private static ChessGame.TeamColor getPieceColor(ChessGame game, int row, int col){
        ChessBoard board = game.getBoard();
        return board.getPiece(new ChessPosition(row, col)).getTeamColor();
    }

    private static ChessPiece.PieceType getSquarePiece(ChessGame game, int row, int col){
        ChessBoard board = game.getBoard();
        return board.getPiece(new ChessPosition(row, col)).getPieceType();
    }
    private static void printHeaderText(PrintStream out, String coordinate) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(coordinate);
    }

    private static void printerMarginText(PrintStream out, String coordinate) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(coordinate);
    }

    private static void setBlack(PrintStream out) {
        out.print((SET_BG_COLOR_BLACK));
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setWhite(PrintStream out){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

}
