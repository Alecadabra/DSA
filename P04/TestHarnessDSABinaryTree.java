/*  ==============================================================
**      Author: Alec Maughan 19513869
**     Purpose: Test harness for binary search tree
**        Date: 27/8/19
**  ==============================================================
*/
import java.util.*;
import java.io.*;

public class TestHarnessDSABinaryTree
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner( System.in );
        int menu = '0';
        DSABinarySearchTree tree = new DSABinarySearchTree();

        while( menu != 8 )
        {
            System.out.print( "Select an option:\n\n[1] Run Test Harness\n"
                + "\n[2] Display Tree\n[3] Populate Tree\n\n[4] Read CSV\n"
                + "[5] Write CSV\n\n" + "[6] Read Serialized\n"
                + "[7] Write Serialized\n\n[8] Exit\n\n > " );
            menu = sc.nextInt();
            System.out.println( "=========================================\n" 
                );
            switch( menu )
            {
                case 1:
                {
                    // Run Test Harness
                    testHarness();
                    break;
                }
                case 2:
                {
                    // Display
                    tree.display();
                    break;
                }
                case 3:
                {
                    // Populate tree
                    for( int i = 0; i < 10; i++ )
                    {
                        tree.insert( ( i * 10 ),
                            ( i * 100 ) );
                    }
                    tree.display();
                    break;
                }
                case 4:
                {
                    // Read CSV
                    readCSV( tree );
                    break;
                }
                case 5:
                {
                    // Write CSV
                    writeCSV( tree );
                    break;
                }
                case 6:
                {
                    // Read Serialized
                    readSerial( tree );
                    break;
                }
                case 7:
                {
                    // Write Serialized
                    writeSerial( tree );
                    break;
                }
                case 8:
                {
                    // Exit
                    break;
                }
                default:
                {
                    System.out.println( "Invalid menu selection" );
                }
            }
            System.out.println( "=========================================\n" 
                );
        }
    }

    public static void testHarness()
    {
        DSABinarySearchTree tree = new DSABinarySearchTree();

        System.out.println( "\n=========================================\n" ); 
        System.out.println( "Populating tree" );
        for( int i = 1; i <= 10; i++ )
        {
            tree.insert( i, ( i * 10 ) );
            tree.insert( i * -1, ( i * -10 ) );
        }
        tree.display();

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting find()" );
        testFind( tree );

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting height()" );
        testHeight( tree );

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting insert()" );
        testInsert( tree );

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting delete()" );
        testDelete( tree );

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting traversals" );
        testTraversals( tree );

        System.out.println( "\n=========================================" );
        System.out.println( "\nTesting balance()" );
        testBalance( tree );
        System.out.println( "\n=========================================\n" );

        // display() not directly tested but used throughout test harness
    }

    public static void readCSV( DSABinarySearchTree tree )
    {
        String fileName;
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        Scanner sc = new Scanner( System.in );
        String line;
        String[] lineArr = new String[ 2 ];
        int key, value;

        System.out.print( "Enter file name: " );
        fileName = sc.nextLine();

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                lineArr = line.split(",");
                key = Integer.parseInt( lineArr[0] );
                value = Integer.parseInt( lineArr[1] );

                tree.insert( key, value );

                line = bfr.readLine();
            }
            bfr.close();

            System.out.println( "File read successfully" );
        }
        catch( IOException e )
        {
            System.out.println( "Error occurred\n" );
            e.printStackTrace();
            if( strm != null )
            {
                try
                {
                    strm.close();
                }
                catch( Exception e2 )
                {
                    System.out.println( "File cannot be closed :(" );
                }
            }
        }
    }

    public static void writeCSV( DSABinarySearchTree tree )
    {
        String fileName;
        FileOutputStream strm = null;
        PrintWriter pw;
        int order;
        Scanner sc = new Scanner( System.in );
        DSAQueue queue = new DSAQueue();
        int i = 1;

        System.out.println( "Enter file name: " );
        fileName = sc.nextLine();

        try
        {
            strm = new FileOutputStream( fileName );
            pw = new PrintWriter( strm );

            System.out.print( "Select traversal:\n[1] Preorder\n" +
                "[2] Inorder\n[3] Postorder\n > " );
            order = sc.nextInt();

            switch( order )
            {
                case 1:
                {
                    queue = tree.traversePreorder();
                    break;
                }
                case 2:
                {
                    queue = tree.traverseInorder();
                    break;
                }
                case 3:
                {
                    queue = tree.traversePostorder();
                    break;
                }
                default:
                {
                    System.out.println( "Invalid selection, using inorder" );
                    queue = tree.traverseInorder();
                }
            }

            for( Object o : queue )
            {
                pw.println( i + "," + o );
                i++;
            }

            pw.close();
            System.out.println( "File written successfully" );
        }
        catch( IOException e )
        {
            System.out.println( "Error occurred\n" );
            e.printStackTrace();
            if( strm != null )
            {
                try
                {
                    strm.close();
                }
                catch( Exception e2 )
                {
                    System.out.println( "File cannot be closed :(" );
                }
            }
        }
    }

    public static void readSerial( DSABinarySearchTree tree )
    {
        FileInputStream fileStrm = null;
        ObjectInputStream objStrm;
        String fileName;
        Scanner sc = new Scanner( System.in );

        System.out.print( "Enter file name: " );
        fileName = sc.nextLine();

        try
        {
            fileStrm = new FileInputStream( fileName );
            objStrm = new ObjectInputStream( fileStrm );
            tree = ( DSABinarySearchTree )objStrm.readObject();

            objStrm.close();
            System.out.println( "Tree read successfully" );
        }
        catch( ClassNotFoundException e )
        {
            System.out.println( 
                "Error occured, file is not the correct class" );
            if( fileStrm != null )
            {
                try
                {
                    fileStrm.close();
                }
                catch( Exception e2 )
                {        System.out.print( "Enter file name: " );
        fileName = sc.nextLine();


                    System.out.println( "File cannot be closed :(" );
                }
            }
        }
        catch( IOException e )
        {
            System.out.println( "Error occured" );
            e.printStackTrace();
            if( fileStrm != null )
            {
                try
                {
                    fileStrm.close();
                }
                catch( Exception e2 )
                {
                    System.out.println( "File cannot be closed :(" );
                }
            }
        }
    }

    public static void writeSerial( DSABinarySearchTree tree )
    {
        FileOutputStream fileStrm = null;
        ObjectOutputStream objStrm;
        String fileName;
        Scanner sc = new Scanner( System.in );

        System.out.print( "Enter file name: " );
        fileName = sc.nextLine();

        try
        {
            fileStrm = new FileOutputStream( fileName );
            objStrm = new ObjectOutputStream( fileStrm );
            objStrm.writeObject( tree );

            objStrm.close();
            System.out.println( "Tree saved successfully" );
        }
        catch( IOException e )
        {
            System.out.println( "Error occured" );
            e.printStackTrace();
            if( fileStrm != null )
            {
                try
                {
                    fileStrm.close();
                }
                catch( Exception e2 )
                {
                    System.out.println( "File cannot be closed :(" );
                }
            }
        }
    }

    public static void testFind( DSABinarySearchTree tree )
    {
        int value;

        System.out.println( "Searching for key in the tree" );
        System.out.print( "Searching for key 3" );
        value = (int)tree.find( 3 );
        System.out.println( ", found value " + value + 
            " at key 3, expected: 30" );
        
        System.out.println( "\nSearching for key not in the tree" );
        try
        {
            tree.find( 99 );
            System.out.println( "Test failed - no exception thrown" );
        }
        catch( NoSuchElementException e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testHeight( DSABinarySearchTree tree )
    {
        int height;

        height = tree.height();
        System.out.println( "Height is " + height + ", expected: 10" );
    }

    public static void testInsert( DSABinarySearchTree tree )
    {
        int value;

        System.out.println( "Currently key 20 isnt in the tree;" );
        System.out.println( "\tAttempting to find() key 20" );
        try
        {
            tree.find( 20 );
            System.out.println( "\tTest failed - no exception thrown" );
        }
        catch( Exception e )
        {
            System.out.println( "\tAnd an exception was thrown" );
        }
        System.out.println( "\tCurrent height is " + tree.height() );

        System.out.println( "Now adding key 20" );
        tree.insert( 20, 200 );
        System.out.println( "\tAttempting to find() key 20 now" );
        try
        {
            value = (int)tree.find( 20 );
            System.out.println( 
                "\tNo exception thrown as expected, and value found: "
                + value + ", expected: 200" );
        }
        catch( Exception e )
        {
            System.out.println( "\tTest failed - exception was thrown" );
        }
        System.out.println( "\tHeight is now " + tree.height() );
   }

    public static void testDelete( DSABinarySearchTree tree )
    {
        System.out.print( "Tree before: " );
        tree.display();
        System.out.println( "Deleting key 20 (Value 200)" );
        tree.delete( 20 );
        System.out.println( "Tree after: " );
        tree.display();
    }

    public static void testTraversals( DSABinarySearchTree tree )
    {
        DSAQueue queue = new DSAQueue();
        System.out.println( "Preorder traversal:" );
        queue = tree.traversePreorder();
        for( Object o : queue )
        {
            System.out.print( o + " " );
        }
        System.out.println();

        queue = new DSAQueue();
        System.out.println( "Inorder traversal:" );
        queue = tree.traverseInorder();
        for( Object o : queue )
        {
            System.out.print( o + " " );
        }
        System.out.println();

        queue = new DSAQueue();
        System.out.println( "Postorder traversal:" );
        queue = tree.traversePostorder();
        for( Object o : queue )
        {
            System.out.print( o + " " );
        }
        System.out.println();
    }

    public static void testBalance( DSABinarySearchTree tree )
    {
        System.out.println( "Current balance: " + tree.balance() );
        System.out.println( 
            "Adding one element to the right, should balance to 100%" );
        tree.insert( 20, 200 );
        System.out.println( "Balance: " + tree.balance() );
        System.out.println( "Going to add a bunch more to the right side " +
            "and delete from the left to bring balance to 0%" );
        for( int i = 1; i < 11; i++ )
        {
            System.out.print( "\nTree currently: " );
            tree.display();
            tree.delete( -1 * i );
            tree.insert( ( 30 * i ), ( 300 * i ) );
            System.out.println( "Removed from left and added to right\n" +
                "Balance: " + tree.balance() );
        }
    }

}
