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
	public static Font font=new Font("algerian",Font.BOLD,14);
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
		JPanel side_panel=ApplicationLauncher.createSidePanel(b);
	    jfrm.add(b,BorderLayout.CENTER);
	    jfrm.add(side_panel,BorderLayout.NORTH);
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
	public static JPanel createSidePanel(final Board b)
	{
		JPanel main_p=new JPanel();//main panel
		GridBagLayout p=new GridBagLayout();
		main_p.setLayout(p);
		GridBagConstraints gc1=new GridBagConstraints();
		gc1.gridx=0;
		gc1.gridy=0;
		gc1.weightx=0.4;
		GridBagConstraints gc2=new GridBagConstraints();
		gc2.gridx=1;
		gc2.gridy=0;
		gc2.weightx=0.4;
		GridBagConstraints gc3=new GridBagConstraints();
		gc3.gridx=2;
		gc3.gridy=0;
		gc3.weightx=0.4;
		final CellSystem control=b.getCellSystem();//control CellSYstem for current board
		JPanel jpan=new JPanel();//panel holder for 
		jpan.setLayout(new BoxLayout(jpan,BoxLayout.X_AXIS));//this panel will be used to hold components which allow progress to the next generation
		JButton jbc=new JButton("Next Generation");//This button will be used to progress to the next generation(manually)
		jbc.setToolTipText("<html>This button allows the user to move to the next generation<br>"
				+ "Will be disabled if automatic progression is enabled");
	    jbc.setToolTipText("Allows user to auto-progress from one generation to another without manually clicking the \"progress\" button");
	    JCheckBox jcab= new JCheckBox("Automatic progress",true);
	    jcab.setFont(font);
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
        jpan.add(Box.createRigidArea(new Dimension(5,15)));
        jpan.add(jcab);
        //jpan now contains all control/responsive components
        //Now, arrange components in main_panel
        main_p.add(jlab,gc1);
        main_p.add(jbc,gc2);
        main_p.add(jcab,gc3);
        
        
        //set associated text components to JPanel
        b.setAssociatedTextComponents(new JComponent[] {jlab});
	    return main_p;
	}
}
