package chess;

import java.util.*;


/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessGame.TeamColor turn;
    ChessBoard board = new ChessBoard();


    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public void unApplyMove (ChessMove move){
        board.addPiece(new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn()),board.getPiece(move.getEndPosition()));
        board.addPiece(new ChessPosition(move.getEndPosition().getRow(),move.getEndPosition().getColumn()), null);
        switch(getTeamTurn()) {
            case TeamColor.WHITE:
                    this.setTeamTurn(TeamColor.BLACK);
            case TeamColor.BLACK:
                    this.setTeamTurn(TeamColor.WHITE);
                }
        }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        switch(getTeamTurn()) {
            case TeamColor.BLACK:
                Collection<ChessMove> list = board.getPiece(startPosition).pieceMoves(board,startPosition);
                

                isInCheck(TeamColor.BLACK);
            case TeamColor.WHITE:
                break;
            }

        if(board.getPiece(startPosition)!=null){
            return board.getPiece(startPosition).pieceMoves(board, startPosition);
        }
        return null;
    };
    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> list = validMoves(move.getStartPosition());

        if (list.isEmpty()) {
            throw new InvalidMoveException("no valid moves");
        }

        if (board.getPiece(move.getStartPosition()).getTeamColor()!=this.getTeamTurn()){
            throw new InvalidMoveException("not your turn");
        }

        if (list.contains(move)){
            board.addPiece(new ChessPosition(move.getEndPosition().getRow(),move.getEndPosition().getColumn()),board.getPiece(move.getStartPosition()));
//            if (board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.KING){
//                KingPosition(move.getEndPosition().getRow(),move.getEndPosition().getColumn());
//            }
            board.addPiece(new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn()), null);
            if (this.getTeamTurn()==TeamColor.WHITE){
                this.setTeamTurn(TeamColor.BLACK);
            }
            else {
                this.setTeamTurn(TeamColor.WHITE);
            }
        }

        else{
            throw new InvalidMoveException("list didn't contain the move");
        }
    }



    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for(int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.Places[i][j] != null) {
                    ChessPiece searchPiece = board.Places[i][j];
                    if (searchPiece.getPieceType() == ChessPiece.PieceType.KING && searchPiece.getTeamColor() == teamColor) {
                        ChessPosition kingPosition = new ChessPosition(i+1, j+1);
                        for(int k = 0; k<8; k++){
                            for (int l = 0; l<8; l++){
                                if (board.Places[k][l] != null){
                                    ChessPiece questionPiece = board.Places[k][l];
                                    if (questionPiece.getTeamColor() != teamColor){
                                        ChessPosition enemy = new ChessPosition(k+1,l+1);
                                        Collection<ChessMove> list = board.getPiece(enemy).pieceMoves(board,enemy);
                                        for (ChessMove move:list){
                                            System.out.println("endPosition: " + move.getEndPosition());
                                            System.out.println("kingPosition: " + kingPosition);

                                            if(kingPosition.equals(move.getEndPosition())){
                                                System.out.println("here");
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "turn=" + turn +
                ", board=" + board +
                '}';
    }
}
