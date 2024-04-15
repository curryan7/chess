package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessDesign {
    private static final int BOARD_SIZE_IN_SQUARES = 10;

    public static void setGame(ChessGame game) {
        ChessDesign.game = game;
    }

    public void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }

    static ChessGame game ;
    ChessGame.TeamColor color;

    static String[] bigPieces ={"R","N","B","Q","K","B","N","R"};
    static String[] pawns = {"P", "P", "P", "P", "P", "P", "P", "P"};

    public ChessDesign() {}

    public static void finalDraw(int orientation) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        ChessBoard board = new ChessBoard();
        board.resetBoard();
        ChessGame game = new ChessGame();
        game.setBoard(board);
        game.setTeamTurn(ChessGame.TeamColor.BLACK);

        out.print(ERASE_SCREEN);

        drawThemHorizontalAxis(out,orientation);
        drawChessBoard(out, orientation, game);
        drawThemHorizontalAxis(out, orientation);

    }

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
        int i = 0;
        int j =0;
        int k = 0;
        int l = 0;
        for (int boardRow = 0; boardRow <= 7; ++boardRow) {
            for (int boardCol = 0; boardCol <= 9; ++boardCol) {
                if (boardCol == 0 || boardCol == 9) {
                    if (orientation == 1) {
                        switch (boardRow) {
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
                    } else if (orientation == 2) {
                        switch (boardRow) {
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
                } else if ((boardRow + boardCol) % 2 == 0 && orientation == 1) {
                    setWhite(out);
                    setGreenText(out);
                    if (boardRow == 0) {
                        String piece = bigPieces[i];
                        i++;
                        if (i == 7) {
                            i = 0;
                        }
                        out.print(" " + piece + " ");
                    }

                    else if (boardRow == 1){
                        String piece = pawns[j];
                        j++;
                        if (j==7){
                            j = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 6){
                        String piece = pawns[k];
                        k++;
                        if (k==7){
                            k = 0;
                        }
                        setRedText(out);
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow ==7){
                        String piece = bigPieces[l];
                        l++;
                        if (l == 7) {
                            l = 0;
                        }
                        setRedText(out);
                        out.print(" " + piece + " ");
                    }
                    else {
                        out.print("   ");
                    }

                } else if ((boardRow+boardCol)%2 != 0 && orientation == 1) {
                    setBlack(out);
                    setGreenText(out);

                    if (boardRow == 0) {
                        String piece = bigPieces[i];
                        i++;
                        if (i == 7) {
                            i = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 1){
                        String piece = pawns[j];
                        j++;
                        if (j==7){
                            j = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 6){
                        String piece = pawns[k];
                        k++;
                        if (k==7){
                            k = 0;
                        }
                        setRedText(out);
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow ==7){
                        String piece = bigPieces[l];
                        l++;
                        if (l == 7) {
                            l = 0;
                        }
                        setRedText(out);
                        out.print(" " + piece + " ");
                    }
                    else {
                        out.print("   ");
                    }
                }
                else if ((boardRow + boardCol) % 2 == 0 && orientation==2) {
                    setWhite(out);
                    setRedText(out);
                    if (boardRow == 0){
                        String piece = bigPieces[i];
                        i++;
                        if(i==7){
                            i = 0;
                        }
                        out.print(" "+piece+" ");
                    }

                    else if (boardRow == 1){
                        String piece = pawns[j];
                        j++;
                        if (j==7){
                            j = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 6){
                        String piece = pawns[j];
                        k++;
                        if (k==7){
                            k = 0;
                        }
                        setGreenText(out);
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow ==7){
                        String piece = bigPieces[i];
                        l++;
                        if (l == 7) {
                            l = 0;
                        }
                        setGreenText(out);
                        out.print(" " + piece + " ");
                    }
                    else{
                        out.print("   ");
                    }

                } else if ((boardRow + boardCol) % 2 != 0 && orientation==2) {
                    setBlack(out);
                    setRedText(out);
                    if (boardRow == 0) {
                        String piece = bigPieces[i];
                        i++;
                        if (i == 7) {
                            i = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 1){
                        String piece = pawns[j];
                        j++;
                        if (j==7){
                            j = 0;
                        }
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow == 6){
                        String piece = pawns[k];
                        k++;
                        if (k==7){
                            k = 0;
                        }
                        setGreenText(out);
                        out.print(" " + piece + " ");
                    }
                    else if (boardRow ==7){
                        String piece = bigPieces[l];
                        l++;
                        if (l == 7) {
                            l = 0;
                        }
                        setGreenText(out);
                        out.print(" " + piece + " ");
                    }
                    else {
                        out.print("   ");
                    }
                }

                out.print(RESET_BG_COLOR);
            }
            out.println();
        }
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
//        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setWhite(PrintStream out){
        out.print(SET_BG_COLOR_WHITE);
//        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGreenText(PrintStream out){
        out.print(SET_TEXT_COLOR_GREEN);
    }
    private static void setRedText(PrintStream out){
        out.print(SET_TEXT_COLOR_RED);
    }
}
