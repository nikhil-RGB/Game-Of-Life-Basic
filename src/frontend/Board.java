//Objects of this class manage "Board" instances, which in turn, manage a CellSystem
package frontend;
import java.awt.Color;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;

import backend.*;
public final class Board extends JPanel 
{

private static final long serialVersionUID = 1499908L;
private ArrayList<JComponent> text;
//Associated text components can be null, note that these components are not always initialized.
//and will simply be ignored if left uninitialized
private CellSystem cs;//Main CellSystem-works on the backend
private JButton[][] buttons;//buttons in the board GUI
//Initializes a Board GUI representation of the CellSystem object passed as an argument to this constructor
public Board(CellSystem cs)
{
this.cs=cs;
Cell[][] grid=cs.getGrid();
this.setLayout(new GridLayout(grid.length,grid[0].length,3,3));
this.buttons=new JButton[grid.length][grid[0].length];
 for(int i=0;i<this.buttons.length;++i)
 {
	for(int j=0;j<this.buttons[i].length;++j)
	{
		JButton op=buttons[i][j]=new JButton();
		Cell reference=cs.getCellAt(new Point(i,j));
		op.setBorder(ApplicationLauncher.bord);
	    if(reference.getState())
	    {
	    	op.setBackground(ApplicationLauncher.alive_c);
	    	op.setText("ALIVE");
	    }
	    else
	    {
	    	op.setBackground(ApplicationLauncher.dead_c);
	    	op.setText("DEAD");
	    }
	   this.add(op);
	}
 }
}
//This method refreshes the current Board to hold the updated details of it's corresponding CellSystem object
public void refreshBoard()
{
Cell[][] grid=this.cs.getGrid();
for(int i=0;i<grid.length;++i)
{
 for(int j=0;j<grid[0].length;++j)
 {
  JButton ref=this.buttons[i][j];//The JButton being referred to here
  Cell r=grid[i][j];//The Cell object with updated data
  boolean state_n=r.getState();//new state of cell
  if(state_n)
  {
	  ref.setBackground(ApplicationLauncher.alive_c);
	  ref.setText("ALIVE");
  }
  else
  {
	  ref.setBackground(ApplicationLauncher.dead_c);
	  ref.setText("DEAD");
  }
 }
}
if(text!=null)
{
this.refreshSideComponents();
//refresh Side Panel components.	
}
}

//Side panel components refresh method-if null throws NullPointerException
public void refreshSideComponents()
{
 if(this.text==null)
 {
	 throw new NullPointerException();
 }
 JLabel genLab=(JLabel)(this.text.get(0));
 CellSystem cs=this.getCellSystem();
 genLab.setText("Current generation: "+cs.getGeneration());
}
//This method returns the current CellSystem object being utilized
public CellSystem getCellSystem()
{
 return this.cs;	
}
//This method sets associate text components which need to be updated 
public void setAssociatedTextComponents(JComponent[] components)
{
	ArrayList<JComponent> arrs=new ArrayList<>(0);
	for(JComponent jtc:components)
	{
		arrs.add(jtc);
	}
	this.text=arrs;
}
}
