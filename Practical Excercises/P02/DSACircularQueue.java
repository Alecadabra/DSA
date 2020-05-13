import java.util.*;

public class DSACircularQueue extends DSAQueue
{
    // Private Classfields

    private int frontIdx;
    private int count;

    // Constructors
    
    public DSACircularQueue()
    {
        super();
        frontIdx = 0;
        count = 0;
    }

    public DSACircularQueue( int capacity )
    {
        super( capacity );
        frontIdx = 0;
        count = 0;
    }

    // Accessors

    public Object peek()
    {
        Object frontVal;

        if( isEmpty() )
        {
            throw new IllegalArgumentException( "Queue is empty" );
        }
        else
        {
            frontVal = super.queue[ frontIdx ];
        }

        return frontVal;
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

        if( count == super.queue.length )
        {
            full = true;
        }

        return full;
    }

    public int getCount()
    {
        return count;
    }

    // Mutators

    public void enqueue( Object inObject )
    {
        if( isFull() )
        {
            throw new IllegalArgumentException( "Queue is full" );
        }
        else
        {
            super.queue[ endIdx ] = inObject;
            count++;

            if( endIdx == super.queue.length - 1 )
            {
                // If queue needs to loop around for next enqueue
                endIdx = 0;
            }
            else
            {
                // If end index can increment normally
                endIdx++;
            }
        }
    }

    public Object dequeue()
    {
        Object frontVal;

        frontVal = peek();
        count--;

        if( frontIdx == super.queue.length - 1 )
        {
            // If the queue needs to loop around backwards for next dequeue
            frontIdx = 0;
        }
        else
        {
            // If front index can decrement normally
            frontIdx++;
        }

        return frontVal;
    }
}
