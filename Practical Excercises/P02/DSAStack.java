import java.util.*;

public class DSAStack
{
    // Private Classfields
    private Object[] stack;
    private int count;

    // Class Constants
    private static final int DEFAULT_CAPACITY = 100;

    // Default Constructor
    public DSAStack()
    {
        stack = new Object[ DEFAULT_CAPACITY ];
        count = 0;
    }

    // Alternate Constructor
    public DSAStack( int capacity )
    {
        if( capacity <= 0 )
        {
            throw new IllegalArgumentException( 
                "Capacity must be greater than zero" );
        }
        else
        {
            stack = new Object[ capacity ];
            count = 0;
        }
    }

    // Accessors

    public Object top()
    {
        Object topVal;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "Stack is empty" );
        }
        else
        {
            topVal = stack[ count - 1 ];
        }

        return topVal;
    }

    public int getCount()
    {
        return count;
    }

    public boolean isEmpty()
    {
        boolean empty = false;

        if( count == 0 )
        {
            empty = true;
        }

        return empty;
    }

    public boolean isFull()
    {
        boolean full = false;

        if( count == stack.length )
        {
            full = true;
        }

        return full;
    }

    // Mutators

    public void push( Object inObject )
    {
        if( isFull() )
        {
            throw new IllegalArgumentException( "Stack is full" );
        }
        else
        {
            stack[ count ] = inObject;
            count++;
        }
    }

    public Object pop()
    {
        Object topVal;

        topVal = top();
        count--;

        return topVal;
    }
}
