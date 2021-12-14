package BinarySearchTree;

//import java.lang.FdLibm.Pow;
import java.util.Stack;

import java.lang.Math;

public class SearchTree {

	private TreeNode rootNode;

	public Queue tempQueue = new Queue(20);

	// private int depth=0;

	public SearchTree() {

	}

	public void add(String key) {
		TreeNode newNode = new TreeNode(key.toUpperCase());
		Queue bfsQueue = new Queue(100);

		for (TreeNode Xnode : this.tempQueue.Qarray) {
			if (Xnode != null) {

				if (Xnode.key.equals(newNode.key)) {
					System.out.println("This node already exist!!!");
					return;
				}
			}
		}

		if (rootNode == null) {
			// depth++;
			rootNode = newNode;
			rootNode.depthLevel = 1;
			System.out.println("Root child");
			tempQueue.Insert(rootNode);

		}

		else {
			bfsQueue.Insert(rootNode);

			while (!bfsQueue.isEmpty()) {
				// tempQueue.view();

				TreeNode currentNode = bfsQueue.Remove();

				if (currentNode.leftNode == null) {
					currentNode.leftNode = newNode;
					currentNode.leftNode.depthLevel = currentNode.depthLevel + 1;
					newNode.parentNode = currentNode;
					System.out.println("\nLeft");
					System.out.println(newNode.key);

					tempQueue.Insert(newNode);
					break;
				} else if (currentNode.rightNode == null) {
					currentNode.rightNode = newNode;
					currentNode.rightNode.depthLevel = currentNode.depthLevel + 1;
					newNode.parentNode = currentNode;
					System.out.println("\nRight");
					System.out.println(newNode.key);
					tempQueue.Insert(newNode);
					break;
				} else {
					bfsQueue.Insert(currentNode.leftNode);
					bfsQueue.Insert(currentNode.rightNode);

				}

			}

		}
	}

	public void Find(String key) {
		String keyValue = key.toUpperCase();
		Queue bfsQueue = new Queue(100);
		if (rootNode == null) {
			System.out.println("Tree is Empty!!!");
		} else {
			bfsQueue.Insert(rootNode);
			while (!bfsQueue.isEmpty()) {
				// tempQueue.view();
				TreeNode currentNode = bfsQueue.Remove();
				if(currentNode.key.equals(keyValue)) {
					System.out.println(currentNode.key + " found at Level  " + currentNode.depthLevel);
					currentNode.callCount++;
					if(currentNode == rootNode)
						break;
					if(currentNode.callCount > currentNode.parentNode.callCount) {
						if(currentNode == currentNode.parentNode.leftNode)
							restructureLeftNode(currentNode);
						else
							restructureRightNode(currentNode);
					}
					System.out.println(currentNode);
					break;
				}
				
				if (currentNode.leftNode != null) {
					bfsQueue.Insert(currentNode.leftNode);
				} if (currentNode.rightNode != null) {
					bfsQueue.Insert(currentNode.rightNode);
				}
			}
		}
	}

	public void restructureLeftNode(TreeNode currentNode) {
		TreeNode parentNode = currentNode.parentNode;
		TreeNode superParent = parentNode.parentNode;
		if(superParent != null) {
			if(superParent.leftNode == parentNode)
				superParent.leftNode = currentNode;
			else 
				superParent.rightNode = currentNode; 
	
			currentNode.parentNode = superParent;
		}
		
		if(superParent == null) {
			rootNode = currentNode;
			currentNode.parentNode = null;
		}

		parentNode.leftNode = currentNode.rightNode;
		if(currentNode.rightNode != null) {
			currentNode.rightNode.parentNode = parentNode;
		}

		currentNode.rightNode = parentNode;
		parentNode.parentNode = currentNode;

		findDepth(rootNode, 1);
	}

	public void restructureRightNode(TreeNode currentNode) {
		TreeNode parentNode = currentNode.parentNode;
		TreeNode superParent = parentNode.parentNode;

		if(superParent != null) {
			if(superParent.leftNode == parentNode)
				superParent.leftNode = currentNode;
			else 
				superParent.rightNode = currentNode;
	
			currentNode.parentNode = superParent;
		}
		if(superParent == null) {
			rootNode = currentNode;
			currentNode.parentNode = null;
		}
		
		parentNode.rightNode= currentNode.leftNode;
		if(currentNode.leftNode != null) {
			currentNode.leftNode.parentNode = parentNode;
		}

		currentNode.leftNode = parentNode;
		parentNode.parentNode = currentNode;

		findDepth(rootNode, 1);
	}

	public void findDepth(TreeNode node, int depth) {
		if(node == null)
			return;
		
		findDepth(node.leftNode, depth + 1);

		node.depthLevel = depth;

		findDepth(node.rightNode, depth + 1);

		
	}
	
	public void printTrees() {
		Queue bfsQueue = new Queue(100);
		bfsQueue.Insert(rootNode);

		while(!bfsQueue.isEmpty()) {
			TreeNode currentNode = bfsQueue.Remove();
			System.out.print(currentNode.key + ' ');
			System.out.println(currentNode.depthLevel);

			if(currentNode.leftNode != null)
				bfsQueue.Insert(currentNode.leftNode);
			if(currentNode.rightNode != null)
				bfsQueue.Insert(currentNode.rightNode);
		}		
	}

	public void queueModify() {
		Queue temporaryQueue = new Queue(100);
		temporaryQueue.Insert(rootNode);

		tempQueue = new Queue(100);

		while (!temporaryQueue.isEmpty()) {

			TreeNode currentNode = temporaryQueue.Remove();
			tempQueue.Insert(currentNode);

			if (currentNode.leftNode == null) {
			} else if (currentNode.rightNode == null) {
				temporaryQueue.Insert(currentNode.leftNode);
			} else {
				temporaryQueue.Insert(currentNode.leftNode);
				temporaryQueue.Insert(currentNode.rightNode);

			}

		}

	}
	public void reset() {
		for (TreeNode tNode : tempQueue.Qarray) {
			if (tNode == null) {
				break;
			}
			tNode.callCount = 0;
		}
	}
	public String printTree() {
		String tempStr = "";

		for (TreeNode tNode : tempQueue.Qarray) {
			// System.out.print("n");
			if (tNode == null) {
				break;
			}
			tempStr = tempStr + "\n" + tNode.key.toString() + " " + tNode.depthLevel;

		}

		return tempStr;
	}
	public void displayTree() {

		Stack globalStack = new Stack();
		globalStack.push(rootNode);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println("......................................................");

		while (isRowEmpty == false) {
			Stack localStack = new Stack();
			isRowEmpty = true;
			for (int j = 0; j < nBlanks; j++) {
				System.out.print(" ");
			}

			while (globalStack.isEmpty() == false) {
				TreeNode temp = (TreeNode) globalStack.pop();
				if (temp != null) {
					System.out.print(temp.key + " " + temp.callCount);
					localStack.push(temp.leftNode);
					localStack.push(temp.rightNode);
					if (temp.leftNode != null || temp.rightNode != null) {
						isRowEmpty = false;
					}
				} else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}

				for (int j = 0; j < nBlanks * 2 - 2; j++) {
					System.out.print(" ");
				}
			}

			System.out.println();
			nBlanks = nBlanks / 2;

			while (localStack.isEmpty() == false)
				globalStack.push(localStack.pop());
		}
		System.out.println("......................................................");

	}

}
