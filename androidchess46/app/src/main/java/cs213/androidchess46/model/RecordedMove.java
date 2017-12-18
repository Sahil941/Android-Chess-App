package cs213.androidchess46.model;

import java.io.Serializable;


/**
 * This class records the move done by a player.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class RecordedMove implements Serializable {
    private Piece deletedPiece;
    private Piece promotedPiece;
    private boolean hasMoved;
    private boolean enpassant;
    private boolean isCheck;
    private boolean isCheckmate;
    private boolean isStalemate;
    private boolean isResign;
    private boolean isDraw;
    private boolean isPromotion;
    private int movingStartFile;
    private int movingStartRank;
    private int movingFinalFile;
    private int movingFinalRank;
    private int deletedFile;
    private int deletedRank;
    private int castledStartFile;
    private int castledStartRank;
    private int castledFinalFile;
    private int castledFinalRank;


    public RecordedMove (boolean hasMoved, boolean enpassant, int movingStartFile, int movingStartRank, int movingFinalFile, int movingFinalRank, Piece deletedPiece, int deletedFile, int deletedRank) {
        this(hasMoved, enpassant, movingStartFile, movingStartRank, movingFinalFile, movingFinalRank, deletedPiece, deletedFile, deletedRank, -1, -1, -1, -1);
    }


    public RecordedMove (boolean hasMoved, boolean enpassant, int movingStartFile, int movingStartRank, int movingFinalFile, int movingFinalRank, Piece deletedPiece, int deletedFile, int deletedRank, int castledStartFile, int castledStartRank, int castledFinalFile, int castledFinalRank) {
        this.hasMoved = hasMoved;
        this.enpassant = enpassant;
        this.movingStartFile = movingStartFile;
        this.movingStartRank = movingStartRank;
        this.movingFinalFile = movingFinalFile;
        this.movingFinalRank = movingFinalRank;
        this.deletedPiece = deletedPiece;
        this.deletedFile = deletedFile;
        this.deletedRank = deletedRank;
        this.castledStartFile = castledStartFile;
        this.castledStartRank = castledStartRank;
        this.castledFinalFile = castledFinalFile;
        this.castledFinalRank = castledFinalRank;
        this.isCheck = false;
        this.isCheckmate = false;
        this.isStalemate = false;
        this.isResign = false;
        this.isDraw = false;
        this.isPromotion = false;
        this.promotedPiece = null;
    }


    public Piece getPromotedPiece() {
        return this.promotedPiece;
    }


    public void setPromotedPiece(Piece promotedPiece) {
        this.promotedPiece = promotedPiece;
    }


    public boolean hasMovedBefore() {
        return this.hasMoved;
    }


    public void setCastledStartFile(int castledStartFile) {
        this.castledStartFile = castledStartFile;
    }


    public void setCastledStartRank(int castledStartRank) {
        this.castledStartRank = castledStartRank;
    }


    public void setCastledFinalFile(int castledFinalFile) {
        this.castledFinalFile = castledFinalFile;
    }


    public void setCastledFinalRank(int castledFinalRank) {
        this.castledFinalRank = castledFinalRank;
    }


    public boolean isResign() {
        return this.isResign;
    }


    public void setResign(boolean resign) {
        this.isResign = resign;
    }


    public boolean isPromotion() {
        return this.isPromotion;
    }


    public void setPromotion(boolean promotion) {
        this.isPromotion = promotion;
    }


    public boolean isDraw() {
        return this.isDraw;
    }


    public void setDraw(boolean draw) {
        this.isDraw = draw;
    }


    public boolean isCheck() {
        return this.isCheck;
    }


    public void setCheck(boolean inCheck) {
        this.isCheck = inCheck;
    }


    public boolean isCheckmate() {
        return this.isCheckmate;
    }


    public void setCheckmate(boolean inCheckmate) {
        this.isCheckmate = inCheckmate;
    }


    public boolean isStalemate() {
        return this.isStalemate;
    }


    public void setStalemate(boolean inStalemate) {
        this.isStalemate = inStalemate;
    }


    public void setDeletedPiece(Piece deletedPiece) {
        this.deletedPiece = deletedPiece;
    }


    public void setDeletedFile(int deletedFile) {
        this.deletedFile = deletedFile;
    }


    public void setDeletedRank(int deletedRank) {
        this.deletedRank = deletedRank;
    }


    public Piece getDeletedPiece() {
        return this.deletedPiece;
    }


    public int getMovingStartFile() {
        return this.movingStartFile;
    }


    public int getMovingStartRank() {
        return this.movingStartRank;
    }


    public int getMovingFinalFile() {
        return this.movingFinalFile;
    }


    public int getMovingFinalRank() {
        return this.movingFinalRank;
    }


    public int getDeletedFile() {
        return this.deletedFile;
    }


    public int getDeletedRank() {
        return this.deletedRank;
    }


    public int getCastledStartFile() {
        return this.castledStartFile;
    }


    public int getCastledStartRank() {
        return this.castledStartRank;
    }


    public int getCastledFinalFile() {
        return this.castledFinalFile;
    }


    public int getCastledFinalRank() {
        return this.castledFinalRank;
    }
}