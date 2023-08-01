import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {

	@Test
	public void testSimpleAdd() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.size());
	}
	
	@Test
	public void testReHash() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 1000; i++) {
			s.add("Entry " + i);
		}
		assertEquals(2048, s.getCapacity());
		assertEquals(1000, s.size());
	}

	@Test
	public void testNoDuplicates() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 10; i++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		assertEquals(8, s.getCapacity());
		assertEquals(4, s.size());
	}
	@Test
	public void test1() {
		MyHashSet<Integer>hashSet=new MyHashSet<Integer>();
		
		hashSet.add(5);
		hashSet.add(11);
		hashSet.add(13);
		hashSet.add(25);
		hashSet.add(28);
		assertTrue(hashSet.contains(5)==true);
		assertTrue(hashSet.contains(69)==false);
		boolean hasRemoved=hashSet.remove(5);
		System.out.println(hasRemoved);
		assertTrue(hashSet.size()==4);
		
		
		
		
		
		
	
		
		
		
	}
}
