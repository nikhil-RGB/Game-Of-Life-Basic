package backend;

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
}
