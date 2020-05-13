/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: Implementation of FIFO queue ADT
**     Date: 27/10/19
**  ==========================================================================
*/

import java.util.*;

public class DSAQueue<T> extends AbstractQueue<T>
{
    /** Private Classfields **/
    private LinkedList<T> queue;

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory address of new DSAQueue Object
    **   Assertion: Constructs linked list
    */
    public DSAQueue()
    {
        queue = new LinkedList<T>();
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: size
    **      Import: none
    **      Export: (int)
    **   Assertion: Uses the LinkedList<T>.size() function
    */
    public int size()
    {
        return queue.size();
    }

    /*  --------------------------------------------------------------
    **   Submodule: toString
    **      Import: none
    **      Export: queueString (String)
    **   Assertion: Returns a string representation of the queue
    */
    public String toString()
    {
        return queue.toString();
    } 

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: offer
    **      Import: inValue (T)
    **      Export: (boolean)
    **   Assertion: Adds an element to the back of the queue
    */
    public boolean offer( T inValue )
    {
        queue.addLast( inValue );

        return true;
    }

    /*  --------------------------------------------------------------
    **   Submodule: poll
    **      Import: none
    **      Export: (T)
    **   Assertion: Returns the front element of the queue and polls it
    **                  from the queue
    */
    public T poll()
    {
        return (T)queue.pollFirst();
    }

    /*  --------------------------------------------------------------
    **   Submodule: peek
    **      Import: none
    **      Export: (Object)
    **   Assertion: Returns the front value of the queue
    */
    public T peek()
    {
        return (T)queue.peekFirst();
    }

    /** Iteration **/
    /*  --------------------------------------------------------------
    **   Submodule: iterator
    **      Import: none
    **      Export: (Iterator)
    **   Assertion: Returns the Iterator Object for the queue
    */
    public Iterator<T> iterator()
    {
        return queue.descendingIterator();
    }
}
