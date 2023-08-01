package searchTree;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	
	private Tree<K,V> left, right;
	private K key;
	private V value;
	
	
/*
 * Constructor which assigns initializes a NonEmpty tree with a key and a value, as well as
 * a left and right reference
 */
	
	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) { 
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}
/*
 * Method which returns the value of a key. If the key compared to the current key
 * is the same the result is 0 and it returns the value of the key. If the result is smaller
 * than the key we recursively call search on the left, and do the same for the right.
 */
	public V search(K key) {
		int result=key.compareTo(this.key);
		if(result==0) {
			return this.value;
			
		}else if(result<0) {
			return left.search(key);
			
		}else {
			return right.search(key);
		}
		
	}
	/*
	 * Compare the current key to the parameter key. If the compareTo result is 0, that means
	 * that key is already in the Tree and we just need to update the value.
	 * If the result is smaller than we assign left to the recursive call of insert and return
	 * a reference current object. If it is bigger we assign the right to  the recursive call of insert
	 * and return a reference current object.
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		int result=key.compareTo(this.key);
		if(result==0) {
			
			this.value=value;
			return this;
			
			
		}else if(result<0) {
			left=left.insert(key, value);
			return this;
		}else {
			right=right.insert(key, value);
			return this;
		}
	}
	
	/*
	 * If the result is 0, we test out multiple cases. The first case is if the tree has only 
	 * one child or no children. We first try the left max and if that throws an exception we
	 * return the right tree. If that is fine, we keep on going and try the rightMax and if 
	 * that throws an exception we return the left. This covers the cases if there was one child.
	 * After this point, we know there are 2 children from the thing we are trying to remove. 
	 * We set the value of the current key to the max key in the left tree. We then set the key of 
	 * the current key to the leftMax. Now we have copied and replaced the key we wanted to delete and
	 * have put the max key and its value from the left tree. Now we need to get rid of the key we copied.
	 * We do this by calling delete again. Now we know that the max on the left could only have one child,
	 * because if it had something to the right of it that would have to be greater than it. So
	 * the delete is handled in the first part of this method. Then a reference to the current
	 * object is returned. If the result is not the same, left is set to the recursive call of delete
	 * for the left side, and the same for greater than and right.
	 */
	public Tree<K, V> delete(K key) {
		int result=key.compareTo(this.key);
		if(result==0) {
			
		
			
		K leftMax;
		K rightMax;
			
		try {
			leftMax=left.max();
		}catch(TreeIsEmptyException e) {
			return right;
		}
		try {
			rightMax=right.max();
		}catch(TreeIsEmptyException e) {
			return left;
		}
		this.value=this.search(leftMax);
		this.key=leftMax;
		left=left.delete(leftMax);
		return this;
		
		
		
		
		
		
	
			
			  
			
			
		}else if(result<0) {
			left=left.delete(key);
			return this;
		}else {
			right=right.delete(key);
			return this;
		}
		
		
	}
	/*
	 * Basically, this method calls itself until there is an exception thrown and we know we have hit 
	 * the empty tree. This means we can return the current key.
	 */

	public K max() {
		try {
			return right.max();
		}catch(TreeIsEmptyException e) {
			return this.key;
		}
	}
	/*
	 * This method calls itself until there is an exception thrown and we know we have hit 
	 * the empty tree to the left side. This means we can return the current key.
	 */

	public K min() {
		try {
			return left.min();
		}catch(TreeIsEmptyException e) {
			return this.key;
		}
	}
/*
 * Recursively calls leftSize and rightSize and adds 1 to count the current key.
 */
	public int size() {
	
		
		int leftSize=left.size();
		int rightSize=right.size();
		return 1+leftSize+rightSize;
		
				
	}
/*
 * Adds all the keys from the left tree to the collection, returns the current key, and then
 * adds all the keys on the right.
 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(key);
		right.addKeysToCollection(c);
	
		
	
		
	
	
	}
	/*
	 * If the key is smaller than the fromKey, we return the right tree, recursively calling the 
	 * subTree. If the key compared to the toKey is bigger, we return left tree, recursively calling
	 * the subTree. If the keys are the same, we create a new nonEmptyTree and assign the 
	 * left to the left subtree and the right to the right subtree and return the tree.
	 * 
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		if(key.compareTo(fromKey) < 0) {
			return right.subTree(fromKey, toKey);
		} else if (key.compareTo(toKey) > 0) {
			return left.subTree(fromKey, toKey);
		} else { 
			NonEmptyTree<K,V> newTree = new NonEmptyTree<K,V>(key, value, left, right);
			newTree.left = left.subTree(fromKey, toKey);
			newTree.right = right.subTree(fromKey, toKey);
			return newTree;
		}
	}
		
		
	
		
	
}