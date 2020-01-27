import java.util.*;

public class UnitTestDSAQueue
{
    public static void main( String[] args )
    {
        DSAQueue queue = new DSAQueue();

        //Populating Queue
        for( int i = 0; i < 5; i++ )
        {
            queue.enqueue( i );
        }

        // Peek/Dequeue Test
        System.out.println( "\nTesting peek and dequeue" );
        String outStr = new String();
        queue.peek();
        if( queue.getCount() == 5 )
        {
            outStr += "Peek works";
        }
        else
        {
            outStr += "Peek doesn't work";
        }
        outStr += " (Expected " + 5 + ", actual " + queue.getCount() + ") ";

        queue.dequeue();
        if( queue.getCount() == 4 )
        {
            outStr += "Dequeue works";
        }
        else
        {
            outStr += "Dequeue doesn't work";
        }
        outStr += " (Expected " + 4 + ", actual " + queue.getCount() + ") ";
        System.out.println( outStr + "\n" );

        // Empty Test
        System.out.println( "Testing emptiness" );
        while( !queue.isEmpty() )
        {
            System.out.println( "Deqeuing value " + queue.getCount() );
            queue.dequeue();
        }
        System.out.println( "It's empty" );

        System.out.println( "Attempting to remove further" );
        try
        {
            queue.dequeue();
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception was thrown" );
        }

        System.out.println( "All tests completed\n" );
   }
}
