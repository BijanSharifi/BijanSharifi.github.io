package poker;
public class PokerHandEvaluator {
	/* Checks if your hand has a pair by looping through the deck
	 * twice and checking if the value is the same but the actual card
	 * is not the same
	 */
	public static boolean hasPair(Card[] cards) {
		boolean match=false;
		for (int firstRound=0; firstRound<cards.length; firstRound++ ) {
			for (int secondRound=0; secondRound<cards.length; secondRound++) {
				if (cards[firstRound].getValue()== 
						cards[secondRound].getValue() && firstRound!=secondRound ) {
					match=true;
				}
			}
		}
		return match;

	}
	public static boolean hasTwoPair(Card[] cards) {

		
		/* Instance method that checks if there are two pairs of cards
		 * which loops through a total of 4 times and makes sure there are two 
		 * sets of two cards which are the same value, but make sure those values are not
		 * the same because this is not 4 of a kind. For each loop we toggle the match
		 * to true if there is a match
		 */
		int value=0;
		boolean match=false;
		boolean match1=false;
		boolean match2=false;
		for (int card1=0; card1<cards.length-1; card1++) {
			for (int card2=card1+1; card2<cards.length; card2++){
				if (cards[card1].getValue()==cards[card2].getValue()) {
					match1=true;
					value=cards[card2].getValue();
				}
				
				
			}
		}
		for(int card3=0; card3<cards.length-1; card3++) {
			for (int card4=card3+1; card4<cards.length; card4++) {
				if(value != cards[card3].getValue() 
						&& cards[card3].getValue()== cards[card4].getValue()) {
					match2=true;
				}
			}
			
		}
		if (match1==true && match2==true) {
			match=true;
		}
		return match;
		
		

	}
	/* Checks if their is a three of a kind in the hand by using
	 * three for loops that makes sure the value is the same but the 
	 * cards cannot be the same card
	 */
	public static boolean hasThreeOfAKind(Card[] cards) {
		boolean threeOfAKind=false;
		for (int firstRound=0; firstRound<cards.length-1; firstRound++) {
			for (int secondRound=firstRound+1; 
					secondRound<cards.length; secondRound++ ) {
				for (int thirdRound=secondRound+1; 
						thirdRound<cards.length; thirdRound++) {
					if (cards[firstRound].getValue()
							== cards[secondRound].getValue() && 
							cards[thirdRound].getValue()
							== cards[secondRound].getValue()) {
						threeOfAKind=true;
					}

				}
			}
		}
		return threeOfAKind;

	}
	/* Uses 5 nested for loops and each of them has a limit of 5
	 * in which none of the cards can be the same and use an if statement
	 * and if the cards are equal to the card before it plus a value of one, it is
	 * true. Also, we have to account for a broadway straight, which is 10, Jack, Queen, King
	 * and Ace, which we account for in the second if statement.
	 * 
	 */

	public static boolean hasStraight(Card [] cards) {
		boolean straight=false;
		for (int firstRound=0; firstRound<5; firstRound++) {
			for (int secondRound=0; 
					secondRound<5; secondRound++) {
				for (int thirdRound=0; 
						thirdRound<5; thirdRound++) {
					for (int fourthRound=0;
							fourthRound<5; fourthRound++) {
						for (int fifthRound=0; 
								fifthRound<5; fifthRound++) {
							if (firstRound !=secondRound
									&& firstRound !=thirdRound
									&& firstRound != fourthRound
									&& firstRound != fifthRound
									&& secondRound != thirdRound
									&& secondRound != fourthRound
									&& secondRound != fifthRound
									&& thirdRound != fourthRound
									&& thirdRound != fifthRound
									&& fourthRound != fifthRound){
								if (cards[secondRound].getValue()
										==cards[firstRound].getValue()+1
										&& cards[thirdRound].getValue()
										==cards[secondRound].getValue()+1
										&& cards[fourthRound].getValue()
										==cards[thirdRound].getValue()+1
										&& cards[fifthRound].getValue()
										==cards[fourthRound].getValue()+1) {
									straight=true;
								}
									if (cards[firstRound].getValue()==10 
											&& cards[secondRound].getValue()==11
											&& cards[thirdRound].getValue()==12
											&& cards[fourthRound].getValue()==13
											&& cards[fifthRound].getValue()==1) {
										straight=true;
										
									}
								}
									
							}
						}
					}
				}
			}
		return straight;
		}


	
	
	/* Checks if there is a flush by looping through
	 * and if the suits are the same for the cards it is 
	 * a flush
	 */
	public static boolean hasFlush(Card[] cards) {
		for (int i=0; i<4; i++) {
			int set1=cards[i].getSuit();
			int set2=cards[1+i].getSuit();
			if (set1 != set2) {
				return false;
			}
			
		}
		return true;
	}
	/* Checks if the hand has the full house by looping through
	 * twice and if the value is the same and the cards are not the same
	 * and if the counter hits 8 that means there has to be a full house
	 * and it returns true
	 */
	public static boolean hasFullHouse(Card[] cards) {
		int cardNum=0;
		for (int round1=0; round1<5; round1++) {
			for (int round2=0; round2< cards.length; round2++) {
				if (round1 != round2 && 
						cards[round2].getValue()==cards[round1].getValue()) {
					cardNum++;
				}
			}
			if (cardNum==8) {
				return true;
			}
			}
		return false;
		}
		
	
	/* Uses 4 nested for loops and checks if the values are all the same
	 * but makes sure none of the cards are similar
	 */
	public static boolean hasFourOfAKind(Card[] cards) {
		boolean fourOfAKind=false;
		for (int firstRound=0; firstRound<cards.length-1; firstRound++) {
			for (int secondRound=firstRound+1; 
					secondRound<cards.length; secondRound++ ) {
				for (int thirdRound=secondRound+1; 
						thirdRound<cards.length; thirdRound++) {
					for (int fourthRound=thirdRound+1; 
							fourthRound<cards.length; fourthRound++) {
						if (cards[firstRound].getValue()
								==cards[secondRound].getValue()
								&& cards[thirdRound].getValue()
								==cards[secondRound].getValue()
								&& cards[fourthRound].getValue()
								==cards[thirdRound].getValue()) {
							fourOfAKind=true;
						}
					}


				}
			}
		}
		return fourOfAKind;
	}
	/* If the straight is true and the flush is true, the straight flush is there
	 * 
	 */
	public static boolean hasStraightFlush(Card[] cards) {
		if(hasStraight(cards) && hasFlush(cards)) {
			return true;
			
		}else {
			return false;
		}
		
	}
}

