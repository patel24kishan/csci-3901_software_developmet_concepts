package BinarySearchTree;

import java.util.Iterator;

public class Queue {

	private int maxSize;
	public TreeNode[] Qarray;
	private int rear;
	
	public Queue(int size)
	{
		this.maxSize=size;
		this.Qarray=new TreeNode[maxSize];
		rear=-1;
	} 
	
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
	
	public TreeNode Peek() {
		return Qarray[0]; 
	}
	
	public boolean isEmpty() {
		return (rear==-1);
	}
	

	public boolean isFull() {
		return (rear==maxSize-1);
	}
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
