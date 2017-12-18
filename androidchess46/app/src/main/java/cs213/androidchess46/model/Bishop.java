package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * Bishop class, which extends the LongMovementPiece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Bishop extends LongMovementPiece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (Math.abs(startFile - finalFile) == Math.abs(startRank - finalRank) && !pathHasPieces(startFile, startRank, finalFile, finalRank, chessBoard)) {
            if (chessBoard[finalFile][finalRank].getPiece() == null) {
                return true;
            }

            if (chessBoard[finalFile][finalRank].getPiece().isWhite() != chessBoard[startFile][startRank].getPiece().isWhite()) {
                return true;
            }
        }

        return false;
    }


    @Override
    public int getPicture() {
        if (!isWhite()) {
            return R.drawable.blackbishop;
        }
        else {
            return R.drawable.whitebishop;
        }
    }
}