public class TowersOfHanoi
{
    public static void main( String[] args )
    {
        //Move 3 disks in 1st peg to 3rd peg
        System.out.println( "Moving 3 disks on 1st peg to 3rd peg" );
        towers( 3, 1, 3 );
    }

    public static void towers( int n, int src, int dest )
    {
        int tmp;

        if( n == 1 )
        {
            //Base case
            moveDisk( src, dest );
        }
        else
        {
            tmp = 6 - src - dest;

            towers( n - 1, src, tmp );

            moveDisk( src, dest );
            towers( n - 1, tmp, dest );
        }
    }

    public static void moveDisk( int src, int dest )
    {
        System.out.printf( "Move top disk from peg %d to peg %d\n", 
            src, dest );
    }
}
