package backend;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.Point;
//This class mantains an entire system for cells
public final class CellSystem 
{
	
	private Cell[][] grid;//grid of cells
	private int generation;//Current generation number
	private EnvironmentControlThread progressionControl;
	//IIB
	//Standard constructor, creates a 10*10 cell grid system with all cells set to state
	//"DEAD".
	public CellSystem()
	{
		this(10,10);
	}
	//Creates a CellSystem object with all the cells in the system set to "DEAD", allowing the grid
	//to have as many cells as x*y
	public CellSystem(int x,int y)
	{
	 Cell[][] table=new Cell[x][y];
	 for(int i=0;i<table.length;++i)
	  {
		 for(int j=0;j<table[0].length;++j)
		 {
			 table[i][j]=new Cell(new Point(i,j));
		 }
	  }
	 this.grid=table;
	}
	//This constructor allows the programmer to specify a custom size grid, with pre-defined boolean
	//values
	public CellSystem(Cell[][] table)
	{
	 this.grid=table;	
	}
	//Updates the Grid to the next generation
	public void nextGeneration()
	{
	    for(int i=0;i<grid.length;++i)
	    {
	    	for(int j=0;j<grid[0].length;++j)
	    	{
	    	 grid[i][j].stateUpdate(this.grid, 2,5,3);
	    	}
	    }
	}
	//forces the cell-system to refresh itself by calling each-cell's refresh method
	public void refreshSystem()
	{
		for(int i=0;i<grid.length;++i)
	    {
	    	for(int j=0;j<grid[0].length;++j)
	    	{
	    	 grid[i][j].refresh();
	    	}
	    }
	}
	public boolean canSystemContinue()
	{
	 for(int i=0;i<this.grid.length;++i)
	 {
		 for(int j=0;j<this.grid[0].length;++j)
		 {
			 Cell obs=this.grid[i][j];
			 if(obs.getState())
			 {return true;}
		 }
	 }
	 return false;
	}
	//This sub-class is a thread which controls the environment of a cell system
	//An object of this class represents a thread, whose running causes the controlled progression of a
	//cell system object-the outer CellSystem object it is associated with.
	public class EnvironmentControlThread extends Thread
	{
		private volatile boolean allow_automatic;//allows app to check for automatic next-gernation moving
		private volatile boolean allow_next_gen;//allows the next generation of cells to grow.
		//IIB
		{
			progressionControl=this;
		}
		//default constructor
		public EnvironmentControlThread()
		{}
		//parameterized constructor
		public EnvironmentControlThread(boolean auto, boolean next_gen)
		{
			this.allow_automatic=auto;
			this.allow_next_gen=next_gen;
		}
		@Override
		public void run()
		{
		 while(canSystemContinue())
		 {
			
			while(!allow_next_gen)
			{
			  if(allow_automatic)
			  {
				  try
				  {
					Thread.sleep(1500);  
				  }
				  catch(Throwable ex) 
				  {
					ex.printStackTrace();  
				  }
				  break;
				  
			  }
			}
			allow_next_gen=false; 
		    nextGeneration();
		    refreshSystem();
		    if(!canSystemContinue())
		    {
		    	JOptionPane.showMessageDialog(null,"All cells have died/System force killed");
		    }
		 }
		}
	}//End of subclass
 //This method initializes and starts the progression of a CellSystem
 public void activateSystem()
 {
  this.new EnvironmentControlThread();
  this.progressionControl.start();
 }
 //This method actiavtes and initializes a rogression with some pre defined env values
 public void activateSystem(boolean auto,boolean cont_manual)
 {
	 this.new EnvironmentControlThread(auto,cont_manual);
	 this.progressionControl.start();
 }
 //This method sets the allow_automatic variable for the CellSystem's growth environment,
 //therefore allowing the cell system to skip manual user input dependancy for navigating
 //from one generation to another
 public void setAutomaticGrowth(boolean flag)
 {
	 this.progressionControl.allow_automatic=flag;
 }
 //This method gets the allow_automatic property for this CellSystem's growth environment
 public boolean getAutomaticGrowth()
 {
	 return this.progressionControl.allow_automatic;
 }
 //This method allows the programmer to turn on/off manual user interaction for growth of a cell system
 //This property is overriden by allowAutomaticGrowth
 public void setAllowNextGen(boolean flag)
 {
	 this.progressionControl.allow_next_gen=flag;
 }
 //This method allows the programmer to access the allow_next_gen property of the CellSystem's growth environment
 public boolean getAllowNextGen()
 {
	 return this.progressionControl.allow_next_gen;
 }
 //This method force-kills all cells in the current CellSystem, effectively bringing the 
 //system to an end.
 public void forceKill()
 {
	 for(int i=0;i<this.grid.length;++i)
	 {
		 for(int j=0;j<this.grid[0].length;++j)
		 {
			Cell c=this.grid[i][j];
			c.setState(false);
		 }
	 }
 }
 //This method allows you to access the cell DDA of which this System object is comprised
 public  Cell[][] getGrid()
 {
	 return this.getGrid();
 }
 //This method returns the cell at a specified x,y location
 public Cell getCellAt(Point obj)
 {
	 return this.grid[obj.x][obj.y];
 }

}//End of class