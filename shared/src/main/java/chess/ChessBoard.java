package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] Places = new ChessPiece[8][8];
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        Places[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return Places[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
    /* check the type and color to the piece
    put pawns on:

    [2,1-->8] Black
    [7,1-->8] White

    put rooks on:
    [1,1] Black
    [1,8]
    [8,1] White
    [8,8]

    put knights on:
    [1,2] Black
    [1,7]
    [8,2] White
    [8,7]

    put bishops on:
    [1,3] Black
    [1,6]
    [8,3] White
    [8,6]

    put queen on:
    [1,4] Black
    [8,4] White

    put king on:
    [1,5] Black
    [1,8] White
     */
}
