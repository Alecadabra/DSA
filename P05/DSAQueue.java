import java.util.*;

public class DSAQueue implements Iterable
{
    /** Private Classfields **/
    private DSALinkedList queue;

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory address of new DSAQueue Object
    **   Assertion: Constructs linked list
    */
    public DSAQueue()
    {
        queue = new DSALinkedList();
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: count
    **      Import: none
    **      Export: count (int)
    **   Assertion: Iterates through the queue and returns the number of
    **                  elements in the queue
    */
    public int getCount()
    {
        int count = 0;

        for( Object o : this )
        {
            count++;
        }

        return count;
    }

    /*  --------------------------------------------------------------
    **   Submodule: isEmpty
    **      Import: none
    **      Export: (boolean)
    **   Assertion: Returns true if the queue is empty and false otherwise
    */
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: enqueue
    **      Import: inObject (Object)
    **      Export: none
    **   Assertion: Adds an element to the back of the queue
    */
    public void enqueue( Object inObject )
    {
        queue.insertLast( inObject );
    }

    /*  --------------------------------------------------------------
    **   Submodule: dequeue
    **      Import: none
    **      Export: (Object)
    **   Assertion: Returns the front element of the queue and removes it
    **                  from the queue
    */
    public Object dequeue()
    {
        return queue.removeFirst();
    }

    /*  --------------------------------------------------------------
    **   Submodule: peek
    **      Import: none
    **      Export: (Object)
    **   Assertion: Returns the front value of the queue
    */
    public Object peek()
    {
        return queue.peekFirst();
    }

    /** Iteration **/
    /*  --------------------------------------------------------------
    **   Submodule: iterator
    **      Import: none
    **      Export: (Iterator)
    **   Assertion: Returns the Iterator Object for the queue
    */
    public Iterator iterator()
    {
        return queue.iterator();
    }
}
