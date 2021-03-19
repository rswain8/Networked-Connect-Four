import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMain {


    public static void main(String[]args){
        try {


            //school computer
            Socket socket=new Socket("T155204",8345);
            //home computer
            //Socket socket=new Socket("10.0.0.107",8345);

            //make input/output streams to this client
            ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is=new ObjectInputStream(socket.getInputStream());

            Integer colourTurn=(Integer)is.readObject();

            ClientsListener cl=new ClientsListener(is,new ConnectFourGame(os,colourTurn));

            Thread t=new Thread(cl);
            t.start();

        }
        catch(Exception e){
            System.out.println("Error in Clinet Main");
            e.printStackTrace();
        }

    }
}
