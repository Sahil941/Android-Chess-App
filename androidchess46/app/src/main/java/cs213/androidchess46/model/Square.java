package cs213.androidchess46.model;

import cs213.androidchess46.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Square class represents a spot on the board, which has a color and an occupying piece
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Square extends ImageView implements Cloneable {
    private Piece occupyPiece;
    private boolean isWhite;


    public Square(Context context, boolean isWhite) {
        this(context, null, isWhite);
    }


    public Square(Context context, Piece piece, boolean isWhite) {
        super(context);
        setPiece(piece);
        this.isWhite = !isWhite;

        int colorID = isWhite ? R.color.whiteSquare : R.color.blackSquare;
        setBackgroundColor(getResources().getColor(colorID));
    }


    public Square(Context context, AttributeSet attributes) {
        super(context, attributes);
    }


    public Square(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
    }


    /**
     * Sets the piece occupying the square with the parameter
     *
     * @param piece - the new piece occupying the square
     */
    public void setPiece(Piece piece) {
        this.occupyPiece = piece;

        if (this.occupyPiece == null) {
            setImageDrawable(null);
        }
        else {
            setImageResource(this.occupyPiece.getPicture());
            setScaleType(ScaleType.CENTER_INSIDE);
        }
    }


    /**
     * occupyingPiece Accessor
     *
     * @return the occupying piece
     */
    public Piece getPiece() {
        return this.occupyPiece;
    }


    /**
     * Checks to see if there exists an occupyingPiece on the square
     *
     * @return true if there is an occupying piece, else false
     */
    public boolean hasPiece() {
        return this.occupyPiece != null;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        setMeasuredDimension(width, width);
    }


    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
    }


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}