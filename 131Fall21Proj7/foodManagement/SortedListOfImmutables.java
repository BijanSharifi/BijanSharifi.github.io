package foodManagement;

/**
 * A SortedListOfImmutables represents a sorted collection of immutable objects 
 * that implement the Listable interface.  
 * 
 * An array of references to Listable objects is used internally to represent 
 * the list.  
 * 
 * The items in the list are always kept in alphabetical order based on the 
 * names of the items.  When a new item is added into the list, it is inserted 
 * into the correct position so that the list stays ordered alphabetically
 * by name.
 */
public class SortedListOfImmutables {

	/*
	 * STUDENTS:  You may NOT add any other instance variables or
	 * static variables to this class!
	*/
	private Listable[] items;

	/**
	 * This constructor creates an empty list by creating an internal array
	 * of size 0.  (Note that this is NOT the same thing as setting the internal
	 * instance variable to null.) 
	 */
	public SortedListOfImmutables() {
		items=new Listable[0];
	}

	/**
	 *  Copy constructor.  The current object will become a copy of the
	 *  list that the parameter refers to.  
	 *  
	 *  The copy must be made in such a way that future changes to
	 *  either of these two lists will not affect the other. In other words, 
	 *  after this constructor runs, adding or removing things from one of 
	 *  the lists must not have any effect on the other list.
	 *  
	 *  @param other the list that is to be copied
	 */
	public SortedListOfImmutables(SortedListOfImmutables other) {
		items= new Listable[other.getSize()];
		for (int i=0; i<items.length; i++) {
			items[i]=other.items[i];
		}
		
	}

	/**
	 * Returns the number of items in the list.
	 * @return number of items in the list
	 */
	public int getSize() {
		return items.length;
		
	}
	
	/**
	 * Returns a reference to the item in the ith position in the list.  (Indexing
	 * is 0-based, so the first element is element 0).
	 * 
	 * @param i index of item requested
	 * @return reference to the ith item in the list
	 */
	public Listable get(int i) {
		return items[i];
	}
	
	/**
	 * Adds an item to the list.  This method assumes that the list is already
	 * sorted in alphabetical order based on the names of the items in the list.
	 * 
	 * The new item will be inserted into the list in the appropriate place so
	 * that the list will remain alphabetized by names.
	 * 
	 * In order to accomodate the new item, the internal array must be re-sized 
	 * so that it is one unit larger than it was before the call to this method.
	 *  
	 * @param itemToAdd refers to a Listable item to be added to this list
	 */
	/*initialize a boolean variable has added and then make a new Listable which is one size bigger
	 * than the current object. Then a for loop goes through and coppies  Then it has to deal with the 
	 * corner case that the item being added is the first thing on the list. If not there is a
	 * loop  through and compares the previous item to the next item and this is how it stays sorted. If they are 
	 * the same the item gets added at that particular index. Then to continue putting all the other items back in 
	 * order there is another loop that starts at the first index plus 1. This toggles the hasAdded to true. 
	 * If it is still false, then this is for the other corner case and for the last spot on the list. Finally,
	 * we make a copy and allias it.
	 * 
	 */
	public void add(Listable itemToAdd) {
		boolean hasAdded=false;
		Listable[] added= new Listable[items.length+1];
		
		for(int i=0; i<items.length; i++) {
			added[i]=items[i];
		}
		if(items.length==0) {
			added[0]=itemToAdd;
		}else {
			for(int first=0;first<items.length; first++ ) {
				if(itemToAdd.getName().compareTo(added[first].getName())<0 
						&& hasAdded==false ) {
					added[first]=itemToAdd;
					for(int second=first+1; second< added.length; second++) {
						added[second]=items[second-1];
					}
					hasAdded=true;
				
				}
				if(hasAdded==false) {
					added[items.length]=itemToAdd;
				}
			}
			
			}
		items= new Listable[added.length];
		for(int round3=0; round3<added.length; round3++) {
			items[round3]=added[round3];
			
		}
		items=added;
		
		
	}

	/**
	 * Adds an entire list of items to the current list, maintaining the 
	 * alphabetical ordering of the list by the names of the items.
	 * 
	 * @param listToAdd a list of items that are to be added to the current object
	 */
	/* A simple for loop which adds a list to a SortedListOfImmutables in which it goes through and adds
	 * the list
	 * 
	 */
	public void add(SortedListOfImmutables listToAdd) {
		for(int i=0; i<listToAdd.items.length; i++) {
			add(listToAdd.items[i]);
			
		}
	}
	
	/**
	 * Removes an item from the list.
	 * 
	 * If the list contains the same item that the parameter refers to, it will be 
	 * removed from the list.  
	 * 
	 * If the item appears in the list more than once, just one instance will be
	 * removed.
	 * 
	 * If the item does not appear on the list, then this method does nothing.
	 * 
	 * @param itemToRemove refers to the item that is to be removed from the list
	 */
	/*
	 * Goes through and finds the item to remove. Once the item is found, the loop breaks because
	 * there is no reason to keep going through it. Once remove is toggled to true, we create a new 
	 * listable that is one size smaller, because we removed something. Then we go through the array
	 * and find the specific item we need to remove and continue the loop when found. If the names are not the same
	 * we copy what is in items to our new array called removed and increment the counter. Then, like for add,
	 * we allias.
	 */
	public void remove(Listable itemToRemove) {
		boolean remove=false;
		for (int i=0; i<items.length; i++) {
			if(items[i].getName().equals(itemToRemove.getName())) {
				remove=true;
				break;
			}
			
		}
		if(remove==true) {
			Listable[] removed= new Listable[items.length-1];
			int counter=0;
			boolean hasRemoved=false;
			for(int i=0; i<items.length; i++) {
				if(hasRemoved==false && items[i].getName().
						equals(itemToRemove.getName())) {
					hasRemoved=true;
					continue;
				}else {
					removed[counter]=items[i];
					counter++;
				}
				
			}
			items=new Listable[removed.length];
			for(int i=0; i<items.length; i++) {
				items[i]=removed[i];
			}
		}
		
	}

	/**
	 * Removes an entire list of items from the current list.  Any items in the
	 * parameter that appear in the current list are removed; any items in the
	 * parameter that do not appear in the current list are ignored.
	 * 
	 * @param listToRemove list of items that are to be removed from this list
	 */
	/*For loop that does the same thing as the add method for SortedListOfImmutables, but uses remove method.
	 * 
	 */
	public void remove(SortedListOfImmutables listToRemove) {
		for(int i=0; i<listToRemove.items.length; i++) {
			remove(listToRemove.items[i]);
		}
	}

	/**
	 * Returns the sum of the wholesale costs of all items in the list.
	 * 
	 * @return sum of the wholesale costs of all items in the list
	 */
	/*Go through the entire array and have the total cost equal the item at that index, but we add to it each time
	 * we go through because we want the entire cost, not just of the most recent item.
	 * 
	 */
	public int getWholesaleCost() {
		int totalCost=0;
		for(int i=0; i<items.length; i++) {
			totalCost=totalCost + items[i].getWholesaleCost();
		}
		return totalCost;
	}

	/**
	 * Returns the sum of the retail values of all items in the list.
	 * 
	 * @return sum of the retail values of all items in the list
	 */
	/*The same as getWholeSaleCost
	 * 
	 */
	public int getRetailValue() {
		int retailValue=0;
		for(int i=0; i<items.length; i++) {
			retailValue= retailValue + items[i].getRetailValue();
		}
		return retailValue;
	}

	/**
	 * Checks to see if a particular item is in the list.
	 * 
	 * @param itemToFind item to look for
	 * @return true if the item is found in the list, false otherwise
	 */
	/*A for loop that goes through the array and if the item at that index is equal to the itemToFind
	 * we can say it is in the list and can return true. If not, it returns false.
	 * 
	 */
	public boolean checkAvailability(Listable itemToFind) {
		boolean inList=false;
		for(int i=0; i<items.length; i++) {
			if(items[i].equals(itemToFind)) {
				inList=true;
			}
		}
		return inList;
		
	}

	/**
	 * Checks if a list of items is contained in the current list.
	 * If more than one copy of a particular element appear in the 
	 * parameter, then the current list must contain at least that many as well.
	 * 
	 * @param listToCheck list of items that may or may not appear in the
	 * current list
	 * @return true if all items in the parameter are contained in the current 
	 * list. (If more than one copy of a particular element appear in the
	 * parameter, then the current list must contain at least that many as well.)
	 */
	/*Create a new SortedListOfImmutables that has the same size. Use a for loop to go through
	 * the entire loop, and if nothing is found that is the same, we break the loop and can say there is 
	 * not that item avaliable. If it passes then avaliableInList must be true so we set it to true, and we 
	 * remove the sorted item.
	 * 
	 */
	public boolean checkAvailability(SortedListOfImmutables listToCheck) {
		boolean avaliableInList=false;
		SortedListOfImmutables newSorted= new SortedListOfImmutables(this);
		for(int i=0; i<listToCheck.getSize(); i++) {
			if(newSorted.checkAvailability(listToCheck.items[i])==false) {
				avaliableInList=false;
				break;
			}else {
				avaliableInList=true;
				for(int a=0; a<newSorted.getSize(); a++) {
					if(listToCheck.items[i].equals(newSorted.items[a])) {
						newSorted.remove(newSorted.items[a]);
						break;
						
					}
				}
				
			
			}
			
		}
		return avaliableInList;
		
			
			
		}
	

	/*
	 * We'll give you this one for free.  Do not modify this method or you
	 * will fail our tests!
	 */
	public String toString() {
		String retValue = "[ ";
		for (int i = 0; i < items.length; i++) {
			if (i != 0) {
				retValue += ", ";
			}
			retValue += items[i];
		}
		retValue += " ]";
		return retValue;
	}
}
