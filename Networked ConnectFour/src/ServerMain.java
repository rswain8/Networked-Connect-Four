import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public final int BLACK=0;
    public final int RED=1;

    public static ConnectFourGame game;

    public static void main(String[]args){

        game=new ConnectFourGame();

        try {

            ServerSocket serverSocket=new ServerSocket(8345);

            Socket socket=serverSocket.accept();

            ObjectOutputStream bos=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream bis=new ObjectInputStream(socket.getInputStream());
            bos.writeObject(new Integer(0));
            ServersListener sl=new ServersListener(bis,bos,0,game);

            Thread t=new Thread(sl);
            t.start();

            Socket socketTwo=serverSocket.accept();

            ObjectOutputStream ros=new ObjectOutputStream(socketTwo.getOutputStream());
            ObjectInputStream ris=new ObjectInputStream(socketTwo.getInputStream());
            ros.writeObject(new Integer(1));
            ServersListener sl2=new ServersListener(ris,ros,1,game);

            Thread tt=new Thread(sl2);
            tt.start();






        }
        catch(Exception e){
            System.exit(0);
            System.out.println("Error in Sever Main");
            e.printStackTrace();
        }

    }



}
