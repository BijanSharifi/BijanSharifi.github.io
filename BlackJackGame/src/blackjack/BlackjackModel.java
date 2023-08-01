package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {
	private ArrayList<Card>dealerCards;
	private ArrayList<Card>playerCards;
	private Deck deck;
	/*Method which returns the dealerCards by making a copy
	 * of the dealerCards and to return it
	 */
	public ArrayList<Card>getDealerCards(){
		return new ArrayList<Card>(dealerCards);

	}
	/* Method which returns the playerCards by making a copy
	 * of the playerCards and return it
	 */
	public ArrayList<Card>getPlayerCards(){
		return new ArrayList<Card>(playerCards);
	}
	/*Takes in an ArrayList of cards and sets that as
	 * the dealerCards of a new ArrayList
	 */
	public void setDealerCards(ArrayList<Card>cards) {
		dealerCards=new ArrayList<>(cards);
	}

	/*Takes in an ArrayList of cards and sets that as
	 * the playerCards of a new ArrayList
	 */
	public void setPlayerCards(ArrayList<Card>cards) {
		playerCards=new ArrayList<>(cards);

	}
	
	//Method that instantiates a new deck and then shuffles the deck
	 
	public void createAndShuffleDeck(Random random) {
		deck=new Deck();
		deck.shuffle(random);
	}
	
	/*Method which instantiates the dealerCards with an ArrayList of 
	 * cards and adds two cards to the dealerCards
	 */
	public void initialDealerCards() {
		dealerCards=new ArrayList<Card>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}
	/*Method which instantiates the playerCards with an ArrayList of
	 * cards and adds two cards to the playerCards
	 */
	public void initialPlayerCards() {
		playerCards=new ArrayList<Card>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}
	
	//Adds an additional card to the dealerCards 
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}
	//Adds an additional card to the playerCards
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	/*First find the total value of the cards in a hand by counting the Ace as a 
	 * value of one and not 11. Use a for-each loop to do this, then use another for-each
	 * loop to see if there is an ace in the hand. If there is no Ace in the hand then 
	 * use a for-each loop to get the value and make a new ArrayList of Integers and add
	 * the value of the cards to the ArrayList. If it does contain an Ace but the true 
	 * total value is less than or equal to 11 we get the value and then add 10 to the value
	 * and then add both of them in order to the ArrayList of Integers. If the true value
	 * is greater than 11 you just return that value because if you were to count the Ace as 10
	 * it would bust so you only return one value
	 */
	
	public static ArrayList<Integer>possibleHandValues(ArrayList<Card>hand){
		
		int trueTotalValue=0;
		for(Card card: hand) {
			trueTotalValue+=card.getRank().getValue();
			
			
		}
		boolean containsAce=false;
		for(Card card: hand) {
			if(card.getRank()==Rank.ACE) {
				containsAce=true;
				break;
			}
		}
		if(containsAce==false) {
			ArrayList<Integer>intList=new ArrayList<>();
			int totalValue=0;
			for(Card card: hand) {
				totalValue+=card.getRank().getValue();
			}
			intList.add(totalValue);
			return intList;
		}else if(trueTotalValue<=11) {
			ArrayList<Integer>intList=new ArrayList<>();
			int totalValue=0;
			for(Card card: hand) {
			totalValue+=card.getRank().getValue();
			
			}
			int countAceTen=totalValue+10;
			intList.add(totalValue);
			intList.add(countAceTen);
			return intList;
			
		}else {
			ArrayList<Integer>intList=new ArrayList<>();
			int totalValue=0;
			for(Card card: hand) {
				totalValue+=card.getRank().getValue();
			}
			intList.add(totalValue);
			return intList;
		}


	}
	/*Calls the possibleHand values on the parameter hand and stores those values 
	 * in an ArrayList of Integers and we get the max value from this possibleHand by
	 * taking the last index of the ArrayList. If the hand is less than size two or is null
	 * it returns Insufficient Cards and if the possibleHand contains blackJack(21) and 
	 * the hand size is two it returns natural blackJack. If the biggerValue is greater than 21
	 * the hand busts. If none of these happen it is a "normal" hand.
	 */
	public static HandAssessment assessHand(ArrayList<Card>hand) {
		ArrayList<Integer>possibleHand=
				new ArrayList<>(possibleHandValues(hand));
		int blackJack=21;
		
		int biggerValue=possibleHand.get(possibleHand.size()-1);
		
		if(hand==null||hand.size()<2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		}else if(possibleHand.contains(blackJack) && hand.size()==2) {
			return HandAssessment.NATURAL_BLACKJACK;
		}else if(biggerValue>blackJack) {
			return HandAssessment.BUST;
		}else {
			return HandAssessment.NORMAL;
		}
	}
	/*Calls the possibleHand values on the dealerCards and playerCards and stores these values in
	 * an ArrayList of Integers. Does the same thing as the HandAssessment method in which we store
	 * the bigger value in the ArrayList in the dealerValue int and playerValue int. Then we go through
	 * the different scenarios. 
	 */
	public GameResult gameAssessment() {
		ArrayList<Integer>dealerHandPossibleValues=
				new ArrayList<>(possibleHandValues(dealerCards));
		ArrayList<Integer>playerHandPossibleValues=
				new ArrayList<>(possibleHandValues(playerCards));
		int dealerValue=dealerHandPossibleValues
				.get(dealerHandPossibleValues.size()-1);
		int playerValue=playerHandPossibleValues
				.get(playerHandPossibleValues.size()-1);
		if(assessHand(playerCards)==HandAssessment.
				NATURAL_BLACKJACK &&
				!(assessHand(dealerCards)==HandAssessment.
				NATURAL_BLACKJACK)){
					return GameResult.NATURAL_BLACKJACK;
				}else if(assessHand(playerCards)==HandAssessment.
						NATURAL_BLACKJACK
						&& assessHand(dealerCards)==HandAssessment.
						NATURAL_BLACKJACK) {
					return GameResult.PLAYER_WON;
				}else if(assessHand(playerCards)==HandAssessment.BUST) {
					return GameResult.PLAYER_LOST;
				}else if(assessHand(dealerCards)==HandAssessment.BUST) {
					return GameResult.PLAYER_WON;
				}else if(dealerValue>playerValue) {
					return GameResult.PLAYER_LOST;
				}else if(playerValue>dealerValue) {
					return GameResult.PLAYER_WON;
				}else {
					return GameResult.PUSH;
				}
		
	}
	/* Call possibleHandValues on dealerCards and stores it in an ArrayList of Integers.
	 * Then we check if there is an Ace in the dealerCards. If the dealerValue is less than or
	 * equal to 16 the dealer must keep taking cards so we return true. If it is 18 or greater
	 * the dealer must stop taking cards. If the value is 17 and the hand contains an Ace the dealer
	 * must keep taking cards. If it is 17 and it doesn't contain an Ace it returns false.
	 * 
	 */
	public boolean dealerShouldTakeCard() {
	ArrayList<Integer>dealerHandPossibleValues=
			BlackjackModel.possibleHandValues(dealerCards);
	int dealerValue=dealerHandPossibleValues.
			get(dealerHandPossibleValues.size()-1);
	boolean containsAce=false;
	for(Card card: dealerCards) {
		if(card.getRank()==Rank.ACE) {
			containsAce=true;
			break;
		}
	}  
	if(dealerValue<=16) {
		return true;
	}else if(dealerValue>=18) {
		return false;
	}else if(containsAce==true && dealerValue==17) {
		return true;
	}else {
		return false;
	}
	
	
	
}

}
