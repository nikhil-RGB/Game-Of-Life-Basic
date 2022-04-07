package frontend;
import backend.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
//This class deals with launching the application of Game-of-Life 
public final class ApplicationLauncher {
	
	private static volatile boolean initialized;//This variable determines if the application is initialized, i.e initial configuration has been entered
	//true if board is initialized, false if board is not yet initialized-is false by default
	
	//Default Border for components in the main board
	public static Border bord=new RoundedBorder(6);
	
	//standard font
	public static Font font=new Font("SansSerif",Font.BOLD,14);
    
	//This method launches the SWING application 
	public static void main(String[] args) 
	{
         	 runTest();    
	
	}
	
	//This method initializes and launches the main BOARD GUI, still in development and testing
	public static void launchGameboardTest(CellSystem oper)
	{
		SwingUtilities.invokeLater(()->{
		Board b=new Board(oper);
		JFrame jfrm=new JFrame("Game-of-Life board");
		JPanel side_panel=ApplicationLauncher.createSidePanel(b);
	    jfrm.add(b,BorderLayout.CENTER);
	    jfrm.add(side_panel,BorderLayout.NORTH);
	    oper.setAssociate(b);
	    oper.activateSystem(false, false);
	    jfrm.setSize(800,600);
	    jfrm.setVisible(true);
	    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}
	//This method runs a test simulation of a randomly generated board
	public static void runTest()
	{   
		//Test statement
		//System.out.println("first line reached");
		//CellSystem cs=new CellSystem(10,10);
		//System.out.println("Cell System initialized");
		//ArrayList<Point> arr=Tester.generateLiveList(10);
		//System.out.println("List of live cells generated");
		//for(Point p:arr)
		//{
		//cs.getCellAt(p).setState(true);
		//}
		CellSystem cs=ApplicationLauncher.acceptInitialBoard(10, 10);
		System.out.println("Launch method reached");//This statement doesnt seem to be getting executed
		//The statement above prints only if the function which launches the GUI is reached.
		launchGameboardTest(cs);
	}
	//This method creates the side-panel for the main board
	public static JPanel createSidePanel(final Board b)
	{
		JPanel main_p=new JPanel();//main panel
		main_p.setBackground(Color.BLACK);
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
		jbc.setBackground(Color.BLACK);
		jbc.setForeground(Color.WHITE);
		jbc.setBorder(bord);
		jbc.setToolTipText("<html>This button allows the user to move to the next generation<br>"
				+ "Will be disabled if automatic progression is enabled");
	    jbc.setToolTipText("Allows user to auto-progress from one generation to another without manually clicking the \"progress\" button");
	    JCheckBox jcab= new JCheckBox("Automatic progress",false);
	    jcab.setBorder(bord);
	    jcab.setBackground(Color.GRAY);
	    jcab.setFont(font);
	    JLabel jlab=new JLabel("Current Generation: 0");
	    jlab.setBorder(bord);
	    jlab.setFont(font);
	    jlab.setForeground(Color.WHITE);
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
	//Accepts a board with a certain initial configuration from the user-This method is called before
	//the 
	private static CellSystem acceptInitialBoard(int x,int y)
	{
	CellSystem cs=new CellSystem(x,y);
		JPanel initBoard=new JPanel();
		initBoard.setLayout(new GridLayout(x,y,3,3));
		JFrame jfrm=new JFrame("Input Initial Configuration");
		JMenuBar jmb=new JMenuBar();
		JMenu jmen=new JMenu("Click to proceed to automata application");
		JMenuItem jmit=new JMenuItem("Proceed(Cannot be undone");
		jmit.addActionListener((ev)->{
			initialized=true;
		});
		jmen.add(jmit);
		jmb.add(jmen);
		jfrm.setJMenuBar(jmb);;
		jfrm.add(initBoard);
		jfrm.setSize(800, 600);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Default close operation is to exit on close.
		
		ItemListener il=(ie)->
		{
			JToggleButton jtb=(JToggleButton)(ie.getSource());
			Scanner reader=new Scanner(jtb.getActionCommand());
			Cell c=cs.getCellAt(new Point(reader.nextInt(),reader.nextInt()));
			reader.close();
			if(ie.getStateChange()==ItemEvent.SELECTED)
			{
			jtb.setBackground(Color.white);
			jtb.setText("ALIVE");
			c.setState(true);	
			}
			else
			{
			jtb.setBackground(Color.gray);
			jtb.setText("DEAD");
			c.setState(false);
			}
		};
		for(int i=0;i<x;++i)
		{
			for(int j=0;j<y;++j)
			{
				JToggleButton jtb=new JToggleButton("DEAD",false);
				jtb.setBorder(bord);
				jtb.setBackground(Color.GRAY);
				jtb.setActionCommand(i+" "+j);
		        jtb.addItemListener(il);
		        initBoard.add(jtb);
		    }
		}
		jfrm.setVisible(true);
		
		
	
		while(!initialized)
		{
			//While the initial configuation has not been initialized, this loop will run;
		}
		return cs;
	}
}
