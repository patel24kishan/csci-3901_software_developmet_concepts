package BinarySearchTree;

public class Test {

	public static void main(String[] args) {
		
		SearchTree tree=new SearchTree();
		tree.add("Kishan");
		tree.add("Raj");
		tree.add("Jaydeep");
		tree.add("Abhi");
		tree.add("Yogesh");
		tree.add("Pratik");
		tree.add("Varun");
		tree.add("Bc");
		tree.add("Mc");
		tree.add("McAQSXcwq");
		tree.add("adcxs");
		
		System.out.println("\nBinaryTree");
		tree.tempQueue.view();
		System.out.println();
		
		System.out.println("\n");
		
		tree.Find("Yogesh");
		tree.printTrees();
		tree.Find("Yogesh");
		tree.printTrees();
		tree.Find("Yogesh");
		tree.printTrees();
		tree.Find("Raj");
		tree.printTrees();
		tree.Find("adcxs");
		tree.printTrees();
		
		System.out.println("\nBinaryTree after search");
		tree.printTrees();
		
		// System.out.println(tree.printTree());
		// tree.reset();
		// System.out.println("After reset:");
		// tree.displayTree();
	}

}
