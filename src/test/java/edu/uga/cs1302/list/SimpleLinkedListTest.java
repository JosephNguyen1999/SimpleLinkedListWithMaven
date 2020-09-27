package edu.uga.cs1302.list;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import edu.uga.cs1302.list.SimpleLinkedList;


public class SimpleLinkedListTest {

	private SimpleLinkedList<String> list, list2;

	//set up and instantiates the objects before every test
	@Before
	public void setUp() {
		list = new SimpleLinkedList<String>();
		list2 = new SimpleLinkedList<String>();
		list2.add("123");
		list2.add("456");
		list2.add("789");
		list2.add("101112");
	}

	//tears down and cleans up after every test
	@After
	public void tearDown() {
		list = null;
	}

	//Test Case 1: checks add method for empty list
	@Test
	public void testAddEmpty() {	
		assertTrue(list.isEmpty());
		list.add(0, "A");
		assertEquals(list.get(0), "A");
		assertEquals(list.size(), 1);
		list.add(1, "B");
		assertEquals(list.get(1), "B");
		assertEquals(list.size(), 2);
	}

	//Test Case 2: checks add method for adding at beginning
	@Test
	public void testAddBeginning() {	
		list2.add(0, "B");
		assertEquals(list2.get(0), "B");
		assertEquals(list2.size(), 5);
		list2.add(0, "A");
		assertEquals(list2.get(0), "A");
		assertEquals(list2.size(), 6);
	}

	//Test Case 3: checks add method for adding at end
	@Test
	public void testAddEnd() {	
		list2.add(4, "A");
		assertEquals(list2.get(4), "A");
		assertEquals(list2.size(), 5);
		list2.add(5, "B");
		assertEquals(list2.get(5), "B");
		assertEquals(list2.size(), 6);
	}
	
	//Test Case 4: checks add method for adding in the middle
	@Test
	public void testAddMiddle() {	
		list2.add(2, "B");
		assertEquals(list2.get(2), "B");
		assertEquals(list2.size(), 5);
		list2.add(2, "A");
		assertEquals(list2.get(2), "A");
		assertEquals(list2.size(), 6);
	}
	
	//Test Case 5: checks add method to see if IndexOutOfBoundsException is thrown correctly when index is out of range
	@Test (expected = IndexOutOfBoundsException.class)
	public void testAddError() {	
		list.add(-1, "Z");
		list.add(list.size() + 5, "Z");
	}
	
	
	//Test Case 6: checks remove method for removing the first element
	@Test
	public void testRemoveFirst() {	
		list2.remove(0);
		assertEquals(list2.get(0), "456");
		assertEquals(list2.size(), 3);

	}

	//Test Case 7: checks remove method for removing the last element
	@Test
	public void testRemoveLast() {	
		list2.remove(3);
		assertEquals(list2.get(2), "789");
		assertEquals(list2.size(), 3);

	}
	
	//Test Case 8: checks remove method for removing a middle element
	@Test
	public void testRemoveMiddle() {	
		list2.remove(1);
		assertEquals(list2.get(1), "789");
		assertEquals(list2.size(), 3);

	}

	//Test Case 9: checks remove method to see if IndexOutOfBoundsException is thrown correctly when index is out of range
	@Test (expected = IndexOutOfBoundsException.class)
	public void testRemoveError() {	
		list2.remove(-1);
		list2.remove(list.size() + 5);
		list2.remove(list.size());
	}
	
	//Test Case 10: checks indexOf method for an index of existing element
	@Test
	public void testIndexOfPositive() {	
		assertEquals(list2.indexOf("456"), 1);
		assertEquals(list2.indexOf("101112"), 3);

	}
	
	//Test Case 11: checks indexOf method for an index of non-existing element
	@Test
	public void testIndexOfNegative() {	
		assertEquals(list2.indexOf("777"), -1);
		assertEquals(list2.indexOf("-90"), -1);

	}
	
	//Test Case 12: checks indexOf method for an index of the element null
	@Test
	public void testIndexOfNull() {	
		list2.add(2, null);
		assertEquals(list2.indexOf(null), 2);
		list2.add(4, null);
		assertEquals(list2.indexOf(null), 2);

	}
	
}
