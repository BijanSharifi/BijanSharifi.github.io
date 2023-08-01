import java.util.ArrayList;
import java.util.Iterator;

/** 
 * The MyHashSet API is similar to the Java Set interface. This
 * collection is backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/** Unless otherwise specified, the table will start as
	 * an array (ArrayList) of this size.*/
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/** When the ratio of size/capacity exceeds this
	 * value, the table will be expanded. */
	private final static double MAX_LOAD_FACTOR = 0.75;

	
	public ArrayList<Node<E>> hashTable;

	private int size;  // number of elements in the table


	


	
	public static class Node<T> {
		private T data;
		public Node<T> next;  // STUDENTS: Leave this public, too!

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Initializes an empty table with the specified capacity.  
	 *
	 * @param initialCapacity initial capacity (length) of the 
	 * underlying table
	 */
	/*
	 * Initializes hashTable as an ArrayList of nodes and uses a for loop to go through and 
	 * set each index to null until it reaches the argument initialCapacity 
	 */
	public MyHashSet(int initialCapacity) {
		hashTable=new ArrayList<Node<E>>();
		for(int i=0; i<initialCapacity; i++) {
			hashTable.add(null);
		}

	}




	/**
	 * Initializes an empty table of length equal to 
	 * DEFAULT_INITIAL_CAPACITY
	 */
	/*
	 * Initializes hashTable to an ArrayList of nodes and uses a for loop to go through and
	 * set each index to null until it reaches the named constant default initial capacity
	 */
	public MyHashSet() {
		//hashTable=MyHashSet(DEFAULT_INITIAL_CAPACITY);
		hashTable=new ArrayList<Node<E>>();
		for(int i=0; i<DEFAULT_INITIAL_CAPACITY; i++) {
			hashTable.add(null);
		}

	}

	/**
	 * Returns the number of elements stored in the table.
	 * @return number of elements in the table
	 */
	public int size(){

		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * @return length of the table (capacity)
	 */
	public int getCapacity(){
		return hashTable.size();
	}


	/*
	 * Helper method to get the index of the array which is the bucket that a certain element
	 * needs to go in based on its hashCode
	 */
	private int bucketGetter(Object element) {
		int bucketValue=Math.abs(element.hashCode()%getCapacity());
		return bucketValue;

	}

	/** 
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */

	/*
	 * Checks if a certain element is in the hashTable. First assigns a node to the 
	 * head of a link list using the bucketGetter helper method, which gives us which 
	 * bucket this element is in. If curr is null we know it is not in it and we return false.
	 * Then, using a while loop, we traverse the link list and and if the object is found it return
	 * true. Otherwise, it assigns curr to curr.next.
	 */
	public boolean contains(Object element) {
		Node<E> curr=hashTable.get(bucketGetter(element));
		if(curr==null) {
			return false;
		}


		while(curr!=null) {
			if(curr.data.equals(element)) {
				return true;
			}
			curr=curr.next;


		}
		return false;
	}
	//HashCode
	/** Adds the specified element to the collection, if it is not
	 * already present.  If the element is already in the collection, 
	 * then this method does nothing.
	 * 
	 * @param element the element to be added to the collection
	 */
	/*
	 * If the table has the element already, this method does nothing and it returns. 
	 * Otherwise, we create a new node with the element in it. If the bucket that this
	 * node goes into is null, we can use the ArrayLists set method and assign that bucket
	 * to curr and increment. If it is not null we set curr to the head of that linked list 
	 * and we want to add this node to the end. So as long as curr.next is not null we keep 
	 * incrementing curr. When we leave this loop we check again if the curr.next is null
	 * (it always should be) and then curr.next is equal to the newNode. We increment the size.
	 * Also, if the size of the hashTable(how many elements are in it) divided by the number of 
	 * buckets is greater than or equal to the max load factor. If it is, it needs to be resized. 
	 * We create a new ArrayList double the size of the capacity, and then set all the refrences 
	 * to null. Then loop through the array and if the tables index is not null, we set curr
	 * equal to the head of that linked list. While curr is not null, we get a new bucket
	 * for that node. We also make another node that has the same data as curr. Then it comes 
	 * time to place things in the new hash table. If the new hash table at the new bucket index
	 * is null we can use the ArrayList set method and set newCurr to newBucket index. If there are 
	 * already things in the linked list we just traverse it until the next one is null and stick
	 * it there. We then set hashTable to this newHashTable and the old hashTable is garbage.
	 */
	public void add(E eleme1nt) {
		if(contains(eleme1nt)) {
			return;
		}else {
			Node<E>newNode=new Node<>(eleme1nt);
			if(hashTable.get(bucketGetter(eleme1nt))==null) {
				hashTable.set(bucketGetter(eleme1nt), newNode);
				size++;

			}else {
				Node<E>curr=hashTable.get(bucketGetter(eleme1nt));
				while(curr.next!=null) {
					curr=curr.next;
				}
				if(curr.next==null) {
					curr.next=newNode;
					size++;
				}
			}

		}

		if(((double)size()/(double)getCapacity())>=MAX_LOAD_FACTOR) {
			ArrayList<Node<E>>newHashTable=new ArrayList<>(getCapacity()*2);
			for(int i=0; i<(getCapacity()*2); i++) {
				newHashTable.add(null);
			}

			for(int i=0; i<hashTable.size(); i++) {
				if(hashTable.get(i)!=null) {
					Node<E>curr=hashTable.get(i);
					while(curr!=null) {
						int newBucket=Math.abs(curr.data.hashCode()%newHashTable.size());
						Node<E>newCurr=new Node<>(curr.data);
						if(newHashTable.get(newBucket)==null) {
							newHashTable.set(newBucket, newCurr);
						}else {
							Node<E>currNode=newHashTable.get(newBucket);
							while(currNode.next!=null) {
								currNode=currNode.next;
							}
							if(currNode.next==null) {
								currNode.next=newCurr;
							}
						}
						curr=curr.next;
					}
				}
			}
			hashTable=newHashTable;

		}

	}

	/** Removes the specified element from the collection.  If the
	 * element is not present then this method should do nothing (and
	 * return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element 
	 * removed
	 */

	/*
	 * If it doesn't contain the element then there is nothing to remove and the method
	 * does nothing. Otherwise curr is set to the appropriate bucket. If curr is the element
	 * the hashTable is sets that bucket to curr.next and curr is garbage and size is decremented
	 * and this returns true. If it is not the first thing in the linked list we have to 
	 * traverse it and also use a previous reference. While curr is not null we check if curr
	 * has the element, and if it does we set prev.next to curr.next and decrement the size. 
	 * Otherwise we set prev to curr and curr to curr.next.
	 */
	public boolean remove(Object element) {



		if(!contains(element)){
			return false;
		}
		Node<E>curr=hashTable.get(bucketGetter(element));
		if(curr.data.equals(element)) {
			hashTable.set(bucketGetter(element), curr.next);
			size--;
			return true;
		}
		Node<E>prev=null;
		while(curr!=null) {
			if(curr.data.equals(element)) {
				prev.next=curr.next;
				size--;
				return true;

			}else {
				prev=curr;
				curr=curr.next;
			}
		}
		size--;
		return true;


	}









	/** Returns an Iterator that can be used to iterate over
	 * all of the elements in the collection.
	 * 
	 * The order of the elements is unspecified.
	 */

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int bucket=0;
			private int counter=0;
			private boolean firstNode=true;
			private Node<E>currNode;
			//If the number of elements is less than the size this returns true
			@Override
			public boolean hasNext() {
				if(counter<size()) {
					return true;
				}else {
					return false;
				}


			}

			/*
			 * If the hashTable given an index is equal to null to start, we keep increasing the bucket
			 * index until it is not null and we have something to traverse and return that is 
			 * not null. Once it is not null we set currNode to that first node. If the next node is 
			 * null we know there is only one thing in this bucket, we know we need to move on to 
			 * another bucket so we set our boolean firstNode to true, we increase the counter and 
			 * increase the bucket and return the data. If currNode.next is not null we increase the 
			 * counter and switch firstNode to false because we know we still have to continue going
			 * through this bucket. If the hashTable's index is not null, we check if we are dealing
			 * with the firstNode. If we are we set currNode to the first node. We again check 
			 * if the next refrece is null, and if it is we set firstNode to true, increase counter 
			 * and bucket. If the next node is not null we set firstNode to false, and increse the 
			 * counter. If the firstNode is not true, we set currNode to currNode.next. If the next
			 * refrence is null, we set firstNode to true, increase bucket and counter. If the next one
			 * is not null we increase the counter.
			 */
			@Override
			public E next() {
				if(hashTable.get(bucket)==null) {
					while(hashTable.get(bucket)==null) {
						bucket++;
					}
					currNode=hashTable.get(bucket);
					if(currNode.next==null) {
						firstNode=true;
						counter++;
						bucket++;
						return currNode.data;
					}
					counter++;
					firstNode=false;
					return currNode.data;
				}else {
					if(firstNode==true) {
						currNode=hashTable.get(bucket);
						if(currNode.next==null) {
							firstNode=true;
							counter++;
							bucket++;
							return currNode.data;
						}else {
							firstNode=false;
							counter++;
							return currNode.data;
						}


					}else {
						currNode=currNode.next;
						if(currNode.next==null) {
							firstNode=true;
							bucket++;
							counter++;
							return currNode.data;
						}else {
							counter++;
							return currNode.data;
						}
					}

				}

			}



		};
	}

}
