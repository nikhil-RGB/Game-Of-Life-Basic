package frontend;
import backend.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;
//This class deals with 
public final class ApplicationLauncher {
	//standard font
	public static Font font=new Font("algerian",Font.BOLD,18);
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
	    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//This method runs a test simulation of a randomly generated board
	public static void runTest()
	{   
		//Test statement
		System.out.println("first line reached");
		CellSystem cs=new CellSystem(10,10);
		System.out.println("Cell System initialized");
		ArrayList<Point> arr=Tester.generateLiveList(10);
		System.out.println("List of live cells generated");
		for(Point p:arr)
		{
		 cs.getCellAt(p).setState(true);
		}
		System.out.println("Launch method reached");//This statement doesnt seem to be getting executed
		//The statement above prints only if the function which launches the GUI is reached.
		launchGameboardTest(cs);
	}
	//This method creates the side-panel for the main board
	public JPanel createSidePanel(final Board b)
	{
		final CellSystem control=b.getCellSystem();//control CellSYstem for current board
		JPanel jpan=new JPanel();//panel holder for 
		jpan.setLayout(new BoxLayout(jpan,BoxLayout.X_AXIS));//this panel will be used to hold components which allow progress to the next generation
		JButton jbc=new JButton("Next Generation");//This button will be used to progess to the next generation(manually)
		jbc.setToolTipText("<html>This button allows the user to move to the next generation<br>"
				+ "Will be disabled if automatic progression is enabled");
	    jbc.setToolTipText("Allows user to auto-progress from one generation to another without manually clicking the \"progress\" button");
	    JCheckBox jcab= new JCheckBox("Automatic progress");
	    jcab.setFont(font);
	    JTextArea jtxt= new JTextArea("Log for Generation progression");
	    jtxt.setFont(font);
	    JLabel jlab=new JLabel("Current Generation: 0");
	    jlab.setFont(font);
	    ItemListener checkbox= (ev)->//lambda expression for item listener with respect to item changes
	    {
	    if(ev.getStateChange()==ItemEvent.SELECTED)
	    {
	    	control.setAutomaticGrowth(true);
	    	jbc.setEnabled(false);
	    	
	    }
	    else
	    {
	    	control.setAutomaticGrowth(false);
	    	jbc.setEnabled(true);
	    }
	    };
	    ActionListener proceedBttn=(ev)->{//lambda expression for proceeding with next generation manually
	    	control.setAllowNextGen(true);
	    };
	    jcab.addItemListener(checkbox);
	    jbc.addActionListener(proceedBttn);
        //adding components to the JPanel
	    jpan.add(jbc);
        jpan.add(Box.createRigidArea(new Dimension(15,15)));
        jpan.add(jcab);
        //jpan now contains all control/responsive components
        
	    return jpan;
	}
}
