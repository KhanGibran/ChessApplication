import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Setting extends JFrame
{
    private final JLayeredPane lpane = new JLayeredPane();  //father of panel
          
    JPanel jp1=new JPanel();
    JPanel jp2=new JPanel();  
    JPanel jp3=new JPanel();
    JPanel jp4=new JPanel();
    JPanel jp5=new JPanel();
    JLabel j1 = new JLabel();       //here label is used for taking image
    
    JButton b1=new JButton();
    JButton b2=new JButton();
    JButton b3=new JButton();
    ImageIcon img;
    public Setting() throws IOException
    {

        setTitle("Chess Application");
        setUndecorated(true);       //for disabling title bar
        setBounds(0,0,1370,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);  
        add(lpane);
        lpane.setBounds(0, 0, 1370, 800);
        
       
        jp2.setBackground(new Color(0,0,0,0));  //for invisible background
        jp3.setBackground(new Color(0,0,0,0));  //for invisible background
        jp4.setBackground(new Color(0,0,0,0));  //for invisible background
        jp5.setBackground(new Color(0,0,0,0));  //for invisible background
        
        
        //We have used five panels
        
        jp1.setBounds(0, 0, 1370, 800);         //root panel to hold all panel
        jp2.setBounds(665, 150, 150, 80);       //sound text showing label panel
        jp3.setBounds(665, 250, 100, 80);       //for on sound
        jp4.setBounds(665, 355, 100, 80);       //for off sound
        jp5.setBounds(1250, 15, 64, 72);        //for back
        

        img=new ImageIcon("img\\chessfront6.jpg");  
        j1.setIcon(img);
        jp1.add(j1);
        

        JLabel label1 = new JLabel("<html><body><b><u>Sound</b></u></body></html>");
        label1.setFont(new Font("Serif", Font.BOLD, 40));        
        label1.setForeground(Color.WHITE);
        jp2.add(label1);
        
        b1.setIcon(new ImageIcon("img\\volume.png"));       //for on sound
        b2.setIcon(new ImageIcon("img\\mute.png"));         //for off sound
        b3.setIcon(new ImageIcon("img\\back2.png"));        //for back
        jp3.add(b1);
        jp4.add(b2);
        jp5.add(b3);
        
        
        b3.setToolTipText("<html><h2>Back</h2></html>");
        
        
        b1.addMouseListener(new java.awt.event.MouseAdapter() {         //for color change of sound on button, when mouse entered or exit
        @Override
        public void mouseEntered(MouseEvent evt) {
        b1.setBackground(Color.PINK);
        }
        
        @Override
        public void mouseExited(MouseEvent evt) {
            b1.setBackground(UIManager.getColor("control"));
        }
        });
        
        
        
        
        b2.addMouseListener(new java.awt.event.MouseAdapter() {    //for color change of sound off button, when mouse entered or exit
        @Override
        public void mouseEntered(MouseEvent evt) {
        b2.setBackground(Color.PINK);
        }
        
        @Override
        public void mouseExited(MouseEvent evt) {
            b2.setBackground(UIManager.getColor("control"));
        }
        });
        
        
        
        
        
        InputStream music = null;
        try {
            music=new FileInputStream(new File("img\\third.wav"));       //this is our sound file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        AudioStream audios=new AudioStream(music);
        b1.addActionListener((ActionEvent ae) -> {
            try{
                AudioPlayer.player.start(audios);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
            }       
        });
       
        
        
        
        b2.addActionListener((ActionEvent ae) -> {
                AudioPlayer.player.stop(audios);
        });
        
        
            
            
        b3.addMouseListener(new java.awt.event.MouseAdapter() {      //for color change of back button, when mouse entered or exit
            @Override
            public void mouseEntered(MouseEvent evt) {
                b3.setBackground(Color.PINK);
        }
            @Override
            public void mouseExited(MouseEvent evt) {
                b3.setBackground(UIManager.getColor("control"));
            }
        });
        
        
        b3.addActionListener((ActionEvent ae) -> {          //adding action listner at back button
               dispose();
               First_main_interface c=new First_main_interface();
        });    
            
            
        
              
        lpane.add(jp1, 0, 0);
        lpane.add(jp2, 1, 0); 
        lpane.add(jp3, 2, 0);
        lpane.add(jp4, 3, 0);
        lpane.add(jp5, 3, 0);
        validate();
    }
}