import java.util.*;
import java.io.*;

public class UnitTestDSAHeap
{
    public static void main( String[] args )
    {
        DSAHeap heap = new DSAHeap( 7100 );

        title( "Populating heap" );
        populate( heap );

        title( "Testing add(), remove() and getCount()" );
        testOne( heap );

        title( "Testing heapSort()" );
        testSort( heap );

        title( "" );
    }

    public static void populate( DSAHeap heap )
    {
        System.out.println( "Using RandomNames7000.csv" );
        load( heap, "RandomNames7000.csv" );

        System.out.println( "\nLoad Successful" );
    }

    public static void testOne( DSAHeap heap )
    {
        System.out.println( "Initial count of heap entries: " + 
            heap.getCount() + " (Expected: 7000)\n" );

        System.out.println( 
            "Adding a new entry with a priority higher than anything else" +
            " (99999999, \"Test Entry\")" );
        heap.add( 99999999, "Test Entry" );
        System.out.println( "Count now: " + heap.getCount() +
            " (Expected: 7001)" );

        System.out.println( "Removing the highest priority entry, " +
            "should remove the test entry that was just added" );
        System.out.println( "Removed entry: " + heap.remove() +
            " (Expected: Test Entry)" );
        System.out.println( "Count now: " + heap.getCount() +
            " (Expected: 7000)" );
    }

    public static void testSort( DSAHeap heap )
    {
        System.out.println( "Sorting RandomNames7000" );

        FileInputStream strm;
        InputStreamReader rdr;
        BufferedReader bfr;
        Object[][] arr = new Object[7000][2];
        Object[] sortedArr;
        String line;
        int idx = 0;
        Object[][] arr2 = new Object[15][2];
        int temp;

        try
        {
            strm = new FileInputStream( "RandomNames7000.csv" );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                arr[idx][0] = new Integer( line.split(",")[0] );
                arr[idx][1] = line.split(",")[1];
                idx++;
                line = bfr.readLine();
            }

            bfr.close();
        }
        catch( Exception e )
        {
            System.out.println( "File Error" );
            e.printStackTrace();
        }

        sortedArr = heap.heapSort( arr );

        System.out.println( "\nFirst 5 elements:" );
        for( int i = 0; i < 5; i++ )
        {
            System.out.println( sortedArr[i].toString() );
        }

        System.out.println( "\nLast 5 elements:" );
        for( int i = 6995; i < 7000; i++ )
        {
            System.out.println( sortedArr[i].toString() );
        }

        System.out.println( 
            "\nSorting a smaller, randomly generated data set with a " +
            "visualisation" );

        for( int i = 0; i < 15; i++ )
        {
            temp = (int)( Math.random() * 10 + 1 );
            arr2[i][0] = temp;
            arr2[i][1] = temp;
        }

        System.out.println( "\nBefore:" );
        for( int i = 0; i < 15; i++ )
        {
            System.out.printf( "%02d  ", i );
            for( int j = 0; j < (int)arr2[i][0]; j++ )
            {
                System.out.print( "-" );
            }
            System.out.println();
        }
 
        sortedArr = heap.heapSort( arr2 );

        System.out.println( "\nAfter sorting:" );
        for( int i = 0; i < 15; i++ )
        {
            System.out.printf( "%02d  ", i );
            for( int j = 0; j < Integer.parseInt( 
                ( sortedArr[i].toString() ).split(",")[0] ); j++ )
            {
                System.out.print( "-" );
            }
            System.out.println();
        }
        System.out.println( "Look at that! :)" );
    }

    public static void title( String text )
    {
        System.out.println( "\n==========================================\n\n"
            + "\u001b[1m" + text + "\u001b[0m \n" );
    }

    public static void load( DSAHeap heap, String fileName )
    {
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        DSALinkedList lines = new DSALinkedList();
        String line;

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                lines.insertLast( line );

                line = bfr.readLine();
            }

            bfr.close();
        }
        catch( FileNotFoundException e )
        {
            throw new IllegalArgumentException( 
                "File " + fileName + " not found" );
        }
        catch( IOException e )
        {
            if( strm != null )
            {
                try
                {
                    strm.close();
                }
                catch( IOException e2 )
                {
                    throw new IllegalArgumentException(
                        "File reading error, file cannout be closed" );
                }
            }

            throw new IllegalArgumentException( "File reading error" );
        }

        for( Object o : lines )
        {
            heap.add( Integer.parseInt( ( (String)o ).split(",")[0] ),
                ( (String)o ).split(",")[1] );
        }
    }

}
