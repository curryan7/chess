package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public void addMovesInDirection(int rowChange, int colChange, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potentialMoves){
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        int i = 1;
        while(currentRow+rowChange*i>=1 && currentRow+rowChange*i<9 && currentCol+colChange*i>=1 && currentCol+colChange*i<9){
            ChessPosition checkMove = new ChessPosition(currentRow+rowChange*i,currentCol+colChange*i);
            ChessPiece pieceAtMove = board.getPiece(checkMove);
            if (pieceAtMove != null){
                ChessGame.TeamColor colorCheck = this.getTeamColor();
                if (colorCheck != pieceAtMove.getTeamColor()){
                    potentialMoves.add(new ChessMove(myPosition, checkMove, null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                potentialMoves.add(new ChessMove(myPosition, checkMove, null));
            }
            i++;
        }
    }
    public void addMovesSingle(int rowChange, int colChange, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potentialMoves){
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if(currentRow+rowChange>=1 && currentRow+rowChange<9 && currentCol+colChange>=1 && currentCol+colChange<9){
            ChessPosition checkMove = new ChessPosition(currentRow+rowChange,currentCol+colChange);
            ChessPiece pieceAtMove = board.getPiece(checkMove);
            if (pieceAtMove != null){
                ChessGame.TeamColor colorCheck = this.getTeamColor();
                if (colorCheck != pieceAtMove.getTeamColor()){
                    potentialMoves.add(new ChessMove(myPosition, checkMove, null));
                }
            }
            else{
                potentialMoves.add(new ChessMove(myPosition, checkMove, null));
            }
        }
    }
    public void addMovesPawn(int rowChange, int colChange, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potentialMoves){
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if(currentRow+rowChange>=1 && currentRow+rowChange<9 && currentCol+colChange>=1 && currentCol+colChange<9){
            ChessPosition checkMove = new ChessPosition(currentRow+rowChange,currentCol+colChange);
            ChessPiece pieceAtMove = board.getPiece(checkMove);
            if (pieceAtMove == null){
                if (checkPromoPotential(checkMove.getRow())){
                    promotePiece(potentialMoves, myPosition, checkMove);
                }
                else {
                    potentialMoves.add(new ChessMove(myPosition, checkMove, null));
                }
            }
        }
    }

    public Boolean checkPromoPotential(int row){
        return row == 1 | row == 8;
    }
    public void promotePiece(HashSet<ChessMove> potentialMoves,ChessPosition myPosition, ChessPosition checkMove){
        potentialMoves.add(new ChessMove(myPosition, checkMove, PieceType.QUEEN));
        potentialMoves.add(new ChessMove(myPosition, checkMove, PieceType.BISHOP));
        potentialMoves.add(new ChessMove(myPosition, checkMove, PieceType.ROOK));
        potentialMoves.add(new ChessMove(myPosition, checkMove, PieceType.KNIGHT));
    }

    public void attackMovesPawn(int rowChange, int colChange, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potentialMoves){
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if(currentRow+rowChange>=1 && currentRow+rowChange<9 && currentCol+colChange>=1 && currentCol+colChange<9){
            ChessPosition checkMove = new ChessPosition(currentRow+rowChange,currentCol+colChange);
            ChessPiece pieceAtMove = board.getPiece(checkMove);
            if (pieceAtMove != null){
                ChessGame.TeamColor colorCheck = this.getTeamColor();
                if(colorCheck != board.getPiece(checkMove).getTeamColor()) {
                    if (checkPromoPotential(checkMove.getRow())){
                        promotePiece(potentialMoves, myPosition, checkMove);
                    }
                    else {
                        potentialMoves.add(new ChessMove(myPosition, checkMove, null));
                    }
                }
            }
        }
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece.PieceType variant = board.getPiece(myPosition).getPieceType();
        HashSet<ChessMove> potentialMoves = new HashSet<ChessMove>();

        if (variant == PieceType.BISHOP){
            addMovesInDirection(1,1,myPosition,board,potentialMoves);
            addMovesInDirection(1,-1,myPosition,board,potentialMoves);
            addMovesInDirection(-1,-1,myPosition,board,potentialMoves);
            addMovesInDirection(-1,1,myPosition,board,potentialMoves);
        }

        if (variant == PieceType.ROOK){
            addMovesInDirection(1,0,myPosition,board,potentialMoves);
            addMovesInDirection(-1,0,myPosition,board,potentialMoves);
            addMovesInDirection(0,-1,myPosition,board,potentialMoves);
            addMovesInDirection(0,1,myPosition,board,potentialMoves);
        }

        if (variant == PieceType.QUEEN){
            addMovesInDirection(1,0,myPosition,board,potentialMoves);
            addMovesInDirection(-1,0,myPosition,board,potentialMoves);
            addMovesInDirection(0,-1,myPosition,board,potentialMoves);
            addMovesInDirection(0,1,myPosition,board,potentialMoves);
            addMovesInDirection(1,1,myPosition,board,potentialMoves);
            addMovesInDirection(1,-1,myPosition,board,potentialMoves);
            addMovesInDirection(-1,-1,myPosition,board,potentialMoves);
            addMovesInDirection(-1,1,myPosition,board,potentialMoves);
        }

        if (variant == PieceType.KNIGHT){
            addMovesSingle(2,1,myPosition,board,potentialMoves);
            addMovesSingle(2,-1,myPosition,board,potentialMoves);
            addMovesSingle(-2,-1,myPosition,board,potentialMoves);
            addMovesSingle(-2,1,myPosition,board,potentialMoves);
            addMovesSingle(1,2,myPosition,board,potentialMoves);
            addMovesSingle(1,-2,myPosition,board,potentialMoves);
            addMovesSingle(-1,-2,myPosition,board,potentialMoves);
            addMovesSingle(-1,2,myPosition,board,potentialMoves);
        }

        if (variant == PieceType.KING){
            addMovesSingle(1,0,myPosition,board,potentialMoves);
            addMovesSingle(-1,0,myPosition,board,potentialMoves);
            addMovesSingle(0,-1,myPosition,board,potentialMoves);
            addMovesSingle(0,1,myPosition,board,potentialMoves);
            addMovesSingle(1,1,myPosition,board,potentialMoves);
            addMovesSingle(1,-1,myPosition,board,potentialMoves);
            addMovesSingle(-1,-1,myPosition,board,potentialMoves);
            addMovesSingle(-1,1,myPosition,board,potentialMoves);
        }

        if (variant == PieceType.PAWN){
            if(this.pieceColor == ChessGame.TeamColor.BLACK){
                addMovesPawn(-1,0,myPosition,board,potentialMoves);
                attackMovesPawn(-1,1,myPosition,board,potentialMoves);
                attackMovesPawn(-1,-1,myPosition,board,potentialMoves);
                if (myPosition.getRow()==7 && potentialMoves.size()==1){
                    addMovesPawn(-2, 0, myPosition, board, potentialMoves);
                }

            }

            if(this.pieceColor == ChessGame.TeamColor.WHITE){
                addMovesPawn(1,0,myPosition,board,potentialMoves);
                attackMovesPawn(1,1,myPosition,board,potentialMoves);
                attackMovesPawn(1,-1,myPosition,board,potentialMoves);

                if(myPosition.getRow()==2 && potentialMoves.size()==1) {
                    addMovesPawn(2, 0, myPosition, board, potentialMoves);
                }
            }
        }



        return potentialMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
