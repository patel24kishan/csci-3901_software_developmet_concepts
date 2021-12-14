/*
TreeNode is a class which I have created,
 is basically a building block for creating a tree. 

This class helps to store reference to 
 Left Child, Right Child and also Parent Node..

 Along with that it store the depth at which
 that node is located, value of that node and 
 more importantly it stores the call count of
 that particular node.
*/


package BinarySearchTree;

public class TreeNode {
	
	public String key;
	int callCount; //stores call count
	String Value; 
	int depthLevel; //Stores depth of the node
	public TreeNode parentNode; //reference of the parent node
	public TreeNode leftNode,   //reference to Left Child
					rightNode; //reference to Right Child
	
	// Constructor
	public TreeNode(String key) {
		this.key = key;
	}
	
}
