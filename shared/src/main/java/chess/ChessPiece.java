package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {

        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        /*
        current_place = Places[myPosition.getRow()][myPosition.getColumn()];
        type = getPieceType();
        potential_moves = []
        move = current_place

        public Blocked(array next_move) {
        int var counter = 0
            if (next_move[] != NULL){
                 counter += 1;
             }
             if ((next_move.getRow()>8 OR next_move.getRow()<1) AND (next_move.getColumn()>8 OR next_move.getColumn()<1)){
                    counter += 1;
             }
             return counter
        };

        public Maybe(array move){

            while (maybe_move.getRow()<=8 and >=0 and move.getColumn()<=8 and >=0){
                if (move = NULL) {
                    append move to potential_moves (list)
                }
                move += move(additions of move)
            }
        }



        if (type == BISHOP) {
            counter = 0
            while (counter<4){
                counter = Blocked (move[+1,+1])
                counter += Blocked (move[-1,+1])
                counter += Blocked (move[-1,-1])
                counter += Blocked (move[+1,-1])
                Maybe(move[+1,+1])
                Maybe(move[-1,+1])
                Maybe(move[-1,-1])
                Maybe(move[+1,-1])
                }
            return potential_moves
         }


            while (move.getRow()<=8 and >=0 and move.getColumn()<=8 and >=0)
            {
                maybe_move = move[+1][+1]
                if maybe_move array is empty {
                    move = maybe_move
                    append move to potential_moves list}
                move = maybe_move
                }
            move = current_place
            while (move.getRow()<=8 and >=0 and move.getColumn()<=8 and >=0)
            {
                maybe_move = move[-1][+1]
                if maybe_move array is empty {
                    move = maybe_move
                    append move to potential_moves list}
                move = maybe_move
                }
            move = current_place
            while (move.getRow()<=8 and >=0 and move.getColumn()<=8 and >=0)
            {
                maybe_move = move[-1][-1]
                if maybe_move array is empty {
                    move = maybe_move
                    append move to potential_moves list}
                move = maybe_move
                }
            move = current_place
            while (move.getRow()<=8 and >=0 and move.getColumn()<=8 and >=0)
            {
                maybe_move = move[+1][-1]
                if maybe_move array is empty {
                    move = maybe_move
                    append move to potential_moves list}
                move = maybe_move
                }
            move = current_place
         return potential_moves (which is a list)
         */
        }

    }
}
