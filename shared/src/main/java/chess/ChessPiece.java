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

    public void AddMovesInDirection(int row_change, int col_change, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potential_moves){
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();
        int i = 1;
        while(current_row+row_change*i>=1 && current_row+row_change*i<9 && current_col+col_change*i>=1 && current_col+col_change*i<9){
            ChessPosition check_move = new ChessPosition(current_row+row_change*i,current_col+col_change*i);
            ChessPiece pieceAtMove = board.getPiece(check_move);
            if (pieceAtMove != null){
                ChessGame.TeamColor color_check = this.getTeamColor();
                if (color_check != pieceAtMove.getTeamColor()){
                    potential_moves.add(new ChessMove(myPosition, check_move, null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                potential_moves.add(new ChessMove(myPosition, check_move, null));
            }
            i++;
        }
    }
    public void AddMovesSingle(int row_change, int col_change, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potential_moves){
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        if(current_row+row_change>=1 && current_row+row_change<9 && current_col+col_change>=1 && current_col+col_change<9){
            ChessPosition check_move = new ChessPosition(current_row+row_change,current_col+col_change);
            ChessPiece pieceAtMove = board.getPiece(check_move);
            if (pieceAtMove != null){
                ChessGame.TeamColor color_check = this.getTeamColor();
                if (color_check != pieceAtMove.getTeamColor()){
                    potential_moves.add(new ChessMove(myPosition, check_move, null));
                }
            }
            else{
                potential_moves.add(new ChessMove(myPosition, check_move, null));
            }
        }
    }
    public void AddMovesPawn(int row_change, int col_change, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potential_moves){
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        if(current_row+row_change>=1 && current_row+row_change<9 && current_col+col_change>=1 && current_col+col_change<9){
            ChessPosition check_move = new ChessPosition(current_row+row_change,current_col+col_change);
            ChessPiece pieceAtMove = board.getPiece(check_move);
            if (pieceAtMove == null){
                if (check_move.getRow()==8 | check_move.getRow()==1){
                    potential_moves.add(new ChessMove(myPosition, check_move, PieceType.QUEEN));
                    potential_moves.add(new ChessMove(myPosition, check_move, PieceType.BISHOP));
                    potential_moves.add(new ChessMove(myPosition, check_move, PieceType.ROOK));
                    potential_moves.add(new ChessMove(myPosition, check_move, PieceType.KNIGHT));
                }
                else {
                    potential_moves.add(new ChessMove(myPosition, check_move, null));
                }
            }
        }
    }
    public void AttackMovesPawn(int row_change, int col_change, ChessPosition myPosition, ChessBoard board, HashSet<ChessMove> potential_moves){
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        if(current_row+row_change>=1 && current_row+row_change<9 && current_col+col_change>=1 && current_col+col_change<9){
            ChessPosition check_move = new ChessPosition(current_row+row_change,current_col+col_change);
            ChessPiece pieceAtMove = board.getPiece(check_move);
            if (pieceAtMove != null){
                ChessGame.TeamColor color_check = this.getTeamColor();
                if(color_check != board.getPiece(check_move).getTeamColor()) {
                    if (check_move.getRow()==8 | check_move.getRow()==1){
                        potential_moves.add(new ChessMove(myPosition, check_move, PieceType.QUEEN));
                        potential_moves.add(new ChessMove(myPosition, check_move, PieceType.BISHOP));
                        potential_moves.add(new ChessMove(myPosition, check_move, PieceType.ROOK));
                        potential_moves.add(new ChessMove(myPosition, check_move, PieceType.KNIGHT));
                    }
                    else {
                        potential_moves.add(new ChessMove(myPosition, check_move, null));
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
        HashSet<ChessMove> potential_moves = new HashSet<ChessMove>();

        if (variant == PieceType.BISHOP){
            AddMovesInDirection(1,1,myPosition,board,potential_moves);
            AddMovesInDirection(1,-1,myPosition,board,potential_moves);
            AddMovesInDirection(-1,-1,myPosition,board,potential_moves);
            AddMovesInDirection(-1,1,myPosition,board,potential_moves);
        }

        if (variant == PieceType.ROOK){
            AddMovesInDirection(1,0,myPosition,board,potential_moves);
            AddMovesInDirection(-1,0,myPosition,board,potential_moves);
            AddMovesInDirection(0,-1,myPosition,board,potential_moves);
            AddMovesInDirection(0,1,myPosition,board,potential_moves);
        }

        if (variant == PieceType.QUEEN){
            AddMovesInDirection(1,0,myPosition,board,potential_moves);
            AddMovesInDirection(-1,0,myPosition,board,potential_moves);
            AddMovesInDirection(0,-1,myPosition,board,potential_moves);
            AddMovesInDirection(0,1,myPosition,board,potential_moves);
            AddMovesInDirection(1,1,myPosition,board,potential_moves);
            AddMovesInDirection(1,-1,myPosition,board,potential_moves);
            AddMovesInDirection(-1,-1,myPosition,board,potential_moves);
            AddMovesInDirection(-1,1,myPosition,board,potential_moves);
        }

        if (variant == PieceType.KNIGHT){
            AddMovesSingle(2,1,myPosition,board,potential_moves);
            AddMovesSingle(2,-1,myPosition,board,potential_moves);
            AddMovesSingle(-2,-1,myPosition,board,potential_moves);
            AddMovesSingle(-2,1,myPosition,board,potential_moves);
            AddMovesSingle(1,2,myPosition,board,potential_moves);
            AddMovesSingle(1,-2,myPosition,board,potential_moves);
            AddMovesSingle(-1,-2,myPosition,board,potential_moves);
            AddMovesSingle(-1,2,myPosition,board,potential_moves);
        }

        if (variant == PieceType.KING){
            AddMovesSingle(1,0,myPosition,board,potential_moves);
//            KingPosition(1,0, myPosition);
            AddMovesSingle(-1,0,myPosition,board,potential_moves);
            AddMovesSingle(0,-1,myPosition,board,potential_moves);
            AddMovesSingle(0,1,myPosition,board,potential_moves);
            AddMovesSingle(1,1,myPosition,board,potential_moves);
            AddMovesSingle(1,-1,myPosition,board,potential_moves);
            AddMovesSingle(-1,-1,myPosition,board,potential_moves);
            AddMovesSingle(-1,1,myPosition,board,potential_moves);
        }

        if (variant == PieceType.PAWN){
            if(this.pieceColor == ChessGame.TeamColor.BLACK){
                AddMovesPawn(-1,0,myPosition,board,potential_moves);
                AttackMovesPawn(-1,1,myPosition,board,potential_moves);
                AttackMovesPawn(-1,-1,myPosition,board,potential_moves);
                if (myPosition.getRow()==7 && potential_moves.size()==1){
                    AddMovesPawn(-2, 0, myPosition, board, potential_moves);
                }

            }

            if(this.pieceColor == ChessGame.TeamColor.WHITE){
                AddMovesPawn(1,0,myPosition,board,potential_moves);
                AttackMovesPawn(1,1,myPosition,board,potential_moves);
                AttackMovesPawn(1,-1,myPosition,board,potential_moves);

                if(myPosition.getRow()==2 && potential_moves.size()==1) {
                    AddMovesPawn(2, 0, myPosition, board, potential_moves);
                }
            }
        }



        return potential_moves;
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
