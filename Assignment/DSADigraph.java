/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: Implementation of directed graph ADT
**     Date: 27/10/19
**Self-Cite: An older version of this code was previously submitted for Prac 5
**  ==========================================================================
*/

import java.util.*;
import java.io.*;

public class DSADigraph implements Iterable
{
    private class DSAGraphVertex
    {
        /** ================== DSAGraphVertex ================== **/

        /** Private Classfields **/
        private String label;
            // Key for the vertex
        private Object value;
            // Value for the vertex
        private DSALinkedList links;
            // Adjacency list
        // private boolean visited;
            // Used for traversal

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inValue (String)
        **      Export: Memory adress of new DSAGraphVertex
        **   Assertion: Sets label to the toString() of given value, the
        **                  value to the given parameter, constructs links 
        **                  list using default and sets visited to false
        */
        private DSAGraphVertex( Object inValue )
        {
            label = inValue.toString();
            value = inValue;
            links = new DSALinkedList();
            // visited = false;
        }
 
        /*  --------------------------------------------------------------
        **   Submodule: toString()
        **      Import: none
        **      Export: label (String)
        **   Assertion: Returns label
        */
        public String toString()
        {
            return label;
        }

        /*  --------------------------------------------------------------
        **   Submodule: equals()
        **      Import: inVertex (DSAGraphVertex)
        **      Export: (boolean)
        **   Assertion: Compares labels of two verticies for equality
        */
        public boolean equals( DSAGraphVertex inVertex )
        {
            return ( value.equals( inVertex.value ) );
        }

        /** Mutators **/
        /*  --------------------------------------------------------------
        **   Submodule: addEdge()
        **      Import: inVertex (DSAGraphVertex)
        **      Export: none
        **   Assertion: Adds an edge by adding a new vertex to the links list
        */
        private void addEdge( DSAGraphVertex inVertex )
        {
            links.insertLast( inVertex );
        }

        /*  --------------------------------------------------------------
        **   Submodule: setVisited()
        **      Import: none
        **      Export: none
        **   Assertion: Sets visited to true, used for traversal
        */
        /*
        private void setVisited()
        {
            visited = true;
        }
        */

        /*  --------------------------------------------------------------
        **   Submodule: clearVisited()
        **      Import: none
        **      Export: none
        **   Assertion: Sets visited to false, used for traversal
        */
        /*
        private void clearVisited()
        {
            visited = false;
        }
        */
    }



    /** ================== DSADigraph ================== **/

    /** Private Classfield **/
    private DSALinkedList verticies;
        // List of all graph verticies

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory adress of new DSADigraph
    **   Assertion: Constructs the verticies list using default
    */
    public DSADigraph()
    {
        verticies = new DSALinkedList();
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: getVerticies()
    **      Import: none
    **      Export: values (DSALinkedList)
    **   Assertion: Returns a linked list of the values of all verticies
    */
    public DSALinkedList getVerticies()
    {
        DSALinkedList values = new DSALinkedList();

        for( Object o : verticies )
        {
            values.insertLast( ( (DSAGraphVertex)o ).value );
        }

        return values;
    } 

    /*  --------------------------------------------------------------
    **   Submodule: getVertex()
    **      Import: inLabel (String)
    **      Export: vertex (DSAGraphVertex)
    **   Assertion: Finds the vertex in the verticies list with the given
    **                  label, and fails if not found
    */
    public DSAGraphVertex getVertex( String inLabel )
    {
        DSAGraphVertex vertex = null;

        if( !hasVertex( inLabel ) )
        {
            throw new NoSuchElementException( "Vertex " + inLabel +
                " not found" );
        }

        for( Object o : verticies )
        {
            // Search through every vertex to find matching label
            if( ( (DSAGraphVertex)o ).label.equals( inLabel ) )
            {
                vertex = (DSAGraphVertex)o;
            }
        }

        return vertex;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getValue()
    **      Import: inLabel (String)
    **      Export: (Object)
    **   Assertion: Finds the vertex in the verticies list with the given
    **                  label and returns it's value
    */
    public Object getValue( String inLabel )
    {
        return getVertex( inLabel ).value;
    }

    /*  --------------------------------------------------------------
    **   Submodule: hasVertex()
    **      Import: inLabel (String)
    **      Export: found (boolean)
    **   Assertion: Searches the verticies list for the given vertex
    */
    public boolean hasVertex( String inLabel )
    {
        boolean found = false;

        for( Object o : verticies )
        {
            // Search through every vertex to find matching label
            if( ( (DSAGraphVertex)o ).label.equals( inLabel ) )
            {
                found = true;
            }
        }

        return found;
    }

    /*  --------------------------------------------------------------
    **   Submodule: hasVertex()
    **      Import: inVertex (DSAGraphVertex)
    **      Export: found (boolean)
    **   Assertion: Searches the verticies list for the given vertex
    */
    public boolean hasVertex( DSAGraphVertex inVertex )
    {
        boolean found = false;

        for( Object o : verticies )
        {
            if( ( (DSAGraphVertex)o ).equals( inVertex ) )
            {
                found = true;
            }
        }

        return found;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getVertexCount()
    **      Import: none
    **      Export: count (int)
    **   Assertion: Returns the number of verticies in the graph
    */
    public int getVertexCount()
    {
        int count = 0;

        for( Object o : verticies )
        {
            count++;
        }

        return count;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getEdgeCount()
    **      Import: none
    **      Export: (int)
    **   Assertion: Returns the number of edges in the graph
    */
    public int getEdgeCount()
    {
        int count = 0;

        for( Object o : verticies )
        {
            for( Object o2 : ( (DSAGraphVertex)o ).links )
            {
                // Increment for every element in the adjacency list for
                // every induvidual vertex
                count++;
            }
        }

        return count;
    }

    /*  --------------------------------------------------------------
    **   Submodule: getAdjacent()
    **      Import: inLabel (String)
    **      Export: values (DSALinkedList)
    **   Assertion: Returns linked list of values of adjacent verticies
    **                  to the given vertex
    */
    public DSALinkedList getAdjacent( String inLabel )
    {
        DSALinkedList values = new DSALinkedList();

        for( Object o : getVertex( inLabel ).links )
        {
            values.insertLast( ( (DSAGraphVertex)o ).value );
        }

        return values;
    }
 
    /*  --------------------------------------------------------------
    **   Submodule: isAdjacent
    **      Import: label1 (String), label2 (String)
    **      Export: adjacent (boolean)
    **   Assertion: Finds if two verticies are adjacent from given labels
    */
    public boolean isAdjacent( String label1, String label2 )
    {
        DSAGraphVertex vertex1, vertex2;

        vertex1 = getVertex( label1 );
        vertex2 = getVertex( label2 );

        return isAdjacent( vertex1, vertex2 );
    }
    
    /*  --------------------------------------------------------------
    **   Submodule: isAdjacent
    **      Import: vertex1 (DSAGraphVertex), vertex2 (DSAGraphVertex)
    **      Export: adjacent (boolean)
    **   Assertion: Finds if two verticies are adjacent from given verticies
    */
    public boolean isAdjacent( DSAGraphVertex vertex1, DSAGraphVertex vertex2 )
    {
        boolean adjacent = false;

        for( Object o : ( vertex1.links ) )
        {
            if( ((DSAGraphVertex)o).equals( vertex2 ) )
            {
                adjacent = true;
            }
        }

        return adjacent;
    }
 
    /*  --------------------------------------------------------------
    **   Submodule: displayAsList()
    **      Import: none
    **      Export: none
    **   Assertion: Displays graph as an adjacency list
    */
    public void displayAsList()
    {
        for( Object o : verticies )
        {
            System.out.print( ((DSAGraphVertex)o).label + " |" );
            for( Object o2 : ((DSAGraphVertex)o).links )
            {
                System.out.print( "  " + ((DSAGraphVertex)o2).label );
            }
            System.out.println();
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: displayAsMatrix()
    **      Import: none
    **      Export: none
    **   Assertion: Displays graph as an adjacency matrix
    */
    public void displayAsMatrix()
    {
        int numVerticies = getVertexCount();
            // Number of verticies in graph
        DSAGraphVertex[] arr;
            // Array of all verticies in graph
        int[][] matrix;
            // 2D array of matrix, uses 0's for no adjacency and 1's for
            // adjacency
        int idx = 0;
            // index used to fill arr

        arr = new DSAGraphVertex[ numVerticies ];
        matrix = new int[ numVerticies ][ numVerticies ];

        // Populating vertex array
        for( Object o : verticies )
        {
            arr[idx] = (DSAGraphVertex)o;
            idx++;
        }

        // Populating matrix
        for( int i = 0; i < numVerticies; i++ )
        {
            for( int j = 0; j < numVerticies; j++ )
            {
                if( isAdjacent( arr[i], arr[j] ) )
                {
                    matrix[i][j] = 1;
                }
                else
                {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Printing
        System.out.print( " " );
        for( Object o : verticies )
        {
            System.out.print( " | " + ((DSAGraphVertex)o).label );
        }

        for( int i = 0; i < numVerticies; i++ )
        {
            System.out.print( "\n" + arr[i].label );
            for( int j = 0; j < numVerticies; j++ )
            {
                System.out.print( " | " + matrix[i][j] );
            }
        }
        System.out.println();
    }

    /*  --------------------------------------------------------------
    **   Submodule: depthFirstSearch()
    **      Import: none
    **      Export: (String)
    **   Assertion: Wrapper submodule to perform depth first search using 
    **                  DFSRec()
    */
    /*
    public String depthFirstSearch()
    {
        // Set all verticies to unvisited
        clearVisited();

        // Use recursive DSARec() algorithm
        return DFSRec( (DSAGraphVertex)verticies.peekFirst() );
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: DFSRec()
    **      Import: vertex (DSAGraphVertex)
    **      Export: out (String)
    **   Assertion: Recursive helper function to traverse graph using
    **                  depth first search
    */
    /*
    private String DFSRec( DSAGraphVertex vertex )
    {
        DSAGraphVertex subVertex;
            // Current vertex adjacent to imported vertex
        String out = "";
            // Export string

        vertex.setVisited();

        // Iterate through adjacent verticies
        for( Object o : vertex.links )
        {
            // Find an adjacent vertex that is not visited
            subVertex = (DSAGraphVertex)o;
            if( !subVertex.visited )
            {
                out += ( "(" + vertex.toString() + ", " + 
                    subVertex.toString() + ")  " + 
                    DFSRec( subVertex ) );
                    // Recurse
            }
        }

        return out;
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: breadthFirstSearch()
    **      Import: none
    **      Export: (String)
    **   Assertion: Wrapper submodule to perform depth first search using 
    **                  BFSRec()
    */
    /*
    public String breadthFirstSearch()
    {
        DSAQueue queue = new DSAQueue();

        // Set all verticies to unvisited
        clearVisited();

        // Enqueue first vertex
        queue.enqueue( verticies.peekFirst() );
        ( (DSAGraphVertex)verticies.peekFirst() ).setVisited();

        return BFSRec( queue );
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: BFSRec()
    **      Import: queue (DSAQueue)
    **      Export: out (String)
    **   Assertion: Recursive helper function to traverse graph using
    **                  depth first search
    */
    /*
    private String BFSRec( DSAQueue queue )
    {
        DSAGraphVertex vertex = (DSAGraphVertex)queue.dequeue();
            // Current vertex for this recurse
        DSAGraphVertex subVertex;
            // Current vertex in adjacency list of vertex for each iteration
        String out = "";
            // Export string

        // Iterate through adjacent verticies
        for( Object o : vertex.links )
        {
            subVertex = (DSAGraphVertex)o;

            if( !subVertex.visited )
            {
                queue.enqueue( subVertex );
                subVertex.setVisited();
                out += ( "(" + vertex.toString() + ", " + 
                    subVertex.toString() + ")  " );
            }
        }

        if( !queue.isEmpty() )
        {
            // Recurse
            out += BFSRec( queue );
        }

        return out;
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: clearVisited()
    **      Import: none
    **      Export: none
    **   Assertion: Helper function to set all verticies to unvisited
    */ 
    /*
    private void clearVisited()
    {
        for( Object o : verticies )
        {
            // Mark all verticies as not visited
            ( (DSAGraphVertex)o ).clearVisited();
        }
    }
    */

    /*  --------------------------------------------------------------
    **   Submodule: export()
    **      Import: outVerticies (DSALinkedList), outAdjacency (DSALinkedList)
    **      Export: none
    **   Assertion: Exports state of graph to two linked lists, one of
    **                  all verticies, another of all adjacencys in the
    **                  format "<label>:<adjacent label>"
    */
    public void export( DSALinkedList outVerticies, 
        DSALinkedList outAdjacency )
    {
        for( Object o : verticies )
        {
            outVerticies.insertLast( ( (DSAGraphVertex)o ).label );

            for( Object o2 : ( (DSAGraphVertex)o ).links )
            {
                outAdjacency.insertLast( ( (DSAGraphVertex)o ).label + ":" +
                    ( (DSAGraphVertex)o ).label );
            }
        }
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: addVertex()
    **      Import: inlabel (String)
    **      Export: none
    **   Assertion: Adds a vertex to the graph from label
    */
    public void addVertex( Object inValue )
    {
        addVertex( new DSAGraphVertex( inValue ) );
    }

    /*  --------------------------------------------------------------
    **   Submodule: addVertex()
    **      Import: inVertex (DSAGraphVertex)
    **      Export: none
    **   Assertion: Adds a vertex to the graph from given vertex
    */
    public void addVertex( DSAGraphVertex inVertex )
    {
        if( hasVertex( inVertex ) )
        {
            throw new IllegalArgumentException( "Vertex " + 
                inVertex.label + " already exists in graph" );
        }

        verticies.insertLast( inVertex );
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeVertex()
    **      Import: inlabel (String)
    **      Export: none
    **   Assertion: Removes a vertex to the graph from label
    */
    public void removeVertex( String inLabel )
    {
        removeVertex( getVertex( inLabel ) );
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeVertex()
    **      Import: inVertex (DSAGraphVertex)
    **      Export: none
    **   Assertion: Removes a vertex to the graph from given vertex
    */
    public void removeVertex( DSAGraphVertex inVertex )
    {
        if( !hasVertex( inVertex ) )
        {
            throw new IllegalArgumentException( "Vertex " + 
                inVertex.label + " already exists in graph" );
        }

        verticies.removeNode( inVertex );
    }

    /*  --------------------------------------------------------------
    **   Submodule: addEdge()
    **      Import: label1 (String), label2 (String)
    **      Export: none
    **   Assertion: Adds edge between two given verticies from label
    */
    public void addEdge( String label1, String label2 )
    {
        addEdge( getVertex( label1 ), getVertex( label2 ) );
    }

    /*  --------------------------------------------------------------
    **   Submodule: addEdge()
    **      Import: vertex1 (DSAGraphVertex), vertex2 (DSAGraphVertex)
    **      Export: none
    **   Assertion: Adds edge between two given verticies from given 
    **                  verticies
    */
    public void addEdge( DSAGraphVertex vertex1, DSAGraphVertex vertex2 )
    {
        if( isAdjacent( vertex1, vertex2 ) )
        {
            throw new IllegalArgumentException( "Verticies " + vertex1 +
                " and " + vertex2 + " are already connected" );
        }

        vertex1.addEdge( vertex2 );
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeEdge()
    **      Import: inlabel1 (String), inLabel2 (String)
    **      Export: none
    **   Assertion: Removes an edge of the graph from labels
    */
    public void removeEdge( String inLabel1, String inLabel2 )
    {
        removeEdge( getVertex( inLabel1 ), getVertex( inLabel2 ) );
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeEdge()
    **      Import: inVertex1 (DSAGraphVertex), inVertex2 (DSAGraphVertex)
    **      Export: none
    **   Assertion: Removes an edge of the graph from given verticies
    */
    public void removeEdge( DSAGraphVertex inVertex1, 
        DSAGraphVertex inVertex2 )
    {
        if( !isAdjacent( inVertex1, inVertex2 ) )
        {
            throw new IllegalArgumentException( "Verticies " + 
                inVertex1.label + " and " + inVertex2.label +
                " are not adjacent" );
        }

        inVertex1.links.removeNode( inVertex2 );
    }

    /** Iteration **/
    /*  --------------------------------------------------------------
    **   Submodule: iterator
    **      Import: none
    **      Export: (Iterator)
    **   Assertion: Returns the Iterator Object for the graph vertices
    */
    public Iterator iterator()
    {
        return verticies.iterator();
    }
}
