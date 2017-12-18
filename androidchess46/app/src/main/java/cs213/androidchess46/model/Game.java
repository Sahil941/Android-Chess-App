package cs213.androidchess46.model;

import cs213.androidchess46.R;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;


/**
 * Game class, which holds the board in a game of chess.
 * Essentially holds an instance of a game in chess.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Game {
    static final boolean WHITE = true;
    static final boolean BLACK = false;
    private Square[][] chessBoard;
    private Square[][] clone;
    private boolean isDone;
    private boolean isStalemate;
    private boolean isCheckmate;
    private boolean isResign;
    private boolean isWhiteWin;
    private boolean isWhiteMove;
    private boolean isWhiteCheck;
    private boolean isBlackCheck;
    private boolean isCheck;
    private boolean isDraw;
    private boolean recPromot;
    int[] recPromotPos;
    private Location whiteKingLoc;
    private Location blackKingLoc;
    private List<RecordedMove> movesList;
    private Context context;


    public Game(Context context) {
        this.chessBoard = new Square[8][8];
        this.context = context;

        // Initialize first rank of Black's side
        this.chessBoard[0][7] = new Square(context, new Rook(this.BLACK), this.WHITE);
        this.chessBoard[7][7] = new Square(context, new Rook(this.BLACK), this.BLACK);
        this.chessBoard[1][7] = new Square(context, new Knight(this.BLACK), this.BLACK);
        this.chessBoard[6][7] = new Square(context, new Knight(this.BLACK), this.WHITE);
        this.chessBoard[2][7] = new Square(context, new Bishop(this.BLACK), this.WHITE);
        this.chessBoard[5][7] = new Square(context, new Bishop(this.BLACK), this.BLACK);
        this.chessBoard[3][7] = new Square(context, new Queen(this.BLACK), this.BLACK);
        this.chessBoard[4][7] = new Square(context, new King(this.BLACK), this.WHITE);

        // Initialize first rank of White's side
        this.chessBoard[0][0] = new Square(context, new Rook(this.WHITE), this.BLACK);
        this.chessBoard[7][0] = new Square(context, new Rook(this.WHITE), this.WHITE);
        this.chessBoard[1][0] = new Square(context, new Knight(this.WHITE), this.WHITE);
        this.chessBoard[6][0] = new Square(context, new Knight(this.WHITE), this.BLACK);
        this.chessBoard[2][0] = new Square(context, new Bishop(this.WHITE), this.BLACK);
        this.chessBoard[5][0] = new Square(context, new Bishop(this.WHITE), this.WHITE);
        this.chessBoard[3][0] = new Square(context, new Queen(this.WHITE), this.WHITE);
        this.chessBoard[4][0] = new Square(context, new King(this.WHITE), this.BLACK);

        int file;
        int rank;

        // Initialize all black pawns
        rank = 6;
        for (file = 0; file < 8; file++) {
            if (file % 2 == 0) {
                this.chessBoard[file][rank] = new Square(context, new Pawn(this.BLACK), this.BLACK);
            }
            else {
                this.chessBoard[file][rank] = new Square(context, new Pawn(this.BLACK), this.WHITE);
            }
        }

        // Initialize all empty squares
        for (rank = 5; rank >= 2; rank--) {
            for (file = 0; file < 8; file++) {
                if ((file % 2 == 0 && rank % 2 == 0) || (file % 2 != 0 && rank % 2 != 0)) {
                    this.chessBoard[file][rank] = new Square(context, this.BLACK);
                }
                else {
                    this.chessBoard[file][rank] = new Square(context, this.WHITE);
                }
            }
        }

        // Initialize all white pawns
        rank = 1;
        for (file = 0; file < 8; file++) {
            if (file % 2 == 0) {
                this.chessBoard[file][rank] = new Square(context, new Pawn(this.WHITE), this.WHITE);
            }
            else {
                this.chessBoard[file][rank] = new Square(context, new Pawn(this.WHITE), this.BLACK);
            }
        }

        // Initialize all private booleans
        this.isWhiteMove = true;
        this.isDone = false;
        this.isStalemate = false;
        this.isCheckmate = false;
        this.isResign = false;
        this.isWhiteWin = false;
        this.isWhiteCheck = false;
        this.isBlackCheck = false;
        this.isCheck = false;
        this.isDraw = false;
        this.recPromot = false;
        this.recPromotPos = new int[1];
        this.recPromotPos[0] = -1;
        this.movesList = new ArrayList<RecordedMove>();
        this.whiteKingLoc = new Location(4,0);
        this.blackKingLoc = new Location(4,7);
        this.clone = createClone(this.chessBoard);
    }


    public Square[][] getBoard() {
        return this.chessBoard;
    }


    /**
     * Check to see if the move is a valid piece move (from within piece class)
     *
     * @param startFile - The starting file of the piece
     * @param startRank - The starting rank of the piece
     * @param finalFile - The ending file of the piece
     * @param finalRank - The ending rank of the piece
     * @param chessBoard - Can send either real board or clone board (for testing valid move)
     * @return true if it's a valid piece move, else false
     */
    public boolean validPieceMove(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        if (startFile == finalFile && startRank == finalRank) {
            return false;
        }

        if (!chessBoard[startFile][startRank].hasPiece()) {
            return false;
        }

        if (chessBoard[startFile][startRank].getPiece().isWhite() != isWhiteMove()) {
            return false;
        }

        return chessBoard[startFile][startRank].getPiece().validMove(startFile, startRank, finalFile, finalRank, chessBoard);
    }


    /**
     * Check if move is truly valid (both valid piece move and does not bring own king in check)
     *
     * @param startFile - The starting file of the piece
     * @param startRank - The starting rank of the piece
     * @param finalFile - The ending file of the piece
     * @param finalRank - The ending rank of the piece
     * @return true if it is a truly valid move, else false
     */
    public boolean validMove(int startFile, int startRank, int finalFile, int finalRank) {
        if (validPieceMove(startFile, startRank, finalFile, finalRank, this.chessBoard)) {
            movePiece(startFile, startRank, finalFile, finalRank, this.clone);

            boolean kingCheck = isCheck(this.clone, !isWhiteMove());
            undo(this.clone);

            return !kingCheck;
        }

        return false;
    }


    /**
     * Move the piece, and set isWhitesMove to the other side
     *
     * @param startFile - The starting file of the piece
     * @param startRank - The starting rank of the piece
     * @param finalFile - The ending file of the piece
     * @param finalRank - The ending rank of the piece
     * @param chessBoard - The chess board that is being played on
     */
    public void movePiece(int startFile, int startRank, int finalFile, int finalRank, Square[][] chessBoard) {
        RecordedMove move = chessBoard[startFile][startRank].getPiece().move(startFile, startRank, finalFile, finalRank, chessBoard);
        this.movesList.add(move);

        if (chessBoard[finalFile][finalRank].getPiece() instanceof King) {
            if (chessBoard[finalFile][finalRank].getPiece().isWhite()) {
                this.whiteKingLoc.setFile(finalFile);
                this.whiteKingLoc.setRank(finalRank);
            }
            else {
                this.blackKingLoc.setFile(finalFile);
                this.blackKingLoc.setRank(finalRank);
            }
        }

        if (chessBoard[finalFile][finalRank].getPiece() instanceof Pawn) {
            if (isWhiteMove() && finalRank == 7) {
                promotion(startFile, startRank, finalFile, finalRank, chessBoard, move);
            }

            if (!isWhiteMove() && finalRank == 0) {
                promotion(startFile, startRank, finalFile, finalRank, chessBoard, move);
            }
        }

        changePlayer();
    }


    /**
     * Move the piece, then test for check
     *
     * @param startFile - The starting file of the piece
     * @param startRank - The starting rank of the piece
     * @param finalFile - The ending file of the piece
     * @param finalRank - The ending rank of the piece
     */
    public void move(int startFile, int startRank, int finalFile, int finalRank) {
        movePiece(startFile, startRank, finalFile, finalRank, this.chessBoard);
        createClone();
        checkTest();
        this.movesList.get(this.movesList.size() - 1).setCheck(this.isCheck);
        this.movesList.get(this.movesList.size() - 1).setCheckmate(this.isCheckmate);
        this.movesList.get(this.movesList.size() - 1).setStalemate(this.isStalemate);
    }


    public void undo(Square[][] chessBoard) {
        RecordedMove move = this.movesList.remove(this.movesList.size() - 1);

        if (move.isPromotion()) {
            chessBoard[move.getMovingFinalFile()][move.getMovingFinalRank()].setPiece(move.getDeletedPiece());
        }

        chessBoard[move.getMovingStartFile()][move.getMovingStartRank()].setPiece(chessBoard[move.getMovingFinalFile()][move.getMovingFinalRank()].getPiece());

        if (chessBoard[move.getMovingStartFile()][move.getMovingStartRank()].getPiece() instanceof King) {
            if (chessBoard[move.getMovingStartFile()][move.getMovingStartRank()].getPiece().isWhite()) {
                this.whiteKingLoc.setFile(move.getMovingStartFile());
                this.whiteKingLoc.setRank(move.getMovingStartRank());
            }
            else {
                this.blackKingLoc.setFile(move.getMovingStartFile());
                this.blackKingLoc.setRank(move.getMovingStartRank());
            }
        }

        chessBoard[move.getMovingFinalFile()][move.getMovingFinalRank()].setPiece(null);

        if (!move.isPromotion()) {
            chessBoard[move.getMovingStartFile()][move.getMovingStartRank()].getPiece().setHasMoved(move.hasMovedBefore());
            chessBoard[move.getDeletedFile()][move.getDeletedRank()].setPiece(move.getDeletedPiece());
        }

        if (move.getCastledStartFile() != -1) {
            chessBoard[move.getCastledStartFile()][move.getCastledStartRank()].setPiece(chessBoard[move.getCastledFinalFile()][move.getCastledFinalRank()].getPiece());
            chessBoard[move.getCastledFinalFile()][move.getCastledFinalRank()].setPiece(null);
            chessBoard[move.getCastledStartFile()][move.getCastledStartRank()].getPiece().setHasMoved(false);
        }

        changePlayer();
    }


    public List<Pair<Location, Location>> getAllValidMoves() {
        List<Pair<Location, Location>> allValidMoves = new ArrayList<Pair<Location, Location>>();
        Pair<Location, Location> move = null;

        for (int startFile = 0; startFile < 8; startFile++) {
            for (int startRank = 0; startRank < 8; startRank++) {
                for (int finalFile = 0; finalFile < 8; finalFile++) {
                    for (int finalRank = 0; finalRank < 8; finalRank++) {
                        if (validMove(startFile, startRank, finalFile, finalRank)) {
                            move = new Pair<Location,Location>(new Location(startFile, startRank), new Location(finalFile, finalRank));
                            allValidMoves.add(move);
                        }
                    }
                }
            }
        }

        return allValidMoves;
    }


    /**
     * Check to see if king is in check.
     * It's in check if there exists a valid piece move towards the enemy king (isKingWhite)
     *
     * @param chessBoard - The chess board the game is being played on
     * @param isWhiteKing - Whether the piece is a white or black King
     * @return true if the king is in check
     */
    public boolean isCheck(Square[][] chessBoard, boolean isWhiteKing) {
        int kingFile;
        int kingRank;

        if (!isWhiteKing) {
            kingFile = this.blackKingLoc.getFile();
            kingRank = this.blackKingLoc.getRank();
        }
        else {
            kingFile = this.whiteKingLoc.getFile();
            kingRank = this.whiteKingLoc.getRank();
        }

        for (int startFile = 0; startFile < 8; startFile++) {
            for (int startRank = 0; startRank < 8; startRank++) {
                if (validPieceMove(startFile, startRank, kingFile, kingRank, chessBoard)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Test to see if king is in check
     */
    public void checkTest() {
        changePlayer();

        if (!isCheck(this.chessBoard, !isWhiteMove())) {
            unsetCheck();
        }
        else {
            setCheck();
        }
    }


    public void setCheck() {
        this.isCheck = true;
        changePlayer();
        checkmateTest();
    }


    public void checkmateTest() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();
        this.isCheckmate = false;

        if (allMoves.isEmpty()) {
            this.isCheckmate = true;
        }
    }


    public void stalemateTest() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();
        this.isStalemate = false;

        if (allMoves.isEmpty()) {
            this.isStalemate = true;
        }
    }


    public void unsetCheck() {
        this.isCheck = false;
        changePlayer();
        stalemateTest();
    }


    public boolean isWhiteMove() {
        return this.isWhiteMove;
    }


    public void changePlayer() {
        this.isWhiteMove = !isWhiteMove();
    }


    public void createClone() {
        this.clone = createClone(this.chessBoard);
    }


    /**
     * createClone
     *
     * @param chessBoard - The chess board the game is being played on
     * @return a deep clone of board, for checking moves
     */
    public Square[][] createClone(Square[][] chessBoard) {
        Square[][] clone = new Square[8][8];

        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                try {
                    clone[file][rank] = (Square) chessBoard[file][rank].clone();
                }
                catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return clone;
    }


    public void ai() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();

        if (allMoves.size() == 0) {
            return;
        }

        int randNum = (int) (Math.random() * allMoves.size());
        Pair<Location, Location> randMove = allMoves.get(randNum);
        move(randMove.first.getFile(), randMove.first.getRank(), randMove.second.getFile(), randMove.second.getRank());
    }


    public boolean isCheck() {
        return this.isCheck;
    }


    public boolean isCheckmate() {
        return this.isCheckmate;
    }


    public boolean isStalemate() {
        return this.isStalemate;
    }


    public List<RecordedMove> getRecordedMoves() {
        return this.movesList;
    }


    public void promotion(int startFile, int startRank, final int finalFile, final int finalRank, Square[][] chessBoard, final RecordedMove move) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        final int[] pos = new int[1];
        Piece delPawn = chessBoard[finalFile][finalRank].getPiece();

        if (!this.clone.equals(chessBoard)) {
            if (this.recPromot == false) {
                builder.setTitle(R.string.promotion_title).setItems(R.array.promotion_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pos[0] = which;
                        dialog.dismiss();
                        promote(finalFile, finalRank, pos, move);
                    }
                }).setCancelable(false).show();
            }
            else {
                promote(finalFile, finalRank, this.recPromotPos, move);
            }
        }
        else {
            chessBoard[finalFile][finalRank].setPiece(new Queen(!isWhiteMove()));
            chessBoard[finalFile][finalRank].getPiece().setHasMoved(true);
            move.setPromotedPiece(chessBoard[finalFile][finalRank].getPiece());
        }

        move.setDeletedPiece(delPawn);
        move.setDeletedFile(startFile);
        move.setDeletedRank(startRank);
        move.setPromotion(true);
    }


    public void promote(int file, int rank, int[] pos, RecordedMove move) {
        switch(pos[0]) {
            case 0:
                if (!this.recPromot) {
                    this.chessBoard[file][rank].setPiece(new Queen(!isWhiteMove()));
                }
                else {
                    this.chessBoard[file][rank].setPiece(new Queen(isWhiteMove()));
                }

                this.chessBoard[file][rank].getPiece().setHasMoved(true);
                break;
            case 1:
                if (!this.recPromot) {
                    this.chessBoard[file][rank].setPiece(new Knight(!isWhiteMove()));
                }
                else {
                    this.chessBoard[file][rank].setPiece(new Knight(isWhiteMove()));
                }

                this.chessBoard[file][rank].getPiece().setHasMoved(true);
                break;
            case 2:
                if (!this.recPromot) {
                    this.chessBoard[file][rank].setPiece(new Rook(!isWhiteMove()));
                }
                else {
                    this.chessBoard[file][rank].setPiece(new Rook(isWhiteMove()));
                }

                this.chessBoard[file][rank].getPiece().setHasMoved(true);
                break;
            case 3:
                if (!this.recPromot) {
                    this.chessBoard[file][rank].setPiece(new Bishop(!isWhiteMove()));
                }
                else {
                    this.chessBoard[file][rank].setPiece(new Bishop(isWhiteMove()));
                }

                this.chessBoard[file][rank].getPiece().setHasMoved(true);
                break;
            default:
                break;
        }

        move.setPromotedPiece(this.chessBoard[file][rank].getPiece());
    }


    public void setRecordedGamePromotion(Piece piece) {
        this.recPromot = true;

        if (piece.getClass().equals(Queen.class)) {
            this.recPromotPos[0] = 0;
        }
        else if (piece.getClass().equals(Knight.class)) {
            this.recPromotPos[0] = 1;
        }
        else if (piece.getClass().equals(Rook.class)) {
            this.recPromotPos[0] = 2;
        }
        else if (piece.getClass().equals(Bishop.class)) {
            this.recPromotPos[0] = 3;
        }
    }
}