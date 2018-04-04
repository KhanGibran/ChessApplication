import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
public class First_main_interface extends JFrame
{
    private final JLayeredPane lpane = new JLayeredPane();  //father of panel
          
    JPanel jp1=new JPanel();
    JPanel jp2=new JPanel();
    JPanel jp3=new JPanel();   
    JLabel j1 = new JLabel();       //here label is used for taking image
    
    JButton b1=new JButton();
    JButton b2=new JButton();
    JButton b3=new JButton();
    JButton b4=new JButton();
    ImageIcon img;
   
    public First_main_interface()
    {

        setTitle("Chess Application");
        setUndecorated(true);       //for disabling title bar
        setBounds(0,0,1370,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);  
       
        lpane.setBounds(0, 0, 1370, 800);
        //Note 0,0 is the starting point and 1370 is the width of the screen and 800 is the height
        jp1.setBounds(0, 0, 1370, 800); //this is the parent panel which is holding the whole interface of this chess and having image of chess, on which two panels are also embedded
        jp2.setBounds(665, 150, 300, 480);  //middle panel to hold three buttons 
        jp3.setBounds(1250, 15, 64, 72);    // right top panel to hold one button of setting
 
        jp2.setBackground(new Color(0,0,0,0));  //for invisible background
        //(Red, green , blue, alpha)(all ranges from 0 to 255) fourth one decide the opaqueness, just set the value of fourth as 40 and see the difference
        jp3.setBackground(new Color(0,0,0,0));  //for invisible background
    
        img=new ImageIcon("img\\chessfront6.jpg");  //back image of chess
        j1.setIcon(img);           // adding image on the label
        jp1.add(j1);        //add label on the panel
        
        b1.setIcon(new ImageIcon("img\\newgame.png"));
        jp2.add(b1);  
        b2.setIcon(new ImageIcon("img\\credits.png"));
        jp2.add(b2);
        b3.setIcon(new ImageIcon("img\\exit2.png"));
        jp2.add(b3);  
        b4.setIcon(new ImageIcon("img\\setting5.png"));
        jp3.add(b4);
       
        
        b1.setRolloverIcon(new ImageIcon("img\\newgame1.png"));
        b2.setRolloverIcon(new ImageIcon("img\\credits1.png"));
        b3.setRolloverIcon(new ImageIcon("img\\exit12.png"));
        b4.setToolTipText("<html><h2>Settings</h2></html>");
        
        // this function is called when button is clicked so that action event is generated
        //and we are defining the function to listen action event that is generated through the mouse         
        b1.addActionListener((ActionEvent ae) -> {  
            dispose();
            try {
                Chess_controls a=new Chess_controls();
            } catch (IOException ex) {
                Logger.getLogger(First_main_interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        b3.addActionListener((ActionEvent a) ->{
        dispose();
        });
     
        b4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b4.setBackground(Color.PINK);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b4.setBackground(UIManager.getColor("control"));
            }
         });
        
        b4.addActionListener((ActionEvent ae) -> {          
            dispose();
            try {
                Setting a=new Setting();
            } catch (IOException ex) {
                Logger.getLogger(First_main_interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
               
        lpane.add(jp1, 0, 0);       //adding all the three panel on to the lpane
        lpane.add(jp2, 1, 0);
        lpane.add(jp3, 1, 0);
        
        add(lpane);     //adding lpane into the jframe
        validate();     //it is essential to use validate(), search over, why we are using
    }
 
}