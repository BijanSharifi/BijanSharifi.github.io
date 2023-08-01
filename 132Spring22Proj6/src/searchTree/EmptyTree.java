package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();

	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// Nothing to do
	}
	//Nothing to search for
	public V search(K key) {
		return null;
	}
	//Tree is no longer empty, and contains only one tree which are assigned to the key and value
	//The left and right are both the current object
	public NonEmptyTree<K, V> insert(K key, V value) {
		return new NonEmptyTree<K,V>(key, value, this, this);
	}
	//Nothing to delete
	public Tree<K, V> delete(K key) {
		return this;
	}
	//Empty tree so no max
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
		
	}
	//Empty tree so no min
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}
	//Empty tree will have size 0
	public int size() {
		return 0;
	}
	//No keys to add
	public void addKeysToCollection(Collection<K> c) {
		
	}
	//Nothing to add to subTree
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return this;
	}
}