package ui;

import java.awt.print.PrinterAbortException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessDesign {
    private static final int BOARD_SIZE_IN_CHARS = 31;
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static Random rand = new Random();

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        drawThemHorizontalAxis(out);
        drawChessBoard(out);

    }

    private static void drawThemHorizontalAxis(PrintStream out) {
        String[] headers = {"   ", " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", "   "};
        for (int boardCol = 0; boardCol< BOARD_SIZE_IN_SQUARES; ++boardCol){
            drawHorizontalAxis(out, headers[boardCol]);
        }
        System.out.println(RESET_BG_COLOR);
    }

    private static void drawHorizontalAxis(PrintStream out, String headerText){
        printHeaderText(out, headerText);
    }

    private static void drawChessBoard(PrintStream out){
        String[] siders = {" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};
        int i = 0;
        int j = 0;

        for (int boardRow = 1; boardRow <= 9; ++boardRow){
            for (int boardCol = 1; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol){
                if (boardCol ==1 | boardCol == 10){
                    setGray(out);
                }
                else if ((boardRow+boardCol)%2 == 0){
                    setWhite(out);
                }
                else {
                    setBlack(out);
                }

                out.print(EMPTY);
                out.print(RESET_BG_COLOR);
            }

            out.println();
            if (boardRow == 9){
                drawThemHorizontalAxis(out);
            }
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
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setWhite(PrintStream out){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setGray(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }


}
