package cs213.androidchess46.activity;

import cs213.androidchess46.R;
import cs213.androidchess46.model.RecordedGame;
import cs213.androidchess46.model.RecordedList;

import java.io.Serializable;
import java.util.Comparator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * The activity for viewing the list of recorded games of Chess.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class PlaybackGameActivity extends AppCompatActivity {
    RecordedList gamesList;
    ListView listView;
    Comparator<RecordedGame> date;
    Comparator<RecordedGame> title;
    ArrayAdapter<RecordedGame> adapter;
    RecordedGame game = null;
    Button playback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordedactivity);
        this.playback = (Button)findViewById(R.id.button);
        this.playback.setEnabled(false);

        try {
            this.gamesList = RecordedList.read(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (this.gamesList == null) {
            this.gamesList = new RecordedList();
        }

        this.adapter = new ArrayAdapter<RecordedGame>(this, android.R.layout.simple_list_item_1, this.gamesList.getGamesList());
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter(this.adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setSelection(position);
                view.setSelected(true);
                game = (RecordedGame) listView.getItemAtPosition(position);
                playback.setEnabled(true);
            }
        });

        this.date = new Comparator<RecordedGame>() {
            @Override
            public int compare(RecordedGame object1, RecordedGame object2) {
                return object1.getDate().compareTo(object2.getDate());
            }
        };

        this.title = new Comparator<RecordedGame>() {
            public int compare(RecordedGame object1, RecordedGame object2) {
                return object1.getTitle().compareTo(object2.getTitle());
            }
        };

        this.adapter.sort(this.title);
    }


    /**
     * This method is called when the user clicks the
     * Sort By Date button in recordedactivity    *
     * @param view - Sort by date button view
     */
    public void sortByDate(View view) {
        this.adapter.sort(this.date);
    }


    /**
     * This method is called when the user clicks the
     * Sort By Title button in recordedactivity    *
     * @param view - Sort by title button view
     */
    public void sortByTitle(View view) {
        this.adapter.sort(this.title);
    }


    /**
     * This method is called when the user clicks the
     * playback game button in recordedactivity    * Go to the playblack game activity.
     *
     * @param view - playback game button view
     */
    public void playbackGame(View view) {
        Intent i = new Intent(this, PlaybackActivity.class);
        i.putExtra("SelectedGame", (Serializable) this.game);
        System.out.println(this.game.getTitle());
        startActivity(i);
    }
}