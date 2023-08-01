package poker;

public class Deck {

	private Card[] cards;
	
	/*Constructor that initializes a deck in the correct order
	 * Use a for loop to go through and assign each card
	 * with a suit and a value
	 */
	public Deck() {
		cards=new Card[52];
		int cardNum=0;
		int suitNum=0;
		int totalNumber=1;
		for (suitNum=0; suitNum<=3; suitNum++) {
			for (cardNum=1; cardNum<=13; cardNum++) {
				Card newCard= new Card(cardNum, suitNum);
				cards[totalNumber-1]=newCard;
				totalNumber++;
			}
		}

	}
		
		
	
	//Copy constructor for the Deck class
	 
	 
	public Deck(Deck other) {
		cards=new Card[other.getNumCards()];
		for (int i=0; i<cards.length; i++) {
			cards[i]=other.cards[i];
		}
	}
	/* Instance method that returns the position of
	 * the card in the array
	 */
	public Card getCardAt(int position) {
		int value=cards[position].getValue();
		int suite=cards[position].getSuit();
		Card newCard=new Card(value, suite);
		return newCard;
	}
	// Instance method that returns the length of the cards
	public int getNumCards() {
		return cards.length;
	}
	/* Instance method that shuffles the deck by creating two decks
	 * that are half the length of the full deck and assigns half
	 * of the cards to the top and the other half to the bottom
	 * and every other card is assinged to the top and the bottom
	 */
	public void shuffle() {
		Card [] topOfDeck;
		int length=cards.length;
		if (length%2==0) {
			topOfDeck= new Card[length/2]; 
			
		}else{
			topOfDeck= new Card[(length/2)+1];
			
			}
		for (int i=0; i<topOfDeck.length; i++) {
			topOfDeck[i]=cards[i];
		}
		
		Card [] bottomOfDeck=new Card[length/2];
	
		int topLength=topOfDeck.length;
		for (int i=0; i<bottomOfDeck.length; i++) {
			bottomOfDeck[i]=cards[topLength];
			topLength++;
			}
		int goToTop=0;
		int goToBottom=0;
		for (int i=0; i<cards.length; i++) {
			if ((i%2==0)) {
				cards[i]=topOfDeck[goToTop];
				goToTop++;
			}else {
				cards[i]=bottomOfDeck[goToBottom];
				goToBottom++;
			}
		}
		
		
		
		
	}
	/*Instance method that will cut off the deck at a given 
	 * position and it is similar to shuffle but the new deck
	 * is a position and the other part of the deck is the 
	 * remaining length
	 */
	public void cut(int position) {
		Card[] topOfDeck= new Card[position];
		for (int i=0; i<topOfDeck.length; i++) {
			topOfDeck[i]=cards[i];
		}
		Card[] bottomOfDeck= new Card
				[cards.length-topOfDeck.length];
		int cutPoint=position;
		for (int i=0; i<bottomOfDeck.length; i++) {
			bottomOfDeck[i]=cards[cutPoint];
			cutPoint++;
		}
		int topPoint=0;
		for (int i=0; i<cards.length; i++) {
			if(i<bottomOfDeck.length) {
				cards[i]=bottomOfDeck[i];
				
			}else{
				cards[i]=topOfDeck[topPoint];
				topPoint++;
			}
		}
		
		
		
		
	}
	
	/* Instance method that deals cards based on the parameter 
	 * numCards and the array is the size of the numCards
	 * and it deals that number to the array
	 */
	public Card[] deal(int numCards) {
		Card [] dealCard=new Card[numCards];
		for (int i=0; i<dealCard.length; i++) {
			dealCard[i]=cards[i];
		}
		Card [] cardsLeft=new Card
				[cards.length-dealCard.length];
		for (int i=0; i<cardsLeft.length; i++) {
			cardsLeft[i]= cards[numCards+i];
			
		}
		cards=cardsLeft;
		return dealCard;
	}
		
}
