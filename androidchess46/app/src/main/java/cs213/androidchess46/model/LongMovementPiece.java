package cs213.androidchess46.model;


/**
 * Abstract class LongMovementPiece that extends Piece (Applies to King, Queen, Rook, and Bishop)
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public abstract class LongMovementPiece extends Piece {
    public LongMovementPiece(boolean isWhite) {
        super(isWhite);
    }


    public boolean pathHasPieces(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        int file;
        int rank;
        int fileDif;
        int rankDif;

        // If movement is horizontal
        if (startRank == finalRank && startFile != finalFile) {
            // Right movement
            if (startFile < finalFile) {
                for (file = startFile + 1; file < finalFile; file++) {
                    if (chessBoard[file][startRank].hasPiece()) {
                        return true;
                    }
                }
            }

            // Left movement
            if (startFile > finalFile) {
                for (file = startFile - 1; file > finalFile; file--) {
                    if (chessBoard[file][startRank].hasPiece()) {
                        return true;
                    }
                }
            }
        }

        // If movement is vertical
        if (startRank != finalRank && startFile == finalFile) {
            // Upwards movement
            if (startRank < finalRank) {
                for (rank = startRank + 1; rank < finalRank; rank++) {
                    if (chessBoard[startFile][rank].hasPiece()) {
                        return true;
                    }
                }
            }

            // Downwards movement
            if (startRank > finalRank) {
                for (rank = startRank - 1; rank > finalRank; rank--) {
                    if (chessBoard[startFile][rank].hasPiece()) {
                        return true;
                    }
                }
            }
        }

        // If movement is diagonal
        fileDif = startFile - finalFile;
        rankDif = startRank - finalRank;
        if (Math.abs(rankDif) == Math.abs(fileDif)) {
            // Up-Right movement if both fileDif and rankDif are negative
            if (fileDif < 0 && rankDif < 0) {
                rank = startRank + 1;

                for (file = startFile + 1; file < finalFile; file++) {
                    if (chessBoard[file][rank].hasPiece()) {
                        return true;
                    }

                    rank++;
                }
            }

            // Up-Left movement if the fileDif is positive and the rankDif is negative
            if (fileDif > 0 && rankDif < 0) {
                rank = startRank + 1;

                for (file = startFile - 1; file > finalFile; file--) {
                    if (chessBoard[file][rank].hasPiece()) {
                        return true;
                    }

                    rank++;
                }
            }

            // Down-Right movement if the fileDif is negative and the rankDif is positive
            if (fileDif < 0 && rankDif > 0) {
                rank = startRank - 1;

                for (file = startFile + 1; file < finalFile; file++) {
                    if (chessBoard[file][rank].hasPiece()) {
                        return true;
                    }

                    rank--;
                }
            }

            // Down-Left movement if both fileDif and rankDif are positive
            if (fileDif > 0 && rankDif > 0) {
                rank = startRank - 1;

                for (file = startFile - 1; file > finalFile; file--) {
                    if (chessBoard[file][rank].hasPiece()) {
                        return true;
                    }

                    rank--;
                }
            }
        }

        return false;
    }
}