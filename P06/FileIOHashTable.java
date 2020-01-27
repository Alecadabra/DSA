import java.util.*;
import java.io.*;

public class FileIOHashTable
{
    public static void loadCSV( DSAHashTable table, String fileName )
    {
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        DSALinkedList lines = new DSALinkedList();
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
            try
            {
                table.put( ( (String)o ).split(",")[0], 
                    ( (String)o ).split(",")[1] );
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

    public static void saveCSV( DSAHashTable table, String fileName )
    {
        FileOutputStream strm = null;
        PrintWriter pw;
        
        try
        {
            strm = new FileOutputStream( fileName );
            pw = new PrintWriter( strm );

            for( String line : table.export() )
            {
                pw.println( line );
            }

            pw.close();
        }
        /*
        catch( FileNotFoundException e )
        {
            throw new IllegalArgumentException( 
                "File " + fileName + " not found" );
        }
        */
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
}
