package cs213.androidchess46.activity;

import cs213.androidchess46.R;
import cs213.androidchess46.adapter.SquareAdapter;
import cs213.androidchess46.model.Game;
import cs213.androidchess46.model.RecordedGame;
import cs213.androidchess46.model.RecordedList;

import java.io.IOException;
import java.util.Calendar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;


/**
 * The Chess Activity Screen.
 * The activity for actually playing chess.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class GameActivity extends AppCompatActivity {
    private final String WHITE_TURN = "White Player's Turn";
    private final String BLACK_TURN = "Black Player's Turn";
    private GridView chessBoard;
    private Game game;
    private TextView playerTurn;
    private Button undo;
    private Button AI;
    private Button resign;
    private Button draw;
    private boolean isResign;
    private boolean isDraw;
    private boolean isDrawConfirmed;
    private RecordedGame recordedGame = null;
    RecordedList gamesList = new RecordedList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.gamesList = RecordedList.read(this);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        if (this.gamesList == null) {
            this.gamesList = new RecordedList();
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        this.playerTurn = (TextView) findViewById(R.id.playerTurnTextView);
        this.undo = (Button) findViewById(R.id.undoBtn);
        this.AI = (Button) findViewById(R.id.aiBtn);
        this.resign = (Button) findViewById(R.id.resignBtn);
        this.draw = (Button) findViewById(R.id.drawBtn);
        this.playerTurn.setText(WHITE_TURN);
        this.undo.setEnabled(false);
        this.isResign = false;
        this.isDraw = false;
        this.game = new Game(this);
        this.chessBoard = (GridView) findViewById(R.id.chessboardGrid);
        SquareAdapter adapter = new SquareAdapter(this, this.game);
        this.chessBoard.setAdapter(adapter);
    }


    /**
     * Undo the previous move.
     * Can only undo 1 move in the past.
     * Once undo is pressed, it becomes deactivated until someone else moves.
     *
     * @param view - Undo button view
     */
    public void undo(View view) {
        this.game.undo(this.game.getBoard());
        changePlayerText();
        this.game.createClone();
        this.undo.setEnabled(false);
    }


    /**
     * Lets the AI choose a random valid move from the set of valid moves.
     * Calls the move method with this move.
     *
     * @param view - AI button view
     */
    public void ai(View view) {
        this.game.ai();
        changePlayerText();
        this.undo.setEnabled(true);
        resetDraw();

        if (this.game.isCheck() || this.game.isCheckmate() || this.game.isStalemate()) {
            showAlert();
        }
    }


    /**
     * The current player resigns and the game is over.
     *
     * @param view - Resign button view
     */
    public void resign(View view) {
        this.isResign = true;

        if (this.game.getRecordedMoves().isEmpty()) {
            finish();
        }
        else {
            showAlert();
        }
    }


    /**
     * The current player asks for a draw.
     * The next player can choose to accept the draw or not.
     *
     * @param view - Draw button view
     */
    public void draw(View view) {
        if (!this.isDraw) {
            this.isDraw = true;
            this.draw.setText(R.string.draw_confirm);
        }
        else {
            this.isDrawConfirmed = true;

            if (this.game.getRecordedMoves().isEmpty()) {
                finish();
            }
            else {
                showAlert();
            }
        }
    }


    public void move(int startFile, int startRank, int finalFile, int finalRank) {
        if (this.game.validMove(startFile, startRank, finalFile, finalRank)) {
            this.game.move(startFile, startRank, finalFile, finalRank);
            changePlayerText();
            this.undo.setEnabled(true);
            resetDraw();

            if (this.game.isCheck() || this.game.isCheckmate() || this.game.isStalemate()) {
                showAlert();
            }
        }
    }


    public void changePlayerText() {
        this.playerTurn.setText(this.game.isWhiteMove() ? WHITE_TURN : BLACK_TURN);
    }


    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (this.game.isCheck() && !this.game.isCheckmate()) {
            builder.setTitle(R.string.check_title).setPositiveButton(R.string.check_ok_dialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setCancelable(false).show();
            Log.d("Display Alert", "Check!");
        }

        if (this.game.isCheckmate() || this.game.isStalemate() || this.isResign || this.isDrawConfirmed) {
            this.undo.setEnabled(false);
            this.AI.setEnabled(false);
            this.draw.setEnabled(false);
            this.resign.setEnabled(false);
            String title;

            if (this.game.isCheckmate()) {
                title = getResources().getString(R.string.checkmate_title) + " " + (!this.game.isWhiteMove() ? "White" : "Black") + " Player wins!";
            }
            else if (this.game.isStalemate()) {
                title = getResources().getString(R.string.stalemate_title);
            }
            else if (this.isResign) {
                title = getResources().getString(R.string.resign_title) + " " + (!this.game.isWhiteMove() ? "White" : "Black") + " Player wins!";
            }
            else {
                title = getResources().getString(R.string.draw_title);
            }

            final EditText titleInput = new EditText(this);
            titleInput.setInputType(InputType.TYPE_CLASS_TEXT);
            titleInput.setHint(R.string.dialog_title_hint);

            builder.setTitle(title).setMessage(R.string.dialog_save_match_message).setView(titleInput).setPositiveButton(R.string.dialog_save_match_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            }).setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).setCancelable(false);

            final AlertDialog dialog = builder.create();
            dialog.show();

            //Overriding the handler immediately after show to prevent it from dismissing no matter what
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!titleInput.getText().toString().trim().isEmpty()) {
                        recordedGame = new RecordedGame(titleInput.getText().toString(), Calendar.getInstance(), game.getRecordedMoves());

                        if (!recordedGame.getRecordedMoves().isEmpty()) {
                            recordedGame.getRecordedMoves().get(recordedGame.getRecordedMoves().size()-1).setResign(isResign);
                            recordedGame.getRecordedMoves().get(recordedGame.getRecordedMoves().size()-1).setDraw(isDrawConfirmed);
                        }

                        gamesList.addGameToList(recordedGame);

                        try {
                            RecordedList.write(getApplicationContext(), gamesList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        finish();
                    }
                }
            });
        }
    }


    public void resetDraw() {
        this.isDraw = false;
        this.draw.setText(R.string.draw_btn);
    }
}