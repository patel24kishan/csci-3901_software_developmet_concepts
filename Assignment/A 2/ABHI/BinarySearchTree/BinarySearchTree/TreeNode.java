package BinarySearchTree;

public class TreeNode {
	
	public String key;
	int callCount;
	String Value;
	int depthLevel;
	public TreeNode parentNode;
	public TreeNode leftNode,rightNode;
	
	
	public TreeNode(String key) {
	//	super();
		this.key = key;
	}
	
	public void addCallCount()
	{
		this.callCount+=1;
	}
	
	

}
