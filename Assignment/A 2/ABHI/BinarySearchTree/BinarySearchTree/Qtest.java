package BinarySearchTree;

public class Qtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Queue Q=new Queue(5);
		
		TreeNode newNode=new TreeNode("Kishan".toUpperCase());
		TreeNode newNode2=new TreeNode("Raj".toUpperCase());

		Q.Insert(newNode);
		
		Q.Insert(newNode2);
		
		Q.view();
		System.out.println("node: "+Q.Remove().key+"\n");
		Q.view();
		System.out.println(Q.isEmpty());
		
	}

}
