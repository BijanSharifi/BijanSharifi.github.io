package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	
	private ArrayList<Card>cards=new ArrayList<Card>();
	// Constructor that initializes a deck of cards in a certain order
	public Deck() {
		this.cards=new ArrayList<>();
		for(Suit a: Suit.values()) {
			for(Rank b: Rank.values()) {
				cards.add(new Card(b,a));
			}
				
			}
		}
	//Method that shuffles a deck of cards
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	//Method that removes the first card from the ArrayList of Cards and returns it
	public Card dealOneCard() {
		Card removed=cards.get(0);
		cards.remove(0);
		return removed;
		
	}
	
	}
	

