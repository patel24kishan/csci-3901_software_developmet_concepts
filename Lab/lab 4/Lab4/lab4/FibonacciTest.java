package lab4;

import java.util.Scanner;

import com.sun.jdi.event.VMDisconnectEvent;

public class FibonacciTest
{
	public static int Depthcount=0;
	public static boolean flag=true;
	public static void main(String[] args) throws MaximumRecursionDepthException
	{
		Scanner scan = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter Number for Fibonacci sequence:");
	    int key = scan.nextInt();
	    
	    System.out.println("Enter Maximum Depth:");
	    int maxdepth = scan.nextInt();
	    
	    
	    System.out.println("\n\nMaximum Allowed Depth: \n"+maxdepth);
			try {
			
				System.out.println("\nFibonacci Sequence:\n"+fib(key,maxdepth,0));
				
				System.out.println("\nRecursion Depth: "+Depthcount);
				
			} catch (MaximumRecursionDepthException e) {
				System.err.println("\nException Occured: \n"+e.getMessage());
				System.out.println("\nRecursion Depth: "+e.getDepth(Depthcount));
			}

	}

	public static int fib(int n,int Maxdepth,int recursiveDepth) throws MaximumRecursionDepthException
	{
		
		
			if (recursiveDepth>Maxdepth) 
			{	
			
				 throw new MaximumRecursionDepthException("Recursion exceeds Depth Value" );
			}

			if (n==0)
			{
				return 0;	
			}
			else if (n==1)
			{
				
				return 1;	
			}
			else 
			{
				Depthcount++;
				return fib(n-1,Maxdepth,Depthcount)+fib(n-2,Maxdepth,Depthcount);	
			}

	}
}
