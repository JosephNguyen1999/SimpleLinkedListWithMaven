// Greatly simplified, but similar to the LinkedList class in OpenJDK.
package edu.uga.cs1302.list;

import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Iterator;
import java.io.Serializable;



/**
 * This class provides a simple generic list implemented as a doubly linked list. 
 * It is similar to the LinkedList class included in the java.util package.
 *
 * The elements on the list are ordered, and the first element of the list 
 * is at position 0 and the last element is at position list.size() - 1.
 */
public class SimpleLinkedList<E> 
implements SimpleList<E>, Iterable<E>, Serializable
{
	private Node<E> first;        // first node of the list
	private Node<E> last;         // last node of the list
	private int     count;	  // number of list elements
	private int     modCount;     // the total number of modifications 
	// (add and remove calls)

	/**
	 * Creates an empty SimpleLinkedList.
	 */
	public SimpleLinkedList()
	{
		first = null;
		last = null;
		count = 0;
		modCount = 0;
	}

	/**
	 * Checks if this SimpleLinkedList is empty.
	 * @return true if and only if the list is empty
	 */
	public boolean isEmpty()
	{
		return count == 0;
	}

	/**
	 * Returns the number of elements in this SimpleLinkedList.
	 * @return the number of elements in this list
	 */
	public int size()
	{
		return count;
	}

	/**
	 * Adds an element at the end of this list.
	 * @param e the element to be added to the end of this list
	 * @return true
	 */
	public boolean add( E e )
	{
		Node<E> node = new Node<E>( e, last, null );

		if( last == null )  // list is empty
			first = node;
		else
			last.next = node;
		last = node;
		count++;
		modCount++;     // increase modification count; element added
		return true;
	}

	/**
	 * Adds an element to the list at the specified position
	 * @param index the position where the element should be added
	 * @param e the element to be added to the list
	 * @return true
	 * @throws IndexOutOfBoundsException if the index out of range, i.e. index {@literal <} 0 or index {@literal >} the size()
	 */
	public boolean add( int index, E e ) 
	{
		// your code goes here...
		Node<E> temp = first;
		Node<E> node = new Node<E>(e, null, null);
		
		//throws IndexOutOfBoundsException if index is out of range
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		//list is empty
		if (first == null && last == null && size() == 0) {
			first = node;
			last = node;
		}
		//adds to beginning
		else if (index == 0) {
			first.prev = node;
			node.next = first;
			first = node;
		}
		//adds to end
		else if (index == size()) {
			last.next = node;
			node.prev = last;
			last = node;
		}
		//adds element at index in the middle
		else {
			for(int i = 0; i < index - 1; i++) {
				temp = temp.next;
			}
			node.prev = temp;
			node.next = temp.next;
			temp.next = node;
			temp.next.next.prev = node;
		}
		count++;
		modCount++;
		return true;
	}

	/**
	 * Returns the element of the list at the indicated position.
	 * @param index the position of the list element to return
	 * @return the element at position index
	 * @throws IndexOutOfBoundsException if the index is {@literal <} 0 or {@literal >=} the size of the list
	 */
	public E get( int index )
	{ 
		validateIndex( index, count-1 ); // must be an index of an existing element
		Node<E> node = getNodeAt( index );
		return node.elem;
	}   

	/**
	 * Removes an element from the list at the specified position
	 * @param index the position where the element should be removed
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index out of range, i.e. index {@literal <} 0 or {@literal >=} the size()
	 */
	public E remove( int index ) 
	{
		// your code goes here...
		Node<E> temp = first;
		Node<E> indexElem = new Node<E>(null, null, null);
		
		//throws IndexOutOfBoundsException if index is out of range
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		//removes first element
		if (index == 0){
			indexElem = first;
			first = first.next;
			first.prev = null;
		}
		//removes last element
		else if (index == size() - 1) {
			indexElem = last;
			last = last.prev;
			last.next = null;
		}
		//removes element at index in the middle
		else {
			for(int i = 0; i < index - 1; i++) {
				temp = temp.next;
			}
			indexElem = temp.next;
			temp.next.next.prev = temp;
			temp.next = temp.next.next;
			
		}
		count--;
		modCount++;
		//returns element that was removed
		return indexElem.elem;  // you need to modify this return statement
	}

	/**
	 * Returns the index of the first occurrence of a given element on the list equal 
	 * or -1 if the given element is not on the list.  If the argument element is null,
	 * the method returns the index of the first null element on the list, or -1 if the list
	 * has no null elements.
	 * @param e the element to be located on the list.
	 * @return the index of the first occurrence of a given element on the list equal
	 * or -1 if the given element is not on the list.
	 */
	public int indexOf( E e )
	{
		// your code goes here...
		Node<E> temp = first;
		Node<E> node = new Node<E>(e, null, null);
		//goes through list and returns the index of the first occurrence of the given element (including null)
		for (int i = 0; i < size(); i++) {
			if(temp.elem == node.elem) {
				return i;
			}
			temp = temp.next;
		}
		//returns -1 if element is not in the list
		return -1;
	}

	/**
	 * Returns an Iterator of the list elements, starting at the beginning of this list.
	 * @return the created Iterator
	 */
	public Iterator<E> iterator() 
	{
		return new SimpleLinkedListIterator( 0 );
	}

	/**
	 * Returns a ListIterator of the list elements, starting at the given position in this list.
	 * @param index the position of the first element on the list to be returned from the iterator
	 * @return the created ListIterator
	 * @throws IndexOutOfBoundsException if the index is {@literal <} 0 or {@literal >=} the size of the list
	 */
	public ListIterator<E> listIterator( int index ) 
	{
		validateIndex( index, count ); // must be possible to insert after the last element
		return new SimpleLinkedListIterator( index );
	}

	// The methods and inner classes below are private, and are intended for internal use only.

	// Return the node at a given index.
	// The argument, index, must be verified to be a legal index into this list.
	private Node<E> getNodeAt( int index )
	{
		Node<E> curr = first;
		for( int i = 0; i < index; i++ )
			curr = curr.next;
		return curr;
	}

	// Verify that a given index is within bounds 0 through end.
	// The second argument, end, should be either count-1, if the given index must
	// be a valid index of an existing element, or count, if an insert is to be at 
	// the end of a list, or an iterator starting at the right end of the list.
	private void validateIndex( int index, int end )
	{
		if( index < 0 || index > end )
			throw new IndexOutOfBoundsException( "Illegal list index: " + index );
	}

	// This is a private inner class implementing a doubly-linked list node.
	// It makes sense for this class to be private, as it is only useful internally to
	// the SimpleLinnkedList class.
	// Because this class is private, so it is accessible only to the host class SimpleLinkedList,
	// therefore, there is no need to define the variables as private.
	private static class Node<E> {
		E       elem;
		Node<E> next;
		Node<E> prev;

		Node( E elem, Node<E> prev, Node<E> next ) {
			this.elem = elem;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * This class provides an iterator for the SimpleLinkedList.
	 * Some methods have not been implemented intentionally;  you 
	 * are not expected to implement them.
	 */
	private class SimpleLinkedListIterator
	implements ListIterator<E>
	{
		private Node<E> currNode;
		private Node<E> previouslyReturned;
		private int     currPos; // index of the element to be returned next
		private int     expectedModCount; // the count of modifications at the time of this iterator creation

		// Creates a new iterator starting at position index.
		// JavaDoc comment needed
		/**
		 * creates a new iterator starting at the index provided
		 * @param index the starting position index for the iterator
		 */
		public SimpleLinkedListIterator( int index )
		{
			validateIndex( index, count ); // verify the staring index;  may be equal to count
			expectedModCount = modCount;
			previouslyReturned = null;
			if( count == 0 )
				currNode = null;
			else
				currNode = getNodeAt( index );
			currPos = index;
		}

		// Returns true if this list iterator has more elements when traversing the list forward.
		// JavaDoc comment needed
		/**
		 * checks to see if the iterator still needs to keep iterating through the list
		 * @return true, if iterator still has more elements to keep iterating through in the list
		 */
		public boolean hasNext() 
		{
			return currPos < count;
		}

		// Returns true if this list iterator has more elements when traversing the list in the reverse direction.
		// JavaDoc comment needed
		/**
		 * checks to see if the iterator still needs to keep iterating through the list in the reverse direction
		 * @return true, if iterator still has more elements to keep iterating through in the list in the reverse direction
		 */
		public boolean hasPrevious()
		{
			return currPos > 0;
		}

		// Returns the next element on the list.
		// May throw NoSuchElementException if the next element does not exist.
		/**
		 * Returns the next element in the list
		 * @return the next element
		 */
		public E next() 
		{
			checkForComodification();
			if( currPos >= count || currNode == null )
				throw new NoSuchElementException();
			previouslyReturned = currNode;
			currPos++;
			currNode = currNode.next;
			return previouslyReturned.elem;
		}

		// Returns the index of the element that would be returned by a call to next.
		// JavaDoc comment needed
		/**
		 * Returns the index of the next element that would be returned by a call to next
		 * @return the index of the next index
		 */
		public int nextIndex() 
		{
			return currPos;
		}

		// Returns the previous element in the list.
		// JavaDoc comment needed
		/**
		 * Returns the previous element in the list
		 * @return the previous element
		 */
		public E previous() 
		{
			checkForComodification();
			if( currPos <= 0 )
				throw new NoSuchElementException();
			currPos--;
			if( currNode == null ) {
				currNode = last;
				previouslyReturned = last;
				return previouslyReturned.elem;
			}
			else {
				currNode = currNode.prev;
				previouslyReturned = currNode;
				return previouslyReturned.elem;
			}
		}

		// Returns the index of the element that would be returned by a call to previous.
		// JavaDoc comment needed
		/**
		 * Returns the index of the previous element that would be returned by a call to previous
		 * @return the index of the previous index
		 */
		public int previousIndex() 
		{
			return currPos - 1;
		}

		// The following are optional operations which are not supported in the 
		// SimpleLinkedList implementation.

		// Adds a new element
		// not implemented here
		public void add(Object e)
		{
			throw new UnsupportedOperationException( "add called while iterating is not available" );
		}

		// Removes from the list the last element that was returned by next or previous (optional operation).
		// not implemented here
		public void remove() 
		{
			throw new UnsupportedOperationException( "remove called while iterating is not available" );
		}

		// Replaces the last element returned by next or previous with the specified element (optional operation).
		// not implemented here
		public void set(Object e)
		{
			throw new UnsupportedOperationException( "set called while iterating is not available" );
		}

		// check if there was a concurrent modification of the list contents.
		// if yes, throw a ConcurrentModificationException exception
		private final void checkForComodification() 
		{
			if( expectedModCount != SimpleLinkedList.this.modCount )
				throw new ConcurrentModificationException( "list modified while iterator is in progress" );
		}
	}
}
