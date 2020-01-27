import java.util.*;
import java.io.*;

public class UnitTestDSALinkedList
{
    public static void title( String text )
    {
        System.out.println( "\n==========================================\n\n"
            + "\u001b[1m" + text + "\u001b[0m \n" );
    }

    public static void main( String[] args )
    {
        int[] array = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            // Array to compare to
        int[] listArray = new int[10];
            // Array to put linkedList into through iteration
        DSALinkedList list = new DSALinkedList();

        title( "Populating list" );
        for( int i = 0; i < 10; i++ )
        {
            list.insertLast( i + 1 );
        }
        System.out.println( "Linked List: " + list.toString() );
        System.out.println( "Expected: " + Arrays.toString( array ) );
        System.out.println( "\n" );

        title( "Testing node removal" );
        testRemoval( list );
        System.out.println( "\n" );

        title( "Testing node insertion" );
        testInsertion( list );
        System.out.println( "\n" );

        title( "Testing for emptiness checks" );
        testEmpty( list );
        System.out.println( "\n" );

        System.out.println( "All tests completed" );
    }

    public static void testRemoval( DSALinkedList linkedList )
    {
        System.out.println( "Removing from the start" );
        System.out.println( "Removed " + linkedList.removeFirst() );
        System.out.println( linkedList.toString() );

        System.out.println( "Removing from the end" );
        System.out.println( "Removed " + linkedList.removeLast() );
        System.out.println( linkedList.toString() );
    }

    public static void testInsertion( DSALinkedList linkedList )
    {
        System.out.println( "Inserting at the start" );
        linkedList.insertFirst( new Integer( 0 ) );
        System.out.println( "Added " + linkedList.peekFirst() );
        System.out.println( linkedList.toString() );

        System.out.println( "Inserting at the end" );
        linkedList.insertLast( new Integer( 10 ) );
        System.out.println( "Added " + linkedList.peekLast() );
        System.out.println( linkedList.toString() );
    }

    public static void testEmpty( DSALinkedList linkedList )
    {
        System.out.println( "Testing for emptiness: " + linkedList.isEmpty()
            + ". (Expected: false)" );
        System.out.println( "Emptying list from the end" );
        for( int i = 0; i < 10; i++ )
        {
            linkedList.removeLast();
            System.out.println( linkedList.toString() );
        }
        System.out.println( "Testing for emptiness: " + linkedList.isEmpty()
            + ". (Expected: true)" );
        System.out.println( "Attempting to remove further" );
        try
        {
            linkedList.removeLast();
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception was thrown" );
        }
    }
}
