import java.util.*;

public class TestHarnessDSAGraph
{
    public static void main( String[] args )
    {
        DSAGraph graph = new DSAGraph();

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

            // Testing depthFirstSearch()
            spacer();
            System.out.println( "Testing depthFirstSearch()\n" );
            testDepthFirstSearch( graph );

            // Testing breadthFirstSearch()
            spacer();
            System.out.println( "Testing breadthFirstSearch()\n" );
            testBreadthFirstSearch( graph );

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

    public static void populate( DSAGraph graph )
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

        System.out.println( "Loading from prac6_1.al" );
        graph.loadFile( "prac6_1.al" );

        System.out.println( "Adjacency list: " );
        graph.displayAsList();
        System.out.println( "\nAdjacency matrix: " );
        graph.displayAsMatrix();
        System.out.println( "\nExpected adjacency list:\n" +
            "A |  B  E  D  C\nB |  A  E\nE |  A  B  F  G\n" +
            "D |  A  C  F\nC |  A  D\nF |  D  E  G\nG |  E  F" );
    }

    public static void testHasVertex( DSAGraph graph )
    {
        System.out.println( "Testing for vertex A: " + graph.hasVertex( "A" )
            + " (Expected: true)" );
        System.out.println( "Testing for vertex Z: " + graph.hasVertex( "Z" )
            + " (Expected: false)" );
    }

    public static void testGetVertexCount( DSAGraph graph )
    {
        System.out.println( "Vertex count: " + graph.getVertexCount() +
            " (Expected: 7)" );
    }

    public static void testGetEdgeCount( DSAGraph graph )
    {
        System.out.println( "Edge count: " + graph.getEdgeCount() +
            " (Expected: 10)" );
    }

    public static void testGetVertex( DSAGraph graph )
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

    public static void testGetAdjacent( DSAGraph graph )
    {
        DSALinkedList adjacent;

        System.out.print( "Testing adjacency of vertex B:\n\t" );

        adjacent = graph.getAdjacent( "B" );

        for( Object o : adjacent )
        {
            System.out.print( o + "  " );
        }

        System.out.println( "\n\nExpected:\n\tA  E" );

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

    public static void testIsAdjacent( DSAGraph graph )
    {
        System.out.println( 
            "Testing for verticies that are adjacent (A and E): " +
            graph.isAdjacent( "A", "E" ) + " (Expected: true)\n" );
        System.out.println(
            "Testing for verticies that are not adjacent (A and G): " +
            graph.isAdjacent( "A", "G" ) + " (Expeted: false)\n" );
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

    public static void testAddVertex( DSAGraph graph )
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

    public static void testAddEdge( DSAGraph graph )
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

    public static void testDepthFirstSearch( DSAGraph graph )
    {
        System.out.println( graph.depthFirstSearch() );
        System.out.println( "\nExpected:\n" +
            "(A, E)  (B, E)  (E, F)  (F, D)  (D, C)  (F, G)  (A, P)" );
    }

    public static void testBreadthFirstSearch( DSAGraph graph )
    {
        System.out.println( graph.breadthFirstSearch() );
        System.out.println( "\nExpected:\n" +
            "(A, B)  (A, E)  (A, D)  (A, C)  (A, P)  (E, F)  (E, G)" );
    }
}
