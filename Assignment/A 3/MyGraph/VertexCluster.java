/*
	I have created this code with the assumption that
	the vertice can be a Character String, simple string
	or integer/FLoat String or String containing special
	 characters
	
	For example, aws-56Cv,A a, v, C,washer, jay, aws213c (allowed)
				  
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import java.util.Set;

public class VertexCluster {

	public HashMap<String, LinkedList<myVertex>> vertexListHashMap=
										new HashMap<String, LinkedList<myVertex>>(); //Adjacency List to keep Track of each vertex connections
	
	public HashMap<String,myEdge> edgeList=new HashMap<String,myEdge>(); // List of all Edges and each edge is stored as myEdge object
	
	public HashMap<String,Integer> verticesList=new HashMap<String,Integer>();; // List of individual Vertices along with corresponding weights
	
	// Initializing Constructor
	public  VertexCluster()
	{
		
	}
	
	/********************************************* addEdge Method *********************************************/
	
	// source is first end of the Edge 
	// destination means second end of the Edge
	// weight defines weight of that edge
	public boolean addEdge(String source,String destination,int weight)
	{
		String tempSource=source.replaceAll("\\s+",""); // remove any white space before or after source String
		String tempDestination=destination.replaceAll("\\s+",""); //remove any extra space before or after destination String

		 //Source input Validation
		 // Checks for empty/blank, null String
		 //Also checks whethter source is single character or string
		if (tempSource.isBlank() ||tempSource.isEmpty() || tempSource.equals(null) || tempSource.length()==0)
		{
		
			return false;
		}

		//Destination input Validation
		 // Checks for empty/blank, null String
		 //Also checks whethter destination is single character or string
		if (tempDestination.isBlank() ||tempDestination.isEmpty() || tempDestination.equals(null) || tempDestination.length()==0 )
		{
			
			return false;
		}

		//Weight input Validation
		// I have assumed that weight shoud be non-zero number
		//	weight should always be postive integer
		if (weight==0 || weight<0) 
		{
			return false;
		}
		
		String finalSource; 
		String finalDestination; 
		
		 //Comapre source and destination based on ASCII code, which one is lower on alphabetic order
		 //Assigns finalSource the lowe order vertex and finalDestination the other
		int flag=tempSource.compareTo(tempDestination); //Compare order of both vertices
		if(flag<0) 
		{
			finalSource=tempSource;
			finalDestination=tempDestination;
		}
		else
		{
		finalSource=tempDestination;
		finalDestination=tempSource;
		}
		myEdge newEdge=new myEdge(finalSource,finalDestination, weight);	//Create new Edge object
		myVertex nextVertexA=new myVertex(finalDestination, weight);
		myVertex nextVertexB=new myVertex(finalSource, weight);
		
		// Following Loops is use for creating Adjaceny List to check if proper graph is created
		// Along with adjacency List, new Edge are added to edgeList Map
		
		// For edgeList HashMap, create key-value pair as follow:
		// >  key is stored as combined string of 'finalSource + finalDestination' or 'finaldestination + finalSource'
		// >  value is the new myEdge object
		
		// Along with adding Edge to the edgeList, each vertex is added to verticesList as a key-value pair as follow:
		// >  key is stored as vertex itself
		// >  value is deafult weight(1) of the vertex
		if (!edgeList.containsKey(finalSource+finalDestination)  && !edgeList.containsKey(finalDestination+finalSource) ) //Checks if edge with passed key exists
		{
			//Create key- value pair in edgeList
			edgeList.put(finalSource+finalDestination,newEdge);
		
		
		///Implement for Source
			if (vertexListHashMap.containsKey(finalSource) )
			{
				LinkedList<myVertex> tmpLinkedList=vertexListHashMap.get(finalSource);
				tmpLinkedList.add(nextVertexA);
				vertexListHashMap.put(finalSource, tmpLinkedList);
				//System.out.println(" Vertex added to list of srce "+source+" & total Vertex = "+vertexListHashMap.get(source).size());
			
			}
			else
			{
				//System.out.println("New Vertex Added");
				LinkedList<myVertex> tmpLinkedList=new LinkedList<myVertex>();
				tmpLinkedList.push(nextVertexA);
				vertexListHashMap.put(finalSource, tmpLinkedList);
				
				verticesList.put(finalSource,1);
			}
		
			///Implment for dinationdestination
			if (vertexListHashMap.containsKey(finalDestination) )
			{
				LinkedList<myVertex> tmpLinkedList=vertexListHashMap.get(finalDestination);
				tmpLinkedList.add(nextVertexB);
				vertexListHashMap.put(finalDestination, tmpLinkedList);
			}
			else
			{
				//System.out.println("New Vertex Added");
				LinkedList<myVertex> tmpLinkedList=new LinkedList<myVertex>();
				tmpLinkedList.push(nextVertexB);
				vertexListHashMap.put(finalDestination, tmpLinkedList);	
				verticesList.put(finalDestination,1);		
			}
		}
		else
		{
			return false; //For any error
		}
		return true; // For succesfull creating edge in the graph
	}

	/********************************************* clusterVertices Method *********************************************/
	
	//tolerance defines base value for creating clusters and merging them.
	public Set<Set<String>> clusterVertices(float tolerance)
	{
		//Tolerance input Validation
		//No negative value allowed
		if (tolerance<0)
		 {
			return null;
		 }
		Set<Set<String>> clusterSets=new HashSet<Set<String>>(); //Stores set of Clusters

		List<VertexClusterWeight> tempClusterSet=new ArrayList<VertexClusterWeight>(); //Temporary list Stores and keep track of clusters created 
																						//after each edge traversal 
		
		HashMap<String, myEdge> sortedEdgeList=sortHashMapbyWeight(edgeList); //List of edges created based on weight
		
		//Initialize tempclusterSet with max 20 empty VertexClusterWeight object
		//I have concluded after many test cases that number of cluster will not exceeds 20 sets as per given conditons
		for (int i = 0; i < 20; i++)
		{
			VertexClusterWeight VCW=new VertexClusterWeight();
			tempClusterSet.add(i,VCW);
		}
		
		
		
		boolean firstEdge=true; //Flag to check if the first edge is been traversed

		//Folloing loop is traversed for each edge in the sortedEdgeList
		for (Map.Entry<String, myEdge>  edgeObject : sortedEdgeList.entrySet())
		{
			
			
			int	 weight=edgeObject.getValue().getWeight(); //weight from myEdge
			String vertex1=edgeObject.getValue().getSource(); //first vertex(source) from myEdge object 
			String vertex2=edgeObject.getValue().getDestination(); //second vertex(destination) from myEdge object
			
			int weightOfVertex1=verticesList.get(vertex1); //retrieve weight of vertex1 from verticesList
			int weightOfVertex2=verticesList.get(vertex2); //retrieve weight of vertex2 from verticesList
			int minValue=minimumWeight(tempClusterSet, vertex1, vertex2); // stores minimum value from weight of vertex1 & vertex2
			float ratio=(float)weight/minValue; //Calucate ratio

			//Enters loop if the calucated ratio <= tolerancce value
			if (ratio<=tolerance)
			{

				// If first Edge is been Tranversed from sortedEdgeList
				if (firstEdge) 
				{
					
					// add vertex1 & vertex to list of tempclusterSet at index 0
					tempClusterSet.get(0).addVertex(vertex1); 
					tempClusterSet.get(0).addVertex(vertex2);
				
					//Update weight of the lower order vertex among both vertices in the verticesList,
					// if overall edge weight is greater than individual weight of either vertex
					if (weight>weightOfVertex1 || weight>weightOfVertex2)
					{
						verticesList.
						put(CompareVertex(vertex1, vertex2), weight);	
					}

					//Update weight of overall Cluster
					tempClusterSet.get(0).addweight(maximumWeight(tempClusterSet.get(0).getSet())); 
	
					firstEdge=false;//
					
				}
				else // If the edge is not first in order
				{
						// v1Flag determines if vertex1 is present in any existing Cluster
						//v1Index stores the index of cluster vertex1 is present in
						boolean v1Flag=false;
						int v1Index=-1;
						
						// v2Flag determines if vertex1 is present in any existing Cluster
						// v2Index stores the index of cluster vertex2 is present in
						boolean v2Flag=false;
						int v2Index=-1;
					
					// Loops checks if vertex1 and vertex2 are present in list of existing clusters and sets v1Flag and v2Flag accordingly
					// if vertex1 is present in a cluster, store index of that cluster in in  v1Index
					// if vertex2 is present in a cluster, store index of that cluster in in  v2Index

					for (int j = 0; j < tempClusterSet.size(); j++)
					{

							List<String>list1=tempClusterSet.get(j).getSet();
							if (list1.contains(vertex1))
							{
								
								v1Index=j;
								v1Flag=true;
							}
							if (list1.contains(vertex2))
							{
								
								v2Index=j;
								v2Flag=true;
							}
					}

						// if vertex1 & vertex2 both are present in list of clusters
						if (v1Flag==true && v2Flag==true)
						{
							//vertex 1 & vertex2 present in different cluster
							if (v1Index!=v2Index) 
							{
								tempClusterSet.get(v1Index).getSet().addAll(tempClusterSet.get(v2Index).getSet()); //combine cluster at v2Index with v1Index
								
								//Update weight of lower order vertex
								if (weight>weightOfVertex1 || weight>weightOfVertex2)
								{
									verticesList.
									put(CompareVertex(vertex1, vertex2), weight);	
								}
								//Update weight of overall cluster
								tempClusterSet.get(v1Index).addweight(maximumWeight(tempClusterSet.get(v1Index).getSet())); 
								tempClusterSet.remove(v2Index); //Remove cluster at v2Index
							}
							else 
							{
								//	Ignore Edge if both vertices are present in same cluster 
							}
						}
						else if (v1Flag==true && v2Flag==false) //Only vertex1 is present in list of existing cluster 
						{
							tempClusterSet.get(v1Index).getSet().add(vertex2); // add vertex2 to cluster at v1Index
						
							//Update weight of lower order vertex
							if (weight>weightOfVertex1 || weight>weightOfVertex2)
							{
								verticesList.
								put(CompareVertex(vertex1, vertex2), weight);
							}
						    //Update weight of overall cluster
							tempClusterSet.get(v1Index).addweight(maximumWeight(tempClusterSet.get(v1Index).getSet()));
						
						}
						else if (v2Flag==true && v1Flag==false) //Only vertex2 is present in list of existing cluster 
						{
							tempClusterSet.get(v2Index).getSet().add(vertex1);  // add vertex1 to cluster at v2Index
							
							//Update weight of lower order vertex
							if (weight>weightOfVertex1 || weight>weightOfVertex2)
							{
								
								verticesList.
								put(CompareVertex(vertex1, vertex2), weight);	
							}
							//Update weight of overall cluster
							tempClusterSet.get(v2Index).addweight(maximumWeight(tempClusterSet.get(v2Index).getSet()));
						}
						else //if both vertices are not present in any cluster
						{
							//Folloing Loop creates new cluster at first available empty list index 
							//Add vertex1 and verte2 to the list and update the weight of lower order vertex and newly formed cluster
							for (VertexClusterWeight vertexClusterWeight : tempClusterSet) 
							{
								if (vertexClusterWeight.getSet().isEmpty()) 
								{
									vertexClusterWeight.getSet().add(vertex1);
									vertexClusterWeight.getSet().add(vertex2);
									if (weight>weightOfVertex1 || weight>weightOfVertex2)
									{	
										verticesList.put(CompareVertex(vertex1, vertex2), weight);	
									}
									vertexClusterWeight.addweight(maximumWeight(vertexClusterWeight.getSet()));
									break;
								}	
							}
						}	
					}
				
				}

			// If Ratio >Tolerance value
			else 
			{
				// If first Edge is been Tranversed from sortedEdgeList
				if (firstEdge) 
				{
					tempClusterSet.get(0).addVertex(vertex1);// Create new cluster at index 0 containg vertex1 
					tempClusterSet.get(1).addVertex(vertex2);// Create new cluster at index 1 containg vertex2

				tempClusterSet.get(0).addweight(weightOfVertex1);  //add weight of Cluster
				tempClusterSet.get(1).addweight(weightOfVertex2);  //add weight of Cluster
				
					firstEdge=false;	
				}
				
				else
				{	
					// 	v1Flag determines if vertex1 is present in any existing Cluster
					// 	v2Flag determines if vertex2 is present in any existing Cluster
						
				boolean v1Flag=false,v2Flag=false;
				int emptyIndex=-1; // stores the index of first available empty cluster

				// Loops checks if vertex1 and vertex2 are present in list of existing clusters and sets v1Flag and v2Flag accordingly
				// if vertex1 is present in a cluster, store index of that cluster in in  v1Index
				// if vertex2 is present in a cluster, store index of that cluster in in  v2Index
				for (VertexClusterWeight vertexClusterWeight : tempClusterSet) 
				{
					List<String>list1=vertexClusterWeight.getSet();
					
					if (list1.contains(vertex1))
						v1Flag=true;
					
					if (list1.contains(vertex2))
						v2Flag=true;

					if (list1.isEmpty() && emptyIndex==-1)
					 {
						emptyIndex=tempClusterSet.indexOf(vertexClusterWeight); //stores index of empty Cluster
					 }
				}
				
				if (v1Flag==true && v2Flag==true) //Both vertices are present in list of cluster
				{
					// Ignore this Edge as both vertices are in same Index
					
				}
				else if (v1Flag==true && v2Flag==false) //Only vertex1 is present in list of Cluster
				{
					tempClusterSet.get(emptyIndex).addVertex(vertex2); //Create new cluster containin vertex1 at emptyIndex
					tempClusterSet.get(emptyIndex).addweight(verticesList.get(vertex2));	
				}
				else if (v1Flag==false && v2Flag==true)  //Only vertex2 is present in list of Cluster
				{
					tempClusterSet.get(emptyIndex).addVertex(vertex1); //Create new cluster containin vertex2 at emptyIndex
					tempClusterSet.get(emptyIndex).addweight(verticesList.get(vertex1));
				}
				else //If both vertices are not present in the list of Cluster
				{
					int counter=0;
					int emptyIndex1=-1,emptyIndex2=-1;  // store indexes of two empty clusters

					//Loops determine index of empty clusters and assign them to emptyIndex1 and emptyIndex2
					for (VertexClusterWeight vertexClusterWeight : tempClusterSet) 
					{
						List<String>list1=vertexClusterWeight.getSet();
						
						if (counter!=2) 
						{
							if (list1.isEmpty() )
							{
								if(counter==0)
								emptyIndex1=tempClusterSet.indexOf(vertexClusterWeight);
							
								if(counter==1)
								emptyIndex2=tempClusterSet.indexOf(vertexClusterWeight);

							counter++;
							}
						}
					}
					tempClusterSet.get(emptyIndex1).addVertex(vertex1); // Create new cluster at emptyIndex1 conataing vertex1 
					tempClusterSet.get(emptyIndex2).addVertex(vertex2); // Create new cluster at emptyIndex2 conataing vertex2 

					tempClusterSet.get(emptyIndex1).
					addweight(verticesList.get(vertex1)); //Update overall clusterweight new cluster 1

					tempClusterSet.get(emptyIndex2).
					addweight(verticesList.get(vertex2)); //Update overall clusterweight new cluster 2
				}
			}
		}
	}
		
		// Assign CLuster Sets from tempClusterSet to main clusterSets
		for (VertexClusterWeight set : tempClusterSet)
		{
			Set<String> tmpSet=new HashSet<String>();
			if (set.getSet().size()!=0)
			{
				tmpSet.addAll(set.getSet());
				clusterSets.add(tmpSet);
			}	
			
		}
		
		return clusterSets; //Return set of Clusters
	}

	/********************************************* minimumWeight Method *********************************************/

	//This method is use to identify minimum weight from weight of vertex1 and vertex2
	//Main Logic is as follow:
	// if a vertex is present is any existing cluster, then the overall weight of that cluster is considerd as the weight of that vertex
	// if not, then weight of that vertex is fetched from verticesList
	private int minimumWeight(List<VertexClusterWeight> tempClusterSet,String obj1, String obj2)
	{
		int v1Index=-1,
			v2Index=-1;

		int wt1=0, //weight of vertex1
			wt2=0; //weight of vertex2

		for (int j = 0; j < tempClusterSet.size(); j++)
		{	
			
	
				List<String>list1=tempClusterSet.get(j).getSet();
				if (list1.contains(obj1))
				{
					v1Index=j;
				}
				if (list1.contains(obj2))
				{	
					v2Index=j;
				}
		}
			if(v1Index!=-1 && v2Index!=-1)
			{
				wt1=tempClusterSet.get(v1Index).getWeight();
				wt2=tempClusterSet.get(v2Index).getWeight();
			}
			else if(v1Index==-1 && v2Index!=-1)
			{
				wt1=verticesList.get(obj1);
				wt2=tempClusterSet.get(v2Index).getWeight();
			}
			else if(v1Index!=-1 && v2Index==-1)
			{
				wt1=tempClusterSet.get(v1Index).getWeight();
				wt2=verticesList.get(obj2);
			}
			else
			{
				wt1=verticesList.get(obj1);
				wt2=verticesList.get(obj2);
			}
		return (wt1<=wt2)? wt1 :wt2;
	}

	/********************************************* CompareVertex Method *********************************************/
	
	//This method compares the vertices based on ASCII values and return the lower order vertex
	private String CompareVertex(String vertex1,String vertex2) 
	{
		String resultString;
		int flag=vertex1.compareTo(vertex2);
		if(flag<0)
			resultString=vertex1;
		else
			resultString=vertex2;
		return  resultString;
	}
	
	/********************************************* maximumWeight Method *********************************************/

	// This method determines the overall weight of the cluster by comparing weights of each vertices in a cluster
	// and returns highest weight values
	private int maximumWeight(List<String> set)
	{
		int maxValue=0;
		
		//this compares weight of each vertices in the cluster
		for (int i = 0; i <set.size() ; i++)
		{
			int wt1=verticesList.get(set.get(i)); //fetch weight of specified vertex from verticesList

			if (maxValue<wt1)
			{
				maxValue=wt1;	
			}
		}
		return maxValue ;
	}
	
	 
	/********************************************* sortHashMapbyWeight Method *********************************************/

	// this method  sorts the EdgeList based on the weight and order of vertices in each Edge
	private HashMap<String,myEdge> sortHashMapbyWeight( HashMap<String,myEdge> myList) 
	{
		List<Map.Entry<String, myEdge>> tmpList=
				new LinkedList<Map.Entry<String,myEdge>>(myList.entrySet());
		

		Collections.sort(
				tmpList,
				new Comparator<Map.Entry<String, myEdge>>() {
					@Override
					public int compare(Entry<String, myEdge> o1, Entry<String, myEdge> o2)
					{
							int value1=o1.getValue().getWeight(); //weight of vertex1
							int value2=o2.getValue().getWeight(); // weight of vertex2
							String vertex1=o1.getValue().getSource();
							String vertex2=o2.getValue().getSource();
							
							if (value1!=value2)
							{
								return value1-value2;	
							}
							return vertex1.compareTo(vertex2);
					}
				});	
		
				
		HashMap<String, myEdge> resultList=
				new LinkedHashMap<String,myEdge>();
		
				 for (Entry<String, myEdge> me : tmpList) { 
			            resultList.put(me.getKey(), me.getValue()); 
			        } 
				 
		return resultList;
	}
	
	/********************************************* MAIN Method *********************************************/
	/*
	public static void main(String[] args)
	{
		VertexCluster graph=new VertexCluster();
		
		graph.addEdge("  Ajay", "Ben", 3);
		graph.addEdge("Ajay", "Hen", 1);
		graph.addEdge("Ice", "Hen", 1);
		graph.addEdge("Ice  ", "Ben", 4);
		graph.addEdge("Ben", "Cat", 7);
		graph.addEdge("Ice", "Cat", 8);
		graph.addEdge("Gin", "Hen", 7);
		graph.addEdge("Fish", "Gin", 7);
		graph.addEdge("Hen", "Jack", 10);
		graph.addEdge("Dog", "Ice", 12);
		graph.addEdge("Dog", "Cat", 14);
		graph.addEdge("Dog", "Ellen", 8);
		graph.addEdge("Jack", "Fish", 3);
		graph.addEdge("Ellen", "Fish", 2);
		graph.addEdge("Jack", "Dog", 1);
		graph.addEdge("556"," Ellen", 3);

		
		System.out.println(graph.clusterVertices(5.6f));
	}
	*/
}
