import java.util.Scanner; // class uses class Scanner
import java.util.InputMismatchException; // class uses class InputMismatchException
import java.util.Random; // class uses class Random
import java.util.ArrayList; // class uses class ArrayList

public class ArrayDensity 
{
	// main method beings execution of Java application
	public static void main ( String[] args )
	{
		// class Scanner to obtain input from command window
		Scanner input = new Scanner( System.in );
		
		// initializing variables in declarations
		boolean validArrayLength = false;  // boolean flag determines whether to continue or exit loop
		boolean validArrayDensity = false; // boolean flag determines whether to continue or exit loop
		int arrayLength = 0;               // integer array length entered by user 
		double arrayDensity = 0.0;         // double-precision array density entered by user
		
		
		while( !validArrayLength )
		{
			try
			{
				System.out.println( "Please enter array length:" ); 
				arrayLength = input.nextInt(); // input next entry	
				validArrayLength = true; // change boolean flag to exit while loop	
			} // end try
			
			catch ( InputMismatchException e )
			{
				System.out.println( "Error: please enter a valid integer" ); 
				input.nextLine(); // obtain next input from user
			} // end catch	
		} //end while
		
		
		while( !validArrayDensity )
		{
			try
			{
				System.out.println( "Please enter array density:" ); 
				arrayDensity = input.nextDouble(); // input next entry
				
				if ( ( arrayDensity < 0.0 ) || ( arrayDensity > 1.0 ) ) // array density must be between 0 and 1 (inclusive)
					{
					System.out.println( "Error: please enter a double from 0 to 1" );
					input.nextLine(); // obtain next input from user
					}
				
				else
					validArrayDensity = true; // change boolean flag to exit while loop	
			} // end try
			
			catch ( InputMismatchException e)
			{
				System.out.println( "Error: please enter a double-precision floating point:" );
				input.nextLine(); // obtain next input from user
			} // end catch	
		} // end while
		
		
		/**
		 * It seems like for lower densities, createSparseArray and sparseFindMax()
		 * execute much faster than createDenseArray and denseFindMax(), respectively.
		 * This makes sense because sparse arrays are only concerned with storing non-zero
		 * entries within its memory. 
		 */
		
		
		// run createDenseArray
		long startTime1 = System.nanoTime();
		int[] myDenseArray = createDenseArray( arrayLength, arrayDensity );
		long endTime1 = System.nanoTime();
		System.out.print( "createDenseArray() length: " + myDenseArray.length + "  time: " );
		System.out.println( endTime1 - startTime1 );
		
		
		// run createSparseArray
		long startTime2 = System.nanoTime();
		ArrayList<int[]> mySparseArray = createSparseArray( arrayLength, arrayDensity );
		long endTime2 = System.nanoTime();
		System.out.print( "createSparseArray() length: " + mySparseArray.size() + "  time: " );
		System.out.println( endTime2 - startTime2 );
		
		
		// run denseFindMax
		long startTime3 = System.nanoTime();
		denseFindMax( myDenseArray );
		long endTime3 = System.nanoTime();
		System.out.print( "denseFindMax() time: " );
		System.out.println( endTime3 - startTime3 );
		
		
		//run sparseFindMax
		long startTime4 = System.nanoTime();
		sparseFindMax( mySparseArray );
		long endTime4 = System.nanoTime();
		System.out.print( "sparseFindMax() time: ");
		System.out.println( endTime4 - startTime4 );
		
		
		input.close(); // close scanner
		
		
	} // end main
	
	
	public static int[] createDenseArray(int len, double density)
	{
		Random rand = new Random();
		int[] denseArray = new int[len];
		 
		// for each array index, generate a random double-precision number.
		// if this random double-precision number is less than the density value,
		// populate the index with a random integer ranging from 1 to 1000000.
		// otherwise, populate the index with 0.
		
		for(int i = 0; i < denseArray.length; i++)
		{
			if ( rand.nextDouble() < density )
				denseArray[i] = (1 + rand.nextInt(1000000));
			else
				denseArray[i] = 0;
		} // end for		
		
		return denseArray;
		
	} // end create Dense Array		
	
	
	public static ArrayList<int[]> createSparseArray(int len, double density)
	{
		Random rand = new Random();
		ArrayList<int[]> sparseArray = new ArrayList<int[]>();
		
		// for each arraylist index, generate a random double-precision number.
		// if this random double-precision number is less than the density value,
		// create an array with the format {index, random integer from 1 to 1000000}.
		// then, add this array to the arraylist. 
		
		for(int i = 0; i < len; i++)
		{
			if ( rand.nextDouble() < density)
			{
				int[] sparsePair = new int[2];
				sparsePair[0] = i;
				sparsePair[1] = (1 + rand.nextInt(1000000));
				sparseArray.add(sparsePair);
			} // end if	
		} // end for		
		
		return sparseArray;
		
	} // end createSparseArray
	
	
	public static void denseFindMax( int[] array )
	{
		int maxIndex = 0;
		int maxValue = 0;
	
		for ( int i = 0; i < array.length; i++) 
		{
		    if ( array[i] > maxValue) 
		    {
		    	maxIndex = i;
		    	maxValue = array[i];
		    } // end if    
		} // end for
		
		System.out.println("denseFindMax: " + maxValue + " at " + maxIndex);	
		
	} // end denseFindMax
	
	
	public static void sparseFindMax( ArrayList<int[]> list )
	{
		int maxIndex = 0;
		int maxValue = 0;
		
		for(int[] pair : list)
		{
			if (pair[1] > maxValue )
			{
				maxIndex = pair[0];
				maxValue = pair[1];
			} // end if	
		} // end for
		
		System.out.println("sparseFindMax: " + maxValue + " at " + maxIndex);	
		
	} // end sparseFindMax

} // end ArrayDensity
