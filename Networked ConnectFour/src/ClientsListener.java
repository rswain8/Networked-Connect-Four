import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientsListener implements Runnable{



    private ObjectInputStream is;
    private ConnectFourGame frame;

    public ClientsListener(ObjectInputStream is,ConnectFourGame frame){

        this.is=is;
        this.frame=frame;


    }

    public void run(){
        try {

            while(true) {



                Move m=(Move)is.readObject();

                if(m.disconnected){
                    System.out.println("Opponent has disconnected, game now closing");
                    System.exit(0);
                }


                if(m.getColour()!=frame.colourTurn)
                    frame.isTurn=true;
                else
                    frame.isTurn=false;

                frame.makeTurn(m.getCol());




                frame.repaint();

            }
        }
        catch(Exception e){
            System.out.println("Error in clients Listener Main");
            e.printStackTrace();
        }

    }

    /*@Override
    public void mouseClicked(MouseEvent e) {
        int col=e.getX()/100;

        if(frame.status!=7)


        frame.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/
}
