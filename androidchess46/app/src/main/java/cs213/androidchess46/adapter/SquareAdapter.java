package cs213.androidchess46.adapter;

import cs213.androidchess46.activity.GameActivity;
import cs213.androidchess46.listener.SquareDragEventListener;
import cs213.androidchess46.model.Game;
import cs213.androidchess46.model.Square;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;


/**
 * This adapter is for displaying the squares on the chess board gridview.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class SquareAdapter extends BaseAdapter {
    private GameActivity chessActivity;
    private Context context;
    private Square[][] chessBoard;
    private Game game;
    private int item;


    public SquareAdapter(Context context, Game game) {
        this.context = context;
        this.chessBoard = game.getBoard();
        this.game = game;
        this.item = -1;
        this.chessActivity = (GameActivity) context;
    }


    @Override
    public Object getItem(int pos) {
        int file = pos % 8;
        int rank = Math.abs((pos - file)/8 - 7);

        return getItem(file, rank);
    }


    @Override
    public int getCount() {
        return 64;
    }


    public Object getItem(int file, int rank) {
        return this.chessBoard[file][rank];
    }


    @Override
    public long getItemId(int pos) {
        return pos;
    }


    @Override
    public Square getView(final int pos, View view, final ViewGroup parent) {
        final int file = pos % 8;
        final int rank = Math.abs((pos - file)/8 - 7);
        final Square img = this.chessBoard[file][rank];
        img.setTag(file + " " + rank);

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(chessBoard[file][rank]);
                v.startDrag(dragData, myShadow, chessBoard[file][rank],0);

                return true;
            }
        });

        int barHeight = ((Activity) this.context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        img.setOnDragListener(new SquareDragEventListener(this.chessBoard, (GridView) parent, barHeight, this.context));

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item == -1) {
                    if (((Square) v).hasPiece() && ((Square) v).getPiece().isWhite() == game.isWhiteMove()) {
                        ((Square) v).setColorFilter(Color.BLUE);
                        item = pos;
                    }
                }
                else if (item == pos) {
                    ((Square) v).clearColorFilter();
                    item = -1;
                }
                else {
                    int startFile;
                    int startRank;
                    int finalFile;
                    int finalRank;

                    startFile = item % 8;
                    startRank = Math.abs((item - startFile)/8 - 7);
                    chessBoard[startFile][startRank].clearColorFilter();
                    finalFile = pos % 8;
                    finalRank = Math.abs((pos - finalFile)/8 - 7);
                    chessActivity.move(startFile, startRank, finalFile, finalRank);

                    item = -1;
                }
            }
        });

        return this.chessBoard[file][rank];
    }
}