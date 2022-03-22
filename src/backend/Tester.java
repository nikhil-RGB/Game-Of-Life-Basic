package backend;
import java.util.*;
import java.awt.Point;
//As the name indicates, this class exists soley for the purpose of testing.
//Most of the methods here will not make sense and need not be understood by other developers 
//viewing this project.
public final class Tester {
    //main testing method
	//Current tests-ALL VALID
	public static void main(String[] args)
	{
	 Cell[][] table =new Cell[10][10];
	 for(int i=0;i<table.length;++i)
	  {
		 for(int j=0;j<table[0].length;++j)
		 {
			 table[i][j]=new Cell(false,new Point(i,j));
		 }
	  }
	 Cell edge=table[0][1];
	 Cell corner=table[0][0];
	 Cell center=table[5][5];
	 Cell invalid=new Cell(false,new Point(12,12));//Non-existent cell for testing.
	 Cell[] control_cells= {edge, corner, center};//control cells for experiments
    System.out.println(edge.getCellData());
    System.out.println(corner.getCellData());
    System.out.println(center.getCellData());
    //testing for coordinate validity checking function and correction function
    System.out.println("Invalid point is invalid: "+Cell.isPointValid(invalid.getPosition(),table.length,table[0].length));
    //adjacent cell testing
    System.out.println("Attempted correction: "+Cell.correctCoor(invalid.getPosition(),table.length,table[0].length));
    System.out.println(" Corrected coors:\n "+invalid.getCellData());
    
    for(Cell curr:control_cells)
    {
    	printAdjsTest(curr,table);
    }
    
	}
	//Test to print adjacent cells to a particular cell
	private static void printAdjsTest(Cell ref,Cell[][] parent)
	{
		ArrayList<Cell> cells=ref.getAdjacentCells(parent);
		System.out.println("These are the cells adjacent to:\n "+ref.getCellData());
		for(Cell c:cells)
		{
			System.out.println(c.getCellData());
		}
		System.out.println("\n\n");
	}
	//This method plays out a game of life interaction without any user input, meant only for testing purposes
	public static void playOutGame()
	{
		CellSystem cs=new CellSystem();
		cs.activateSystem(true,false);
	}
	//This method generates an initial test configuration CellSystem
	public CellSystem generateConfig(int x, int y,ArrayList<Point> alive_pos)
	{
		CellSystem cs=new CellSystem(x,y);
		for(Point p:alive_pos)
		{
			Cell operate=cs.getCellAt(p);
			operate.setState(true);
		}
		return cs;
	}
	//This is a new testing method
	public void randomTest()
	{
		//Test statements here
	}

}
