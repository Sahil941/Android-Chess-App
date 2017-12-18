package cs213.androidchess46.activity;

import cs213.androidchess46.R;
import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


/**
 * Main Activity for Chess. The Home Screen has a button to play a new
 * game of Chess and a button to view the list of recorded games of Chess.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        Log.d("Height of titlebar", result +"");
    }


    /**
     * This method is called when the user clicks the
     * play chess button in homeactivityxml.
     * Go to the play chess activity.
     *
     * @param view - Play chess button view
     */
    public void playChess(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


    /**
     * This method is called when the user clicks the
     * recorded games button in homeactivity.xml
     * Go to the recorded games activity.
     *
     * @param view - Recorded games button view
     */
    public void viewRecordedGames(View view) {
        Intent intent = new Intent(this, PlaybackGameActivity.class);
        startActivity(intent);
    }
}