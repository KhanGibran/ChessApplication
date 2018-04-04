import java.io.*;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
public class Chess_controls extends JFrame{
    static int kingcomp, kingenemy;  
    // r stands for rook    
    //k stands for knight
    //b stands for bishop
    //q stands for queen
    //a stands for king
    //p stands for pawn
    //There are two types of objects, ours is represented by Capital letter i.e 'R' and of enemy is represented by small letter i.e 'r'
    static int humanwhite=-1;//we are taking 1 means white and 0 means black
   
    static int Deep=4;
   
    public static String chessmatrix[][]={
        {  "r" , "k" , "b" , "a" , "q" , "b" , "k" , "r" } ,
        {  "p" , "p" , "p" , "p" , "p" , "p" , "p" , "p" } ,
        {  " " , " " , " " , " " , " " , " " , " " , " " } ,
        {  " " , " " , " " , " " , " " , " " , " " , " " } ,
        {  " " , " " , " " , " " , " " , " " , " " , " " } ,
        {  " " , " " , " " , " " , " " , " " , " " , " " } ,
        {  "P" , "P" , "P" , "P" , "P" , "P" , "P" , "P" } ,
        {  "R" , "K" , "B" , "Q" , "A" , "B" , "K" , "R" }
    };
     
     public static void perform_move(String move) 
     {
           if (move.charAt(4)!='P') 
           {
                chessmatrix[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessmatrix[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];

                chessmatrix[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
               if ("A".equals(chessmatrix[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) 
               {
                    kingcomp=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
               }
           } 
           else 
           {
                //if pawn promotion  and this is the syntax of any move that contains promotion+capture  move or simple promotion move 
                //(column1,column2,captured-piece,new-piece,P)    here p stands for promotion and noting else 
                chessmatrix[1][Character.getNumericValue(move.charAt(0))]=" ";
                chessmatrix[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
                //here move.charAt(0) means are taking first thing of move string which tells you the column no of the old piece through which our old piece is 
                //promoted to new location which is indicated by the column is charAt(1) which is new column no for the new captured piece and note:
                //charAt(3) tells you the piece which is now promoted ike queen etc. we have string as (34RQP) it means our pawn promoted to queen by 
                //capturing others rook
            }
        
     }
    public static void reverse_move(String move) 
    {
        if (move.charAt(4)!='P') 
        {
            chessmatrix[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessmatrix[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessmatrix[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
                
            if ("A".equals(chessmatrix[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) 
            {
                kingcomp=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        
        } 
        else 
        {
            //if pawn promotion
            chessmatrix[1][Character.getNumericValue(move.charAt(0))]="P";
           
            chessmatrix[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));   //at 2 is the captured piece 
        }
    }
    public Chess_controls() throws IOException      //default constructor of chess_control class
       {  
        
        setTitle("D3G2 Chess Game!");
        setVisible(true);
        setSize(1370,740);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Chess_board_interface ui = new Chess_board_interface();
        add(ui);
        while( !"A".equals(chessmatrix[kingcomp/8][kingcomp%8])) 
        {
            kingcomp++;
        }//get King's location
        
        while(!"a".equals(chessmatrix[kingenemy/8][kingenemy%8])) 
        {
            kingenemy++;       //get king's location       
        }
        
        System.out.println("Total no of possible moves according to board situation");
        System.out.println(posibleMoves());
        //perform_move(algorithm(Deep,1000000, -1000000, "", 0));
        //perform_move("43aPP");
        //reverse_move("43aPP");
        Object[] option={"Machine","human"};
        humanwhite=JOptionPane.showOptionDialog(null, "Who will make first move?", "Options", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        
        if (humanwhite==0) 
        {
            perform_move(algorithm(Deep, 1000000, -1000000, "", 0));
            Reverse();
            this.repaint();
        } 
        for (int i=0;i<8;i++) 
        {
            System.out.println(Arrays.toString(chessmatrix[i]));
        }
    }
    public static void Reverse() 
    {
        String flag;
        int i=0; 
        while(i<32)
        {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(chessmatrix[r][c].charAt(0))) 
            {
                flag=chessmatrix[r][c].toLowerCase();
            } 
            else 
            {
                flag=chessmatrix[r][c].toUpperCase();
            }
            if (Character.isUpperCase(chessmatrix[7-r][7-c].charAt(0))) 
            {
                chessmatrix[r][c]=chessmatrix[7-r][7-c].toLowerCase();
            } 
            else 
            {
                chessmatrix[r][c]=chessmatrix[7-r][7-c].toUpperCase();
            }
            chessmatrix[7-r][7-c]=flag;
            i++;
        }
        int kingTemp=kingcomp;
        kingcomp=63-kingenemy;
        kingenemy=63-kingTemp;
    }
    public static int rate()
    {
        return 10;
    }
    public static String algorithm(int dep, int b, int a, String move, int player) throws IOException 
    {
        //return in the form of 1234b##########
        String list=posibleMoves();
                /*if(dep==0 && kingsafety_decided()){
                    return "Stalemate";
        }
            *if(dep==0 && !kingsafety_decided()){
                    return "Checkmate";
        }
        */    
            if (dep==0 || list.length()==0) {return move+(Rateit.rating(list.length(), dep)*(player*2-1));}

        //list=sortMoves(list);
        player=1-player;//either 1 or 0
        int i=0; 
        while(i<list.length())
        {
            perform_move(list.substring(i,i+5));
            Reverse();
            String getstring=algorithm(dep-1, b, a, list.substring(i,i+5), player);
            int value=Integer.valueOf(getstring.substring(5));
            Reverse();
            reverse_move(list.substring(i,i+5));
            if (player==0) 
            {
                if (value<=b) 
                {
                    b=value; 
                    if (dep==Deep) 
                    {
                        move=getstring.substring(0,5);
                    }
                }
            } 
            else 
            {
                if (value>a) 
                {
                    a=value; 
                    if (dep==Deep) 
                    {
                        move=getstring.substring(0,5);
                    }
                }
            }
            if (a>=b) 
            {
                if (player==0) 
                {
                    return move+b;
                } 
                else 
                {
                    return move+a;
                }
            }
            i+=5;
        }
        if (player==0) 
        {
            return move+b;
        } 
        else 
        {
            return move+a;
        }
    }
   public static String posibleMoves() throws IOException 
   {
        String movesstring="";
        int i=0; 
        while(i<64){
            String ch=chessmatrix[i/8][i%8];
            if("P".equals(ch))
            {
                movesstring=movesstring+pawn_movements(i);
            }
            else if("R".equals(ch))
            {
                movesstring=movesstring+rook_movements(i);
            }
            else if("K".equals(ch))
            {
                movesstring=movesstring+knight_movements(i);
            }
            else if("B".equals(ch))
            {
                movesstring=movesstring+bishop_movements(i);
            }
            else if("Q".equals(ch))
            {
                movesstring=movesstring+queen_movements(i);
            }
            else if("A".equals(ch))
            {
                movesstring=movesstring+king_movements(i);
            }
            i++;
        }
        return movesstring;//x1,y1,x2,y2,captured piece
    }


        public static String pawn_movements(int i) 
        {
        String list="", oldmaterial;
        int r=i/8, c=i%8;
        int j=-1; 
        while(j<=1)
        {        
            //two values of j representing two positions one up-left and one is up-right which is used to capture others material
            try 
            {//capture
                if (Character.isLowerCase(chessmatrix[r-1][c+j].charAt(0)) && i>=16) 
                {
                    oldmaterial=chessmatrix[r-1][c+j];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r-1][c+j]="P";
                    if (kingsafety_decided()) 
                    {
                        list=list+r+c+(r-1)+(c+j)+oldmaterial;
                    }
                    chessmatrix[r][c]="P";
                    chessmatrix[r-1][c+j]=oldmaterial;
                }
            } catch (Exception e) {}
            try 
            {//promotioning and capturing take place
                if (Character.isLowerCase(chessmatrix[r-1][c+j].charAt(0)) && i<16) 
                {
                    String[] flag={"Q","R","B","K"};
                    int k=0;  
                    while(k<4){
                        oldmaterial=chessmatrix[r-1][c+j];
                        chessmatrix[r][c]=" ";
                        chessmatrix[r-1][c+j]=flag[k];
                        if (kingsafety_decided()) 
                        {
                            //column1,column2,captured-piece,new-piece,P    //no need to show the row no because it is well known that it is from 0 to 1
                            list=list+c+(c+j)+oldmaterial+flag[k]+"P";     //c is 1 and c+j means either one left and one right in the zero row of the chess board
                        }       // above p at 5th position means we are doing promotion in the board, we can skip it also, but to assurance
                        chessmatrix[r][c]="P";
                        chessmatrix[r-1][c+j]=oldmaterial;
                    k++;
                    }
                }
            } catch (Exception e) {}
            j=j+2;
        }
        try 
        {       //move one up and no capture
            if (" ".equals(chessmatrix[r-1][c]) && i>=16) 
            {
                oldmaterial=chessmatrix[r-1][c];
                chessmatrix[r][c]=" ";
                chessmatrix[r-1][c]="P";
                if (kingsafety_decided()) 
                {
                    list=list+r+c+(r-1)+c+oldmaterial;
                }
                chessmatrix[r][c]="P";
                chessmatrix[r-1][c]=oldmaterial;
            }
        } 
        catch (Exception e) {}
        try 
        {//promotion && no capture
            if (" ".equals(chessmatrix[r-1][c]) && i<16) 
            {
                String[] flag={"Q","R","B","K"};
                int k=0; 
                while(k<4)
                {
                    oldmaterial=chessmatrix[r-1][c];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r-1][c]=flag[k];
                    if (kingsafety_decided()) 
                    {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+c+c+oldmaterial+flag[k]+"P";
                    }
                    chessmatrix[r][c]="P";
                    chessmatrix[r-1][c]=oldmaterial;
                   k++;
                }
            }
        } catch (Exception e) {}
        try 
        {//move two up
            if (" ".equals(chessmatrix[r-1][c]) && " ".equals(chessmatrix[r-2][c]) && i>=48) 
            {
                oldmaterial=chessmatrix[r-2][c];
                chessmatrix[r][c]=" ";
                chessmatrix[r-2][c]="P";
                if (kingsafety_decided()) 
                {
                    list=list+r+c+(r-2)+c+oldmaterial;
                }
                chessmatrix[r][c]="P";
                chessmatrix[r-2][c]=oldmaterial;
            }
        } 
        catch (Exception e) {}
        return list;
    }

        public static String rook_movements(int i)
        {
        String list="", oldmaterial;
        int r=i/8, c=i%8;
        int flag=1;
        int j=-1;
        while(j<=1)
        {
            try 
            {
                while(" ".equals(chessmatrix[r][c+flag*j]))
                {
                    oldmaterial=chessmatrix[r][c+flag*j];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r][c+flag*j]="R";
                    if (kingsafety_decided()) 
                    {
                        list=list+r+c+r+(c+flag*j)+oldmaterial;
                    }
                    chessmatrix[r][c]="R";
                    chessmatrix[r][c+flag*j]=oldmaterial;
                    flag++;
                }
                if (Character.isLowerCase(chessmatrix[r][c+flag*j].charAt(0))) 
                {
                    oldmaterial=chessmatrix[r][c+flag*j];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r][c+flag*j]="R";
                    if (kingsafety_decided()) 
                    {
                        list=list+r+c+r+(c+flag*j)+oldmaterial;
                    }
                    chessmatrix[r][c]="R";
                    chessmatrix[r][c+flag*j]=oldmaterial;
                }
            } 
            catch (Exception e) {}
            flag=1;
            try 
            {
                while(" ".equals(chessmatrix[r+flag*j][c]))
                {
                    oldmaterial=chessmatrix[r+flag*j][c];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r+flag*j][c]="R";
                    if (kingsafety_decided()) 
                    {
                        list=list+r+c+(r+flag*j)+c+oldmaterial;
                    }
                    chessmatrix[r][c]="R";
                    chessmatrix[r+flag*j][c]=oldmaterial;
                    flag++;
                }
                if (Character.isLowerCase(chessmatrix[r+flag*j][c].charAt(0))) 
                {
                    oldmaterial=chessmatrix[r+flag*j][c];
                    chessmatrix[r][c]=" ";
                    chessmatrix[r+flag*j][c]="R";
                    if (kingsafety_decided()) 
                    {
                        list=list+r+c+(r+flag*j)+c+oldmaterial;
                    }
                    chessmatrix[r][c]="R";
                    chessmatrix[r+flag*j][c]=oldmaterial;
                }
            } catch (Exception e) {}
            flag=1;
        j=j+2;
        }
        return list;
    }
    public static String knight_movements(int i) 
    {
        String list="", oldmaterial;
        int r=i/8, c=i%8;
        int j=-1; 
        while(j<=1)
        {
            int k=-1; 
            while(k<=1)
            {
                try 
                {
                    if (Character.isLowerCase(chessmatrix[r+j][c+k*2].charAt(0)) || " ".equals(chessmatrix[r+j][c+k*2])) {
                        oldmaterial=chessmatrix[r+j][c+k*2];
                        chessmatrix[r][c]=" ";
                        if (kingsafety_decided()) {
                            list=list+r+c+(r+j)+(c+k*2)+oldmaterial;
                        }
                        chessmatrix[r][c]="K";
                        chessmatrix[r+j][c+k*2]=oldmaterial;
                    }
                } 
                catch (Exception e) {}
                try 
                {
                    if (Character.isLowerCase(chessmatrix[r+j*2][c+k].charAt(0)) || " ".equals(chessmatrix[r+j*2][c+k])) 
                    {
                        oldmaterial=chessmatrix[r+j*2][c+k];
                        chessmatrix[r][c]=" ";
                        if (kingsafety_decided()) 
                        {
                            list=list+r+c+(r+j*2)+(c+k)+oldmaterial;
                        }
                        chessmatrix[r][c]="K";
                        chessmatrix[r+j*2][c+k]=oldmaterial;
                    }
                } 
                catch (Exception e) {}
            k=k+2;
            }
            j=j+2;
        }
        return list;
    }
    public static String bishop_movements(int i) 
    {
        String list="", oldmaterial;
        int r=i/8, c=i%8;
        int flag=1;
        int j=-1; 
        while(j<=1)
        {
            int k=-1;
            while(k<=1)
            {
                try {
                    while(" ".equals(chessmatrix[r+flag*j][c+flag*k]))
                    {
                        oldmaterial=chessmatrix[r+flag*j][c+flag*k];
                        chessmatrix[r][c]=" ";
                        chessmatrix[r+flag*j][c+flag*k]="B";
                        if (kingsafety_decided()) 
                        {
                            list=list+r+c+(r+flag*j)+(c+flag*k)+oldmaterial;
                        }
                        chessmatrix[r][c]="B";
                        chessmatrix[r+flag*j][c+flag*k]=oldmaterial;
                        flag++;
                    }
                    if (Character.isLowerCase(chessmatrix[r+flag*j][c+flag*k].charAt(0))) 
                    {
                        oldmaterial=chessmatrix[r+flag*j][c+flag*k];
                        chessmatrix[r][c]=" ";
                        chessmatrix[r+flag*j][c+flag*k]="B";
                        if (kingsafety_decided()) 
                        {
                            list=list+r+c+(r+flag*j)+(c+flag*k)+oldmaterial;
                        }
                        chessmatrix[r][c]="B";
                        chessmatrix[r+flag*j][c+flag*k]=oldmaterial;
                    }
                } catch (Exception e) {}
                flag=1;
                k=k+2;
            }
        j=j+2;
        }
        return list;
    }    public static String queen_movements(int i) {
        String movesstring="", oldmaterial;
        int r=i/8, c=i%8;
        int flag=1;
        int j=-1; 
        while(j<=1) 
        {
            int k=-1; 
            while(k<=1) 
            {
                if (j!=0 || k!=0) 
                {
                    try 
                    {
                        while(" ".equals(chessmatrix[r+flag*j][c+flag*k]))
                        {
                            oldmaterial=chessmatrix[r+flag*j][c+flag*k];
                            chessmatrix[r][c]=" ";
                            chessmatrix[r+flag*j][c+flag*k]="Q";
                            if (kingsafety_decided()) 
                            {
                                movesstring=movesstring+r+c+(r+flag*j)+(c+flag*k)+oldmaterial;
                            }
                            chessmatrix[r][c]="Q";
                            chessmatrix[r+flag*j][c+flag*k]=oldmaterial;
                            flag++;
                        }
                        if (Character.isLowerCase(chessmatrix[r+flag*j][c+flag*k].charAt(0))) 
                        {
                            oldmaterial=chessmatrix[r+flag*j][c+flag*k];
                            chessmatrix[r][c]=" ";
                            chessmatrix[r+flag*j][c+flag*k]="Q";
                            if (kingsafety_decided()) 
                            {
                                movesstring=movesstring+r+c+(r+flag*j)+(c+flag*k)+oldmaterial;
                            }
                            chessmatrix[r][c]="Q";
                            chessmatrix[r+flag*j][c+flag*k]=oldmaterial;
                        }
                    } catch (Exception e) {}
                    flag=1;
                }   
            k++;
            }
        j++;
        }
        return movesstring;
    }
    
    public static String king_movements(int k)
    {
	String movestring="";
	int r=k/8,c=k%8; 
        int i,j;
	i=-1;
        while(i<=1)
	{
            j=-1;
            while(j<=1)
            {
                if(i!=0 || j!=0)        //ignoring the case when at the original position to assume
		{
                    try{
                        if((" ".equals(chessmatrix[r+i][c+j]))  || (Character.isLowerCase(chessmatrix[r+i][c+j].charAt(0))))
			{       
                            String oldflag;
                            oldflag=chessmatrix[r+i][c+j];
                            chessmatrix[r+i][c+j]="A";
			    chessmatrix[r][c]=" ";
                            int kingTemp=kingcomp;
                            kingcomp=k+i*8+j;  //setting king position because our king position is changing and we are storing king location
			    if(kingsafety_decided())    // hence we are updating the value at this time only this function is doing so coz king is moving in this only
			    {
				movestring=movestring+r+c+(r+i)+(c+j)+oldflag;
			    }
                            chessmatrix[r][c]="A";
                            chessmatrix[r+i][c+j]=oldflag;
                            kingcomp=kingTemp;
                        }
                        }catch(Exception e){}
                    }
             j++;
            }        
        i++;   
        }	
	return movestring;
    }

    public static boolean kingsafety_decided() 
    {
        //bishop/queen
        int flag=1;
        int i=-1; 
        while(i<=1)
        {
            int j=-1; 
            while(j<=1)
            {
                try 
                {
                    while(" ".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8+flag*j])) 
                    {
                        flag++;
                    }
                    if ("b".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8+flag*j]) || "q".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8+flag*j])) 
                    {
                        return false;
                    }
                } catch (Exception e) {}
                flag=1;
            j=j+2;
            }
        i=i+2;
        }
        //case of queen and rook
        i=-1; 
        while(i<=1)
        {
            try 
            {
                while(" ".equals(chessmatrix[kingcomp/8][kingcomp%8+flag*i])) 
                {
                    flag++;
                }
                if ("r".equals(chessmatrix[kingcomp/8][kingcomp%8+flag*i]) || "q".equals(chessmatrix[kingcomp/8][kingcomp%8+flag*i])) 
                {
                    return false;
                }
            } 
            catch (Exception e) {}
            flag=1;
            try 
            {
                while(" ".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8])) 
                {
                    flag++;
                }
                if ("r".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8]) || "q".equals(chessmatrix[kingcomp/8+flag*i][kingcomp%8])) 
                {
                    return false;
                }
            } catch (Exception e) {}
            flag=1;
        i=i+2;
        }
        //case for the knight
        i=-1; 
        while(i<=1)
        {
            int j=-1; 
            while(j<=1)
            {
                try 
                {
                    if ("k".equals(chessmatrix[kingcomp/8+i][kingcomp%8+j*2])) 
                    {
                        return false;
                    }
                } 
                catch (Exception e) {}
                try 
                {
                    if ("k".equals(chessmatrix[kingcomp/8+i*2][kingcomp%8+j])) 
                    {
                        return false;
                    }
                } catch (Exception e) {}
            j=j+2;
            }
        i=i+2;
        }
        //case where pawn moves
        if (kingcomp>=16) 
        {
            try 
            {
                if ("p".equals(chessmatrix[kingcomp/8-1][kingcomp%8-1])) 
                {
                    return false;
                }
            } catch (Exception e) {}
            try 
            {
                if ("p".equals(chessmatrix[kingcomp/8-1][kingcomp%8+1])) 
                {
                    return false;
                }
            } catch (Exception e) {}
            //king
            i=-1; 
            while(i<=1)
            {
                int j=-1; 
                while(j<=1)
                {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(chessmatrix[kingcomp/8+i][kingcomp%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                j++;
                }
            i++;
            }
        }
        return true;
    }
}