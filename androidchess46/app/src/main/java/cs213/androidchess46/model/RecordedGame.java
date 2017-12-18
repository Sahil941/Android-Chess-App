package cs213.androidchess46.model;

import java.util.List;
import java.util.Calendar;
import java.io.Serializable;


/**
 * This class holds the necessary information to playback a game of chess.
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class RecordedGame implements Serializable {
    private List<RecordedMove> movesList;
    private String title;
    private Calendar date;


    public RecordedGame(String title, Calendar date, List<RecordedMove> movesList) {
        this.title = title;
        this.date = date;
        this.movesList = movesList;
    }


    public String getTitle(){
        return this.title;
    }


    public Calendar getDate(){
        return this.date;
    }


    public List<RecordedMove> getRecordedMoves() {
        return this.movesList;
    }


    public String toString() {
        return this.title + ":\n     " + this.date.getTime().toString();
    }
}