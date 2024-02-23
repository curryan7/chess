package chess;

import java.sql.SQLOutput;
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

    public ChessBoard DeepCopy(ChessBoard board) {
        ChessBoard copyBoard = new ChessBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece originalPiece = board.Places[i][j];
                if (originalPiece != null) {
                    ChessPiece clonedPiece = new ChessPiece(originalPiece.getTeamColor(), originalPiece.getPieceType()); // Assuming ChessPiece has a copy constructor
                    copyBoard.Places[i][j] = clonedPiece;
                }
            }
        }

        return copyBoard;
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
    public void ApplyMove(ChessMove move, ChessBoard testBoard){
        // we have our chess pieces and chess positions that are relevant to the function
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(startPosition);
        // this is for normal case when there is no piece in the endPosition
        //applied the move
        testBoard.addPiece(endPosition, startPiece);
        testBoard.addPiece(startPosition, null);
    }

//    public void unApplyMove (ChessMove move, ChessBoard testBoard){
//        ChessPosition startPosition = move.getStartPosition();
//        ChessPosition endPosition = move.getEndPosition();
//        ChessPiece startPiece = board.getPiece(endPosition);
//        ChessPiece endPiece = board.getPiece(startPosition);
//
//        testBoard.addPiece(startPosition, startPiece);
//        testBoard.addPiece(endPosition, endPiece);
//
////        testBoard.addPiece(startPosition, endPiece);
////        testBoard.addPiece(endPosition, startPiece);
//
//    }




    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> list = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> invalidList = new HashSet<ChessMove>();
        ChessBoard originalBoard = DeepCopy(board);
        ChessPiece startPiece = board.getPiece(startPosition);
        ChessGame.TeamColor originalColor = getTeamTurn();
        ChessGame.TeamColor testColor = startPiece.getTeamColor();
        setTeamTurn(testColor);

        for (ChessMove move : list) {
           ChessBoard testBoard = DeepCopy(board);
           ApplyMove(move, testBoard);
           setBoard(testBoard);
           setTeamTurn(testColor);
           
           if(this.getTeamTurn()==TeamColor.BLACK){
               if(isInCheck(TeamColor.BLACK)){
                   invalidList.add(move);
               }
           }
           if(this.getTeamTurn()==TeamColor.WHITE){
               if(isInCheck(TeamColor.WHITE)){
                   invalidList.add(move);
               }
           }
           setBoard(originalBoard);
           setTeamTurn(originalColor);
        }

        for (ChessMove move:invalidList){
            list.remove(move);
        }
        return list;
    }



    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn());
        ChessPiece startPiece = board.getPiece(startPosition);
        Collection<ChessMove> list = validMoves(move.getStartPosition());
        ChessPosition endPosition = new ChessPosition(move.getEndPosition().getRow(),move.getEndPosition().getColumn());
        ChessPiece endPiece = board.getPiece(endPosition);
        if (startPiece==null){
            throw new InvalidMoveException("no piece there");
        }

        if (list.isEmpty()) {
            if(isInCheck(this.getTeamTurn())){
                throw new InvalidMoveException("You're in check");
            }
            throw new InvalidMoveException("no valid moves");
        }

        if (startPiece.getTeamColor()!=this.getTeamTurn()){
            throw new InvalidMoveException("not your turn");
        }

        if (list.contains(move)){
            if(endPiece != null && startPiece.getTeamColor() != endPiece.getTeamColor()){
                board.addPiece(endPosition,startPiece);
                board.addPiece(startPosition,null);
                if(move.getPromotionPiece()!=null){
                    ChessPiece promoPiece = new ChessPiece(getTeamTurn(),move.getPromotionPiece());
                    board.addPiece(endPosition, promoPiece);
                    board.addPiece(startPosition, null);
                }
            } else {
                if(move.getPromotionPiece()!=null){
                    ChessPiece promoPiece = new ChessPiece(getTeamTurn(),move.getPromotionPiece());
                    board.addPiece(endPosition, promoPiece);
                    board.addPiece(startPosition, null);
                } else {
                    board.addPiece(endPosition, startPiece);
                    board.addPiece(startPosition, endPiece);
                }
            }
            if (this.getTeamTurn()==TeamColor.WHITE){
                this.setTeamTurn(TeamColor.BLACK);
            } else {
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
                                            if(kingPosition.equals(move.getEndPosition())){
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
        for(int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.Places[i][j] != null) {
                    ChessPiece searchPiece = board.Places[i][j];
                    if (searchPiece.getPieceType() == ChessPiece.PieceType.KING && searchPiece.getTeamColor() == teamColor) {
                        if (isInCheck(teamColor)){
                            ChessPosition kingPosition = new ChessPosition(i+1, j+1);
                            Collection<ChessMove> list = validMoves(kingPosition);
                            if(list.isEmpty()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
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
        for(int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.Places[i][j] != null) {
                    ChessPiece searchPiece = board.Places[i][j];
                    if (searchPiece.getPieceType() == ChessPiece.PieceType.KING && searchPiece.getTeamColor() == teamColor) {
                        if (!isInCheck(teamColor)){
                            ChessPosition kingPosition = new ChessPosition(i+1, j+1);
                            Collection<ChessMove> list = validMoves(kingPosition);
                            if(list.isEmpty()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
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
