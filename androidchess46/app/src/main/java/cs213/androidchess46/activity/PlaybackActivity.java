package cs213.androidchess46.activity;

import cs213.androidchess46.R;
import cs213.androidchess46.adapter.PlaybackAdapter;
import cs213.androidchess46.model.Game;
import cs213.androidchess46.model.RecordedGame;
import cs213.androidchess46.model.RecordedMove;
import java.util.List;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * The activity for viewing the playback of a selected chess game
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class PlaybackActivity extends AppCompatActivity {
    private final String WHITE_TURN = "White's Turn";
    private final String BLACK_TURN = "Black's Turn";
    private GridView chessBoard;
    private Game game;
    private TextView playerTurn;
    private TextView gameInfo;
    private Button prev;
    private Button next;
    private boolean isResign;
    private boolean isDraw;
    private RecordedGame playbackGame = null;
    private List<RecordedMove> movesList = null;
    private int moveIndex = -1;
    private RecordedMove curr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.playrecordedactivity);

        this.playbackGame = (RecordedGame) getIntent().getSerializableExtra("SelectedGame");
        this.movesList = this.playbackGame.getRecordedMoves();
        this.playerTurn = (TextView) findViewById(R.id.playerTurnTextView);
        this.prev = (Button) findViewById(R.id.prevBtn);
        this.next = (Button) findViewById(R.id.nextBtn);

        this.playerTurn.setText(WHITE_TURN);
        this.prev.setEnabled(false);
        this.isResign = false;
        this.isDraw = false;

        this.game = new Game(this);
        this.chessBoard = (GridView) findViewById(R.id.chessboardGrid);
        PlaybackAdapter adapter = new PlaybackAdapter(this, this.game);
        this.chessBoard.setAdapter(adapter);
    }


    /**
     * previous
     * Show the previous move
     *
     * @param view - Preview button view
     */
    public void previous(View view) {
        this.game.undo(this.game.getBoard());
        this.moveIndex--;
        changePlayerText();

        if (this.moveIndex < 0) {
            this.prev.setEnabled(false);
        }

        if (this.moveIndex < this.movesList.size() - 1) {
            this.next.setEnabled(true);
        }
    }


    /**
     * next
     * Shows the next move in recorded game
     *
     * @param view - Next button view
     */
    public void next(View view) {
        this.moveIndex++;
        showMove();
        changePlayerText();
        this.prev.setEnabled(true);

        if (this.moveIndex + 1 >= this.movesList.size()) {
            this.next.setEnabled(false);
        }
    }


    /**
     * showMove
     * Moves the pieces in proper location to show move of current RecordedMove
     */
    public void showMove(){
        this.curr = this.movesList.get(this.moveIndex);

        if (this.curr.isPromotion()) {
            this.game.setRecordedGamePromotion(this.curr.getPromotedPiece());
        }

        this.game.move(this.curr.getMovingStartFile(), this.curr.getMovingStartRank(), this.curr.getMovingFinalFile(), this.curr.getMovingFinalRank());

        if (this.curr.isCheck() || this.curr.isCheckmate() || this.curr.isStalemate() || this.curr.isResign() || this.curr.isDraw()) {
            showAlert();
        }
    }


    public void changePlayerText() {
        this.playerTurn.setText(this.game.isWhiteMove() ? WHITE_TURN : BLACK_TURN);
    }


    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (this.curr.isCheck() && !this.curr.isCheckmate()) {
            builder.setTitle(R.string.check_title).setPositiveButton(R.string.check_ok_dialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();}
            }).setCancelable(false).show();
            Log.d("Display Alert", "Check!");
        }

        if (this.curr.isCheckmate() || this.curr.isStalemate() || this.curr.isResign() || this.curr.isDraw()) {
            this.prev.setEnabled(false);
            String title;

            if (this.curr.isCheckmate()) {
                title = getResources().getString(R.string.checkmate_title) + " " + (!this.game.isWhiteMove() ? "White" : "Black") + " wins!";
            }
            else if (this.curr.isStalemate()) {
                title = getResources().getString(R.string.stalemate_title);
            }
            else if (this.curr.isResign()) {
                title = getResources().getString(R.string.resign_title) + " " + (!this.game.isWhiteMove() ? "White" : "Black") + " wins!";
            }
            else {
                title = getResources().getString(R.string.draw_title);
            }

            builder.setTitle(title).setPositiveButton(R.string.check_ok_dialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setCancelable(false).show();
        }
    }
}