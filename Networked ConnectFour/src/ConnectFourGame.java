import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;

public class ConnectFourGame extends JFrame implements KeyListener,MouseListener,WindowListener
{
    private int[][] board;
    public static final int PICKING=7;
    public static final int EMPTY = 0;
    public static final int RED = 1;
    public static final int BLACK =2;
    public static final int DRAW =3;
    public static final int RED_WINS =4;
    public static final int BLACK_WINS =5;
    public static final int PLAYING =6;

    boolean isBLACK;
    boolean playingComputer;
    boolean isTurn;
    boolean disconnected=false;

    //int colourTurn;

    //public int status=7;

    public int currentTurn=0;

    public int turn=0;

    BufferedImage buffer = new BufferedImage(700,700,BufferedImage.TYPE_4BYTE_ABGR);

    int colourTurn;

    ObjectOutputStream os;

    boolean isOver=false;

    public ConnectFourGame(ObjectOutputStream os,int colourTurn)
    {
        setSize(700,700);
       // setUndecorated(true);
        if(colourTurn==1)
            isTurn=true;
        addMouseListener(this);
        addKeyListener(this);
        addWindowListener(this);
        this.colourTurn=colourTurn;
        this.os=os;
        board = new int[6][7];
        for(int r=0; r<6;r++)
        {
            for(int c=0; c<7; c++)
            {
                board[r][c] = EMPTY;
            }
        }
        setVisible(true);
    }
    public ConnectFourGame()
    {
        setSize(700,700);
        // setUndecorated(true);
        addMouseListener(this);
        addKeyListener(this);

        this.os=os;
        board = new int[6][7];
        for(int r=0; r<6;r++)
        {
            for(int c=0; c<7; c++)
            {
                board[r][c] = EMPTY;
            }
        }
        setVisible(true);
    }
    public void paint(Graphics g){
        Graphics bg=buffer.getGraphics();

        if(isOver)
            reset();




        bg.setColor(Color.GREEN);
        bg.fillRect(0,0,getWidth(),getHeight());


        for(int r=0;r<board.length;r++)
            for(int c=0;c<board[0].length;c++) {

                if (board[r][c] == RED) {
                    bg.setColor(Color.RED);
                    bg.fillOval( c * 100,r * 100+50 ,100, 100);
                } else if (board[r][c] == BLACK) {
                    bg.setColor(Color.BLACK);
                    bg.fillOval( c * 100 ,r * 100+50, 100, 100);
                }
                else{
                    bg.setColor(Color.WHITE);
                    bg.fillOval(c*100,r*100+50,100,100);
                }
            }
        bg.setFont(new Font("Times New Roman",Font.BOLD,64));
        if(status()==BLACK_WINS){
            bg.setColor(Color.BLACK);
            bg.fillRect(100,100,500,400);
            bg.setColor(Color.RED);
            bg.drawString("Black Wins!",200,200);
            bg.setFont(new Font("Times New Roman",Font.PLAIN,32));
            bg.drawString("Press 'N' for new game",215,270);
        }
        else if(status()==RED_WINS){
            bg.setColor(Color.BLACK);
            bg.fillRect(100,100,500,400);
            bg.setColor(Color.RED);
            bg.drawString("Red Wins!",200,200);
            bg.setFont(new Font("Times New Roman",Font.PLAIN,32));
            bg.drawString("Press 'N' for new game",215,270);
        }
        else if(status()==DRAW){
            bg.setColor(Color.BLACK);
            bg.fillRect(100,100,500,400);
            bg.setColor(Color.RED);
            bg.drawString("Nobody Wins!",200,200);
            bg.setFont(new Font("Times New Roman",Font.PLAIN,32));
            bg.drawString("Press 'N' for new game",215,270);
        }  bg.setFont(new Font("Times New Roman",Font.BOLD,64));
        /*if(status==7){
            bg.setColor(Color.BLACK);
            bg.fillRect(100,100,500,400);
            bg.setColor(Color.WHITE);
            bg.drawString("Choose Opponent",100,200);
            bg.setFont(new Font("Times New Roman",Font.PLAIN,32));
            bg.setColor(Color.RED);
            bg.drawString("Computer",150,300);
            bg.setColor(Color.BLUE);
            bg.drawString("Human",450,300);
        }*/
        g.drawImage(buffer,0,0,null);
    }
    public boolean dropPiece(int c, int piece)
    {
        for(int r=5; r>=0; r--)
        {
            if(board[r][c] == EMPTY)
            {
                board[r][c] = piece;
                return true;
            }
        }

        return false;
    }

    public int status()
    {
        // horizontal
        for(int r=0; r<6;r++)
        {
            for(int c=0; c<=3; c++)
            {

                if(board[r][c] == RED &&board[r][c+1] == RED
                        &&board[r][c+2] == RED &&board[r][c+3] == RED)
                    return RED_WINS;
                else if(board[r][c] == BLACK &&board[r][c+1] == BLACK
                        &&board[r][c+2] == BLACK &&board[r][c+3] == BLACK)
                    return BLACK_WINS;
            }
        }

        // veritical
        for(int r=0; r<=2;r++)
        {
            for(int c=0; c<7; c++)
            {
                if(board[r][c] == RED &&board[r+1][c] == RED
                        &&board[r+2][c] == RED &&board[r+3][c] == RED)
                    return RED_WINS;
                else if(board[r][c] == BLACK &&board[r+1][c] == BLACK
                        &&board[r+2][c] == BLACK &&board[r+3][c] == BLACK)
                    return BLACK_WINS;
            }
        }
        //
        //
        //
        //
        for(int r=0; r<=2;r++)
        {
            for(int c=3; c<7; c++)
            {

                if(board[r][c] == RED &&board[r+1][c-1] == RED
                        &&board[r+2][c-2] == RED &&board[r+3][c-3] == RED)
                    return RED_WINS;
                else if(board[r][c] == BLACK &&board[r+1][c-1] == BLACK
                        &&board[r+2][c-2] == BLACK &&board[r+3][c-3] == BLACK)
                    return BLACK_WINS;
            }
        }

        //
        //
        //
        //
        for(int r=0; r<=2;r++)
        {
            for(int c=0; c<=3; c++)
            {

                if(board[r][c] == RED &&board[r+1][c+1] == RED
                        &&board[r+2][c+2] == RED &&board[r+3][c+3] == RED)
                    return RED_WINS;
                else if(board[r][c] == BLACK &&board[r+1][c+1] == BLACK
                        &&board[r+2][c+2] == BLACK &&board[r+3][c+3] == BLACK)
                    return BLACK_WINS;
            }
        }

        // playing
        for(int r=0; r<6;r++)
        {
            for(int c=0; c<7; c++)
            {

                if(board[r][c] == EMPTY)
                    return PLAYING;
            }
        }

        return DRAW;
    }
    public void makeTurn(int col){
         turn++;
        if(playingComputer) {

            dropPiece(col, RED);

            dropPiece((int)(Math.random()*7), BLACK);

        }
        else{

            if (turn % 2 == 0) {
                dropPiece(col, RED);

            } else {
                dropPiece(col, BLACK);
            }
        }
    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){

        if(e.getKeyChar()=='n'){
            isOver=true;}
        repaint();
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){

        int col=e.getX()/100;
        int colour=-1;



        try{
        if(turn%2==0&& isTurn){
            os.writeObject(new Move(col, colourTurn));
            os.flush();
        }else if(isTurn){
         os.writeObject(new Move(col, colourTurn));
            os.flush();
        }
        }
        catch(Exception ee){System.out.println("Error in c4g");}
    }
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void reset()
    {//System.exit(0);
        //status=7;
        for(int r=0; r<6;r++)
        {
            for(int c=0; c<7; c++)
            {
                board[r][c] = EMPTY;
            }
        }
        isOver=false;

    }
    public boolean isTurn(int c){
        if(turn%2==0 && c==0)
            return true;
        if(turn%2!=0 && c==1)
            return true;


            return false;

    }
    public boolean isBlackTurn(int c){
        if(turn%2!=0 && c==1)
            return true;
        else
            return false;
    }
    public void changeTurn(){
        if(colourTurn==1)
            colourTurn=0;
        else
            colourTurn=1;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("s");
        Move m=new Move(0,5);
        m.setDisconnected(true);
        try{os.writeObject(m);}
        catch(Exception ee){}
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}