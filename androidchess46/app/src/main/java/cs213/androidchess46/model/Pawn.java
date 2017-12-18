package cs213.androidchess46.model;

import cs213.androidchess46.R;


/**
 * Pawn class, which extends the Piece abstract class
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
        setEnpassant(true);
    }


    @Override
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        // If pawn is going straight
        if (startFile == finalFile) {
            if (isWhite() && startRank + 1 == finalRank && chessBoard[finalFile][finalRank].getPiece() == null){
                setEnpassant(false);
                return true;
            }

            // If pawn is white and it's first turn, then pawn can move up to 2 spots
            if (isWhite() && startRank == 1 && startRank + 2 == finalRank && chessBoard[finalFile][finalRank].getPiece() == null){
                setEnpassant(true);
                return true;
            }

            if (!isWhite() && startRank - 1 == finalRank && chessBoard[finalFile][finalRank].getPiece() == null) {
                setEnpassant(false);
                return true;
            }

            // If pawn is black and it's first turn, then pawn can move up to 2 spots
            if (!isWhite() && startRank == 6 && startRank - 2 == finalRank && chessBoard[finalFile][finalRank].getPiece() == null){
                setEnpassant(true);
                return true;
            }
        }

        // If pawn is going diagonal into spot of opponent
        if (isWhite() && startRank + 1 == finalRank && startFile + 1 == finalFile && chessBoard[startFile + 1][startRank + 1].getPiece() != null && !chessBoard[startFile + 1][startRank + 1].getPiece().isWhite()) {
            setEnpassant(false);
            return true;
        }

        if (isWhite() && startRank + 1 == finalRank && startFile - 1 == finalFile && chessBoard[startFile - 1][startRank + 1].getPiece() != null && !chessBoard[startFile - 1][startRank + 1].getPiece().isWhite()) {
            setEnpassant(false);
            return true;
        }

        if (!isWhite() && startRank - 1 == finalRank && startFile + 1 == finalFile && chessBoard[startFile + 1][startRank - 1].getPiece() != null && chessBoard[startFile + 1][startRank - 1].getPiece().isWhite()) {
            setEnpassant(false);
            return true;
        }

        if (!isWhite() && startRank - 1 == finalRank && startFile - 1 == finalFile && chessBoard[startFile - 1][startRank - 1].getPiece() != null && chessBoard[startFile - 1][startRank - 1].getPiece().isWhite()) {
            setEnpassant(false);
            return true;
        }

        // If pawn is going diagonal for enpassant
        if (shouldEnpassant(startFile, startRank, finalFile, finalRank, chessBoard)) {
            setEnpassant(false);
            return true;
        }

        return false;
    }


    @Override
    public RecordedMove move(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        RecordedMove move = new RecordedMove(hasMoved(), getEnpassant(), startFile, startRank, finalFile, finalRank, chessBoard[finalFile][finalRank].getPiece(), finalFile, finalRank);
        boolean willEnpassant = shouldEnpassant(startFile, startRank, finalFile, finalRank, chessBoard);
        Piece startPiece = chessBoard[startFile][startRank].getPiece();

        chessBoard[finalFile][finalRank].setPiece(startPiece);
        chessBoard[startFile][startRank].setPiece(null);

        // If pawn is going diagonal for enpassant, then remove the piece it is passing
        if (startFile != finalFile && willEnpassant) {
            move.setDeletedRank(startRank);

            if (isWhite() && startRank + 1 == finalRank && startFile + 1 == finalFile) {
                move.setDeletedPiece(chessBoard[startFile + 1][startRank].getPiece());
                move.setDeletedFile(startFile + 1);
                chessBoard[startFile + 1][startRank].setPiece(null);
            }

            if (isWhite() && startRank + 1 == finalRank && startFile - 1 == finalFile) {
                move.setDeletedPiece(chessBoard[startFile - 1][startRank].getPiece());
                move.setDeletedFile(startFile - 1);
                chessBoard[startFile - 1][startRank].setPiece(null);
            }

            if (!isWhite() && startRank - 1 == finalRank && startFile + 1 == finalFile) {
                move.setDeletedPiece(chessBoard[startFile + 1][startRank].getPiece());
                move.setDeletedFile(startFile + 1);
                chessBoard[startFile + 1][startRank].setPiece(null);
            }

            if (!isWhite() && startRank - 1 == finalRank && startFile - 1 == finalFile) {
                move.setDeletedPiece(chessBoard[startFile - 1][startRank].getPiece());
                move.setDeletedFile(startFile - 1);
                chessBoard[startFile - 1][startRank].setPiece(null);
            }
        }

        setHasMoved(true);

        return move;
    }


    public boolean shouldEnpassant(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (isWhite() && startRank == 4 && finalRank == 5 && startFile + 1 == finalFile && chessBoard[startFile + 1][startRank].getPiece() != null && !chessBoard[startFile + 1][startRank].getPiece().isWhite() && canEnpassant(startFile + 1, startRank, chessBoard) && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }

        if (isWhite() && startRank == 4 && finalRank == 5 && startFile - 1 == finalFile && chessBoard[startFile - 1][startRank].getPiece() != null && !chessBoard[startFile - 1][startRank].getPiece().isWhite() && canEnpassant(startFile - 1, startRank, chessBoard) && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }

        if (!isWhite() && startRank == 3 && finalRank == 2 && startFile + 1 == finalFile && chessBoard[startFile + 1][startRank].getPiece() != null && chessBoard[startFile + 1][startRank].getPiece().isWhite() && canEnpassant(startFile + 1, startRank, chessBoard) && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }

        if (!isWhite() && startRank == 3 && finalRank == 2 && startFile - 1 == finalFile && chessBoard[startFile - 1][startRank].getPiece() != null && chessBoard[startFile - 1][startRank].getPiece().isWhite() && canEnpassant(startFile - 1, startRank, chessBoard) && chessBoard[finalFile][finalRank].getPiece() == null) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * canEnpassant
     * Indicates whether pawn can perform enpassant
     *
     * @param file - The file for the pawn
     * @param rank - The rank for the pawn
     * @param chessBoard - The chess board
     * @return true if pawn is able to do an enpassant
     */
    public boolean canEnpassant(int file, int rank, Square[][] chessBoard) {
        boolean underAttack = false;
        boolean isWhite = chessBoard[file][rank].getPiece().isWhite();

        // Check for different locations to see if piece would have been under attack, if it only moved one space up instead of two
        if (isWhite && rank != 0 && file != 7 && chessBoard[file + 1][rank].getPiece()!= null && !chessBoard[file + 1][rank].getPiece().isWhite()) {
            underAttack = true;
        }
        else if (isWhite && rank != 0 && file != 0 && chessBoard[file - 1][rank].getPiece()!= null && !chessBoard[file - 1][rank].getPiece().isWhite()) {
            underAttack = true;
        }
        else if (!isWhite && rank != 7 && file != 7 && chessBoard[file + 1][rank].getPiece()!= null && chessBoard[file + 1][rank].getPiece().isWhite()) {
            underAttack = true;
        }
        else if (!isWhite && rank != 7 && file != 0 && chessBoard[file - 1][rank].getPiece()!= null && chessBoard[file - 1][rank].getPiece().isWhite()) {
            underAttack = true;
        }

        return chessBoard[file][rank].getPiece().getEnpassant() && underAttack;
    }


    @Override
    public int getPicture() {
        if (!isWhite()) {
            return R.drawable.blackpawn;
        }
        else {
            return R.drawable.whitepawn;
        }
    }
}