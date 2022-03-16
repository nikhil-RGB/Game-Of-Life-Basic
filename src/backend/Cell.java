package backend;
//An object of this cell represents a unit in the infinite grid of "Game of Life", a 0-player game which follows
//the concept of cellular automata.
//A cell can have two states, alive(1) or dead(0).
//The cell can also resurrect(go back to being alive rather than dead if a certain set of conditions are met)
import java.awt.Point;
import java.util.*;
public final class Cell
{
	private boolean new_state;//changed state for a cell after generation calculations-properties similar to state.
	private boolean state;//state of the cell currently, can be DEAD(false) or ALIVE(true)
	//can also be read as is_alive.
	private Point position;//position of the cell in it's parent grid, in terms of "x-coor y-coor"
    
	//Standard constructor for a cell object, sets the cell to dead, only defines it's position
	public Cell(Point pos)
	{
	 this.position=pos;
	}
	//Standard constructor for a cell object
	public Cell(boolean state_inf, Point pos)
    {
    	this.state=state_inf;
    	this.position=pos;
    }
    //This function returns all 8 adjacent cells to a particular cell.
    //This includes top, top-right,top-left, right, left,bottom, bottom-right, bottom-left
	public ArrayList<Cell> getAdjacentCells(Cell[][] parent)
    {
    	ArrayList<Cell> cells=new ArrayList<>(0);
    	int xc=this.position.x;//current x coordinate
    	int yc=this.position.y;//current y coordinate
    	int xM=parent.length-1;//Maximum possible x- coordinate for given grid
    	int yM=parent[xM].length-1;//Maximum possible y-coordinate for given grid
    	Point top=new Point(xc-1,yc);//top cell
    	Point right=new Point(xc,yc+1);//right cell
    	Point bottom=new Point(xc+1,yc);//bottom cell
    	Point left=new Point(xc,yc-1);//left cell
    	Point top_right=new Point(xc-1,yc+1);//top-right cell
    	Point top_left=new Point(xc-1,yc-1);//top-left cell
    	Point bottom_right=new Point(xc+1,yc+1);//bottom-right cell
    	Point bottom_left=new Point(xc+1,yc-1);//bottom-left cell
        Point[] array= {top,right,bottom,left,top_right,top_left,bottom_right,bottom_left}; 	
        //Correct coordinates of cells which would otherwise not exits by changin non existant coordinates to
        //boundary/edge coordinates, and therefore effectively wrapping grids around each other.
        for(Point p:array)
        {
        	    Cell.correctCoor(p, xM, yM);
        		Cell adj=parent[p.x][p.y];
        		cells.add(adj);
        	
        }
    	return cells;
    }
    //This function corrects invalid co-ordinates, returns true if a correction was required,
    //false in any other case
    public static boolean correctCoor(Point p,int xm,int ym)
    {
    	if(!Cell.isPointValid(p,xm,ym))
    	{
    		if(p.x<0)
    		{
    			p.x=xm;
    		}
    		if(p.y<0)
    		{
    			p.y=ym;
    		}
    		if(p.x>xm)
    		{
    		  p.x=0;
    		}
    		if(p.y>ym)
    		{
    		  p.y=0;
    		}
    		return true;
    	}
    	return false;
    }
    //This function returns true if a point p is located inside a certain range, and 
    //is above zero
    public static boolean isPointValid(Point p,int max_x,int max_y)
    {
     if(((p.x<0)||(p.y<0))||((p.x>max_x)||(p.y>max_y)))
     {return false;}
     return true;
    }
    //sets the state of the invoking cell to either DEAD(0) or ALIVE(1)
    public void setState(boolean state)
    {
    this.state=state;	
    }
    //This getter method gets the state of the invoking cell as either DEAD(0) or ALIVE(1)
    public boolean getState()
    {
    	return this.state;
    }
    //This method gets the current position of the invoking cell
    public Point getPosition()
    {
    	return this.position;
    }
    //This method gets a String representation for all the information currently stored in the cell.
    //Most useful in testing purposes.
    public String getCellData() 
    {
    	String state=(this.state)?"ALIVE":"DEAD";
    	String pos=this.position.x+", "+this.position.y;
    	return "The cell at "+pos+" is currently "+state;
    }
    //This function returns the number of cells alive in a particular list.
    public static int countAlive(ArrayList<Cell> cells)
    {
    	int alive=0;
    	for(Cell c:cells)
    	{
    		if(c.getState())
    		{
    			++alive;
    		}
    	}
    	return alive;
    }
    //This function returns the number of cells dead in a particular list.
    public static int countDead(ArrayList<Cell> cells)
    {
    	int alive=Cell.countAlive(cells);
        return cells.size()-alive;
    }
    //This function performs an "update" operation and allows you to "update" the backup state variable of the cell to the value it 
    //will have in the next generation.
    
    //Parameter definition:
    //parent=parent grid for invoking cell
    //LB=minimum number of cells required to exist so that a cell may continue existing without dying
    //UB=maximum number of cells after which a cell will die of overcrowding
    //ress=exact number of cells required to revive a cell
    public boolean stateUpdate(Cell[][] parent,final int LB,final int UB,final int ress )
    {
    int aliveAR=0;
    int dedAR=0;
    ArrayList<Cell> cells= this.getAdjacentCells(parent);
    int alive=Cell.countAlive(cells);//number of living adjacent cells
    //Dies of loneliness
    if(alive<LB)
    {
    	this.new_state=false;
    }
    //Resurrects due to optimal conditions
    else if(alive==ress)
    {
    	this.new_state=true;
    }
    //Dies of overcrowding
    else if(alive>UB)
    {
    	this.new_state=false;
    }
    //Mantains same state as before
    else
    {
    	this.new_state=this.state;
    }
    return this.new_state;
    }
    //This method "refreshes" the cell by updating the "state" variable's value with
    //the value of the "new_state" variable
    public void refresh()
    {
    	this.state=this.new_state;
    }
}
