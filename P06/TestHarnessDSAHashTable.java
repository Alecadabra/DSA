import java.util.*;

public class TestHarnessDSAHashTable
{
    public static void main( String[] args )
    {
        DSAHashTable table = new DSAHashTable( 7000 );
        DSAHashTable table2 = new DSAHashTable( 7000 );

        title( "Populating table"  );
        populate( table, "RandomNames7000.csv" );

        title( "Testing get()" );
        testGet( table );

        title( "Testing hasKey()" );
        testHasKey( table );

        title( "Testing put(), remove() and getLoadFactor()" );
        testVarious( table );

        title( "Testing export()" );
        testExport( table );

        title( "Running test harness again with a new table " +
            "loaded from this file" );

        title( "Populating new table with file created with export()" );
        populate( table2, "saved.csv" );

        title( "Testing get() with new table" );
        testGet( table2 );

        title( "Testing hasKey() with new table" );
        testHasKey( table2 );

        title( "Testing put(), remove() and getLoadFactor() with new table" );
        testVarious( table2 );

        title( "End of Test Harness" );

    }

    public static void title( String text )
    {
        System.out.println( 
            "\n=========================================================\n" +
            "\u001b[1m" + text + "\u001b[0m \n" );
    }

    public static void populate( DSAHashTable table, String fileName )
    {
        System.out.println( "Loading from " + fileName );

        FileIOHashTable.loadCSV( table, fileName );

        System.out.println( "\nLoad successful" );
    }

    public static void testGet( DSAHashTable table )
    {
        System.out.println( "Testing for existing key 14495655" );
        System.out.println( "Value found: " + table.get( "14495655" ) +
            " (Expected: Sofia Bonfiglio)\n" );

        System.out.println( "Testing for non existent key 19513869" );
        try
        {
            table.get( "19513869" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testHasKey( DSAHashTable table )
    {
        System.out.println( "Testing for existing key 14490422: " + 
            table.hasKey( "14490422" ) + " (Expected: true)\n" );

        System.out.println( "Testing for non existant key 19513869: " +
            table.hasKey( "19513869" ) + " (Expected: false)" );
    }

    public static void testVarious( DSAHashTable table )
    {
        System.out.printf( "Initial load factor: %.5f \n\n", 
            table.getLoadFactor() );

        System.out.println( 
            "Adding an entry with put() (Key: \"ABC\", Value: \"XYZ\")" );
        table.put( "ABC", "XYZ" );
        System.out.printf( 
            "Load factor after addition: %.5f (Expected to be higher)\n\n",
            table.getLoadFactor() );

        System.out.println( 
            "Attempting to add this entry a second time (Duplicate entry)" );
        try
        {
            table.put( "ABC", "XYZ" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }

        System.out.println( "\nDeleting the ABC XYZ entry with remove()" );
        table.remove( "ABC" );
        System.out.printf( ( "Load factor after removal: %.5f " + 
            "(Expected to be back to original)\n\n" ), 
            table.getLoadFactor() );

        System.out.println( 
            "Attempting to remove an entry that does not exist (Key \"123\")" 
            );
        try
        {
            table.remove( "123" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testExport( DSAHashTable table )
    {
        System.out.println( "Using export to save table state to saved.csv" );

        FileIOHashTable.saveCSV( table, "saved.csv" );
    }
}
