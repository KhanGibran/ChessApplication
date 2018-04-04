import java.util.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Chess_board_interface extends JPanel implements  MouseMotionListener,MouseListener
{
    
    static int size=87;
    static int x=0,y=0;
    static int oldx,oldy;
    static int newx,newy;
    @Override
    public void paintComponent(Graphics g)
    {
       
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for(int i=0;i<64;i+=2)
        {
            if((i/8)%2==0)
                g.setColor(new Color(128,128,128));
            else
                g.setColor(new Color(255, 255, 255));
            
            g.fillRect((i%8 )*size  +320, (i/8)*size + 7,size , size);  // first two are the starting -->x and y(V) coordinates and rest two are width and height
            
            if((i/8)%2!=0)
                g.setColor(new Color(128,128,128));
            else
                g.setColor(new Color(255, 255, 255));
            
            g.fillRect(((i+1)%8)*size +320,((i+1)/8)* size + 7, size ,size);
        }
        Image PieceImage;
        PieceImage=new ImageIcon("img\\chesspiece2.png").getImage();
     int m=0;
     while(m<64)
     {
            int j1=-1,c=-1;
            String ch=Chess_controls.chessmatrix[m/8][m%8];
            switch (ch) 
            {
                case "A": j1=0; c=0;
                    break;
                case "a": j1=0; c=1;
                    break;
                case "B": j1=2; c=0;
                    break;
                case "r": j1=4; c=1;
                    break;
                case "K": j1=3; c=0;
                    break;
                case "k": j1=3; c=1;
                    break;
                case "P": j1=5; c=0;     // j stands for column no and k for row no
                    break;
                case "p": 
                          j1=5; 
                          c=1;
                          break;
                case "R": 
                          j1=4; 
                          c=0;    // for rook indexes
                          break;
                case "Q": j1=1; c=0;
                    break;
                case "q": j1=1; c=1;
                    break;
                case "b": j1=2; c=1;
                    break;
            }
            if (j1!=-1 && c!=-1) 
            {
                        //here ours component size in photo is 333 by 333
                        // we are taking two things
                        //first to crop the component from the image
                        //second to place the image component on the desired location on the chess board
                        //m%8 tells the column no in the chess board
                        //m/8 tells the row no in the chess board to place
         g.drawImage(PieceImage, (m%8)*size + 320 , (m/8)*size + 7, (m%8+1)*size + 320, (m/8+1)*size + 7,  j1*333, c*333, (j1+1)*333, (c+1)*333, this);
            //first four arguments in the above lines are for taking the position on the board to set the image
            //next four tells which part of the image to crop so that to fit the desired location
            }
            m++;
     }
         g.setColor(new Color(0,0,0));
         
         g.fillRect(313, 0, 7, 703);
         g.fillRect(313 ,0, 703, 7);
         g.fillRect(1016, 0, 7, 703);
         g.fillRect(313, 703, 710, 7);
    }
       @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) 
    {        //while mouse pressing we are storing the location where mouse was hit
        if (    e.getX()>=320 && (e.getX()<(8*size+320)) && e.getY()>=7 && (e.getY()<8*size+7)) {
            //if inside the board
            oldx=e.getX()-320;      //we are subtracting 320 because then old x will contain the exact value when it will be divided by the get size
            oldy=e.getY()-7;        //and then we get row and column no.  like (oldy/size) gives row no and oldx/size gives the column no      
            repaint();      //let coordinates be  (534,144)   now reduce them by 320 and 7 then we get    (214,137) 
        }   //now oldy/size gives 137/87=1 and oldx/size gives 214/87=2   
    }   //it means  we have our cursor on 2nd row(index 1) and 3rd column(index 2)and this location is being pressed
    @Override
    public void mouseReleased(MouseEvent e) 
    {
        /*               if (available.replaceAll(dragMove, "").length()<available.length()) 
                { //now the move which you want to make exist in the possible combinations or not
                    //if valid move, means you are making a valid move that is present in the available string
                    for(int k=0;k<t;k++)
                    {
                        if(arr[k][0]==dragMove){
                            Chess_controls.perform_move(arr[k][1]);
                            z=0;
                            break;
                        }
                    }
                    if(z==0)
                        return;
                    Chess_controls.perform_move( dragMove );
                    arr[t][0]=dragMove;
                    Chess_controls.Reverse();         //now its turn for the chess computer to make the move by flipping the chess board
                    try 
                    {                       //question is why we flipped the chess board for the computer to perform the move
                                //because we set the move only for the upper case letters and not for small case, hence by interchanging the computer gets upper case letters
                        int beta_value=1000000;
                        int alpha_value=-1000000;
                        String p=Chess_controls.algorithm(Chess_controls.Deep,beta_value , alpha_value, "", 0);
                        if(p=="Checkmate" || p=="Stalemate"){
                            JOptionPane.showMessageDialog(this,p,"NULL", 0);
                        return;
                        }
                        Chess_controls.perform_move(p);
                        arr[t][1]=p;
                        t++;
                    } 
*/
        if (    e.getX()>=320 && (e.getX()<(8*size+320)) && e.getY()>=7 && (e.getY()<8*size+7)){        //bound the mouse releasing location
                //it cannot be greater than or less than the board parameters designed, hence putting constraint over four borders
                //if inside the board
            newx=e.getX()-320;
            newy=e.getY()-7;
            if (e.getButton()==MouseEvent.BUTTON1) 
            {    //checking whether mouse pressed button was left or not(because work over left button only)
                String dragMove;
                if (newy/size==0 && oldy/size==1 && "P".equals(Chess_controls.chessmatrix[oldy/size][oldx/size])) 
                {
                    //pawn promotion like 10RQP here pawn has captured the rook piece of computer and replaced with Queen
                    //and its move string contain only column no of old and new location and captured piece and promoted material and then P indicating promotion
                    dragMove=""+oldx/size+newx/size+Chess_controls.chessmatrix[newy/size][newx/size]+"QP";  //in future we will show here the option
                            // for the material u want to replace your pawn with
                } 
                else 
                {
                    //normal move
                    dragMove=""+oldy/size+oldx/size+newy/size+newx/size+Chess_controls.chessmatrix[(newy)/size ][(newx)/size];  //means 7152p   
                    //means pawn is captured by knight and this move is to be reflected back in the chessmatrix and after that chess matrix is repaint()
                }       
                String available = null;
                try 
                {
                    available = Chess_controls.posibleMoves();   //intialising with all the moves possible at a particular point of time
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(Chess_board_interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (available.replaceAll(dragMove, "").length()<available.length()) 
                { //now the move which you want to make exist in the possible combinations or not
                    //if valid move, means you are making a valid move that is present in the available string
                    Chess_controls.perform_move( dragMove );  
                    Chess_controls.Reverse();         //now its turn for the chess computer to make the move by flipping the chess board
                    try 
                    {                       //question is why we flipped the chess board for the computer to perform the move
                                //because we set the move only for the upper case letters and not for small case, hence by interchanging the computer gets upper case letters
                        int beta_value=1000000;
                        int alpha_value=-1000000;
                        Chess_controls.perform_move(Chess_controls.algorithm(Chess_controls.Deep,beta_value , alpha_value, "", 0));
                    } 
                    catch (IOException ex) 
                    {  //after flipping the board , one move is make by the computer in reponse of our move made
                        Logger.getLogger(Chess_board_interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Chess_controls.Reverse(); //and then make board as it was
                    repaint();      
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {//This funcion is used when mouse is cliked
    }
    @Override
    public void mouseExited(MouseEvent e) {//when mouse is exited from the area
    }
    @Override
    public void mouseDragged(MouseEvent e) {// this funcion is used when mouse is dragged
    }
    @Override
    public void mouseEntered(MouseEvent e) {//when mouse in entered inside the area
    }
    
}