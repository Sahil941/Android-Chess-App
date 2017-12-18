package cs213.androidchess46.model;

import java.io.Serializable;


/**
 * The abstract class for Chess Pieces
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public abstract class Piece implements Serializable, Cloneable {
    private boolean isWhite;
    private boolean hasMoved;
    private boolean enpassant;


    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        this.hasMoved = false;
        this.enpassant = false;
    }


    /**
     * Returns whether or not the color of a piece is white
     *
     * @return true if piece is white, else false
     */
    public boolean isWhite() {
        return this.isWhite;
    }


    /**
     * Returns whether or not the piece has moved
     *
     * @return true if piece has moved, else false
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }


    /**
     * Changes the value of hasMoved to true, if piece
     * has moved, else to false if piece has not moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }


    /**
     * Check to see if a move is valid
     *
     * @param startFile - The starting file
     * @param startRank - The starting rank
     * @param finalFile - The ending file
     * @param finalRank - The ending rank
     * @param chessBoard - The chess board
     * @return true if the move is valid, else false
     */
    public abstract boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard);


    /**
     * Moves the piece from startFile and startRank to the
     * finalFile and finalRank and removes pieces as necessary
     *
     * @param startFile - The starting file
     * @param startRank - The starting rank
     * @param finalFile - The ending file
     * @param finalRank - The ending rank
     * @param chessBoard - The chess board
     * @return Pair of the deleted piece and it's integer
     */
    public RecordedMove move(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        Piece sPiece = chessBoard[startFile][startRank].getPiece();
        Piece fPiece = chessBoard[finalFile][finalRank].getPiece();

        RecordedMove move = new RecordedMove(hasMoved(), getEnpassant(), startFile, startRank, finalFile, finalRank, fPiece, finalFile, finalRank);

        chessBoard[finalFile][finalRank].setPiece(sPiece);
        chessBoard[startFile][startRank].setPiece(null);
        setHasMoved(true);

        return move;
    }


    /**
     * Gets the enpassant value
     *
     * @return boolean indicating whether this piece can be captured by enpassant
     */
    public boolean getEnpassant() {
        return this.enpassant;
    }


    /**
     * Sets new enpassant value
     *
     * @param enpassant - new enpassant value
     */
    public void setEnpassant(boolean enpassant) {
        this.enpassant = enpassant;
    }


    /**
     * Depending on the piece, return the correct drawable id corresponding to the piece and color
     *
     * @return the drawable id corresponding to the piece and color combination
     */
    public abstract int getPicture();


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}