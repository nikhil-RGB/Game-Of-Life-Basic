package frontend;
import backend.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
//This class deals with 
public final class ApplicationLauncher {
    //This method launches the SWING application 
	public static void main(String[] args) 
	{
     
		
	}
	
	//This method initializes and launches the main BOARD GUI, still n development and testing
	public static void launchGameboardTest(CellSystem oper)
	{
		ArrayList<Point> alive=new ArrayList<>(0);
		Point[] arr= {new Point(5,5),new Point(5,4),new Point(5,6),new Point(5,7),new Point(6,5),new Point(6,7)};
		Board b=new Board(oper);
		JFrame jfrm=new JFrame("Game-of-Life board");
	    jfrm.add(b,BorderLayout.CENTER);
	    oper.setAssociate(b);
	    oper.activateSystem(true, false);
	    jfrm.setSize(800,600);
	    jfrm.setVisible(true);
	}

}
