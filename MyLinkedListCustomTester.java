
/**
 * Name: Mary Vu
 * ID: A17081683
 * Email: m2vu@ucsd.edu
 * Sources used: None
 * 
 * This file contains one class MyLinkedListCustomTester. This is a private
 * tester file for PA 4 and it uses JUnit 4.12 testing.
 */

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.TestRule;

/**
 * This class contains all the private test cases for PA 4 for
 * all the methods in MyListIterator. It contains six instance variables, half
 * of which are MyLinkedLists and the other half are MyListIterators, which are
 * used for the various tests in this class.
 * 
 * IMPORTANT: Do not change the method headers and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class MyLinkedListCustomTester {
    private MyLinkedList listLen0, listLen1, listLen2;
    private MyLinkedList.MyListIterator listLen0Iter;
    private MyLinkedList.MyListIterator listLen1Iter;
    private MyLinkedList.MyListIterator listLen2Iter;

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        listLen0 = new MyLinkedList();
        listLen0Iter = listLen0.new MyListIterator();

        listLen1 = new MyLinkedList();
        listLen1.add("Mary");
        listLen1Iter = listLen1.new MyListIterator();

        listLen2 = new MyLinkedList();
        listLen2.add("Mary");
        listLen2.add("Vu");
        listLen2Iter = listLen2.new MyListIterator();
    }

    /**
     * Test the hasNext method when there is a sentinal tail node in the
     * forward direction
     */
    @Test
    public void testHasNext() {
        assertFalse("call hasNext when the linked list is empty",
                listLen0Iter.hasNext());
    }

    /**
     * Test the next method when the next node is the sentinal tail node
     */
    @Test
    public void testNext() {
        try {
            listLen0Iter.next();
            fail();
        } catch (Exception NoSuchElementException) {
            // passes
        }
    }

    /**
     * Test the hasPrevious method the previous node is the sentinal head node
     */
    @Test
    public void testHasPrevious() {
        assertFalse("call hasPrevious when the linked list is empty",
                listLen0Iter.hasPrevious());
    }

    /**
     * Test the previous method when called listLen1Iter after next has been
     * called once, and test the previous method when called on an empty linked
     * list
     */
    @Test
    public void testPrevious() {
        // Manually doing listLen1Iter.next()
        listLen1Iter.left = listLen1.head.getNext();
        listLen1Iter.right = listLen1.head.getNext().getNext();
        listLen1Iter.idx = 1;
        listLen1Iter.canRemoveOrSet = true;
        listLen1Iter.forward = true;

        // check the element value after calling previous
        assertEquals("Mary", listLen1Iter.previous());
        // check the nodes returned after doing previous
        assertEquals("The node that left points to after calling previous",
                listLen1.head, listLen1Iter.left);
        assertEquals("The node that right points to after calling previous",
                listLen1.head.getNext(), listLen1Iter.right);

        // calling previous on an empty linked list
        try {
            listLen0Iter.previous();
            fail();
        } catch (Exception NoSuchElementException) {
            //passes
        }
    }

    /**
     * Test the nextIndex method when list iterator was created on an empty
     * linked list
     */
    @Test
    public void testNextIndex() {
        assertEquals("Index on an empty linked list", 0,
                listLen0Iter.nextIndex());
        assertEquals("Check size is equal to next next index", listLen0.size(), 
                listLen0Iter.nextIndex());
    }

    /**
     * Test the previousIndex method when list iterator was created on an
     * empty linked list.
     */
    @Test
    public void testPreviousIndex() {
        assertEquals("Index on an empty linked list", -1,
                listLen0Iter.previousIndex());
        assertEquals("Check instance variable idx is unchanged", 0, 
                listLen0Iter.idx);
    }

    /**
     * Test the set method when it is the first method called on an iterator,
     * test the set method when remove was the most recent method called on the
     * iterator, and test set method when it has a null argument
     */
    @Test
    public void testSet() {
        // ensure .set() cannot be called if .next() or .previous() haven't
        // been called
        try {
            listLen1Iter.set("Vu");
            fail();
        } catch (Exception IllegalStateException) {
            // passes
        }
        /*
         * /* ensure .set() cannot be called if .remove() was just called
         */
        // manually doing listLen2Iter.next()
        listLen2Iter.left = listLen2.head.getNext();
        listLen2Iter.right = listLen2.head.getNext().getNext();
        listLen2Iter.idx = 1;
        listLen2Iter.forward = true;
        listLen2Iter.canRemoveOrSet = true;
        listLen2Iter.remove();
        try {
            listLen2Iter.set("hello");
            fail();
        } catch (Exception IllegalStateException) {
            // passes
        }

        /* ensure .set() cannot be called if the element targeted is null */
        // manually doing listLen1Iter.next
        listLen1Iter.left = listLen1.head.getNext();
        listLen1Iter.right = listLen1.head.getNext().getNext();
        listLen1Iter.idx = 1;
        listLen1Iter.canRemoveOrSet = true;
        listLen1Iter.forward = true;
        try {
            listLen1Iter.set(null);
            fail();
        } catch (Exception NullPointerException) {
            // passes
        }

        // test .set() when given null element on empty linked list
        try {
            listLen0Iter.set(null);
            fail();
        } catch (Exception NullPointerException) {
            // passes
        }

    }

    /**
     * Test the remove method when neither next nor previous were called, and
     * test the remove method on an empty linked list
     */
    @Test
    public void testRemoveTestOne() {
        try {
            listLen1Iter.remove();
            fail();
        } catch (Exception IllegalStateException) {
            // passes
        }

        // testing on empty linked list
        try {
            listLen0Iter.remove();
            fail();
        } catch (Exception IllegalStateException) {
            // passes
        }
    }

    /**
     * test the remove method if add or remove have been called since the most
     * recent next/previous call
     */
    @Test
    public void testRemoveTestTwo() {
        // manually doing listLen2Iter.next()
        listLen2Iter.left = listLen2.head.getNext();
        listLen2Iter.right = listLen2.head.getNext().getNext();
        listLen2Iter.idx = 1;
        listLen2Iter.canRemoveOrSet = true;
        listLen2Iter.forward = true;
        // calling add to change the state of canRemoveOrSet
        listLen2Iter.add("hi!");
        try {
            listLen2Iter.remove();
            fail();
        } catch (Exception IllegalStatException) {
            // passes
        }
    }

    /**
     * test the add method when element is null
     */
    @Test
    public void testAdd() {
        // manually doing listLen2Iter.next()
        listLen2Iter.left = listLen2.head.getNext();
        listLen2Iter.right = listLen2.head.getNext().getNext();
        listLen2Iter.idx = 1;
        listLen2Iter.canRemoveOrSet = true;
        listLen2Iter.forward = true;
        try {
            listLen2Iter.add(null);
            fail();
        } catch (Exception NullPointerException) {
            // passes
        }

    }

}