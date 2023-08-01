package tests;

import static org.junit.Assert.*;

//import java.util.Iterator;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {

	/* Write a lot of tests! */
	
	@Test
	public void test1() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(5);
		assertTrue(list.getSize()==1);
		list.addToEnd(9);
		list.addToEnd(11);
		assertTrue(list.getSize()==3);
		list.addToFront(11);
		list.addToFront(15);
		assertTrue(list.getSize()==5);
		assertTrue(list.getFirst()==15);
		assertTrue(list.getLast()==11);
		
		
	}
	
	@Test
	public void test2() {
	BasicLinkedList<Integer>list=new BasicLinkedList<>();
	list.addToFront(5);
	assertTrue(list.getFirst()==5 && list.getLast()==5);
	list.addToEnd(55);
	list.addToFront(69);
	assertTrue(list.getFirst()==69 && list.getLast()==55);
	Integer x=list.retrieveLastElement();
	assertTrue(x==55 && list.getSize()==2);
	
		
	}
	@Test
	public void test3() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(5);
		list.addToFront(89);
		assertTrue(list.getLast()==5);
		list.addToFront(62);
		list.addToFront(5);
		list.removeAllInstances(5);
		assertTrue(list.getFirst()==62 && list.getLast()==89 && list.getSize()==2);
		
		
		
		
	}
	@Test
	public void test4() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(9);
		list.addToEnd(65);
		list.addToEnd(5);
		list.addToEnd(9);
		list.addToEnd(89);
		list.addToEnd(9);
		list.addToEnd(11);
		list.addToEnd(9);
		assertTrue(list.getSize()==8);
		list.removeAllInstances(9);
		assertTrue(list.getFirst()==65 && list.getLast()==11);
		assertTrue(list.getSize()==4);
	
		
		
		
		
		
		
		
	}
	@Test
	public void test5() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(5);
		list.addToEnd(5);
		list.addToEnd(3);
		list.addToEnd(4);
		list.addToEnd(5);
		list.addToEnd(5);
		list.removeAllInstances(5);
		assertTrue(list.getSize()==2);
		assertTrue(list.getFirst()==3);
		
	}
	@Test
	public void test6() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(5);
		list.addToEnd(4);
		list.addToEnd(6);
		list.removeAllInstances(4);
		assertTrue(list.getSize()==2 && list.getFirst()==5 && list.getLast()==6);
		
	}
	@Test
	public void test7() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(9);
		list.addToFront(6);
		list.addToFront(6);
		list.addToFront(4);
		list.addToFront(6);
		list.addToFront(2);
		list.addToFront(3);
		list.addToFront(1);
		list.removeAllInstances(6);
		//System.out.print(list.getSize());
		assertTrue(list.getSize()==5);
		assertTrue(list.getFirst()==1);
		
		
		
		
	}
	@Test
	public void test8() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(5);
		list.addToFront(6);
		list.addToFront(7);
		list.addToFront(5);
		list.addToFront(5);
		list.removeAllInstances(5);
		assertTrue(list.getSize()==2);
		
		
		
		
	}
	@Test
	public void test9() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(88).addToEnd(55).addToEnd(42).addToEnd(35).addToEnd(35);
		list.removeAllInstances(35);
		//System.out.print(list.getSize());
		assertTrue(list.getSize()==3);
		
	}
	
	@Test
	public void test10() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(54).addToEnd(35).addToEnd(58).addToEnd(35).addToEnd(85);
		list.removeAllInstances(35);
		assertTrue(list.getSize()==3);
		
	}
	@Test
	public void test11() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(54);
		list.addToEnd(35);
		list.addToEnd(35);
		list.addToEnd(58);
		list.addToEnd(85);
		assertTrue(list.getFirst()==54);
		assertTrue(list.getLast()==85);
		list.removeAllInstances(35);
		assertTrue(list.getSize()==3);
		
	
		
		
		
		
	}
	@Test
	public void test12() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(55);
		list.addToEnd(55);
		list.addToEnd(55);
		list.addToEnd(55);
		list.removeAllInstances(55);
		assertTrue(list.getSize()==0);
		assertTrue(list.getFirst()==null);
		assertTrue(list.getLast()==null);
		
		
	
	}
	@Test
	public void test13() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(55);
		assertTrue(list.getSize()==1);
		
	}
	
	
	@Test
	public void test14() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToEnd(5).addToEnd(5).addToEnd(7).addToEnd(7).addToEnd(5).addToEnd(5);
		list.removeAllInstances(5);
		assertTrue(list.getSize()==2);
		
		
	}
	
	@Test
	public void test15() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(9);
		list.addToFront(6);
		list.addToFront(10);
		list.addToFront(0);
		list.addToFront(8);
		list.addToFront(8);
		list.addToFront(9);
		list.addToFront(65);
		list.addToFront(8);
		list.removeAllInstances(8);
		assertTrue(list.getSize()==6);
		assertTrue(list.getFirst()==65);
		assertTrue(list.getLast()==9);

		
		
	}
	@Test
	public void test16() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(10);
		list.addToFront(9);
		list.addToFront(11);
		list.removeAllInstances(12);
		assertTrue(list.getSize()==3 && list.getFirst()==11 && list.getLast()==10);
		list.retrieveFirstElement();
		assertTrue(list.getSize()==2 && list.getFirst()==9 && list.getLast()==10);
		list.retrieveFirstElement();
		assertTrue(list.getSize()==1);
		assertTrue(list.getFirst()==10);
		assertTrue(list.getLast()==10);
		list.removeAllInstances(10);
		assertTrue(list.getSize()==0);
		assertTrue(list.getFirst()==null);
		
	}
	@Test
	public void test17() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(11);
		list.addToFront(45);
		list.addToFront(66);
		list.addToFront(89);
		list.addToFront(12);
		list.addToFront(19);
		list.addToFront(55);
		for(Integer a: list) {
			System.out.println(a);
			System.out.println(a);
		}
		
		
	}
	@Test
	public void test18() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(5);
		list.addToFront(5);
		list.addToFront(5);
		list.addToFront(5);
		list.addToFront(5);
		list.removeAllInstances(5);
		assertTrue(list.getSize()==0 && list.getFirst()==null && list.getLast()==null);
		
	}
	@Test
	public void test19() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		assertTrue(list.getFirst()==null && list.getLast()==null && list.getSize()==0);
		
	}
	@Test
	public void test20() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(29);
		list.addToFront(69);
		list.addToFront(21);
		list.addToFront(55);
		list.addToFront(null);
		assertTrue(list.getFirst()==null);
		list.retrieveFirstElement();
		assertTrue(list.getSize()==4);
		list.addToFront(69);
		list.removeAllInstances(69);
		assertTrue(list.getSize()==3);
		
	}
	@Test
	public void test21() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(20);
		list.addToFront(20);
		list.addToFront(20);
		list.addToFront(20);
		list.addToFront(20);
		list.removeAllInstances(20);
		assertTrue(list.getSize()==0 && list.getFirst()==null && list.getLast()==null);
		
	}
	@Test
	public void test22() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(20);
		list.addToFront(20);
		
		list.removeAllInstances(20);
		assertTrue(list.getSize()==0 && list.getFirst()==null);
		
	}
	@Test
	public void test23() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(21);
		list.addToFront(20);
		list.addToFront(21);
		list.removeAllInstances(21);
		assertTrue(list.getSize()==1);
		assertTrue(list.getFirst()==20);
		
		
	}
	@Test
	public void test24() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(20);
		list.addToFront(21);
		list.addToFront(20);
		list.removeAllInstances(21);
		assertTrue(list.getSize()==2);
		assertTrue(list.getFirst()==20);
		list.removeAllInstances(18);
		assertTrue(list.getSize()==2);
		assertTrue(list.getFirst()==20);
		list.removeAllInstances(20);
		assertTrue(list.getSize()==0);
		
		
	
		
	}
	@Test
	public void test25() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(20);
		list.addToFront(21);
		list.removeAllInstances(21);
		assertTrue(list.getSize()==1);
		assertTrue(list.getLast()==20);
		
	}
	@Test
	public void test26() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(22);
		list.removeAllInstances(22);
		assertTrue(list.getSize()==0 && list.getFirst()==null);
		
		
	}
	
	@Test
	public void test27() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(22);
		list.addToFront(22);
		list.addToFront(23);
		list.removeAllInstances(23);
		assertTrue(list.getSize()==2);
		assertTrue(list.getFirst()==22);
		
	}
	@Test
	public void test28() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(22);
		list.addToFront(23);
		list.addToFront(23);
		list.addToFront(23);
		list.addToFront(22);
		list.removeAllInstances(23);
		assertTrue(list.getSize()==2);
		
	}
	@Test 
	public void test29() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		assertTrue(list.getSize()==0);
		assertTrue(list.getFirst()==null);
	}
	@Test
	public void test30() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(21);
		list.addToFront(20);
		list.removeAllInstances(21);
		assertTrue(list.getSize()==1);
		assertTrue(list.getFirst()==20);
		
	}
	@Test
	public void test31() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		assertTrue(list.getFirst()==null);
		
		
	}
	@Test
	public void test32() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(10);
		list.addToFront(11);
		list.addToFront(10);
		list.removeAllInstances(10);
		assertTrue(list.getSize()==1);
		assertTrue(list.getFirst()==11);
	}
	@Test
	public void test33() {
		BasicLinkedList<Integer>list=new BasicLinkedList<>();
		list.addToFront(25);
		list.addToFront(55);
		list.addToFront(55);
		list.addToFront(25);
		list.removeAllInstances(25);
		assertTrue(list.getSize()==2);
		list.removeAllInstances(55);
		assertTrue(list.getSize()==0);
		
	}
}
