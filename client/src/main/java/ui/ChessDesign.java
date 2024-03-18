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

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        
        drawThemHorizontalAxis(out, 2);
        drawChessBoard(out,2);
        drawThemHorizontalAxis(out, 2);

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

    private static void drawChessBoard(PrintStream out, int orientation) {
        for (int boardRow = 0; boardRow <= 7; ++boardRow) {
            for (int boardCol = 0; boardCol <= 9; ++boardCol) {
                if (orientation == 1){

                }
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
                    out.print(EMPTY);
                } else {
                    setBlack(out);
                    out.print(EMPTY);
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
