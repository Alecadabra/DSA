import java.util.*;
import java.io.*;

public class FileIOHeap
{
    public static void loadCSV( DSAHeap heap, String fileName )
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

    /*
    public static void saveCSV( DSAHeap heap, String fileName )
    {
        FileOutputStream strm = null;
        PrintWriter pw;
        
        try
        {
            strm = new FileOutputStream( fileName );
            pw = new PrintWriter( strm );

            for( String line : heap.export() )
            {
                pw.println( line );
            }

            pw.close();
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
    }
    */
}
