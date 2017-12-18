package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * Knight class, which extends the Piece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (Math.abs(startFile - finalFile) == 1 && Math.abs(startRank - finalRank) == 2 && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }
        else if (Math.abs(startFile - finalFile) == 2 && Math.abs(startRank - finalRank) == 1 && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }
        else if (Math.abs(startFile - finalFile) == 1 && Math.abs(startRank - finalRank) == 2 && chessBoard[finalFile][finalRank].getPiece().isWhite() != chessBoard[startFile][startRank].getPiece().isWhite()) {
            return true;
        }
        else if (Math.abs(startFile - finalFile) == 2 && Math.abs(startRank - finalRank) == 1 && chessBoard[finalFile][finalRank].getPiece().isWhite() != chessBoard[startFile][startRank].getPiece().isWhite()) {
            return true;
        }

        return false;
    }


    @Override
    public int getPicture() {
        if (!isWhite()) {
            return R.drawable.blackknight;
        }
        else {
            return R.drawable.whiteknight;
        }
    }
}