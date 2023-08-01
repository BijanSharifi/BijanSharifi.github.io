import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * <P>
 * The HeavyBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * The HeavyBag class provides Bag semantics: it represents a collection with
 * duplicates. The "Heavy" part of the class name comes from the fact that the
 * class needs to efficiently handle the case where the bag contains 100,000,000
 * copies of a particular item (e.g., don't store 100,000,000 references to the
 * item).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a heavy bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the HeavyBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll.  (We will not be over-riding those).
 * All other methods defined in
 * the Collection interface will be implemented here.
 * </P>
 */
public class HeavyBag<T> extends AbstractCollection<T> implements Serializable {

    /* Leave this here!  (We need it for our testing) */
	private static final long serialVersionUID = 1L;

	
	/* Create whatever instance variables you think are good choices */
	
	private Map<T, Integer> bag;
	
	
	
	
	
	
	
	
	/**
     * Initialize a new, empty HeavyBag
     */
    public HeavyBag() {
    	bag=new HashMap<T, Integer>();
    }

    /**
     * Adds an instance of o to the Bag
     * 
     * @return always returns true, since added an element to a bag always
     *         changes it
     * 
     */
    @Override
    public boolean add(T o) {
    	if(!bag.containsKey(o)) {
    		bag.put(o, 1);
    		return true;
    	}else {
    		bag.put(o, bag.get(o)+1);
    		return true;
    	}
    }

    /**
     * Adds multiple instances of o to the Bag.  If count is 
     * less than 0 or count is greater than 1 billion, throws
     * an IllegalArgumentException.
     * 
     * @param o the element to add
     * @param count the number of instances of o to add
     * @return true, since addMany always modifies
     * the HeavyBag.
     */
    public boolean addMany(T o, int count) {
    	if(count<0 || count>1000000000) {
    		throw new IllegalArgumentException();
    	}
    	if(!bag.containsKey(o)) {
    		bag.put(o, count);
    		return true;
    	}else {
    		bag.put(o, bag.get(o)+count);
    		return true;
    	}
    	
    }
    
    /**
     * Generate a String representation of the HeavyBag. This will be useful for
     * your own debugging purposes, but will not be tested other than to ensure that
     * it does return a String and that two different HeavyBags return two
     * different Strings.
     */
    @Override
    public String toString() {
    	
    	
    	return bag.toString();
    	
    }

    /**
     * Tests if two HeavyBags are equal. Two HeavyBags are considered equal if they
     * contain the same number of copies of the same elements.
     * Comparing a HeavyBag to an instance of
     * any other class should return false;
     */
    @Override
    public boolean equals(Object o) {
    	if(o==this) {
    		return true;
    	}
    	if(!(o instanceof HeavyBag)) {
    		return false;
    	}
    	HeavyBag<T> equalBag=(HeavyBag<T>)o;
    	boolean containsValues=false;
    	if(!this.containsAll(equalBag)) {
    		return false;
    	}else {
    		int counter=0;
    		for(T curr: this.uniqueElements()) {
    			if(this.bag.get(curr)==equalBag.bag.get(curr)) {
    				counter++;
    			}
    		}
    		if(uniqueElements().size()==counter) {
    			containsValues=true;
    		}
    	}
    	return containsValues && this.size()==equalBag.size() && this.hashCode()==equalBag.hashCode();
    	
    	
    	
    	
    	
    }

    /**
     * Return a hashCode that fulfills the requirements for hashCode (such as
     * any two equal HeavyBags must have the same hashCode) as well as desired
     * properties (two unequal HeavyBags will generally, but not always, have
     * unequal hashCodes).
     */
    @Override
    public int hashCode() {
    	return bag.hashCode();
    }

    /**
     * <P>
     * Returns an iterator over the elements in a heavy bag. Note that if a
     * Heavy bag contains 3 a's, then the iterator must iterate over 3 a's
     * individually.
     * </P>
     */
    @Override
    public Iterator<T> iterator() {
    	return new Iterator<T>() {
    		private int count;
    		private int size=size();
    		private Set<T>keySet=bag.keySet();
    		private int num=0;
    		private Iterator<T> keys=keySet.iterator();
    		private T curr=null;
    		
    		
    		
    		

			@Override
			public boolean hasNext() {
				return count<size;
				
			}

			@Override
			public T next() {
				
				if(num==0) {
					curr=keys.next();
					num=bag.get(curr);
				}
				if(num>0) {
					num--;
				}
				
				count++;
				return curr;
				
				
				
				
				
				
				
				
				
			}
			
			@Override
			public void remove() {
				if(bag.get(curr)==1) {
					bag.remove(curr);
					size--;
					num=0;
					return;
				}else {
					bag.put(curr, bag.get(curr)-1);
					num--;
					size--;
					return;
				}
				
				
			}
    		
    	};
    	
    }

    /**
     * return a Set of the elements in the Bag (since the returned value is a
     * set, it can contain no duplicates. It will contain one value for each 
     * UNIQUE value in the Bag).
     * 
     * @return A set of elements in the Bag
     */
    public Set<T> uniqueElements() {
    	return bag.keySet();
    }

    /**
     * Return the number of instances of a particular object in the bag. Return
     * 0 if it doesn't exist at all.
     * 
     * @param o
     *            object of interest
     * @return number of times that object occurs in the Bag
     */
    public int getCount(Object o) {
    	if(!bag.containsKey(o)) {
    		return 0;
    	}else {
    		return bag.get(o);
    	}
    }

    /**
     * Given a random number generator, randomly choose an element from the Bag
     * according to the distribution of objects in the Bag (e.g., if a Bag
     * contains 7 a's and 3 b's, then 70% of the time choose should return an a,
     * and 30% of the time it should return a b.
     * 
     * This operation can take time proportional to the number of unique objects
     * in the Bag, but no more.
     * 
     * This operation should not affect the Bag.
     * 
     * @param r
     *            Random number generator
     * @return randomly chosen element
     */
    public T choose(Random r) {
    	int counter=0;
    	T randomElement=null;
    	int index=r.nextInt(this.size());
    	for(T curr: bag.keySet()) {
    		counter+=bag.get(curr);
    		if(counter>index) {
    			randomElement=curr;
    			return randomElement;
    		}
    	}
    	return randomElement;
    	
    }

    /**
     * Returns true if the Bag contains one or more instances of o
     */
    @Override
    public boolean contains(Object o) {
    	if(bag.containsKey(o)) {
    		return true;
    	}else {
    		return false;
    	}
    }


    /**
     * Decrements the number of instances of o in the Bag.
     * 
     * @return return true if and only if at least one instance of o exists in
     *         the Bag and was removed.
     */
    @Override
    public boolean remove(Object o) {
    	if(!bag.containsKey(o)) {
    		return false;
    	}
    	if(bag.get(o)==1) {
    		bag.remove(o);
    		return true;
    	}else {
    		bag.put((T)o, bag.get(o)-1);
    		return true;
    		
    	
    	}
    }

    /**
     * Total number of instances of any object in the Bag (counting duplicates)
     */
    @Override
    public int size() {
    	int size=0;
    	for(T key: bag.keySet()) {
    		size+=bag.get(key);
    		
    	}
    	return size;
    }
}