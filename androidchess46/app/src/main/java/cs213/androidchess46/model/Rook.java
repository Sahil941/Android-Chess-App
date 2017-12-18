package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * Rook class, which extends the LongMovementPiece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Rook extends LongMovementPiece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (((startFile == finalFile && startRank != finalRank) || (startFile != finalFile && startRank == finalRank)) && !pathHasPieces(startFile, startRank, finalFile, finalRank, chessBoard)) {
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
            return R.drawable.blackrook;
        }
        else {
            return R.drawable.whiterook;
        }
    }
}