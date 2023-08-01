package listClass;

import java.util.Iterator;
//Class which creates a basic linked list makes it iterable
public class BasicLinkedList<T> implements Iterable<T> {
	//Private Instance Variables for the BasicLinkedList Class
	private Node<T>head;
	private Node<T>tail;
	private int size;
	
	
	
	//Static nested Node Class
	private static class Node<T>{
		//Private instance variables for Node class
		private Node<T> next;
		private T data;
		
		//Constructor for Node class
		public Node(T element) {
			data=element;
			next=null;
		}
		
	}
	//Constructor for BasicLinkedList class which sets head and tail reference to null and size to zero
	public BasicLinkedList() {
		head=null;
		tail=null;
		size=0;
	}
	//Getter for size
	public int getSize() {
		return this.size;
	}
	/*
	 * First deals with the corner case of the size of the list being zero. We create a new node with
	 * the specified data and set the head to the new node and the tail to the new node and increase 
	 * the size. In regular cases we also create a new node with the specified data and we set the 
	 * tail.next to the newNode and then re-assign the tail. Then we want to set this next reference to 
	 * null because it is the tail. We then increase the size and return the refrence.
	 */
	public BasicLinkedList<T>addToEnd(T data){
		if(size==0) {
			Node<T>newNode=new Node<>(data);
			head=newNode;
			tail=newNode;
			size++;
			return this;
		}
		
		
	/*	for(Node<T>curr=head; curr!=null; curr=curr.next) {
			
			if(curr.next==null) {
				Node<T> newNode=new Node<>(data);
				newNode.next=null;
				tail=newNode;
				curr.next=newNode;
				size++;
				return this;
				
				
			}
		}*/
		
		Node<T>newNode=new Node<>(data);
		tail.next=newNode;
		tail=newNode;
		newNode.next=null;
		size++;
		
		
		return this;
		
		
	
		
		
	}
	
	/*
	 * First deals with the corner case of size zero. Like the previous method, we make a new node
	 * with the data passed in the arguments. The head and tail are set to this new Node and the
	 * size is increased.In normal cases we set this new node's next reference to the head, then 
	 * reassign the head to this new node and increment the size
	 */
	public BasicLinkedList<T>addToFront(T data){
		if(size==0) {
			Node<T>newNode=new Node<>(data);
			head=newNode;
			tail=newNode;
			size++;
			return this;
		}
		Node<T>newNode=new Node<>(data);
		newNode.next=head;
		head=newNode;
		size++;
		return this;
		
	}
	
	/*
	 * If head is null this returns null but in all other cases it returns the data in the head node
	 */
	public T getFirst() {
		if(head==null) {
			return null;
		}else {
			return head.data;
		}
		
		
	}
	/*
	 * If the tail is null this returns null but in all other cases it returns the data in the tail node
	 */
	public T getLast() {
		if(tail==null) {
			return null;
		}else {
			return tail.data;
		}
	}
	/*
	 * If the list is size zero it returns null. If the size is equal to one we return the data in that
	 * node and set head and tail to null and decrement the size. In all other cases we assign
	 * a reference to the head and set the head to the next element and then set the former head 
	 * reference to null and decrement the size.
	 */
	public T retrieveFirstElement() {
		if(size==0) {
			return null;
		}
		if(size==1) {
			T data=head.data;
			head=null;
			tail=null;
			size--;
			return data;
			
		}
		Node<T>toRemove=head;
		head=toRemove.next;
		toRemove.next=null;
		size--;
		return toRemove.data;
	}
	/*
	 * If size is zero we return null. If size is 1 we store the data from the tail and then set
	 * head and tail to null and decrement the size. In all other cases we have to loop through the 
	 * list starting at the head, and ending when before the tail and setting the curr to curr.next.
	 * Then we check if the node two away (next.next) is null. If it is we store the next node, which
	 * is the tail, and set the new tail to the curr. We also set the tail.next to null and decrement
	 * the size
	 */
	public T retrieveLastElement() {
		if(size==0) {
			return null;
		}
		if(size==1) {
			T data=tail.data;
			head=null;
			tail=null;
			size--;
			return data;
		}
		T data=null;
		for(Node<T>curr=head; curr.next!=null; curr=curr.next) {
			if(curr.next.next==null) {
				data=curr.next.data;
				tail=curr;
				curr.next=null;
				size--;
				return data;
				
				
				
				
				
			}
			
			
		}
		return data;
		
	}
	/*
	 * Method that removes all instances in a certain node. First, we deal with a corner case of when
	 * the list contains all the same data and it happens to all be the target data. We go through
	 * the list to figure out how many instances of the target data are there. If that number is
	 * equal to the size of the list we know that we need to remove every single element of the list
	 * and we do this by setting the size to zero, and setting the head and tail to null. We then check
	 * if the head and tail are the target data. If this is true, we keep removing by calling the 
	 * retrieveFristElement and retrieveLastElement methods. After we know the head and tail are not
	 * target data we check a corner case if the list is equal to size 1. If the target data in that 
	 * node is equal to the target data we set head and tail to null and size to zero. If it does not 
	 * equal target data we return the linked list because the method should be over. If we make it past 
	 * there we have to loop through the list, starting at the node after the head, because we know the 
	 * head cannot be the target data. We stop before curr is tail, because again we know the tail can't
	 * have the target data. We also use another node called prev which starts at head. If curr is 
	 * equal to the targetdata we set prev.next to curr.next, curr.next to null, and decrement the size.
	 * If it doesn't equal the target data we move prev up one by setting it to prev.next.
	 */
	public BasicLinkedList<T>removeAllInstances(T targetData){
		
		int counter=0;
		for(Node<T>curr=head; curr!=null; curr=curr.next) {
			if(curr.data.equals(targetData)) {
				counter++;
			}
		}
		
		if(counter==this.getSize()) {
			size=0;
			head=null;
			tail=null;
			return this;
		}
		
		while(targetData.equals(head.data)) {
			this.retrieveFirstElement();
			
			
			
			
		}
		

		while(targetData.equals(tail.data)) {
			this.retrieveLastElement();
			
		
			
		}
		
	
		
		
		
	
		if(size==1) {
			if(head.data.equals(targetData)) {
				head=null;
				tail=null;
				size=0;;
				return this;
			}else {
				return this;
			}
		}
		
		
		
		
		
		
		
		
		
	Node<T>prev=head;
		for(Node<T>curr=head.next; curr!=tail; curr=prev.next) {
			if(curr.data.equals(targetData)) {
				prev.next=curr.next;
				curr.next=null;
				size--;
			}else {
				prev=prev.next;
			}
			
		}
		
		return this;
		
		
		
		
		
		
		}
		
		
	/*
	 * Method contains an anonymous innerclass. We use a node called curr as an instance variable.
	 * Using an initialization block, we set curr equal to a new node, which doesn't contain anything.
	 * We also set curr.next to the head of the linked list. If curr.next is not null, the hasNext method
	 * always returns true because there is another node after. If it does equal null, this returns
	 * false. The next method returns the data in the next node. First, we set curr equal to
	 * curr.next and then we return curr.data.
	 */
	@Override
	public Iterator<T>iterator(){
		return new Iterator<T>(){
			
			private Node<T>curr;
			{curr = new Node<>(null);
			curr.next=head;
			}
			
			

			@Override
			public boolean hasNext() {
				if(curr.next!=null) {
					return true;
				}else {
					return false;
				}
				
			}

			@Override
			public T next() {
				curr=curr.next;
				return curr.data;
			}
			
		
		
		
		};
				}
	

	
	
	
	
	
			
			
			
			
			
			
	
}

