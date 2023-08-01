package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Test;

import searchTree.EmptyTree;
import searchTree.SearchTreeMap;
import searchTree.Tree;







public class PublicTests{
	
	@Test
	public void testEasyBSTSearch() {
		Tree<Integer,String> t = EmptyTree.getInstance();
		assertTrue(t.search(0) == null);
		t = t.insert(5, "THIS IS 5");
		assertEquals("THIS IS 5", t.search(5));
		t = t.insert(8, "THIS IS 8");
		assertEquals("THIS IS 8", t.search(8));
		
	}
	
	@Test
	public void testEmptySearchTreeMap() {
		SearchTreeMap<String, Integer> s = new SearchTreeMap<String, Integer>();
		assertEquals(0, s.size());
		try {
			assertEquals(null, s.getMin());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		try {
			assertEquals(null, s.getMax());
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			assert true; // as intended
		}
		assertEquals(null, s.get("a"));
	}
	
	@Test
	public void testBasicSearchTreeMapStuff() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
	}
	@Test
	public void test1() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		assertTrue(s.size()==0);
		s.put(5, 7);
		s.put(5, 11);
		assertTrue(s.size()==1);
		assertTrue(s.get(5)==11);
		s.put(65, 11);
		s.put(11, 28);
		assertTrue(s.size()==3);
		s.put(28, 50);
		s.put(89, 12);
		s.remove(5);
		assertTrue(s.size()==4);
		s.put(5, 11);
		assertTrue(s.getMax()==89);
		assertTrue(s.get(s.getMax())==12);
		assertTrue(s.getMin()==5);
		assertTrue(s.get(s.getMin())==11);
		
		
		
		
		
	
	}
	@Test
	public void test2()  {
		Tree<Integer,Integer> t = EmptyTree.getInstance();
		t=t.insert(5, 5);
		assertTrue(t.size()==1);
		t=t.insert(65, 2);
		assertTrue(t.size()==2);
		t=t.insert(65, 4);
		assertTrue(t.size()==2);
		t=t.delete(65);
		assertTrue(t.size()==1);
		t=t.insert(8, 99);
		
		
		
		
		
		
		
	}
	@Test
	public void test3() {
		Tree<Integer,Integer> t = EmptyTree.getInstance();
		t=t.insert(10, 10);
		t=t.insert(25, 25);
		t=t.insert(45, 45);
		t=t.insert(50, 50);
		t=t.insert(75, 75);
		t=t.insert(60, 60);
		t=t.insert(100, 100);
		assertTrue(t.size()==7);
		t=t.delete(25);
		assertTrue(t.size()==6);
		t=t.delete(50);
		assertTrue(t.size()==5);
		
		
		
	}
	@Test
	public void test4() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		s.put(10, 10);
		s.put(25, 25);
		s.put(45, 45);
		s.put(50, 50);
		s.put(75, 75);
		s.put(60, 60);
		s.put(100, 100);
		assertTrue(s.size()==7);
		assertTrue(s.getMax()==100);
		assertTrue(s.get(s.getMax())==100);
		s.put(100, 101);
		assertTrue(s.size()==7);
		assertTrue(s.get(s.getMax())==101);
		s.remove(50);
		assertTrue(s.size()==6);
		s.remove(100);
		assertTrue(s.size()==5);
		assertTrue(s.getMax()==75);
		assertTrue(s.getMin()==10);
		s.put(9, 11);
		System.out.println(s.getMin());
		System.out.println(s.get(s.getMin()));
		
		
		
		
		
	}
	@Test
	public void test5() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		s.put(5, 5);
		s.put(88,7);
		s.put(55, 1);
		s.put(45, 99);
		s.remove(45);
		assertTrue(s.size()==3);
		assertTrue(s.getMin()==5);
	}
	@Test
	public void test6() {
		Tree<Integer, Integer>s=EmptyTree.getInstance();
		s=s.insert(55, 67);
		s=s.insert(11, 26);
		s=s.insert(80, 61);
		s=s.insert(7, 11);
		s=s.insert(88, 90);
		ArrayList<Integer>collection=new ArrayList<>();
		s.addKeysToCollection(collection);
		System.out.println(collection.size());
		System.out.println(collection.toString());
	
		
	
		
		
		
		
	}
	@Test
	public void test7() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		s.put(55, 89);
		s.put(11, 6);
		s.put(77, 62);
		s.put(45, 25);
		s.put(9, 20);
		System.out.println(s.keyList().toString());
		
		
		
		
	}
	@Test
	public void test8() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		for(int i=1; i<101; i++) {
			s.put(i, i);
		}
		assertTrue(s.size()==100);
		s.remove(55);
		assertTrue(s.size()==99);
		s.put(55,55);
		
		
		
		
		
	}
	@Test
	public void test9() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		for(int i=1; i<101; i++) {
			s.put(1, i);
		}
		assertTrue(s.size()==1);
		assertTrue(s.get(s.getMax())==100);
		assertTrue(s.get(s.getMin())==100);
	}
	@Test
	public void test10() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		for(int i=1; i<101; i+=2) {
			s.put(i, i);
		}
		assertTrue(s.size()==50);
		assertTrue(s.getMax()==99);
		for(int i=1; i<50; i++) {
			s.remove(i);
		}
		assertTrue(s.size()==25);
		assertTrue(s.getMin()==51);
		
		
		
	}
	@Test
	public void test11() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		for(int i=1; i<101; i+=2) {
			s.put(i, i);
		}
		SearchTreeMap<Integer, Integer>sub=s.subMap(40, 60);
		System.out.println(sub.keyList());
		
	}
	@Test
	public void test12() {
		SearchTreeMap<Integer, Integer> s= new SearchTreeMap<>();
		for(int i=1; i<101; i++) {
			s.put(i, i);
		}
		for(int i=1; i<101; i+=3) {
			s.remove(i);
			s.remove(i+1);
		}
		System.out.println(s.keyList());
		
	}
	@Test
	public void test13() {
		Tree<Integer, Integer>s=EmptyTree.getInstance();
		for(int i=1; i<101; i++) {
			s=s.insert(i, i);
		}
		Tree<Integer, Integer>subTree=s.subTree(40, 60);
		assertTrue(subTree.size()==21);
		for(int i=1; i<101; i+=3) {
			s=s.delete(i);
			s=s.delete(i+1);
			
		}
		ArrayList<Integer>keyList=new ArrayList<>();
		s.addKeysToCollection(keyList);
		s=s.insert(12, 15);
		System.out.println(keyList);
		assertTrue(s.search(12)==15);
		s=s.insert(12, 12);
		assertTrue(s.search(12)==12);
		
	
		
	}
	
	
}