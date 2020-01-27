import java.util.*;

public class QueueTest
{
    public static void main( String[] args )
    {
        DSAQueue<Integer> queue = new DSAQueue<Integer>();

        //Populating Queue
        for( int i = 0; i < 5; i++ )
        {
            queue.add( i );
        }

        // Iterator test
        System.out.println( "\nTesting iteraor" );
        System.out.print( "Elements in queue: " );
        for( int i : queue )
        {
            System.out.print( i + " " );
        }
        System.out.println( "\nExpected: 4 3 2 1 0" );


        // Peek/Remove Test
        System.out.println( "\nTesting peek and remove" );
        String outStr = new String();
        queue.peek();
        if( queue.size() == 5 )
        {
            outStr += "Peek works";
        }
        else
        {
            outStr += "Peek doesn't work";
        }
        outStr += " (Expected " + 5 + ", actual " + queue.size() + ") ";

        queue.remove();
        if( queue.size() == 4 )
        {
            outStr += "Remove works";
        }
        else
        {
            outStr += "Remove doesn't work";
        }
        outStr += " (Expected " + 4 + ", actual " + queue.size() + ") ";
        System.out.println( outStr + "\n" );

        // Empty Test
        System.out.println( "Testing emptiness" );
        while( !queue.isEmpty() )
        {
            System.out.println( "Removing value " + queue.size() );
            queue.remove();
        }
        System.out.println( "It's empty" );

        System.out.println( "Attempting to remove further" );
        try
        {
            queue.remove();
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception was thrown" );
        }

        System.out.println( "All tests completed\n" );
   }
}
