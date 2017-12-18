package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * King class, which extends the LongMovementPiece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class King extends LongMovementPiece {
    public King(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (startFile == finalFile && Math.abs(startRank - finalRank) == 1 && (chessBoard[finalFile][finalRank].getPiece() == null || chessBoard[finalFile][finalRank].getPiece().isWhite() != isWhite())) {
            return true;
        }

        if (startRank == finalRank && Math.abs(startFile - finalFile) == 1 && (chessBoard[finalFile][finalRank].getPiece() == null || chessBoard[finalFile][finalRank].getPiece().isWhite() != isWhite())) {
            return true;
        }

        if (Math.abs(startFile - finalFile) == 1 && Math.abs(startRank - finalRank) ==1 && (chessBoard[finalFile][finalRank].getPiece() == null || chessBoard[finalFile][finalRank].getPiece().isWhite() != isWhite())) {
            return true;
        }

        if (!hasMoved()) {
            if (finalFile == 6 && (finalRank + 1 == 1 || finalRank + 1 == 8) && chessBoard[7][finalRank].getPiece() instanceof Rook && !chessBoard[7][finalRank].getPiece().hasMoved() && chessBoard[7][finalRank].getPiece().isWhite() == isWhite() && !pathHasPieces(startFile, startRank, 7, finalRank, chessBoard)) {
                return true;
            }

            if (finalFile == 2 && (finalRank + 1 == 1 || finalRank + 1 == 8) && chessBoard[0][finalRank].getPiece() instanceof Rook && !chessBoard[0][finalRank].getPiece().hasMoved() && chessBoard[0][finalRank].getPiece().isWhite() == isWhite() && !pathHasPieces(startFile, startRank, 0, finalRank, chessBoard)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public RecordedMove move(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        RecordedMove move = new RecordedMove(hasMoved(), getEnpassant(), startFile, startRank, finalFile, finalRank, chessBoard[finalFile][finalRank].getPiece(), finalFile, finalRank);

        if (!hasMoved() && finalFile == 6 && (finalRank == 0 || finalRank == 7) && chessBoard[7][finalRank].getPiece() instanceof Rook && !chessBoard[7][finalRank].getPiece().hasMoved() && chessBoard[7][finalRank].getPiece().isWhite() == isWhite() && !pathHasPieces(startFile, startRank, 7, finalRank, chessBoard)) {
            castling(startFile, startRank, 7, finalRank, chessBoard, move);
        }
        else if (!hasMoved() && finalFile == 2 && (finalRank == 0 || finalRank == 7) && chessBoard[0][finalRank].getPiece() instanceof Rook && !chessBoard[0][finalRank].getPiece().hasMoved() && chessBoard[0][finalRank].getPiece().isWhite() == isWhite() && !pathHasPieces(startFile, startRank, 0, finalRank, chessBoard)) {
            castling(startFile, startRank, 0, finalRank, chessBoard, move);
        }
        else {
            chessBoard[finalFile][finalRank].setPiece(chessBoard[startFile][startRank].getPiece());
            chessBoard[startFile][startRank].setPiece(null);
        }

        setHasMoved(true);

        return move;
    }


    /**
     * castling()
     * Moves pieces according to castling rules
     *
     * @param startFile - The starting file of the piece
     * @param startRank - The starting rank of the piece
     * @param finalFile - The ending file of the piece
     * @param finalRank - The ending rank of the piece
     * @param chessBoard - The chess board
     * @param move - The move that was recorded
     * @return void
     */
    public void castling(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard, RecordedMove move) {
        Piece king = chessBoard[startFile][startRank].getPiece();
        Piece rook = chessBoard[finalFile][finalRank].getPiece();

        rook.setHasMoved(true);

        move.setCastledStartRank(finalRank);
        move.setCastledFinalRank(finalRank);

        if (finalFile == 7) {
            chessBoard[6][finalRank].setPiece(king);
            chessBoard[5][finalRank].setPiece(rook);
            chessBoard[7][finalRank].setPiece(null);
            chessBoard[4][finalRank].setPiece(null);
            move.setCastledStartFile(7);
            move.setCastledFinalFile(5);
        }

        if (finalFile == 0) {
            chessBoard[2][finalRank].setPiece(king);
            chessBoard[3][finalRank].setPiece(rook);
            chessBoard[0][finalRank].setPiece(null);
            chessBoard[4][finalRank].setPiece(null);
            move.setCastledStartFile(0);
            move.setCastledFinalFile(3);
        }
    }


    @Override
    public int getPicture() {
        if (!isWhite()) {
            return R.drawable.blackking;
        }
        else {
            return R.drawable.whiteking;
        }
    }
}