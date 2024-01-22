package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor= pieceColor;
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
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ArrayList<ChessMove> potential_moves = new ArrayList<>();
        ChessPiece.PieceType variant = board.getPiece(myPosition).getPieceType();

        if (variant==PieceType.BISHOP){
            int i = 0;
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();

            while (i+current_row<8 && current_col+i<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row-i>1 && current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }


            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row-i>1 && current_col+i<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row+i<8 && current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }
        }
        if (variant==PieceType.KING){
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();
            ChessPosition check_move = new ChessPosition(current_row+1,current_col+1);
            if (current_row<8 && current_col<8) {
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        }
                    }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-1, current_col+1);
            if (current_row>0 && current_col<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-1, current_col-1);
            if (current_row>0 && current_col>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row+1, current_col-1);
            if (current_row<8 && current_col>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-1, current_col);
            if (current_row>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row+1, current_col);
            if (current_row<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row, current_col+1);
            if (current_col<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row, current_col-1);
            if (current_col>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }
        }
        if (variant==PieceType.KNIGHT){
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();
            ChessPosition check_move = new ChessPosition(current_row+2,current_col+1);
            if (current_row<8 && current_col<8) {
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-2, current_col+1);
            if (current_row>0 && current_col<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-1, current_col-2);
            if (check_move.getRow()>0 && check_move.getColumn()>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row+1, current_col-2);
            if (check_move.getRow()<8 && check_move.getColumn()>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row+2, current_col-1);
            if (check_move.getRow()<8 && check_move.getColumn()>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row+1, current_col+2);
            if (check_move.getRow()<8 && check_move.getColumn()<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-1, current_col+2);
            if (check_move.getRow()>0 && check_move.getColumn()<8){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

            check_move = new ChessPosition(current_row-2, current_col-1);
            if (check_move.getRow()>0 && check_move.getColumn()>0){
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }

        }
        if (variant==PieceType.ROOK){
            int i = 0;
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();

            while (i+current_col<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (current_row-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (i+current_row<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }
        }
        if (variant==PieceType.QUEEN){
            int i = 0;
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();

            while (i+current_row<8 && current_col+i<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row-i>1 && current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }


            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row-i>1 && current_col+i<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }
            }
            i = 0;
            current_row = myPosition.getRow();
            current_col = myPosition.getColumn();

            while (current_row+i<8 && current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i=0;
            while (i+current_col<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row,current_col+i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (current_col-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row,current_col-i);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (current_row-i>1){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row-i,current_col);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }

            i = 0;
            while (i+current_row<8){
                i = i+1;
                ChessPosition check_move = new ChessPosition(current_row+i,current_col);
                if (board.getPiece(check_move)!=null) {
                    ChessGame.TeamColor color_check = board.getPiece(check_move).getTeamColor();
                    if (color_check != this.getTeamColor()){
                        ChessMove new_move = new ChessMove(myPosition,check_move,null);
                        potential_moves.add(new_move);
                        break;}
                    else {
                        break;
                    }
                }
                else {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                }

            }
        }
        if (variant==PieceType.PAWN){
            int current_row = myPosition.getRow();
            int current_col = myPosition.getColumn();

            if (board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                ChessPosition check_move = new ChessPosition(current_row +1, current_col);
                if (myPosition.getRow()==2 && board.getPiece(check_move)==null) {
                    ChessMove new_move = new ChessMove(myPosition,check_move,null);
                    potential_moves.add(new_move);
                   ChessPosition check_move2 = new ChessPosition(current_row + 2, current_col);
                    if (board.getPiece(check_move2) == null){
                        ChessMove new_move2 = new ChessMove(myPosition,check_move2,null);
                        potential_moves.add(new_move2);
                    }
                }
            }

            if (board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.BLACK) {
                ChessPosition check_move = new ChessPosition(current_row - 1, current_col);
                if (myPosition.getRow() == 7 && board.getPiece(check_move) == null) {
                    ChessMove new_move = new ChessMove(myPosition, check_move, null);
                    potential_moves.add(new_move);
                    ChessPosition check_move2 = new ChessPosition(current_row - 2, current_col);
                    if (board.getPiece(check_move2) == null) {
                        ChessMove new_move2 = new ChessMove(myPosition, check_move2, null);
                        potential_moves.add(new_move2);
                    }
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

