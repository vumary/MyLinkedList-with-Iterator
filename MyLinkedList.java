
/**
 * Name: Mary Vu
 * Email: m2vu@ucsd.edu
 * Sources used: None
 * 
 * This file contains three classes, MyLinkedList, a nested Node class, and a
 * nested MyListIterator class. It imports AbstractList and ListIterator from 
 * Java's built in library. 
 */

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This is our implementation of a doubly linked list that stores the size,
 * sentinel head, and sentinel tail. It contains multiple methods that serve
 * as operations to do on the linked list.
 */

public class MyLinkedList<E> extends AbstractList<E> {

	int size;
	Node head;
	Node tail;

	/**
	 * A Node class that holds data and references to previous and next Nodes.
	 */
	protected class Node {
		E data;
		Node next;
		Node prev;

		/**
		 * Constructor to create singleton Node
		 * 
		 * @param element Element to add, can be null
		 */
		public Node(E element) {
			// Initialize the instance variables
			this.data = element;
			this.next = null;
			this.prev = null;
		}

		/**
		 * Set the parameter prev as the previous node
		 * 
		 * @param prev - new previous node
		 */
		public void setPrev(Node prev) {
			this.prev = prev;
		}

		/**
		 * Set the parameter next as the next node
		 * 
		 * @param next - new next node
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/**
		 * Set the parameter element as the node's data
		 * 
		 * @param element - new element
		 */
		public void setElement(E element) {
			this.data = element;
		}

		/**
		 * Accessor to get the next Node in the list
		 * 
		 * @return the next node
		 */
		public Node getNext() {
			return this.next;
		}

		/**
		 * Accessor to get the prev Node in the list
		 * 
		 * @return the previous node
		 */
		public Node getPrev() {
			return this.prev;
		}

		/**
		 * Accessor to get the Nodes Element
		 * 
		 * @return this node's data
		 */
		public E getElement() {
			return this.data;
		}
	}

	// Implementation of the MyLinkedList Class
	/** Only 0-argument constructor is defined */
	/**
	 * Intializes an empty linked list with a sentinel head, sentinel tail, and
	 * defaults size to 0
	 */
	public MyLinkedList() {
		this.head = new Node(null);
		this.tail = new Node(null);
		this.head.setNext(this.tail);
		this.tail.setPrev(this.head);
		this.size = 0;
	}

	@Override
	/**
	 * Returns the number of nodes not including sentinel head and tail
	 * 
	 * @return Number of nodes being stored
	 */
	public int size() {
		return this.size;
	}

	@Override
	/**
	 * Get data within the node at position index
	 * 
	 * @param index The position in question
	 * @return Data at index
	 * @throws IndexOutOfBoundsException When index is less than 0 or greater
	 * than or equal to size
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node nextInList = this.head;
		for (int i = 0; i <= index; i++) {
			nextInList = nextInList.getNext();
		}
		return nextInList.getElement();
	}

	/**
	 * Add a node into this list by index.
	 * 
	 * @param index The position in question
	 * @param data  Element that will be added
	 * @throws NullPointerException The input index can be any integer in
	 * between zero and the number of elements(inclusive on both ends)
	 * @throws IndexOutOfBoundsException When index is less than zero or
	 * index is greater than size
	 */
	@Override
	public void add(int index, E data) {
		if (data == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}
		this.size++;
		// Inserting the new node at correct index
		Node newNode = new Node(data);
		Node nodeAtIndex = this.getNth(index);
		newNode.setPrev(nodeAtIndex.getPrev());
		newNode.setNext(nodeAtIndex);
		nodeAtIndex.getPrev().setNext(newNode);
		nodeAtIndex.setPrev(newNode);
	}

	/**
	 * Add a node a the end of the list
	 * 
	 * @param data Element that will be added
	 * @return Always returns true
	 * @throws NullPointerException When data is null
	 */
	public boolean add(E data) {
		if (data == null) {
			throw new NullPointerException();
		}
		// Inserting the new node at the end of LL
		Node newNode = new Node(data);
		newNode.setPrev(this.tail.getPrev());
		newNode.setNext(this.tail);
		this.tail.getPrev().setNext(newNode);
		this.tail.setPrev(newNode);
		this.size++;
		return true;
	}

	/**
	 * Set the element for the node at index to data and return the element
	 * that was previously stored
	 * 
	 * @param index The position in question
	 * @param data  Element that will replace the element previously stored
	 * @return Element that was replaced
	 * @throws NullPointerException When data is null
	 */
	public E set(int index, E data) {
		if (data == null) {
			throw new NullPointerException();
		}
		E oldElement = this.get(index);
		this.getNth(index).setElement(data);
		return oldElement;
	}

	/**
	 * Remove the node from the position index in this list and return
	 * the data within the removed node
	 * 
	 * @param index The position in question
	 * @return Element that was removed
	 */
	public E remove(int index) {
		Node oldNode = this.getNth(index);
		oldNode.getPrev().setNext(oldNode.getNext());
		oldNode.getNext().setPrev(oldNode.getPrev());
		this.size--;
		return (E) oldNode.getElement();
	}

	/**
	 * Removes all nodes from the list
	 */
	public void clear() {
		this.head.setNext(this.tail);
		this.tail.setPrev(this.head);
		this.size = 0;
	}

	/**
	 * Determine if the list is empty
	 * 
	 * @return If list is empty return true, otherwise return false
	 */
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * A helper method that returns the Node at a specified position
	 * 
	 * @param index The position in question
	 * @return Node at index
	 * @throws IndexOutOfBoundsException When index is less than 0 or index is
	 * greater than or equal to the number of elements in the list
	 */
	protected Node getNth(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		// Looping until we reach the node at specified index
		Node nextInList = this.head;
		for (int i = 0; i <= index; i++) {
			nextInList = nextInList.getNext();
		}
		return (Node) nextInList;
	}

	/**
	 * Overrides AbstractList's implementation of listIterator
	 * 
	 * @return A new MylistIterator with type ListIterator
	 */
	public ListIterator<E> listIterator() {
		return new MyListIterator();
	}

	/**
	 * Overrides AbstractList's implementation of iterator
	 * 
	 * @return A new MyListIterator with type Iterator
	 */
	public Iterator<E> iterator() {
		return new MyListIterator();
	}

	/**
	 * This is my implementation of a list iterator that stores the left and
	 * right node, the iterator's index, whether the direction the iterator
	 * is moving is forward or not, and whether or not a node can be removed or
	 * set. It contains multiple methods that serve as operations to do on the
	 * linked list using the iterator which saves your place.
	 */

	protected class MyListIterator implements ListIterator<E> {

		// class variables here
		Node left, right;
		int idx;
		boolean forward;
		boolean canRemoveOrSet;

		/** Only 0-argument constructor is defined */
		/**
		 * Initializes an iterator with left pointing to the linked
		 * list's head and right pointing the node that head's next is pointing
		 * to (which can be either be an element node or the tail). It also
		 * defaults index to 0, the direction to be forward, and sets
		 * canRemoveOrSet to false.
		 */
		public MyListIterator() {
			this.left = head;
			this.right = head.getNext();
			this.idx = 0;
			this.forward = true;
			this.canRemoveOrSet = false;
		}

		@Override
		/**
		 * Determines whether right is pointing to the tail or an element node
		 * 
		 * @return true if there is an element node when going the forward
		 * direction and false if it is a sentinal node
		 */
		public boolean hasNext() {
			return this.right.getNext() != null;
		}

		@Override
		/**
		 * Store the right and then move the iterator forward by one node
		 * 
		 * @return the next element in the list when going forward
		 * @throws NoSuchElementException if there is no such element i.e.
		 * there is sentinal node when going forward
		 */
		public E next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			E nextElem = this.right.getElement(); // storing element to return
			this.left = this.right;
			this.right = this.right.getNext();
			this.forward = true;
			idx++;
			this.canRemoveOrSet = true;
			return nextElem;
		}

		@Override
		/**
		 * Determines whether left is pointing to the head or an element node
		 * 
		 * @return true if there is an element node when going in the backward
		 * direction from the current iterator position and false if it is a
		 * sentinal node
		 */
		public boolean hasPrevious() {
			return this.left.getPrev() != null;
		}

		/**
		 * Store the left and then move the iterator backward by one node
		 * 
		 * @return the next element in the list when going backward
		 * @throws NoSuchElementException if there is no such element i.e.
		 * here is a sentinal node when going backwards
		 */
		@Override
		public E previous() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			}
			E prevElem = this.left.getElement(); // storing element to return
			this.right = this.left;
			this.left = this.left.getPrev();
			this.forward = false;
			idx--;
			this.canRemoveOrSet = true;
			return prevElem;
		}

		@Override
		/**
		 * Determine index of the next element in which the iterator is placed
		 * 
		 * @return the index of the element that would be returned by a call to
		 * next() and return the list size if at the end of the list
		 */
		public int nextIndex() {
			if (!this.hasNext()) { // checking if iterator is at end of list
				return size();
			}
			return this.idx;
		}

		/**
		 * Insert the given item into the list immediately before the element
		 * that that would be returned by next()
		 * 
		 * @param e Element to be added
		 * @throws NullPointerException if element e is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			// update instance variables
			size++;
			idx++;
			this.canRemoveOrSet = false;
			// adding node and updating pointers
			Node addNode = new Node(e);
			this.left.setNext(addNode);
			addNode.setPrev(this.left);
			addNode.setNext(this.right);
			this.right.setPrev(addNode);
			this.left = addNode;
		}

		@Override
		/**
		 * Determine index of the previous element in which the iterator is
		 * placed
		 * 
		 * @return the index of the element that would be returned by a call to
		 * previous() and return -1 if at the start of the list
		 */
		public int previousIndex() {
			return (idx - 1);
		}

		@Override
		/**
		 * Remove the last element node returned by the most recent
		 * next/previous call
		 * 
		 * @throws IllegalStateException neither next nor previous were called,
		 * or if add or remove have been called since the most recent 
		 * next/previous call
		 */
		public void remove() {
			if (!this.canRemoveOrSet) {
				throw new IllegalStateException();
			}
			// checks which element node to remove depending on which direction
			// the iterator is moving
			if (forward) {
				this.left.getPrev().setNext(this.right);
				this.right.setPrev(this.left.getPrev());
				this.left = this.left.getPrev();
				this.idx--;
			}
			if (!forward) {
				this.left.setNext(this.right.getNext());
				this.right.getNext().setPrev(this.left);
				this.right = this.right.getNext();
			}
			size--;
			this.canRemoveOrSet = false;
		}

		@Override
		/**
		 * For the node returned by the most recent next/previous call, replace
		 * its value with the new value element
		 * 
		 * @param e Element that will replace the previous element stored
		 * @throws NullPointerException  if element e is null
		 * @throws IllegalStateException neither next nor previous were called,
		 * or if add or remove have been called since the most recent 
		 * next/previous call
		 */
		public void set(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			if (!this.canRemoveOrSet) {
				throw new IllegalStateException();
			}
			// checks which node to set the element of depending on which
			// direction the iterator is moving
			if (this.forward) {
				this.left.setElement(e);
			}
			if (!this.forward) {
				this.right.setElement(e);
			}
		}
		
	}
}
