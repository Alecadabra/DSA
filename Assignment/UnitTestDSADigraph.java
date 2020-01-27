import java.util.*;
import java.io.*;

public class UnitTestDSADigraph
{
    public static void main( String[] args )
    {
        DSADigraph graph = new DSADigraph();

        try
        {
            // Populating graph
            spacer();
            System.out.println( "Populating graph\n" );
            populate( graph );

            // Testing hasVertex()
            spacer();
            System.out.println( "Testing hasVertex()\n" );
            testHasVertex( graph );

            // Testing getVertexCount()
            spacer();
            System.out.println( "Testing getVertexCount()\n" );
            testGetVertexCount( graph );

            // Testing getEdgeCount()
            spacer();
            System.out.println( "Testing getEdgeCount()\n" );
            testGetEdgeCount( graph );

            // Testing getVertex()
            spacer();
            System.out.println( "Testing getVertex()\n" );
            testGetVertex( graph );

            // Testing getAdjacent()
            spacer();
            System.out.println( "Testing getAdjacent()\n" );
            testGetAdjacent( graph );

            // Testing isAdjacent()
            spacer();
            System.out.println( "Testing isAdjacent()\n" );
            testIsAdjacent( graph );

            // Testing addVertex()
            spacer();
            System.out.println( "Testing addVertex()\n" );
            testAddVertex( graph );

            // Testing addEdge()
            spacer();
            System.out.println( "Testing addEdge()\n" );
            testAddEdge( graph );

            // Ommited as not used in this assignment

            // Testing depthFirstSearch()
            /*
            spacer();
            System.out.println( "Testing depthFirstSearch()\n" );
            testDepthFirstSearch( graph );

            // Testing breadthFirstSearch()
            spacer();
            System.out.println( "Testing breadthFirstSearch()\n" );
            testBreadthFirstSearch( graph );
            */

            spacer();
        }
        catch( Exception e )
        {
            System.out.println( "Unexpected exception thrown, details below" 
                );
            spacer();
            e.printStackTrace();
        }
    }

    public static void spacer()
    {
        System.out.println( 
            "\n=================================================\n" );
    }

    public static void populate( DSADigraph graph )
    {
        /* // Hard coded
        System.out.println( "Adding verticies A to F" );
        for( char i = 'A'; i < 'G'; i++ )
        {
            graph.addVertex( ( "" + i ) );
        }
        graph.displayAsList();
        System.out.println();
        graph.displayAsMatrix();
        System.out.println();

        System.out.println( "Adding Edges (A,E), (C,E), (B,C)" );
        graph.addEdge( "A", "B" );
        graph.addEdge( "C", "E" );
        graph.addEdge( "B", "C" );
        */

        System.out.println(
            "Loading from TestDataDSADigraph.txt (prac6_2.al from P05)" );
        loadFile( "TestDataDSADigraph.txt", graph );

        System.out.println( "Adjacency list: " );
        graph.displayAsList();
        System.out.println( "\nAdjacency matrix: " );
        graph.displayAsMatrix();
        System.out.println( "\nExpected adjacency list:\n" +
            "A  |  B  D  E\n" +
            "B  |  A  C  E\n" +
            "D  |  A  C\n" +
            "E  |  A  B  C\n" +
            "C  |  B  D  E\n" );
    }

    public static void testHasVertex( DSADigraph graph )
    {
        System.out.println( "Testing for vertex A: " + graph.hasVertex( "A" )
            + " (Expected: true)" );
        System.out.println( "Testing for vertex Z: " + graph.hasVertex( "Z" )
            + " (Expected: false)" );
    }

    public static void testGetVertexCount( DSADigraph graph )
    {
        System.out.println( "Vertex count: " + graph.getVertexCount() +
            " (Expected: 5)" );
    }

    public static void testGetEdgeCount( DSADigraph graph )
    {
        System.out.println( "Edge count: " + graph.getEdgeCount() +
            " (Expected: 14)" );
    }

    public static void testGetVertex( DSADigraph graph )
    {
        System.out.println( "Testing for vertex A:" );
        System.out.println( "Found with label " + graph.getVertex( "A" ) +
            " (Expected: A)" );

        System.out.println( "\nTesting for vertex not in graph:" );
        try
        {
            graph.getVertex( "Z" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testGetAdjacent( DSADigraph graph )
    {
        DSALinkedList adjacent;

        System.out.print( "Testing adjacency of vertex B:\n\t" );

        adjacent = graph.getAdjacent( "B" );

        for( Object o : adjacent )
        {
            System.out.print( o + "  " );
        }

        System.out.println( "\n\nExpected:\n\tA  C  E" );

        System.out.println( 
            "\nTesting for adjacency of vertex that does not exist" );

        try
        {
            graph.getAdjacent( "Z" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testIsAdjacent( DSADigraph graph )
    {
        System.out.println( 
            "Testing for verticies that are adjacent (A and E): " +
            graph.isAdjacent( "A", "E" ) + " (Expected: true)\n" );
        System.out.println(
            "Testing for verticies that are not adjacent (A and C): " +
            graph.isAdjacent( "A", "C" ) + " (Expeted: false)\n" );
        System.out.println( 
            "Testing for verticies that do not exist (Y and Z):" );
        try
        {
            graph.isAdjacent( "Y", "Z" );
            System.out.println( "No excpetion thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testAddVertex( DSADigraph graph )
    {
        System.out.println( "Graph before:" );
        System.out.println( "hasVertex() for P (Vertex not in graph): " + 
            graph.hasVertex( "P" ) + " (Expected: false)" );
        graph.displayAsList();
        
        System.out.println( "\nAdding vertex P:" );
        graph.addVertex( "P" );
        System.out.println( "hasVertex() for P now: " + graph.hasVertex( "P" )
            + " (Expected: true)" );
        graph.displayAsList();

        System.out.println( "\nAdding vertex that already exists (A):" );
        try
        {
            graph.addVertex( "A" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    public static void testAddEdge( DSADigraph graph )
    {
        System.out.println( "Graph before:" );
        System.out.println( 
            "isAdjacent() for A and P (Verticies not adjacent): " +
            graph.isAdjacent( "A", "P" ) + " (Expected: false)" );
        graph.displayAsList();

        System.out.println( "\nAdding edge between A and P:" );
        graph.addEdge( "A", "P" );
        System.out.println( 
            "isAdjacent() for A and P now: " +
            graph.isAdjacent( "A", "P" ) + " (Expected: true)" );
        graph.displayAsList();

        System.out.println( "\nAdding edge that already exists (A and E):" );
        try
        {
            graph.addEdge( "A", "E" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }

        System.out.println( 
            "\nAdding edge between verticies that don't exist (Y and Z):" );
        try
        {
            graph.addEdge( "Y", "Z" );
            System.out.println( "No exception thrown :(" );
        }
        catch( Exception e )
        {
            System.out.println( "Exception thrown as expected" );
        }
    }

    /* Ommited as not needed for assignment
    public static void testDepthFirstSearch( DSADigraph graph )
    {
        System.out.println( graph.depthFirstSearch() );
        System.out.println( "\nExpected:\n" +
            "(A, E)  (B, E)  (E, F)  (F, D)  (D, C)  (F, G)  (A, P)" );
    }

    public static void testBreadthFirstSearch( DSADigraph graph )
    {
        System.out.println( graph.breadthFirstSearch() );
        System.out.println( "\nExpected:\n" +
            "(A, B)  (A, E)  (A, D)  (A, C)  (A, P)  (E, F)  (E, G)" );
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: loadFile
    **      Import: fileName (String)
    **      Export: none
    **   Assertion: Loads graph state from file
    */
    public static void loadFile( String fileName, DSADigraph graph )
    {
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        DSALinkedList lines = new DSALinkedList();
            // List of lines in file as strings
        String line;
        String[] edgeVerticies = new String[ 2 ];
            // 2-element array of the verticies at each end of given edge

        // File IO
        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                // Save lines to linked list
                lines.insertLast( line );

                line = bfr.readLine();
            }
        
            bfr.close();
        }
        catch( FileNotFoundException e )
        {
            throw new IllegalArgumentException( "File " + fileName +
                " not found" );
        }
        catch( IOException e )
        {
            if( strm != null )
            {
                // If file is still open, try close it
                try
                {
                    strm.close();
                }
                catch( IOException e2 )
                {
                    throw new IllegalArgumentException( 
                        "Error in file reading, file cannot be closed" );
                }
            }
            else
            {
                throw new IllegalArgumentException(
                    "Error in file reading" );
            }
        }

        // Populatiing graph
        for( Object o : lines )
        {
            edgeVerticies = ( (String)o ).split( " " );
                // Populate the array with the verticies on either side of 
                // the edge given in the file ( [ A, B ] )

            // Adding verticies
            for( String s : edgeVerticies )
            {
                // Add each vertex in the array that isnt already in the
                // graph
                if( !graph.hasVertex( s ) )
                {
                    graph.addVertex( s );
                }
            }

            // Adding edges
            graph.addEdge( edgeVerticies[0], edgeVerticies[1] );
        }
    }
}
