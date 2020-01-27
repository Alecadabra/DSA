/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: File Input/Output operations for SocialSim, handles network
**              files, event files and log files
**     Date: 27/10/19
**  ==========================================================================
*/

import java.util.*;
import java.io.*;

public class FileIO
{
    /*  --------------------------------------------------------------
    **   Submodule: saveNet
    **      Import: fileName (String), net (Network)
    **      Export: none
    **   Assertion: Saves a network's graph to file in the same format
    **                  as the network input files
    */
    public static void saveNet( String fileName, Network net )
    {
        // File IO Objects
        FileOutputStream strm = null;
        PrintWriter pw;

        // State of the network graph as strings
        DSALinkedList verticies = new DSALinkedList();
        DSALinkedList adjacency = new DSALinkedList();

        net.export( verticies, adjacency );

        try
        {
            strm = new FileOutputStream( fileName );
            pw = new PrintWriter( strm );

            for( Object o : verticies )
            {
                // Print each vertex (Each user)
                pw.println( (String)o );
            }

            for( Object o : adjacency )
            {
                // Print each adjacency (Each follow)
                pw.println( (String)o );
            }

            pw.close();
        }
        catch( IllegalArgumentException e )
        {
            System.out.println( "Error in network: " + e.getMessage() );
        }
        catch( IOException e )
        {
            if( strm != null )
            {
                // Stream is still open :O
                try
                {
                    strm.close();
                }
                catch( Exception e2 ) {}
            }
            System.out.println( "File writing error: " + e.getMessage() );
        }
    }
 
    /*  --------------------------------------------------------------
    **   Submodule: loadNet
    **      Import: fileName (String), net (Network)
    **      Export: none
    **   Assertion: Loads a network graph state from file
    */
    public static void loadNet( String fileName, Network net )
    {
        // File IO Objects
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;

        String line;
            // Raw line
        String[] elements;
            // Line split by ":"

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                elements = line.split( ":" );
                if( elements.length == 1 )
                {
                    // New user
                    net.newUser( line.trim() );
                }
                else if( elements.length == 2 )
                {
                    // New follow
                    net.newFollow( elements[0].trim(), elements[1].trim() );
                }
                else
                {
                    throw new IllegalArgumentException( "Invalid line '" +
                        line + "'" );
                }

                line = bfr.readLine();
            }

            bfr.close();
        }
        catch( IllegalArgumentException e )
        {
            System.out.println( "Error in network file: " + e.getMessage() );
        }
        catch( FileNotFoundException e )
        {
            System.out.println( "Error, network file not found" );
        }
        catch( IOException e )
        {
            if( strm != null )
            {
                // Stream is still open :O
                try
                {
                    strm.close();
                }
                catch( Exception e2 ) {}
            }
            System.out.println( "File reading error: " + e.getMessage() );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: loadEvents
    **      Import: fileName (String), net (Network)
    **      Export: none
    **   Assertion: Loads a network's events queue from file
    */
    public static void loadEvents( String fileName, Network net )
    {
        // File IO Objects
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;

        String line;
            // Raw line
        String[] elements;
            // Line split by ":"

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            line = bfr.readLine();
            while( line != null )
            {
                elements = line.split( ":" );
                for( int i = 0; i < elements.length; i++ )
                {
                    elements[i] = elements[i].trim();
                }

                net.newEvent( elements );
                    // newEvent() accepts String varargs, which allows a
                    // String array to be passed straight in as the varargs

                line = bfr.readLine();
            }

            bfr.close();
        }
        catch( IllegalArgumentException e )
        {
            System.out.println( "Error in event file: " + e.getMessage() );
        }
        catch( FileNotFoundException e )
        {
            System.out.println( "Error, event file not found" );
        }
        catch( IOException e )
        {
            if( strm != null )
            {
                // Stream is still open :O
                try
                {
                    strm.close();
                }
                catch( Exception e2 ) {}
            }
            System.out.println( "File reading error: " + e.getMessage() );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: appendStats
    **      Import: fileName (String), stats (String)
    **      Export: none
    **   Assertion: Appends the given statistics and logs to the end of
    **                  the log file
    */
    public static void appendStats( String fileName, String stats )
    {
        // File IO Objects
        FileWriter writer = null;
        PrintWriter pw;

        try
        {
            writer = new FileWriter( fileName, true );
                // Open for appending
            pw = new PrintWriter( writer );

            pw.println( stats );

            pw.close();
        }
        catch( IOException e )
        {
            if( writer != null )
            {
                // Stream is still open :O
                try
                {
                    writer.close();
                }
                catch( Exception e2 ) {}
            }
            System.out.println( "File writing error: " + e.getMessage() );
        }
    }
 
}
