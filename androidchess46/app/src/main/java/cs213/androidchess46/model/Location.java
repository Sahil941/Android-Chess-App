package cs213.androidchess46.model;

/**
 * @author Mohamed Ameer
 * @author Sahil Kumbhani
 */
public class Location {
    private int file;
    private int rank;


    public Location(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }


    public int getFile() {
        return this.file;
    }


    public void setFile(int file) {
        this.file = file;
    }


    public int getRank() {
        return this.rank;
    }


    public void setRank(int rank) {
        this.rank = rank;
    }
}