package backend;

import java.awt.Point;
import java.util.ArrayList;

public final class UtilityMethods
{
	//This method clones a 2-d array of generic type T.
	public static<T> void clone2Dfrom(T[][] oldarr,T[][] newarr)
	{
	 if((oldarr==null)||(newarr==null))
	 {
	  throw new IllegalArgumentException("Null arguements are not allowed");
	 }
	 else if(oldarr.length!=newarr.length)
	 {
	 throw new UnsupportedOperationException("array sizes do not match, operation aborted");
	 }
	 
	 for(int i=0;i<oldarr.length;++i)
	 {
		 for(int j=0;j<newarr.length;++j)
		 {
			 newarr[i][j]=oldarr[i][j];
		 }
	 }
	 //2D array cloned
	}
	//This method generates a list of live cells, as requested to setup an initial configuration.
	public static ArrayList<Point> generateLiveList(int size,int x1,int y1)
	{
		if(size>(x1*y1))
		{
			throw new IllegalArgumentException("Requested live cells are more than total cells in board");
		}
		ArrayList<Point> alive=new ArrayList<>(0);
		for(int i=0;i<size;++i)
		{
			Point pp=null;
			do {
			int x=(int)(Math.random()*x1);
			int y=(int)(Math.random()*y1);
			pp=new Point(x,y);
			
			if(!alive.contains(pp))
			{
			  	alive.add(pp);
			  	break;//Exit do-while loop
			}
			}
			while(alive.contains(pp));
		}
		return alive;
	}
}
