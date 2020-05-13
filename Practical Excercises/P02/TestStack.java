import java.util.*;

public class TestStack
{
    public static void main( String[] args )
    {
        // Capacity Test
        DSAStack stack = new DSAStack( 5 );

        System.out.println( "Testing capacity of 5" );
        try
        {
            System.out.print( "Adding integers: " );
            for( int i = 0; i <= 5; i++ )
            {
                stack.push( new Integer( i + 1 ) );
                System.out.print( ( i + 1 ) + ", " );
            }
            System.out.println( "\nCapacity exceeded :(" );
        }
        catch( IllegalArgumentException e )
        {
            System.out.println( "\nIt's full :)" );
        }
        System.out.println( "\n" );

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

    }
}
