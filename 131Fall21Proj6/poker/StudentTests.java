package poker;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentTests {

	/* Use the @Test annotation for JUnit 4 
	 * [This is just an example, please erase
	 * it and write some real tests.]    */
	@Test
	public void testDeck() {
		Deck a = new Deck(); 
		
		for (int i=0; i<=51; i++) {
			Card c=a.getCardAt(i);
			int testSuit=i/13;
			
			
			assertTrue(c.getSuit()==testSuit);
			int testValue=(i%13)+1;
			
			assertTrue(c.getValue()==testValue);
		}
		
		
	}
	@Test
	public void testCopyDeck() {
		Deck a= new Deck();
		
		Deck b= new Deck(a);
		for (int i=0; i<51; i++) {
			
			Card a1=a.getCardAt(i);
			Card b1=b.getCardAt(i);
			assertTrue(a1.getSuit()==b1.getSuit());
			assertTrue(a1.getValue()==b1.getValue());
		}
	}
	@Test
	public void testGetCardAt() {
		Deck a=new Deck();
		Card a1 = new Card(4,2);
		
		Deck b=new Deck();
		Card b1= b.getCardAt(29);
		System.out.println(b1.getSuit());
		System.out.println(b1.getValue());
		assertTrue(a1.getSuit()==b1.getSuit());
		assertTrue(a1.getValue()==b1.getValue());
	}
	@Test
	public void testShuffle() {
		Deck a=new Deck();
		Card a1=a.getCardAt(13);
		Card a2=a.getCardAt(6);
		
		a.shuffle();
		
		assertTrue(a1.getSuit()==a.getCardAt(26).getSuit() && a1.getValue()==a.getCardAt(26).getValue());
		assertTrue(a2.getSuit()==a.getCardAt(12).getSuit() && a2.getValue()==a.getCardAt(12).getValue());
		
	}
	@Test
	public void testCut() {
		Deck a =new Deck();
		Card a1=a.getCardAt(4);
		a.cut(4);
		assertTrue(a1.getSuit()==a.getCardAt(0).getSuit() && a1.getValue()==a.getCardAt(0).getValue());
		Deck b=new Deck();
		Card b1=b.getCardAt(10);
		b.cut(10);
		assertTrue(b1.getSuit()==b.getCardAt(0).getSuit() && b1.getValue()==b.getCardAt(0).getValue());
	}
	@Test
	public void testDeal() {
		Deck a =new Deck();
		Card a1=a.getCardAt(5);
		a.deal(5);
		Card a2=a.getCardAt(0);
		assertTrue(a1.getSuit()==a2.getSuit() && a1.getValue()==a2.getValue());
		Deck b=new Deck();
		Card b1=b.getCardAt(15);
		b.deal(15);
		Card b2=b.getCardAt(0);
		assertTrue(b1.getSuit()==b2.getSuit() && b1.getValue()==b2.getValue());
		
	}
	@Test
	public void testGetNumCards() {
		Deck a=new Deck();
		assertTrue(a.getNumCards()==52);
		
		
	}
	@Test
	public void testHasPair() {
		Card[] a= new Card[2];
		a[0]= new Card(1,0);
		a[1]=new Card(1,1);
		assertTrue(PokerHandEvaluator.hasPair(a));
		Card[] b=new Card[3];
		b[0]=new Card(4,3);
		b[1]=new Card(8,2);
		b[2]=new Card(2,0);
		assertFalse(PokerHandEvaluator.hasPair(b));
		Card[] c=new Card[3];
		c[0]=new Card(2,3);
		c[1]=new Card(1,2);
		c[2]=new Card(2,0);
		assertTrue(PokerHandEvaluator.hasPair(c));
		
			
		
	}
	@Test
	public void testHasTwoPair() {
		Card[]a= new Card[4];
		a[0]= new Card(3,0);
		a[1]= new Card(3,2);
		a[2]=new Card(4,1);
		a[3]=new Card(4,0);
		assertTrue(PokerHandEvaluator.hasTwoPair(a));
		Card[]b= new Card[3];
		b[0]= new Card(3,0);
		b[1]= new Card(3,1);
		b[2]= new Card(3,2);
		assertFalse(PokerHandEvaluator.hasTwoPair(b));
		Card[]c=new Card[4];
		c[0]= new Card(4,0);
		c[1]= new Card(4,1);
		c[2]= new Card(4,2);
		c[3]= new Card(4,3);
		assertFalse(PokerHandEvaluator.hasTwoPair(c));
	}
	@Test
	public void testHasThreeOfAKind() {
		Card[]a= new Card[3];
		a[0]=new Card(3,1);
		a[1]=new Card(3,2);
		a[2]=new Card(3,3);
		assertTrue(PokerHandEvaluator.hasThreeOfAKind(a));
		Card[]b=new Card[3];
		b[0]=new Card(3,0);
		b[1]=new Card(5,0);
		b[2]=new Card(8,0);
		assertFalse(PokerHandEvaluator.hasThreeOfAKind(b));
	}
	@Test
	public void testHasStraight() {
		Card[]a=new Card[5];
		a[0]=new Card(5,1);
		a[1]=new Card(6,3);
		a[2]=new Card(7,0);
		a[3]=new Card(8,0);
		a[4]=new Card(9,2);
		assertTrue(PokerHandEvaluator.hasStraight(a));
		Card[]b=new Card[5];
		b[0]=new Card(10,2);
		b[1]=new Card(11,0);
		b[2]=new Card(12,1);
		b[3]=new Card(13,3);
		b[4]=new Card(1,0);
		assertTrue(PokerHandEvaluator.hasStraight(b));
		Card[]c=new Card[5];
		c[0]=new Card(12,1);
		c[1]=new Card(13,2);
		c[2]=new Card(1,0);
		c[3]=new Card(2,2);
		c[4]=new Card(3,0);
		assertFalse(PokerHandEvaluator.hasStraight(c));
		
	}
	@Test
	public void testHasFlush() {
		Card[]a=new Card[5];
		a[0]=new Card(11,1);
		a[1]=new Card(4,1);
		a[2]=new Card(7,1);
		a[3]=new Card(1,1);
		a[4]=new Card(13,1);
		assertTrue(PokerHandEvaluator.hasFlush(a));
		Card[]b=new Card[5];
		b[0]=new Card(6,2);
		b[1]=new Card(1,2);
		b[2]=new Card(4,2);
		b[3]=new Card(6,1);
		b[4]=new Card(1,2);
		assertFalse(PokerHandEvaluator.hasFlush(b));
	}
	@Test
	public void hasFullHouse() {
		Card[]a=new Card[5];
		a[0]=new Card(1,1);
		a[1]=new Card(1,3);
		a[2]=new Card(4,0);
		a[3]=new Card(4,1);
		a[4]=new Card(4,3);
		assertTrue(PokerHandEvaluator.hasFullHouse(a));
		Card[]b=new Card[5];
		b[0]=new Card(1,1);
		b[1]=new Card(1,2);
		b[2]=new Card(1,0);
		b[3]=new Card(3,0);
		b[4]=new Card(1,3);
		assertFalse(PokerHandEvaluator.hasFullHouse(b));
	}
	@Test
	public void hasFourOfAKind() {
		Card[]a=new Card[5];
		a[0]=new Card(4,0);
		a[1]=new Card(4,1);
		a[2]=new Card(4,2);
		a[3]=new Card(4,3);
		a[4]=new Card(13,0);
		assertTrue(PokerHandEvaluator.hasFourOfAKind(a));
		Card[]b=new Card[3];
		b[0]=new Card(11,0);
		b[1]=new Card(11,2);
		b[2]=new Card(11,3);
		assertFalse(PokerHandEvaluator.hasFourOfAKind(b));
	}
	@Test
	public void hasStraightFlush() {
		Card[]a=new Card[5];
		a[0]=new Card(9,0);
		a[1]=new Card(10,0);
		a[2]=new Card(11,0);
		a[3]=new Card(12,0);
		a[4]=new Card(13,0);
		assertTrue(PokerHandEvaluator.hasStraightFlush(a));
		Card[]b=new Card[5];
		b[0]=new Card(5,1);
		b[1]=new Card(6,3);
		b[2]=new Card(7,1);
		b[3]=new Card(8,1);
		b[4]=new Card(9,1);
		assertFalse(PokerHandEvaluator.hasStraightFlush(b));
		
	}
	
	

}
