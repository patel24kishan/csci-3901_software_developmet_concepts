/*
For inserting new node to the tree,
I thought of using BFS technique which 
allows me to add new Node at the bottom
of tree.

Implementing BFS with Queue is easy but 
usage of in-built Collections Framework is not 
allowed so I created my own Queue class using 
standard Array and implemented some basic Queue methods.
*/

package BinarySearchTree;

public class Queue {

	private int maxSize;
	public TreeNode[] Qarray;
	private int rear;
	
	//Constructor
	public Queue(int size)
	{
		this.maxSize=size;
		this.Qarray=new TreeNode[maxSize];
		rear=-1;
	} 
	
	// Simple Queue Insert method
	public void Insert(TreeNode l) 
	{
		if (isFull()) {
			System.err.println("queue is Full !!!");
			return;
		}
		else {
			rear++;
			Qarray[rear] = l;
		}
	}
	
	//Remove elements from head of the Queue
	public TreeNode Remove() 
	{
		TreeNode temp = null;
		if (isEmpty()) {
			System.err.println("queue is Empty !!!");
			return null;
		}
		else
		{
			temp = Qarray[0];
			for(int i=0; i<Qarray.length-1; ++i) {
				Qarray[i]=Qarray[i+1];
			}
			rear--;
			return temp;
		}
	}
	
	//Check if Queue is Empty
	public boolean isEmpty() {
		return (rear==-1);
	}
	
	//Check if Queue is Full
	public boolean isFull() {
		return (rear==maxSize-1);
	}

	//Print Elements of the Queue
	public void view()
	{
		System.out.print("[");
		for (int i = 0; i <rear+1 ; i++) {
			if (Qarray[i]==null)
			{
				return;
			}
			System.out.print(Qarray[i].key.toString()+" "+Qarray[i].depthLevel+" , ");
		}
		System.out.print("]");
	}

}
