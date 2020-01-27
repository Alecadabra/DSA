import java.util.*;
import java.io.*;

public class DSAGraph
{
    private class DSAGraphVertex
    {
        /** ================== DSAGraphVertex ================== **/

        /** Private Classfields **/
        private String label;
            // Key for the vertex
        private DSALinkedList links;
            // Adjacency list
        private boolean visited;
            // Used for traversal

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inLabel (String)
        **      Export: Memory adress of new DSAGraphVertex
        **   Assertion: Sets label to given value, constructs links list
        **                  using default and sets visited to false
        */
        private DSAGraphVertex( String inLabel )
        {
            label = inLabel;
            links = new DSALinkedList();
            visited = false;
        }
 
        /** Accessors **/
        /*  --------------------------------------------------------------
        **   Submodule: getLabel()
        **      Import: none
        **      Export: label (String)
        **   Assertion: Returns label
        */
        private String getLabel()
        {
            return label;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getAdjacent()
        **      Import: none
        **      Export: adjacent (DSALinkedList)
        **   Assertion: Returns a linked list of all adjacent vertexes
        */
        private DSALinkedList getAdjacent()
        {
            return links;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getVisited()
        **      Import: none
        **      Export: visited (boolean)
        **   Assertion: Returns visited, used for traversal
        */
        private boolean getVisited()
        {
            return visited;
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
            return ( label.equals( inVertex.getLabel() ) );
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
        private void setVisited()
        {
            visited = true;
        }

        /*  --------------------------------------------------------------
        **   Submodule: clearVisited()
        **      Import: none
        **      Export: none
        **   Assertion: Sets visited to false, used for traversal
        */
        private void clearVisited()
        {
            visited = false;
        }
    }



    /** ================== DSAGraph ================== **/

    /** Private Classfield **/
    private DSALinkedList verticies;
        // List of all graph verticies

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory adress of new DSAGraphVertex
    **   Assertion: Constructs the verticies list using default
    */
    public DSAGraph()
    {
        verticies = new DSALinkedList();
    }

    /** Accessors **/
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
            if( ( (DSAGraphVertex)o ).getLabel().equals( inLabel ) )
            {
                vertex = (DSAGraphVertex)o;
            }
        }

        return vertex;
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
            if( ( (DSAGraphVertex)o ).getLabel().equals( inLabel ) )
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
            for( Object o2 : ( (DSAGraphVertex)o ).getAdjacent() )
            {
                // Increment for every element in the adjacency list for
                // every induvidual vertex
                count++;
            }
        }

        // Each edge is represented twice as the graph is undirected, so
        // we divide by 2

        return ( count / 2 );
    }

    /*  --------------------------------------------------------------
    **   Submodule: getAdjacent()
    **      Import: inLabel (String)
    **      Export: (DSALinkedList)
    **   Assertion: Returns linked list of adjacent verticies to the
    **                  given vertex
    */
    public DSALinkedList getAdjacent( String inLabel )
    {
        return getVertex( inLabel ).getAdjacent();
    }

 
    /*  --------------------------------------------------------------
    **   Submodule: isAdjacent
    **      Import: label1 (String), label2 (String)
    **      Export: adjacent (boolean)
    **   Assertion: Finds if two verticies are adjacent from given labels
    */
    public boolean isAdjacent( String label1, String label2 )
    {
        boolean adjacent = false;
        DSAGraphVertex vertex1, vertex2;

        vertex1 = getVertex( label1 );
        vertex2 = getVertex( label2 );

        for( Object o : ( vertex1.getAdjacent() ) )
        {
            if( ( (DSAGraphVertex)o ).equals( vertex2 ) )
            {
                adjacent = true;
            }
        }

        return adjacent;
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

        for( Object o : ( vertex1.getAdjacent() ) )
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
            System.out.print( ((DSAGraphVertex)o).getLabel() + " |" );
            for( Object o2 : ((DSAGraphVertex)o).getAdjacent() )
            {
                System.out.print( "  " + ((DSAGraphVertex)o2).getLabel() );
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
            System.out.print( " | " + ((DSAGraphVertex)o).getLabel() );
        }

        for( int i = 0; i < numVerticies; i++ )
        {
            System.out.print( "\n" + arr[i].getLabel() );
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
    public String depthFirstSearch()
    {
        // Set all verticies to unvisited
        clearVisited();

        // Use recursive DSARec() algorithm
        return DFSRec( (DSAGraphVertex)verticies.peekFirst() );
    }

    /*  --------------------------------------------------------------
    **   Submodule: DFSRec()
    **      Import: vertex (DSAGraphVertex)
    **      Export: out (String)
    **   Assertion: Recursive helper function to traverse graph using
    **                  depth first search
    */
    private String DFSRec( DSAGraphVertex vertex )
    {
        DSAGraphVertex subVertex;
            // Current vertex adjacent to imported vertex
        String out = "";
            // Export string

        vertex.setVisited();

        // Iterate through adjacent verticies
        for( Object o : vertex.getAdjacent() )
        {
            // Find an adjacent vertex that is not visited
            subVertex = (DSAGraphVertex)o;
            if( !subVertex.getVisited() )
            {
                out += ( "(" + vertex.toString() + ", " + 
                    subVertex.toString() + ")  " + 
                    DFSRec( subVertex ) );
                    // Recurse
            }
        }

        return out;
    }

    /*  --------------------------------------------------------------
    **   Submodule: breadthFirstSearch()
    **      Import: none
    **      Export: (String)
    **   Assertion: Wrapper submodule to perform depth first search using 
    **                  BFSRec()
    */ 
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

    /*  --------------------------------------------------------------
    **   Submodule: BFSRec()
    **      Import: queue (DSAQueue)
    **      Export: out (String)
    **   Assertion: Recursive helper function to traverse graph using
    **                  depth first search
    */ 
    private String BFSRec( DSAQueue queue )
    {
        DSAGraphVertex vertex = (DSAGraphVertex)queue.dequeue();
            // Current vertex for this recurse
        DSAGraphVertex subVertex;
            // Current vertex in adjacency list of vertex for each iteration
        String out = "";
            // Export string

        // Iterate through adjacent verticies
        for( Object o : vertex.getAdjacent() )
        {
            subVertex = (DSAGraphVertex)o;

            if( !subVertex.getVisited() )
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

    /*  --------------------------------------------------------------
    **   Submodule: clearVisited()
    **      Import: none
    **      Export: none
    **   Assertion: Helper function to set all verticies to unvisited
    */ 
    private void clearVisited()
    {
        for( Object o : verticies )
        {
            // Mark all verticies as not visited
            ( (DSAGraphVertex)o ).clearVisited();
        }
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: addVertex()
    **      Import: inlabel (String)
    **      Export: none
    **   Assertion: Adds a vertex to the graph from label
    */
    public void addVertex( String inLabel )
    {
        if( hasVertex( inLabel ) )
        {
            throw new IllegalArgumentException( "Vertex " + inLabel +
                " already exists in graph" );
        }

        verticies.insertLast( new DSAGraphVertex( inLabel ) );
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
                inVertex.getLabel() + " already exists in graph" );
        }

        verticies.insertLast( inVertex );
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
        vertex2.addEdge( vertex1 );
    }

    /** File IO **/
    /*  --------------------------------------------------------------
    **   Submodule: loadFile
    **      Import: fileName (String)
    **      Export: none
    **   Assertion: Loads graph state from file
    */
    public void loadFile( String fileName )
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
                if( !hasVertex( s ) )
                {
                    addVertex( s );
                }
            }

            // Adding edges
            addEdge( edgeVerticies[0], edgeVerticies[1] );
        }
    }
}
