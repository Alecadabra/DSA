import java.util.*;
import java.io.*;

public class HashTableTest
{
    public static void main( String[] args )
    {
        HashMap<String, String> table = new HashMap<String, String>();

        title( "Populating table"  );
        populate( table, "RandomNames7000.csv" );

        title( "Testing get()" );
        testGet( table );

        title( "Testing containsKey()" );
        testHasKey( table );

        title( "Testing put() and remove()" );
        testVarious( table );

        title( "End of Test Harness" );
    }

    public static void title( String text )
    {
        System.out.println( 
            "\n=========================================================\n" +
            "\u001b[1m" + text + "\u001b[0m\n" );
    }

    public static void populate( HashMap<String, String> table, String fileName )
    {
        System.out.println( "Loading from " + fileName );

        loadCSV( table, fileName );

        System.out.println( "\nLoad successful" );
    }

    public static void testGet( HashMap<String, String> table )
    {
        System.out.println( "Testing for existing key 14495655" );
        System.out.println( "Value found: " + table.get( "14495655" ) +
            " (Expected: Sofia Bonfiglio)\n" );

        System.out.println( "Testing for non existent key 19513869" );
        if( table.get( "19513869" ) == null )
        {
            System.out.println( "Returned null thrown as expected" );
        }
        else
        {
            System.out.println( "Didn't work :(" );
        }
    }

    public static void testHasKey( HashMap<String, String> table )
    {
        System.out.println( "Testing for existing key 14490422: " + 
            table.containsKey( "14490422" ) + " (Expected: true)\n" );

        System.out.println( "Testing for non existant key 19513869: " +
            table.containsKey( "19513869" ) + " (Expected: false)" );
    }

    public static void testVarious( HashMap<String, String> table )
    {
        System.out.println( 
            "Adding an entry with put() (Key: \"ABC\", Value: \"XYZ\")" );
        table.put( "ABC", "XYZ" );

        System.out.println( "\nDeleting the ABC XYZ entry with remove()" );
        table.remove( "ABC" );

        System.out.println( 
            "Attempting to remove an entry that does not exist (Key \"123\")" 
            );
        if( table.remove( "123" ) == null )
        {
            System.out.println( "Returned null thrown as expected" );
        }
        else
        {
            System.out.println( "Didn't work :(" );
        }
    }

    public static void loadCSV( HashMap<String, String> table, String fileName )
    {
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        LinkedList<String> lines = new LinkedList<>();
        String line;
        int dupes = 0;

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                lines.addLast( line );

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

        for( String s : lines )
        {
            try
            {
                table.put( s.split(",")[0],
                    s.split(",")[1] );
            }
            catch( IllegalArgumentException e )
            {
                dupes++;
            }
        }

        if( dupes != 0 )
        {
            System.out.println( "File IO Message: " + 
                dupes + " duplicate keys found in file" );
        }
    }
}
