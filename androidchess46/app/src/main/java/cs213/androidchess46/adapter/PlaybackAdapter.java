package cs213.androidchess46.adapter;

import cs213.androidchess46.activity.PlaybackActivity;
import cs213.androidchess46.model.Game;
import cs213.androidchess46.model.Square;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.content.Context;
import android.widget.BaseAdapter;


/**
 * This adapter is for displaying the squares on the chess board
 * gridview specifically for the playback view of recorded chess games.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class PlaybackAdapter extends BaseAdapter {
    private PlaybackActivity playbackActivity;
    private Context context;
    private Square[][] chessBoard;
    private Game game;
    private int item;


    public PlaybackAdapter(Context context, Game game) {
        this.context = context;
        this.chessBoard = game.getBoard();
        this.game = game;
        this.item = -1;
        this.playbackActivity = (PlaybackActivity) context;
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

        int barHeight = ((Activity) this.context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

        return this.chessBoard[file][rank];
    }
}