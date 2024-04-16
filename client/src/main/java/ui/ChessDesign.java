package ui;

import chess.ChessGame;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessDesign {
    private static final int BOARD_SIZE_IN_SQUARES = 10;

    static ChessGame game;

    static String[] bigPieces = {"R", "N", "B", "Q", "K", "B", "N", "R"};
    static String[] pawns = {"P", "P", "P", "P", "P", "P", "P", "P"};

    public ChessDesign() {}

    public static void setGame(ChessGame game) {
        ChessDesign.game = game;
    }

    public static void finalDraw(int orientation) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawHorizontalAxis(out, orientation);
        drawChessBoard(out, orientation);
        drawHorizontalAxis(out, orientation);
    }

    private static void drawHorizontalAxis(PrintStream out, int orientation) {
        String[] headers = orientation == 1
                ? new String[]{"   ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", "   "}
                : new String[]{"   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", "   "};

        for (String header : headers) {
            printHeaderText(out, header);
        }

        System.out.println(RESET_BG_COLOR);
    }

    private static void drawChessBoard(PrintStream out, int orientation) {
        int i = 0, j = 0, k = 0, l = 0;

        for (int boardRow = 0; boardRow <= 7; ++boardRow) {
            for (int boardCol = 0; boardCol <= 9; ++boardCol) {
                if (boardCol == 0 || boardCol == 9) {
                    printMarginText(out, orientation == 1 ? " " + (8 - boardRow) + " " : " " + (boardRow + 1) + " ");
                } else {
                    if ((boardRow + boardCol) % 2 == 0) {
                        setSquareColors(out, orientation, true);
                        drawPieces(out, boardRow, orientation, i, j, k, l);
                    } else {
                        setSquareColors(out, orientation, false);
                        drawPieces(out, boardRow, orientation, i, j, k, l);
                    }
                }
            }
            out.println();
        }
    }

    private static void setSquareColors(PrintStream out, int orientation, boolean isEvenSquare) {
        if (isEvenSquare) {
            out.print(orientation == 1 ? SET_BG_COLOR_WHITE : "\u001B[49m");
            out.print(SET_TEXT_COLOR_GREEN);
        } else {
            out.print(orientation == 1 ? SET_BG_COLOR_BLACK : "\u001B[49m");
            out.print(SET_TEXT_COLOR_RED);
        }
    }

    private static void drawPieces(PrintStream out, int boardRow, int orientation, int i, int j, int k, int l) {
        if (boardRow == 0 || boardRow == 7) {
            String piece = bigPieces[orientation == 1 ? l++ : i++];
            l %= 7;
            i %= 7;
            out.print(" " + piece + " ");
        } else if (boardRow == 1 || boardRow == 6) {
            String piece = pawns[boardRow == 1 ? j++ : k++];
            j %= 7;
            k %= 7;
            out.print(" " + piece + " ");
        } else {
            out.print("   ");
        }
    }

    private static void printHeaderText(PrintStream out, String coordinate) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(coordinate);
    }

    private static void printMarginText(PrintStream out, String coordinate) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(coordinate);
    }
}
