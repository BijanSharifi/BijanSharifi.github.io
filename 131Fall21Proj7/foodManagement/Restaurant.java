package foodManagement;

/**
 *  The Restaurant has a name (String), a menu (list of Entrees), an inventory
 *  (list of Food), and an amount of cash on hand, measured in pennies (int)
 * 
 *  This class facilitates orders being placed, deliveries being made to the
 *  inventory, and entrees being added to the menu.
 */
public class Restaurant {

	/*
	 * STUDENTS:  YOU MAY NOT ADD ANY OTHER INSTANCE VARIABLES OR 
	 * STATIC VARIABLES TO THIS CLASS!
	 */
	private String name;
	private SortedListOfImmutables menu;       // A list of Entree objects	
	private SortedListOfImmutables inventory;  // A list of Food objects
	private int cash;

	/**
	 * Standard constructor.  The menu and the inventory are both initialized as 
	 * empty lists.  The name and cash amount are set to match the paramters.
	 * 
	 * @param nameIn name of the restaurant
	 * @param startingCash cash amount that the restaurant will have, measured
	 * in pennies
	 */
	public Restaurant(String nameIn, int startingCash) {
		name=nameIn;
		cash=startingCash;
		menu=new SortedListOfImmutables();
		inventory= new SortedListOfImmutables();
		
	}

	/**
	 * Getter for the name of the restaurant.
	 * 
	 * @return reference to the name of the restaurant
	 */
	public String getName() {
		return this.name; //Returns the name of restaurant
	}

	/**
	 * Getter for the menu.
	 * 
	 * @return a reference to a copy of the menu
	 */
	public SortedListOfImmutables getMenu() {
		return new SortedListOfImmutables(menu); // returns a menu
	}

	/**
	 * Adds an entree to the menu.
	 * 
	 * @param entreeToAdd reference to the entree to be added to the menu
	 */
	public void addEntree(Entree entreeToAdd) {
		menu.add(entreeToAdd); //uses add method from SortedListOfImmutables to add an entree to the menu

	}
	
	/**
	 * Getter for the inventory.
	 * 
	 * @return a reference to a copy of the inventory
	 */
	public SortedListOfImmutables getInventory() {
		SortedListOfImmutables newInventory= 
				new SortedListOfImmutables(inventory); //creates a new list of sorted immutables with the parameter inventory
		return newInventory;
	}

	/**
	 * Getter for the current amount of cash on hand
	 * 
	 * @return the current amount of cash, measured in pennies
	 */
	public int getCash() {
		return this.cash; //Returns the cash amount
	}

	/**
	 * Checks if the Food items contained in the specified Entree are 
	 * actually contained in the restaurant's inventory.
	 * 
	 * @param entree Entree that we are checking against the inventory
	 * @return true if the list of Food items contained in the Entree are
	 * all present in the inventory, false otherwise.
	 */
	
	/*Creates a new SortedListOfImmutables using the entree food list and then using the check
	 * availability method to see if the sortedListOfImmutables Food is in inventory
	 * 
	 */
	public boolean checkIfInInventory(Entree entree) {
		SortedListOfImmutables food= 
				new SortedListOfImmutables(entree.getFoodList()); 
		return inventory.checkAvailability(food);
	}

	/**
	 * Adds the specified list of food items to the inventory.  If the 
	 * total wholesale cost of all of the food items combined exceeds 
	 * the amount of cash on hand, then NONE of the food items are added 
	 * to the inventory.  If the amount of cash on hand is sufficient to
	 * pay for the shipment, then the amount of cash on hand is reduced by 
	 * the wholesale cost of the shipment.
	 * 
	 * @param list food items to be added to the inventory
	 * @return true if the food items are added; false if the food items are
	 * not added because their wholesale cost exceeds the current cash
	 * on hand
	 */
	
	/*Checks if the wholesale cost of the list that is being added to invetory. If the cost
	 * is greater than the cash amount then it returns false. But if there is enough cash it uses
	 * the add method and adds it to inventory and subtracts the cost of the item
	 */
	public boolean addShipmentToInventory(SortedListOfImmutables list) {
		if(list.getWholesaleCost()>this.cash) {
			return false;
		
		}else {
			inventory.add(list);
			this.cash= this.cash-list.getWholesaleCost();
			return true;
		}
	}

	/**
	 * Removes the food items contained in the specified Entree from the inventory.
	 * If the inventory does not contain all of the items required for this
	 * Entree, then NOTHING is removed from the inventory.  If the inventory contains
	 * all of the required items, then the amount of cash on hand is INCREASED by
	 * the retail value of the Entree.
	 *  
	 * @param entree Entree that has been ordered
	 * @return true if the food items are removed from the inventory; false
	 * if the food items were not removed because one or more required items
	 * were not contained in the inventory
	 */
	/*This method first checks the availability and if the entree is on the food list
	 * then the restuarant gets the cash amount of the item added to the cash but the 
	 * remove method is used to remove that entree and it is returned true.
	 * 
	 */
	public boolean placeOrder(Entree entree) {
		if(inventory.checkAvailability(entree.getFoodList())==true) {
			this.cash += entree.getRetailValue();
			inventory.remove(entree.getFoodList());
			return true;
			
		}else {
			return false;
		}
	}

	

}
