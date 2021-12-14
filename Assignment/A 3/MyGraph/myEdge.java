/*
	This class helps in keeping track of each edge as
	it stores every values that makes an Edge - source, destination and weight.

	This class makes operating on Edge very easy and requires only single reference 
	to access specific Edge when needed.
*/
public class myEdge {

	public String sourceVertice;  //
	public String destinationVertice;
	public int weight;
	
	//Constructor to create user defined Edge
	public myEdge(String A, String B, int wt) 
	{ 
		this.sourceVertice=A;
		this.destinationVertice=B;
		this.weight=wt;
	}

	//	getter method to access Source of an Edge
	public String getSource()
	{
		return sourceVertice;
	}
	
	//	getter method  to access Destination of an Edge
	public String getDestination() {
		return destinationVertice;
	}
	
	//	getter method  to access Weight of an Edge
	public int getWeight() {
		return weight;
	}

}
