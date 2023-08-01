package tests;

import deckOfCards.*;
import blackjack.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {

	@Test
	public void testDeckConstructorAndDealOneCard() {
		Deck deck = new Deck();
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int valueCounter = 0; valueCounter < 13; valueCounter++) {
				Card card = deck.dealOneCard();
				assertEquals(card.getSuit().ordinal(), suitCounter);
				assertEquals(card.getRank().ordinal(), valueCounter);
			}
		}
	}
	
	/* This test will pass only if an IndexOutOfBoundsException is thrown */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testDeckSize() {
		Deck deck = new Deck();
		for (int i = 0; i < 53; i++) {  // one too many -- should throw exception
			deck.dealOneCard();
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck deck = new Deck();
		Random random = new Random(1234);
		deck.shuffle(random);
		assertEquals(new Card(Rank.KING, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.TEN, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.SPADES), deck.dealOneCard());
		for (int i = 0; i < 20; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.SIX, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.FIVE, Suit.CLUBS), deck.dealOneCard());
		for (int i = 0; i < 24; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.EIGHT, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.HEARTS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.CLUBS), deck.dealOneCard());
	}
	
	@Test
	public void testGameBasics() {
		Random random = new Random(3723);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards();
		game.initialDealerCards();
		game.playerTakeCard();
		game.dealerTakeCard();
		ArrayList<Card> playerCards = game.getPlayerCards();
		ArrayList<Card> dealerCards = game.getDealerCards();
		assertTrue(playerCards.get(0).equals(new Card(Rank.QUEEN, Suit.HEARTS)));
		assertTrue(playerCards.get(1).equals(new Card(Rank.SIX, Suit.DIAMONDS)));
		assertTrue(playerCards.get(2).equals(new Card(Rank.EIGHT, Suit.HEARTS)));
		assertTrue(dealerCards.get(0).equals(new Card(Rank.THREE, Suit.CLUBS)));
		assertTrue(dealerCards.get(1).equals(new Card(Rank.NINE, Suit.SPADES)));
		assertTrue(dealerCards.get(2).equals(new Card(Rank.FIVE, Suit.CLUBS)));		
	}
	
	@Test
	public void testPossibleHand() {
		BlackjackModel game=new BlackjackModel();
		ArrayList<Card>hand=new ArrayList<>();
		hand.add(new Card(Rank.ACE, Suit.CLUBS));
		hand.add(new Card(Rank.EIGHT, Suit.HEARTS));
		hand.add(new Card(Rank.ACE, Suit.DIAMONDS));
		ArrayList<Integer>values=new ArrayList<>(game.possibleHandValues(hand));
		System.out.print(values);
		
		BlackjackModel game1=new BlackjackModel();
		ArrayList<Card>hand1=new ArrayList<>();
		hand1.add(new Card(Rank.ACE, Suit.CLUBS));
		hand1.add(new Card(Rank.ACE, Suit.HEARTS));
		hand1.add(new Card(Rank.ACE, Suit.SPADES));
		ArrayList<Integer>values1=new ArrayList<>(game1.possibleHandValues(hand1));
		System.out.print(values1);
		
		BlackjackModel game2=new BlackjackModel();
		ArrayList<Card>hand2=new ArrayList<>();
		hand2.add(new Card(Rank.KING, Suit.DIAMONDS));
		hand2.add(new Card(Rank.TEN, Suit.SPADES));
		hand2.add(new Card(Rank.ACE, Suit.HEARTS));
		ArrayList<Integer>values2=new ArrayList<>(game2.possibleHandValues(hand2));
		System.out.print(values2);
		
		
		BlackjackModel game3=new BlackjackModel();
		ArrayList<Card>hand3=new ArrayList<>();
		hand3.add(new Card(Rank.FIVE, Suit.HEARTS));
		hand3.add(new Card(Rank.SEVEN, Suit.CLUBS));
		hand3.add(new Card(Rank.NINE, Suit.SPADES));
		ArrayList<Integer>values3=new ArrayList<>(game3.possibleHandValues(hand3));
		System.out.print(values3);
		
		BlackjackModel game4=new BlackjackModel();
		ArrayList<Card>hand4=new ArrayList<>();
		hand4.add(new Card(Rank.FOUR, Suit.HEARTS));
		hand4.add(new Card(Rank.NINE, Suit.HEARTS));
		hand4.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
		ArrayList<Integer>values4=new ArrayList<>(game4.possibleHandValues(hand4));
		System.out.print(values4);
		
		BlackjackModel game5=new BlackjackModel();
		ArrayList<Card>hand5=new ArrayList<>();
		hand5.add(new Card(Rank.TEN, Suit.DIAMONDS));
		hand5.add(new Card(Rank.EIGHT, Suit.HEARTS));
		hand5.add(new Card(Rank.SEVEN, Suit.SPADES));
		ArrayList<Integer>values5=new ArrayList<>(game5.possibleHandValues(hand5));
		System.out.print(values5);
		
		
	
	}
	
	@Test
	public void testGetLastInt() {
		BlackjackModel game=new BlackjackModel();
		ArrayList<Card>hand=new ArrayList<>();
		hand.add(new Card(Rank.TEN, Suit.DIAMONDS));
		hand.add(new Card(Rank.ACE, Suit.HEARTS));
		ArrayList<Integer>values=new ArrayList<>(game.possibleHandValues(hand));
		int maxValue=values.get(values.size()-1);
		assertTrue(maxValue==21);
		
		
		BlackjackModel game1=new BlackjackModel();
		ArrayList<Card>hand1=new ArrayList<>();
		hand1.add(new Card(Rank.ACE, Suit.SPADES));
		hand1.add(new Card(Rank.FOUR, Suit.DIAMONDS));
		hand1.add(new Card(Rank.TEN, Suit.DIAMONDS));
		ArrayList<Integer>values1=new ArrayList<>(game1.possibleHandValues(hand1));
		int maxValue1=values1.get(values1.size()-1);
		assertTrue(maxValue1==15);
	}
	
	
	
	@Test
	public void testAssessHand() {
		BlackjackModel game=new BlackjackModel();
		
		ArrayList<Card>hand=new ArrayList<>();
		hand.add(new Card(Rank.ACE, Suit.DIAMONDS));
		assertTrue(game.assessHand(hand)==HandAssessment.INSUFFICIENT_CARDS);
		
		BlackjackModel game1=new BlackjackModel();
		ArrayList<Card>hand1=new ArrayList<>();
		hand1.add(new Card(Rank.ACE, Suit.SPADES));
		hand1.add(new Card(Rank.QUEEN, Suit.HEARTS));
		assertTrue(game1.assessHand(hand1)==HandAssessment.NATURAL_BLACKJACK);
		
		
	}
	
	
	
	
	
	@Test
	public void testAssessGame() {
		
		BlackjackModel game= new BlackjackModel();
		ArrayList<Card> playerCards=new ArrayList<>();
		ArrayList<Card> dealerCards=new ArrayList<>();
		playerCards.add(new Card(Rank.ACE, Suit.SPADES));
		playerCards.add(new Card(Rank.JACK, Suit.HEARTS));
		dealerCards.add(new Card(Rank.TWO, Suit.SPADES));
		dealerCards.add(new Card(Rank.FOUR, Suit.DIAMONDS));
		game.setDealerCards(dealerCards);
		game.setPlayerCards(playerCards);
		assertTrue(game.gameAssessment()==GameResult.NATURAL_BLACKJACK); 
		
	
		
		
	
		
	}
	
	@Test
	public void testDealerShouldTakeCard() {
		BlackjackModel game=new BlackjackModel();
		
		ArrayList<Card> dealerCards=new ArrayList<>();
		
		dealerCards.add(new Card(Rank.EIGHT, Suit.HEARTS));
		dealerCards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
		game.setDealerCards(dealerCards);
		
		assertTrue(game.dealerShouldTakeCard()==true);
		
		BlackjackModel game1=new BlackjackModel();
		ArrayList<Card>dealerCards1=new ArrayList<>();
		dealerCards1.add(new Card(Rank.ACE,Suit.DIAMONDS));
		dealerCards1.add(new Card(Rank.SIX, Suit.SPADES));
		game1.setDealerCards(dealerCards1);
		assertTrue(game1.dealerShouldTakeCard()==true);
		
		BlackjackModel game2=new BlackjackModel();
		ArrayList<Card>dealerCards2=new ArrayList<>();
		dealerCards2.add(new Card(Rank.TEN, Suit.HEARTS));
		dealerCards2.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
		game2.setDealerCards(dealerCards2);
		assertTrue(game2.dealerShouldTakeCard()==false);
		
		BlackjackModel game3=new BlackjackModel();
		ArrayList<Card>dealerCards3=new ArrayList<>();
		dealerCards3.add(new Card(Rank.ACE, Suit.DIAMONDS));
		dealerCards3.add(new Card(Rank.TEN, Suit.CLUBS));
		game3.setDealerCards(dealerCards3);
		assertTrue(game3.dealerShouldTakeCard()==false);
		
		BlackjackModel game4=new BlackjackModel();
		ArrayList<Card>dealerCards4=new ArrayList<>();
		dealerCards4.add(new Card(Rank.ACE, Suit.SPADES));
		dealerCards4.add(new Card(Rank.SIX, Suit.HEARTS));
		game4.setDealerCards(dealerCards4);
		assertTrue(game4.dealerShouldTakeCard()==true);
		
	



}
	
	
}
