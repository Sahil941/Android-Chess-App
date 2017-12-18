package cs213.androidchess46.listener;

import cs213.androidchess46.activity.GameActivity;
import cs213.androidchess46.model.Square;
import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;


/**
 * The class is the listener for dragging pieces around.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class SquareDragEventListener implements View.OnDragListener{
    private GameActivity chessActivity;
    private Square[][] chessBoard;
    private GridView gridView;
    private Context context;
    private int barHeight;
    private int startFile;
    private int startRank;
    private int finalFile;
    private int finalRank;


    public SquareDragEventListener(Square[][] chessBoard, GridView gridView, int barHeight, Context context) {
        this.chessBoard = chessBoard;
        this.gridView = gridView;
        this.barHeight = barHeight;
        this.context = context;
        this.chessActivity = (GameActivity) context;
    }


    @Override
    public boolean onDrag(View view, DragEvent event) {
        int x;
        int y;

        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                x = (int) event.getX();
                y = (int) event.getY();
                y -= this.barHeight;

                this.startFile = this.gridView.pointToPosition(x, y) % 8;
                this.startRank = Math.abs((this.gridView.pointToPosition(x, y) - this.startFile)/8 - 7);

                if (!this.chessBoard[this.startFile][this.startRank].hasPiece()) {
                    return false;
                }

                break;
            case DragEvent.ACTION_DROP:
                this.finalFile = this.gridView.getPositionForView(view) % 8;
                this.finalRank = Math.abs((this.gridView.getPositionForView(view) - this.finalFile)/8 - 7);

                if (this.startFile == this.finalFile && this.startRank == this.finalRank) {
                    return true;
                }

                this.chessActivity.move(this.startFile, this.startRank, this.finalFile, this.finalRank);

                break;
            default: break;
        }

        return true;
    }
}