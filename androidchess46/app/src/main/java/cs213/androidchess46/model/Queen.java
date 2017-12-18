package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * Queen class, which extends the LongMovementPiece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Queen extends LongMovementPiece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        // Check if move is a valid long movement
        if ((Math.abs(startFile - finalFile) == Math.abs(startRank - finalRank) || ((startFile == finalFile && startRank != finalRank) || (startFile != finalFile && startRank == finalRank))) && !pathHasPieces(startFile, startRank, finalFile, finalRank, chessBoard)) {
            if (chessBoard[finalFile][finalRank].getPiece() == null || chessBoard[finalFile][finalRank].getPiece().isWhite() != isWhite()) {
                return true;
            }
        }

        return false;
    }


    @Override
    public int getPicture() {
        if (!isWhite()) {
            return R.drawable.blackqueen;
        }
        else {
            return R.drawable.whitequeen;
        }
    }
}