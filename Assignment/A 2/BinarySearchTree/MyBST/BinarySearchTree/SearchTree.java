package BinarySearchTree;

public class SearchTree {

	private TreeNode rootNode; // Stores the every first Node added to the tree
								// reference to the root Node of BST

	/*
	 * NOTE
	 * 
	 * As this Queue can't be initialized dynamically, I have estimated that each
	 * tree will suppose maximum of 100 nodes and after that efficiency of using
	 * Binary Search Tree decreases.
	 * 
	 */
	public Queue tempQueue = new Queue(100); // This Queue stores and keeps track of every
											// 	node added to the tree using add()

	// default Constructor
	public SearchTree() {

	}

	/****************************************
	 				 Add method
	 ****************************************/

	public boolean add(String key) {
		
		// Check if node key is null, empty strings or multiple spaces
		if (key.equals(null)|| key.equals("") || key.equals("\\s+")) {
			return false;	
			}

		TreeNode newNode = new TreeNode(key.toUpperCase()); // Changes key value to UpperCase 
		Queue bfsQueue = new Queue(100); //Initialize Queue for BFS approach

		/*
			this loop checks if node key already exists,
			and if yes, returns false
		*/
		for (TreeNode Xnode : this.tempQueue.Qarray) {
			if (Xnode != null) {

				if (Xnode.key.equals(newNode.key)) {
					System.out.println("This node already exist!!!");
					return false;
				}
			}
		}

		// assign rootNode the new node if it's empty
		//Also insert node to tempQueue and increment it's depth
		if (rootNode == null) {
			// depth++;
			rootNode = newNode;
			rootNode.depthLevel = 1;
			tempQueue.Insert(rootNode);

		}

		/*
			This logic is based on Breadth First Search (BFS)
			to add the new Node to bottom of the tree.

			First, it checks left child is empty or not
			If not, then the right child is checked
		*/ 
		else {
			bfsQueue.Insert(rootNode);

			while (!bfsQueue.isEmpty()) { //Traverse unitl bfsQueue is empty

				TreeNode currentNode = bfsQueue.Remove();

				 //Check if currentNode's left child is empty
				 //If it is, newNode is assign to it
				if (currentNode.leftNode == null) {
					currentNode.leftNode = newNode;
					currentNode.leftNode.depthLevel = currentNode.depthLevel + 1; //Increment depth of newNode
					newNode.parentNode = currentNode; 	// assign currentNode as parent to newNode
					System.out.println(newNode.key);
					tempQueue.Insert(newNode);
					break;	
				}
				 //Check if currentNode's right child is empty
				 //If it is, newNode is assign to it
				 else if (currentNode.rightNode == null) {
					currentNode.rightNode = newNode;
					currentNode.rightNode.depthLevel = currentNode.depthLevel + 1; //Increment depth of new node
					newNode.parentNode = currentNode;  	// assign currentNode as parent to newNode
				
					System.out.println(newNode.key);
					tempQueue.Insert(newNode);
					break;
				} 
				else {
					bfsQueue.Insert(currentNode.leftNode);   //Insert Left & Right Child 
					bfsQueue.Insert(currentNode.rightNode);  //of currentNode to the bfsQueue

				}

			}

		}

		return true; //Return true is node added to tree successfully
	}

	/****************************************
	 				 find method
	 ****************************************/
	public int find(String key) {

		// Check if node key is null, empty strings or multiple spaces
		if (key.equals(null)|| key.equals("") || key.equals("\\s+")) {
			return 0;	
			}
		String keyValue = key.toUpperCase();  // Changes key value to UpperCase 
		Queue bfsQueue = new Queue(100);
		int depthValue=0;					// store the depth of the node which is to be found
		
		//Checks if Tree is Empty
		if (rootNode == null) {
			System.out.println("Tree is Empty!!!");
		}
			/*This logic is also Based on BFS
			
				It checks if the requested node is 
				present in the tree, if it is, call count is incremented
				restructure method is called based on type (Left/Right)
				 of recently found node			
			*/
			else {
			bfsQueue.Insert(rootNode);
			while (!bfsQueue.isEmpty()) {
				
				TreeNode currentNode = bfsQueue.Remove();
				if (currentNode.key.equals(keyValue)) {
					currentNode.callCount++; //Increment call count of recently found node
					if (currentNode == rootNode) // exit llop if the traversed node is root node
						break;
					if (currentNode.callCount > currentNode.parentNode.callCount) {
						if (currentNode == currentNode.parentNode.leftNode) 
							restructureLeftNode(currentNode); //if the node is Left chid, then 
															//restructureLeftNode() is called
							else								
							restructureRightNode(currentNode);//if the node is Left chid, then 
					}										//restructureLeftNode() is called
				
					depthValue=currentNode.depthLevel;
					break;
				}

				if (currentNode.leftNode != null) {
					bfsQueue.Insert(currentNode.leftNode);
				}
				if (currentNode.rightNode != null) {
					bfsQueue.Insert(currentNode.rightNode);
				}
			}
		}
		return depthValue; // Return depth value of the found Node
	}

	/****************************************
	 		 RestructureLeftNode method
	 ****************************************/

	public void restructureLeftNode(TreeNode currentNode) {
		TreeNode parentNode = currentNode.parentNode;
		TreeNode superParent = parentNode.parentNode;
		if (superParent != null) {
			if (superParent.leftNode == parentNode)
				superParent.leftNode = currentNode;
			else
				superParent.rightNode = currentNode;

			currentNode.parentNode = superParent;
		}

		if (superParent == null) {
			rootNode = currentNode;
			currentNode.parentNode = null;
		}

		parentNode.leftNode = currentNode.rightNode;
		if (currentNode.rightNode != null) {
			currentNode.rightNode.parentNode = parentNode;
		}

		currentNode.rightNode = parentNode;
		parentNode.parentNode = currentNode;

		findDepth(rootNode, 1); //finds new depth of each node after restructuring (if changed)
	}

	/****************************************
	 		 RestructureRightNode method
	 ****************************************/

	public void restructureRightNode(TreeNode currentNode) {
		TreeNode parentNode = currentNode.parentNode;
		TreeNode superParent = parentNode.parentNode;

		if (superParent != null) {
			if (superParent.leftNode == parentNode)
				superParent.leftNode = currentNode;
			else
				superParent.rightNode = currentNode;

			currentNode.parentNode = superParent;
		}
		if (superParent == null) {
			rootNode = currentNode;
			currentNode.parentNode = null;
		}

		parentNode.rightNode = currentNode.leftNode;
		if (currentNode.leftNode != null) {
			currentNode.leftNode.parentNode = parentNode;
		}

		currentNode.leftNode = parentNode;
		parentNode.parentNode = currentNode;

		findDepth(rootNode, 1); //finds new depth of each node after restructuring (if changed)
	}

	/****************************************
	 		findDepth method
	 ****************************************/
	
	 /*
	 	This method, using Inorder traversal, find the depth of the 
		 node, passed as parameter
 	*/
	public void findDepth(TreeNode node, int depth) {
		if (node == null)
			return;

		findDepth(node.leftNode, depth + 1); //Recursive call with node's Left child as parameter

		node.depthLevel = depth;

		findDepth(node.rightNode, depth + 1); //Recursive call with node's Right child as parameter

	}

	/****************************************
	 				reset method
	 ****************************************/
	
	 /*
	 	This method, resets call count of every active 
		 node in the BinarySearchTree. 
 	*/
	public void reset()
	{
		//Loops through tempQueue that store every node
		for (TreeNode Xnode : this.tempQueue.Qarray) {
			if (Xnode != null) {

				Xnode.callCount=0; 
			}
		}
	}

	/****************************************
	 		printTree method
	 ****************************************/
	
	 /*
	 	This method returns String which ultimately
		 prints multi line structure with each line 
		 representing every node & their depth value. 
 	*/
	public String printTree() {
		Queue bfsQueue = new Queue(100);  //Initialize Queue for BFS approach
		bfsQueue.Insert(rootNode);
		String TEMPstring="";  //Empty String
		
		// Loops unitl the bfsQueue is empty
		while (!bfsQueue.isEmpty()) {
			TreeNode currentNode = bfsQueue.Remove();

			// add node key and depth value to TEMPstring on a new line
			TEMPstring=TEMPstring+'\n'+currentNode.key+" "+currentNode.depthLevel;

			if (currentNode.leftNode != null)    //Inserts Left child of current node to bfsQueue if not empty
				bfsQueue.Insert(currentNode.leftNode);
			if (currentNode.rightNode != null)	//Inserts Right child of current node to bfsQueue if not empty
				bfsQueue.Insert(currentNode.rightNode);
		}
		return TEMPstring; //Return TEMPstring which is now a multi line structure
	}

}
