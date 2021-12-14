package lab4;

public class MaximumRecursionDepthException extends RuntimeException
{
	public MaximumRecursionDepthException(String message)
	{
		super(message);
	}
	
	public String getMessage()
	{
		return "Recursion exceeds Maximum Depth Value !!!";
	}
	
	public int getDepth(int depth)
	{
		return depth;
	}
}
