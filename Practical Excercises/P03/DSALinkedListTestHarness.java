import java.util.*;
import java.io.*;

public class DSALinkedListTestHarness
{
    public static void main( String[] args )
    {
        boolean exit = false;
        int menu = '0';
        Scanner sc = new Scanner( System.in );
        DSALinkedList list = new DSALinkedList();

        while( !exit )
        {
            System.out.print( 
                "=========== DSA Linked List Test Harness ===========\n\n" +
                "Test Harnesses:\n" +
                "[1] Run linked list test harness\n[2] Run stack test harness"
                + "\n[3] Run queue test harness\n\nSerialization:\n" +
                "[4] Read a serialized file\n[5] Display the list\n" +
                "[6] Write a serialized file\n[7] Create sample linked list\n"
                + "[8] Exit\n\nSelect an option: " );
            try
            {
                menu = sc.nextInt();
                System.out.println( 
                    "\n====================================================\n" 
                    );
                switch( menu )
                {
                    case 1:
                    {
                        // Run test harness for linked list
                        testLinkedList();
                        break;
                    }
                    case 2:
                    {
                        // Run test harness for stack
                        testStack();
                        break;
                    }
                    case 3:
                    {
                        // Run test harness for queue
                        testQueue();
                        break;
                    }
                    case 4:
                    {
                        // Read a serialized file
                        list = loadListUI( list );
                        break;
                    }
                    case 5:
                    {
                        // Display linked list
                        System.out.println( linkedListToString( list ) );
                        break;
                    }
                    case 6:
                    {
                        // Write a serialized file
                        saveListUI( list );
                        break;
                    }
                    case 7:
                    {
                        // Create sample linked list
                        int[] array = new int[]{ 1, 2, 3, 4, 5 };
                        populateList( list, array );
                        break;
                    }
                    case 8:
                    {
                        // Exit
                        exit = true;
                        break;
                    }
                    default:
                    {
                        // Invalid menu selection
                        throw new IllegalArgumentException( 
                            "Invalid menu option" );
                    }
                }
            }
            catch( Exception e )
            {
                System.out.println( "Error: " + e.getMessage() );
            }

            System.out.println();
        }

        System.out.println( "Bye :)" );
    }

    /** Test Harnesses */
    public static void testLinkedList()
    {
        int[] array = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            // Array to compare to
        int[] listArray = new int[10];
            // Array to put linkedList into through iteration
        DSALinkedList linkedList = new DSALinkedList();

        System.out.println( "Populating list" );
        populateList( linkedList, array );
        System.out.println( "Linked List: " + 
            linkedListToString( linkedList ) );
        System.out.println( "Expected: " + Arrays.toString( array ) );
        System.out.println( "\n" );

        System.out.println( "Testing node removal" );
        testRemoval( linkedList );
        System.out.println( "\n" );

        System.out.println( "Testing node insertion" );
        testInsertion( linkedList );
        System.out.println( "\n" );

        System.out.println( "Testing for emptiness checks" );
        testEmpty( linkedList );
        System.out.println( "\n" );

        System.out.println( "All tests completed" );
    }

    public static void testStack()
    {
        DSAStack stack = new DSAStack();

        //Populating Stack
        for( int i = 0; i < 5; i++ )
        {
            stack.push( i );
        }

        // Pop/Top Test
        System.out.println( "Testing pop and top" );
        String outStr = new String();
        stack.top();
        if( stack.getCount() == 5 )
        {
            outStr += "Top works";
        }
        else
        {
            outStr += "Top doesn't work";
        }
        outStr += " (Expected " + 5 + ", actual " + stack.getCount() + ") ";

        stack.pop();
        if( stack.getCount() == 4 )
        {
            outStr += "Pop works";
        }
        else
        {
            outStr += "Pop doesn't work";
        }
        outStr += " (Expected " + 4 + ", actual " + stack.getCount() + ") ";
        System.out.println( outStr + "\n\n" );

        // Empty Test
        System.out.println( "Testing emptiness" );
        while( !stack.isEmpty() )
        {
            System.out.println( "Popping value " + stack.getCount() );
            stack.pop();
        }
        System.out.println( "It's empty" );

        System.out.println( "Attempting to remove further" );
        try
        {
            stack.pop();
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception was thrown" );
        }

        System.out.println( "All tests completed" );
    }

    public static void testQueue()
    {
        DSAQueue queue = new DSAQueue();

        //Populating Queue
        for( int i = 0; i < 5; i++ )
        {
            queue.enqueue( i );
        }

        // Peek/Dequeue Test
        System.out.println( "Testing peek and dequeue" );
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
        System.out.println( outStr + "\n\n" );

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

        System.out.println( "All tests completed" );
   }

    /** Serialization **/
    /*  --------------------------------------------------------------
    **   Submodule: save
    **      Import: objToSave (DSALinkedList), fileName (String)
    **      Export: none
    **   Assertion: Saves Linked List object to file using serialization
    */
    public static void save( DSALinkedList objToSave, String fileName )
    {
        FileOutputStream fileStrm;
            // File IO object
        ObjectOutputStream objStrm;
            // Serialization object

        try
        {
            fileStrm = new FileOutputStream( fileName );
            objStrm = new ObjectOutputStream( fileStrm );
            objStrm.writeObject( objToSave );

            objStrm.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
            System.out.println( "================" );
            System.out.println( e.getMessage() );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: load
    **      Import: fileName (String)
    **      Export: inObj (DSALinkedList)
    **   Assertion: Loads Linked list from file using serialization
    */
    public static DSALinkedList load( String fileName )
    {
        FileInputStream fileStrm;
            // File IO object
        ObjectInputStream objStrm;
            // Serialization object
        DSALinkedList inObj;
            // Linked list object to load to

        try
        {
            fileStrm = new FileInputStream( fileName );
            objStrm = new ObjectInputStream( fileStrm );
            inObj = ( DSALinkedList )objStrm.readObject();

            objStrm.close();
        }
        catch( ClassNotFoundException e )
        {
            throw new IllegalArgumentException( 
                "DSALinkedList class not found " + e.getMessage() );
        }
        catch( IOException e )
        {
            throw new IllegalArgumentException( 
                "Unable to load object from file" );
        }

        return inObj;
    }

    /** Assorted helper methods for test harnesses **/
    public static DSALinkedList loadListUI( DSALinkedList list )
    {
        String fileName;
        Scanner sc = new Scanner( System.in );

        System.out.println( "Provide a file name to load from:" );
        fileName = sc.nextLine();

        return load( fileName );
    }

    public static void saveListUI( DSALinkedList list )
    {
        String fileName;
        Scanner sc = new Scanner( System.in );

        System.out.println( "Provide a file name to save to:" );
        fileName = sc.nextLine();

        save( list, fileName );

        System.out.println( "Save successful" );
    }   

    public static void populateList( DSALinkedList linkedList, int[] array )
    {
        for( int i = 0; i < array.length; i++ )
        {
            linkedList.insertLast( array[i] );
        }
    }

    public static int[] listToArray( DSALinkedList linkedList )
    {
        Iterator iter = linkedList.iterator();
        int i = 0;
        int[] listArray = new int[ getLinkedListSize( linkedList ) ];
        while( iter.hasNext() )
        {
            listArray[i] = (int)iter.next();
            i++;
        }

        return listArray;
    }

    public static String linkedListToString( DSALinkedList linkedList )
    {
        int[] array = new int[ getLinkedListSize( linkedList ) ];
        array = listToArray( linkedList );

        return Arrays.toString( array );
    }

    public static int getLinkedListSize( DSALinkedList linkedList )
    {
        int size = 0;

        for( Object o : linkedList )
        {
            size++;
        }

        return size;
    }

    public static void testRemoval( DSALinkedList linkedList )
    {
        System.out.println( "Removing from the start" );
        System.out.println( "Removed " + linkedList.removeFirst() );
        System.out.println( linkedListToString( linkedList ) );

        System.out.println( "Removing from the end" );
        System.out.println( "Removed " + linkedList.removeLast() );
        System.out.println( linkedListToString( linkedList ) );
    }

    public static void testInsertion( DSALinkedList linkedList )
    {
        System.out.println( "Inserting at the start" );
        linkedList.insertFirst( new Integer( 0 ) );
        System.out.println( "Added " + linkedList.peekFirst() );
        System.out.println( linkedListToString( linkedList ) );

        System.out.println( "Inserting at the end" );
        linkedList.insertLast( new Integer( 10 ) );
        System.out.println( "Added " + linkedList.peekLast() );
        System.out.println( linkedListToString( linkedList ) );
    }

    public static void testEmpty( DSALinkedList linkedList )
    {
        System.out.println( "Testing for emptiness: " + linkedList.isEmpty()
            + ". (Expected: false)" );
        System.out.println( "Emptying list from the end" );
        for( int i = 0; i < 10; i++ )
        {
            linkedList.removeLast();
            System.out.println( linkedListToString( linkedList ) );
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
