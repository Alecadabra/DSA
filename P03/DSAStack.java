import java.util.*;

public class DSAStack implements Iterable
{
    /** Private Classfield **/
    private DSALinkedList stack;
        //The stack itself is a linked list

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory address of new DSAStack object
    **   Assertion: Constructs the Linked List
    */
    public DSAStack()
    {
        stack = new DSALinkedList();
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: top
    **      Import: none
    **      Export: topVal (Object)
    **   Assertion: Returns the top value of the stack
    */
    public Object top()
    {
        if( isEmpty() )
        {
            throw new IllegalArgumentException( "Stack is empty" );
        }

        return stack.peekFirst();
    }

    /*  --------------------------------------------------------------
    **   Submodule: getCount()
    **      Import: none
    **      Export: count (int)
    **   Assertion: Iterates through the stack and returns the number of
    **                  elements it contains
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
    **      Export: empty (boolean)
    **   Assertion: Returns true if the stack is empty and false otherwise
    */
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: push
    **      Import: inObject (Object)
    **      Export: none
    **   Assertion: Pushes an element onto the top of the stack
    */
    public void push( Object inObject )
    {
        stack.insertFirst( inObject );
    }

    /*  --------------------------------------------------------------
    **   Submodule: pop
    **      Import: none
    **      Export: (Object)
    **   Assertion: Returns the top value of the stack and removes it from
    **                  the stack
    */
    public Object pop()
    {
        return stack.removeFirst();
    }

    /** Iteration **/
    /*  --------------------------------------------------------------
    **   Submodule: iterator
    **      Import: none
    **      Export: (Iterator)
    **   Assertion: Returns the iterator object for the stack
    */
    public Iterator iterator()
    {
        return stack.iterator();
    }
}
