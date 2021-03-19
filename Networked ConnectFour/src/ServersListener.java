import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServersListener implements Runnable{

    public static int value=0;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private static ArrayList<ObjectOutputStream>outs=new ArrayList<>();
    int turn;
    ConnectFourGame game;
    public ServersListener(ObjectInputStream is, ObjectOutputStream os, int turn,ConnectFourGame game){
        this.turn=turn;
        this.game=game;
        this.os=os;
        this.is=is;
        outs.add(os);
    }

    public void run() {

        while (true) {
            try {

                Move move = (Move) is.readObject();

                if(move.isDisconnected()){
                    System.out.println("Opponent has disconnected");
                    os.writeObject(move);
                }


                if(true){
                    game.makeTurn(move.getCol());
                    for (ObjectOutputStream out : outs) {
                        out.writeObject(move);
                        out.flush();
                    }changeTurn();}
//isnt working because it is only changing the turn for current sl

            } catch (Exception e) {
                System.out.println("Error in Server Listener Main");
                System.exit(0);
                e.printStackTrace();
            }
        }
    }
    public void changeTurn(){
        if(turn==0)
            turn=1;
        else
            turn=0;
    }
}
