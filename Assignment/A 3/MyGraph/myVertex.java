/*
	This class stores value of particular vertex objects along with weight of edge,
	this vertex is connected with.

	This class, particularly helps in creating adjacenct List that determines
	if a graph is successfully created or not.
*/
public class myVertex {

	private String valueCharacter; //Vertex value
	private int Weight; //Weight of associated Edge
	
	public myVertex(String value,int Wt)
	{
		this.valueCharacter=value;
		this.Weight=Wt;	
	}
	
	public String getVertex() {
		return valueCharacter;
	}

	public int getWeight() {
		return Weight;
	}
}
