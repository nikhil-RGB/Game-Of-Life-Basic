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
     SwingUtilities.invokeLater(()->{
    	 runTest();
     });
		
	}
	
	//This method initializes and launches the main BOARD GUI, still in development and testing
	public static void launchGameboardTest(CellSystem oper)
	{
		Board b=new Board(oper);
		JFrame jfrm=new JFrame("Game-of-Life board");
	    jfrm.add(b,BorderLayout.CENTER);
	    oper.setAssociate(b);
	    oper.activateSystem(true, false);
	    jfrm.setSize(800,600);
	    jfrm.setVisible(true);
	}
	//This method runs a test simulation of a randomly enerated board
	public static void runTest()
	{
		CellSystem cs=new CellSystem(10,10);
		ArrayList<Point> arr=Tester.generateLiveList(10);
		for(Point p:arr)
		{
		 cs.getCellAt(p).setState(true);
		}
		launchGameboardTest(cs);
	}
}
