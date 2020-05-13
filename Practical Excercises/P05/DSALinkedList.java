import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable, Serializable
{
    private class DSAListNode implements Serializable
    {
        /** ================== DSAListNode ================== **/

        /** Private Classfields **/
        private Object value;
            // The value of the list node
        private DSAListNode prev, next;
            // References to the previous and next nodes in the list

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inValue (Object)
        **      Export: Memory adress of new DSAListNode
        **   Assertion: Sets prev and next to null and sets value to given
        **                  value
        */
        public DSAListNode( Object inValue )
        {
            value = inValue;
            prev = null;
            next = null;
        }

        /** Accessors **/
        /*  --------------------------------------------------------------
        **   Submodule: getValue
        **      Import: none
        **      Export: value (Object)
        **   Assertion: Returns the value of the ListNode
        */
        public Object getValue()
        {
            return value;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getPrev
        **      Import: none
        **      Export: prev (DSAListNode)
        **   Assertion: Returns a reference to the previous node in the list
        */
        public DSAListNode getPrev()
        {
            return prev;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getNext
        **      Import: none
        **      Export: next (DSAListNode)
        **   Assertion: Returns a reference to the next node in the list
        */
        public DSAListNode getNext()
        {
            return next;
        }

        /** Mutators **/
        /*  --------------------------------------------------------------
        **   Submodule: setValue
        **      Import: inValue (Object)
        **      Export: none
        **   Assertion: Sets the value of the list node to the given value
        */
        public void setValue( Object inValue )
        {
            value = inValue;
        }

        /*  --------------------------------------------------------------
        **   Submodule: setPrev
        **      Import: inListNode (DSAListNode)
        **      Export: none
        **   Assertion: Sets the previous node to the given node
        */
        public void setPrev( DSAListNode inListNode )
        {
            prev = inListNode;
        }

        /*  --------------------------------------------------------------
        **   Submodule: setNext
        **      Import: inListNode (DSAListNode)
        **      Export: none
        **   Assertion: Sets the next node to the given node
        */
        public void setNext( DSAListNode inListNode )
        {
            next = inListNode;
        }
    }



    private class DSALinkedListIterator implements Iterator, Serializable
    {
        /** ================== DSALinkedListIterator ================== **/

        /** Private Classfield **/
        private DSAListNode iterNext;
            // Iteration cursor

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: linkedList (DSALinkedList)
        **      Export: Memory adress of new DSALinkedListIterator
        **   Assertion: Sets cursor to head of linked list
        */
        public DSALinkedListIterator( DSALinkedList linkedList )
        {
            iterNext = linkedList.head;
        }

        /** Iterator Interface Implementation **/
        /*  --------------------------------------------------------------
        **   Submodule: hasNext
        **      Import: none
        **      Export: (boolean)
        **   Assertion: Returns true if the cursor is not null and returns
        **                  false otherwise
        */
        public boolean hasNext()
        {
            return iterNext != null;
        }

        /*  --------------------------------------------------------------
        **   Submodule: next
        **      Import: none
        **      Export: value (Object)
        **   Assertion: Returns true if the cursor is not null and returns
        **                  false otherwise
        */
        public Object next()
        {
            Object value;

            if( iterNext == null )
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue();
                    //Get the value in the node
                iterNext = iterNext.getNext();
                    //Ready for subsequent calls to next()
            }

            return value;
        }

        /*  --------------------------------------------------------------
        **   Submodule: remove
        **      Import: none
        **      Export: none
        **   Assertion: Not supported, throws an exception
        */
        public void remove()
        {
            throw new UnsupportedOperationException( "Not supported" );
        }
    }



    /** ================== DSALinkedList ================== **/

    /** Private Classfields **/
    private DSAListNode head, tail;
        // References to the head and tail list nodes

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory address of new DSALinkedList Object
    **   Assertion: Sets head and tail references to null
    */
    public DSALinkedList()
    {
        head = null;
        tail = null;
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: isEmpty
    **      Import: none
    **      Export: empty (boolean)
    **   Assertion: Returns true if the head points to null, otherwise
    **                  returns false
    */
    public boolean isEmpty()
    {
        boolean empty = false;

        empty = head == null;

        return empty;
    }

    /*  --------------------------------------------------------------
    **   Submodule: peekFirst
    **      Import: none
    **      Export: nodeValue (Object)
    **   Assertion: Returns the value of the head node
    */
    public Object peekFirst()
    {
        Object nodeValue;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "List is empty" );
        }
        else
        {
            nodeValue = head.getValue();
        }

        return nodeValue;
    }

    /*  --------------------------------------------------------------
    **   Submodule: peekLast
    **      Import: none
    **      Export: nodeValue (Object)
    **   Assertion: Returns the value of the tail node
    */
    public Object peekLast()
    {
        Object nodeValue;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "List is empty" );
        }
        else
        {
            nodeValue = tail.getValue();
        }

        return nodeValue;
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: insertFirst
    **      Import: inValue (Object)
    **      Export: none
    **   Assertion: Inserts a new node at the head of the list with the
    **                  given value
    */
    public void insertFirst( Object inValue )
    {
        DSAListNode newNd = new DSAListNode( inValue );

        if( isEmpty() )
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            // Set the current head to the next of the new node, and set the
            // head to the new node
            newNd.setNext( head );
            head.setPrev( newNd );
            head = newNd;
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: insertLast
    **      Import: inValue (Object)
    **      Export: none
    **   Assertion: Inserts a new node at the tail of the list with the
    **                  given value
    */
    public void insertLast( Object inValue )
    {
        DSAListNode newNd = new DSAListNode( inValue );

        if( isEmpty() )
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            tail.setNext( newNd );
            newNd.setPrev( tail );
            tail = newNd;
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeFirst
    **      Import: none
    **      Export: nodeValue (Object)
    **   Assertion: Returns the value of the first node in the list and
    **                  removes the node
    */
    public Object removeFirst()
    {
        Object nodeValue;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "List is empty" );
        }
        else if( head.getNext() == null )
        {
            nodeValue = head.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = head.getValue();
            head = head.getNext();
        }

        return nodeValue;
    }

    /*  --------------------------------------------------------------
    **    Submodule: removeLast
    **      Import: none
    **      Export: nodeValue (Object)
    **   Assertion: Returns the value of the last node in the list and
    **                  removes the node
    */
    public Object removeLast()
    {
        Object nodeValue;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "List is empty" );
        }
        else if( tail.getPrev() == null )
        {
            nodeValue = tail.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = tail.getValue();
            tail.getPrev().setNext( null );
            tail = tail.getPrev();
        }

        return nodeValue;
    }

    /** Iteration **/
    /*  --------------------------------------------------------------
    **   Submodule: iterator
    **      Import: none
    **      Export: linkedListIterator (Iterator)
    **   Assertion: Returns a new instance of DSALinkedListIterator
    */
    public Iterator iterator()
    {
        return new DSALinkedListIterator( this );
    }
}
