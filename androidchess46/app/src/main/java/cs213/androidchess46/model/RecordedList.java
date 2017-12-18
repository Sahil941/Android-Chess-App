package cs213.androidchess46.model;

import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This class stores and provides functionality to maintain a list of recorded games and is the core of the
 * serialization. The list of recorded games is serialized into the savedGames.dat file in the dat folder directory
 *
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class RecordedList implements Serializable {
    private static final long serialVersionUID = 1619565176333294847L;
    public static final String storeFile = "savedGames";
    private List<RecordedGame> gamesList;


    /**
     * RecordedList constructor
     */
    public RecordedList() {
        this.gamesList = new ArrayList<RecordedGame>();
    }


    /**
     * getGamesList
     * Returns the overall list of Recorded games for the application
     *
     * @return the overall list of recorded games for the application
     */
    public List<RecordedGame> getGamesList() {
        return this.gamesList;
    }


    /**
     * addGameToList
     * Adds indicated game to the overall list of games
     *
     * @param game - The game to add to the overall list of games
     */
    public void addGameToList(RecordedGame game) {
        this.gamesList.add(game);
    }


    /**
     * toString
     * Will display the Titles of all recorded games
     *
     * @return String - all recorded games
     */
    public String toString() {
        if (this.gamesList == null) {
            return "No Recorded Games Available";
        }

        String output = "";

        for(RecordedGame game : this.gamesList) {
            output += game.getTitle() + " ";
        }

        return output;
    }


    /**
     * Read the savedGames.bin file and return the RecordedList model containing the list of all games.
     *
     * @return	return the RecordedList model of all games
     * @throws IOException	Exception for serialization
     * @throws ClassNotFoundException	Exception for serialization
     */
    public static RecordedList read(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = context.openFileInput(storeFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        RecordedList gamesList = (RecordedList) objectInputStream.readObject();
        objectInputStream.close();

        return gamesList;
    }


    /**
     * Given the RecordedList model, write this data into savedGames.dat, overwriting anything on there.
     *
     * @param gamesList	- The RecordedList model to write with
     * @throws IOException	Exception for serialization
     */
    public static void write (Context context, RecordedList gamesList) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(storeFile, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(gamesList);
        objectOutputStream.close();
        fileOutputStream.close();
    }
}