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
public final class ApplicationLauncher
{
	//Start of class
    
	//temporary class variable, used only once, for the sake of thread safety
	public static volatile JToggleButton[][] temp;
	
	//Dimensions of the grid in which cellular automata is being carried out 
	private static Dimension grid_n;
	
    //Time between generation switches
	public static long gen_gap;
	
	private static volatile boolean initialized;//This variable determines if the application is initialized, i.e initial configuration has been entered
	//true if board is initialized, false if board is not yet initialized-is false by default
	
	public static int alive_UB;
	//Upper-bound for adjacent cells above which they will die.
	//by default, this value is 5
	
	public static int alive_LB;
	//Lower Bound for cells to stay alive below which they will die.
	//by default, this value is 2
	
	public static int resurrect;
	//Exact limit at which the cell in question resurrects.
	//by default, this value is 3.
	
	public static Color dead_c;
	//Standard Color specifier for a cell which is dead. 
	public static Color alive_c;
	//Standard color specifier for a cell which is alive.
	
	//Default Border for components in the main board
	public static Border bord=new RoundedBorder(6);
	
	//standard font
	public static Font font=new Font("SansSerif",Font.BOLD,14);
    
	//static initialization block
	static
	{
		dead_c=Color.GRAY;
		alive_c=Color.WHITE;
		alive_UB=5;
		alive_LB=2;
		resurrect=3;
		gen_gap=1500;//original value
		//gen_gap=700;test
		grid_n=new Dimension(10,10);//original value
		//grid_n=new Dimension(20,20);test
	}
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
		ApplicationLauncher.acceptUserSize();
		
		CellSystem cs=ApplicationLauncher.acceptInitialBoard(grid_n.width,grid_n.height);
		//custom size board will now be launched.
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
	JFrame jfrm=new JFrame("Input Initial Configuration");
	SwingUtilities.invokeLater(()->{	
	    JToggleButton array[][]=new JToggleButton[x][y];
	    JPanel initBoard=new JPanel();
		initBoard.setLayout(new GridLayout(x,y,3,3));
		
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
			jtb.setBackground(ApplicationLauncher.alive_c);
			jtb.setText("ALIVE");
			c.setState(true);	
			}
			else
			{
			jtb.setBackground(ApplicationLauncher.dead_c);
			jtb.setText("DEAD");
			c.setState(false);
			}
		};
		for(int i=0;i<x;++i)
		{
			for(int j=0;j<y;++j)
			{
				JToggleButton jtb=new JToggleButton("DEAD",false);
				array[i][j]=jtb;
				jtb.setBorder(bord);
				jtb.setBackground(ApplicationLauncher.dead_c);
				jtb.setActionCommand(i+" "+j);
		        jtb.addItemListener(il);
		        initBoard.add(jtb);
		    }
		}
		temp=array;
		jmb.add(generateAdditionalOptions(jfrm,array));
		jfrm.setVisible(true);
	    });
		
	    boolean chk;
	    do
	    {
		
	    while(!initialized)
		{
			//While the initial configuration has not been initialized, this loop will run;
		}
	    chk=!ApplicationLauncher.validityCheck(temp);
	    if(chk)
	    {
	    	JOptionPane.showMessageDialog(null,"Invalid initial configuration\nAt least one cell should be alive","Error",JOptionPane.ERROR_MESSAGE, null);
	        initialized=false;
	    }
	    }
		while(chk);
		jfrm.setVisible(false);
		jfrm.dispose();
		return cs;
	}
	//This function generates additional menu options for the main additional options JMenu
	public static JMenu generateAdditionalOptions(JFrame parent,JToggleButton[][] butts)
	{
		JMenu menu=new JMenu("Additional Options");
		//first option is change color scheme 
		//second option is to change resurrection/death bounds for cells.

	    //Item 1- Color chooser, allows user to change colors for alive/dead cells.
		//Item 3-allows the user to reset the initial configuration.
		//Item 4-allows the user to reset custom time gaps for automatic generation gaps
	    JMenuItem color=new JMenuItem("Change Colour Scheme");
	    
	    color.addActionListener((ae)->{
	    	boolean flag1;
		    boolean flag2;
	    	do 
	        {      
	    	ApplicationLauncher.alive_c=JColorChooser.showDialog(parent,"Select Color for live cells ",Color.WHITE);
	        flag1=(ApplicationLauncher.alive_c==null);
	        if(flag1)
	        {
	        	JOptionPane.showMessageDialog(parent,"Invalid Selection,try again!");
	        }
	        }
	        while(flag1);
	        do
	        {
	        ApplicationLauncher.dead_c=JColorChooser.showDialog(parent,"Select Color for dead cells ",Color.GRAY);
	        flag2=(ApplicationLauncher.dead_c==null)||(ApplicationLauncher.dead_c.equals(ApplicationLauncher.alive_c));
	        if(flag2)
	        {
	        	JOptionPane.showMessageDialog(parent,"Invalid Selection,try again!");
	        }
	        }
	        while(flag2);
	        ApplicationLauncher.refreshButtonSet(butts);
	        //gui refreshed in the  lineabove
	    });
	    menu.add(color);
	    
	    JMenuItem bounds=new JMenuItem("Change pre-defined rules");
	    
	    //action listener for bounds JMenuItem
	    bounds.addActionListener((ev)->{
	    	String[] options= new String[8];
		    Object[] answers=new Object[3];
		    String[] defaults= {"2","5","3"};
		    String questions[]= 
		    	{
		    		  "Number of adjacent cells below below which cell dies of loneliness",
		    		  "Number of adjacent cells above which cell dies of overcrowding",
		    		  "Number of adjacent cells for which a dead cell comes back to life"
		    	};
		    for(int i=0;i<options.length;++i)
		    {
		    	options[i]=(i+1)+"";
		    }
	    	for(int i=0;i<3;++i)
	    	{
	    	  answers[i]=JOptionPane.showInputDialog(null, questions[i], 
	                  "Change generation-control rules", JOptionPane.QUESTION_MESSAGE,null, options, defaults[i]);
	    	}
	    	ApplicationLauncher.alive_LB=(answers[0]==null)?ApplicationLauncher.alive_LB:Integer.parseInt((String)answers[0]);
            ApplicationLauncher.alive_UB=(answers[1]==null)?ApplicationLauncher.alive_UB:Integer.parseInt((String)answers[1]);
            ApplicationLauncher.resurrect=(answers[2]==null)?ApplicationLauncher.resurrect:Integer.parseInt((String)answers[2]);
	    });
	    menu.add(bounds);
	    
	    JMenuItem resetBoard=new JMenuItem("Reset Board");
	    resetBoard.addActionListener((ev)->{
	    	//add code to reset board here
	    	//This nested loop can slow down the entire application as a whole, especially if
	    	//more buttons need to be clicked, so run it as a seperate thread.
	    Thread thrd=new Thread()
	    { 
	    	public void run()
	    	{
	    	for(int i=0;i<butts.length;++i)
	    	 {
	    		for(int j=0;j<butts[i].length;++j)
	    		{
	    			if(butts[i][j].getText().equals("ALIVE"))
	    			{
	    				butts[i][j].doClick();
	    			}
	    		}
	    	 }
	    	menu.repaint();
	    	}
	    };
	    thrd.start();
	    });
	    menu.add(resetBoard);
	    
	    //Menu item for setting custom time intervals
	    JMenuItem jmt=new JMenuItem("Set custom growth rate");
	    jmt.addActionListener((ev)->{
	    	LABELLED:
	    	do
	    	{
	    	 long val=0;
	    	 String input=JOptionPane.showInputDialog("<html>Enter the new time gap between"
	    	 		+ "generations<br>(in milliseconds)",ApplicationLauncher.gen_gap );
	    	 input=ApplicationLauncher.invalidateNegativeInput(input);
	    	 try 
	    	 {
	    		
	    		val=Long.parseLong(input);
	    		ApplicationLauncher.gen_gap=val;
	    		break;
	    	 }
	    	 catch(Exception ex) 
	    	 {
	    		 if(input==null)
	    		 {break LABELLED;}
	    		 JOptionPane.showMessageDialog(null,"Invalid input,try again","Faulty input!",JOptionPane.ERROR_MESSAGE, null);
	    	 }
	    	}
	    	while(true);
	    });
	    menu.add(jmt);
	    
	    //This JMenuItem allows the user to randomize initial configurations
	    JMenuItem rand=new JMenuItem("Randomize initial configuration");
	    rand.addActionListener((ev)->{
	    	int size=0;
	    	do 
	    	{
	    		String inp=JOptionPane.showInputDialog("Enter the number of live cells to be randomized into the board.");
                if(inp==null)
                {return;}
                inp=ApplicationLauncher.invalidateNegativeInput(inp);
	    		try
                {
                	int tmp=Integer.parseInt(inp);
                	if(tmp>(butts.length*butts[0].length))
                	{
                		throw new Exception();
                	}
                	size=tmp;
                	ApplicationLauncher.randomizeConfig(butts, size);
                	break;
                }
                catch(Exception ex)
                {
                	JOptionPane.showMessageDialog(null,"Please enter a valid non-negative integer value");
                }
	    	}
	    	while(true);
	    	
	    });
	    menu.add(rand);
	    
	    return menu;
	}
	
	//This method should be used exclusively for refreshing the board's colour after 
	//the color selection has been dynamically changed.
	public static void refreshButtonSet(JToggleButton[][] btns)
	{
		for(int i=0;i<btns.length;++i)
		{
			for(int j=0;j<btns[i].length;++j)
			{
				JToggleButton oper=btns[i][j];
				Color nn=(oper.getText().equalsIgnoreCase("ALIVE"))?ApplicationLauncher.alive_c:ApplicationLauncher.dead_c;
			    oper.setBackground(nn);
			}
		}
	}
	
	//getter method for generation gap(in milliseconds)
	public static long getGenGap()
	{
		return ApplicationLauncher.gen_gap;
	}
	
	//setter method for generation gap(in milliseconds)
	public static void setGenGap(long gap)
	{
		ApplicationLauncher.gen_gap=gap;
	}
	
	//This method allows for custom input for a user's grid preference 
	public static Dimension acceptUserSize()
	{
	int[] dimensions= {(int)grid_n.getWidth(),(int)grid_n.getHeight()};
	String[] words= {"rows","columns"};
	//1st and 2nd input loop
	for(int i=0;i<2;++i)
	{
	do
	{
	try
	{
	 String input=JOptionPane.showInputDialog("Enter number of "+words[i],10);
	 if(input==null) {break;}
	 input=ApplicationLauncher.invalidateNegativeInput(input);
	 int temp=Integer.parseInt(input);
	 dimensions[i]=temp;
	 break;
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Invalid input,try again","Invalid",JOptionPane.ERROR_MESSAGE, null);
	}
	}
	while(true);
	}
	return (grid_n=new Dimension(dimensions[0],dimensions[1]));
	}
	//This method force converts valid numeric input with a minus sign into an invalid format by adding garbage characters 
	//to the string if there is a minus sign.
	public static String invalidateNegativeInput(String inp)
	{
	if(inp==null)
	{return null;}
		String output=inp;
		if(inp.contains("-")||inp.equals("0"))
		{
			output+="garbage";
		}
	return output;	
	}
	//This method checks if the current initial configuration is valid for the cellular automata design
	public static boolean validityCheck(JToggleButton[][] butts)
	{
		for (int i=0;i<butts.length;++i)
		{
			for(int j=0;j<butts[i].length;++j)
			{
				Color c=butts[i][j].getBackground();
				if(c.equals(alive_c))
				{return true;}
			}
		}
	return false;
	}
	//This method randomizes the initial configuration of the board
	public static void randomizeConfig(JToggleButton[][] array,int size)
	{
		Thread t=new Thread() {
		public void run()
		{
		ArrayList<Point> points=UtilityMethods.generateLiveList(size,array.length,array[0].length);
		 for(Point p:points)
		  {
			JToggleButton jtb=array[p.x][p.y];
			jtb.setBackground(alive_c);
			jtb.setText("ALIVE");
		   }
		 }
		};
		t.start();
	}
	
//End of class	
}
