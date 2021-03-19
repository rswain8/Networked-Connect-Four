import java.io.Serializable;

public class Move implements Serializable{

    int col;
    int colour;
    boolean disconnected;

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public final int RED=0;
    public final int BLACK=1;

    public Move(int col,int colour){
        disconnected=false;
        this.col=col;this.colour=colour;}


    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
