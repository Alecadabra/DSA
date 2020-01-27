/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: Implementation of priority max heap ADT
**     Date: 27/10/19
**Self-Cite: This code was previously submitted for Prac 7
**  ==========================================================================
*/

import java.util.*;

public class DSAHeap
{
    private class DSAHeapEntry
    {
        /** ================== DSAHeapEntry ================== **/

        /** Private Classfields **/
        private int priority;
            // Priority to order by
        private Object value;
            // Value stored in entry

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inPriority (int), inValue (Object)
        **      Export: Memory adress of new DSAHeapEntry
        **   Assertion: Sets state to given values
        */
        public DSAHeapEntry( int inPriority, Object inValue )
        {
            priority = inPriority;
            value = inValue;
        }

        public String toString()
        {
            return ( priority + "," + value );
        }
    }

    /** ================== DSAHeap ================== **/

    /** Private Classfields **/
    private DSAHeapEntry[] heap;
        // Array representation of heap
    private int count;
        // No. of entries in array

    /** Constructors **/
    /*  --------------------------------------------------------------
    ** Constructor: Alternate
    **      Import: maxSize (int)
    **      Export: Memory adress of new DSAHeap
    **   Assertion: Initializes array to given size and count to zero
    */
    public DSAHeap( int maxSize )
    {
        heap = new DSAHeapEntry[ maxSize ];
        count = 0;
    }

    /*  --------------------------------------------------------------
    ** Constructor: Copy
    **      Import: inHeap (DSAHeap)
    **      Export: Memory adress of new DSAHeap
    **   Assertion: Uses copy of state of import
    */
    public DSAHeap( DSAHeap inHeap )
    {
        heap = inHeap.getArray();
        count = inHeap.getCount();
    }

    /** Getters / Setter **/
    /*  --------------------------------------------------------------
    **   Submodule: add()
    **      Import: priority (int), value (Object)
    **      Export: none
    **   Assertion: Adds an entry to the heap, uses trickleUp() 
    **                  recursive algorithm
    */
    public void add( int priority, Object value )
    {
        heap[count] = new DSAHeapEntry( priority, value );

        trickleUp( count );
        count++;
    }

    /*  --------------------------------------------------------------
    **   Submodule: remove()
    **      Import: none
    **      Export: (Object)
    **   Assertion: Removes the highest priority entry from the top
    **                  of the heap, uses trickleDown() recursive
    **                  algorithm
    */
    public Object remove()
    {
        Object retValue = heap[0].value;
        count--;

        heap[0] = heap[count];
        heap[count] = null;
        trickleDown( 0 );

        return retValue;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getCount()
    **      Import: none
    **      Export: count (int)
    **   Assertion: Returns the count of entries in heap
    */
    public int getCount()
    {
        return count;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getArray
    **      Import: none
    **      Export: copyArray (DSAHeapEntry[])
    **   Assertion: Returns copy of the heap array
    */
    public DSAHeapEntry[] getArray()
    {
        return heap.clone();
    }

    /** Heap Sort **/
    /*  --------------------------------------------------------------
    **   Submodule: heapSort()
    **      Import: values (Object[])
    **      Export: none
    **   Assertion: Implementation of max heap sort, an O(N log N) sort.
    **                  Takes import as a 2D array, at each element is a
    **                  2-element Object array, [0] is an Integer
    **                  representing priority and [1] is the value Object
    */
    public DSAHeapEntry[] heapSort( Object[][] arr )
    {
        DSAHeapEntry temp;
        count = arr.length;
        heap = new DSAHeapEntry[ count ];

        if( arr[0][0] instanceof Integer )
        {
            // Import is 2D array of priority ints and values
            for( int i = 0; i < count; i++ )
            {
                heap[i] = new DSAHeapEntry( (int)arr[i][0], arr[i][1] );
            }
        }
        else
        {
            throw new IllegalArgumentException(
                "Heap sort import must be an array of 2 element arrays of" +
                " priority integers and Object values" );
        }

        // Heapify the array
        for( int i = ( count / 2 ) - 1; i >= 0; i-- )
        {
            trickleDown( i );
        }

        // Heap sort
        for( int i = count - 1; i >= 1; i-- )
        {
            /* Swap top entry with i'th entry */
            temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;

            // trickleDown() must use i as the value for count
            count = i;

            // Trickle down the top entry
            trickleDown( 0 );
        }

        count = arr.length;

        return heap;

    }

    /** Private Trickle Up / Down **/
    /*  --------------------------------------------------------------
    **   Submodule: trickleUp()
    **      Import: idx (int)
    **      Export: none
    **   Assertion: Recursive algorithm to trickle up an entry based
    **                  on priority of itself and it's parent
    */
    private void trickleUp( int idx )
    {
        int parentIdx = getParentIdx( idx );
        DSAHeapEntry temp;

        if( ( idx > 0 ) && 
            ( heap[idx].priority > heap[parentIdx].priority ) )
        {
            // Swap current index with parent
            temp = heap[parentIdx];
            heap[parentIdx] = heap[idx];
            heap[idx] = temp;

            // Recurse
            trickleUp( parentIdx );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: trickleDown()
    **      Import: idx (int)
    **      Export: none
    **   Assertion: Recursive algorithm to trickle down an entry based
    **                  on priority of itself and it's children
    */
    private void trickleDown( int idx )
    {
        int leftIdx = getLeftChildIdx( idx );
        int rightIdx = getRightChildIdx( idx );
        int largeIdx;
        DSAHeapEntry temp;

        if( leftIdx < count )
        {
            // Finding largest of two children of current index
            largeIdx = leftIdx;
            if( rightIdx < count )
            {
                if( heap[leftIdx].priority < heap[rightIdx].priority )
                {
                    largeIdx = rightIdx;
                }
            }
            if( heap[largeIdx].priority > heap[idx].priority )
            {
                // Swap current index with larger child
                temp = heap[largeIdx];
                heap[largeIdx] = heap[idx];
                heap[idx] = temp;

                // Recurse
                trickleDown( largeIdx );
            }
        }
    }

    /** Private Helper Functions **/
    /*  --------------------------------------------------------------
    **   Submodule: leftChildIdx()
    **      Import: idx (int)
    **      Export: (int)
    **   Assertion: Uses arithmetic to determine the index of the 
    **                  left child of the given index
    */
    private int getLeftChildIdx( int idx )
    {
        return ( idx * 2 ) + 1;
    }

    /*  --------------------------------------------------------------
    **   Submodule: rightChildIdx()
    **      Import: idx (int)
    **      Export: (int)
    **   Assertion: Uses arithmetic to determine the index of the 
    **                  right child of the given index
    */
    private int getRightChildIdx( int idx )
    {
        return ( idx * 2 ) + 2;
    }

    /*  --------------------------------------------------------------
    **   Submodule: parentIdx()
    **      Import: idx (int)
    **      Export: (int)
    **   Assertion: Uses arithmetic to determine the index of the 
    **                  parent of the given index
    */
    private int getParentIdx( int idx )
    {
        return ( idx - 1 ) / 2;
    }
}
