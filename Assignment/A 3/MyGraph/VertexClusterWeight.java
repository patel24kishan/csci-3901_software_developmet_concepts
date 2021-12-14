/*
	This class is used to store cluster as a list and also
	stores the overall weight of the cluster.

	Ideally, set is preferrec over list for creating cluster as it
	elimantes the rosk of duplicate values.

	But I chose list over set to  temporary store vertices in cluster 
	list is better suited for performing different operations on the 
	stored data and also merging two lists is better than merging two sets.

*/
import java.util.ArrayList;
import java.util.List;


public class VertexClusterWeight
{
	private List<String> vertexSet=new ArrayList<String>(); //Temporay Cluster list
	private int weight; //Cluster Weight
	
	//Add a vertex to the cluster list
	public void addVertex(String str) {	
			this.vertexSet.add(str);
	}

	//Update weight of the cluster
	public void addweight(int wt)
	{
		this.weight=wt;
	}
	
	// getter method to access cluster list
	public  List<String> getSet() 
	{
		return this.vertexSet;
	}

	// getter method to access cluster weight
	public int getWeight()
	{
		return this.weight;
	}
	
	
}
