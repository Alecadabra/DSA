/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: Handles command-line arguments for SocialSim program and performs
**              last resort exception handling
**     Date: 27/10/19
**  ==========================================================================
*/

import java.util.*;

public class SocialSim
{
    public static void main( String[] args )
    {
        try
        {
            if( args.length == 0 )
            {
                // Usage Information
                System.out.println( "\n" +
                    "SocialSim - Social network simulator\n" +
                    "Options:\n" +
                    "    Interactive Testing Environment\n" +
                    "    Run with -i\n" +
                    "\n" +
                    "    Simulation Mode\n" +
                    "    Run with -s <netfile> <eventfile> <prob_like> " +
                    "<prob_foll>\n" );
            }
            else if( args[0].equals( "-i" ) )
            {
                // Interactive Mode
                UI.InteractiveMode();
            }
            else if( args[0].equals( "-s" ) )
            {
                // Simulation Mode
                UI.SimulationMode( args[1], args[2], 
                    Double.parseDouble( args[3] ), 
                    Double.parseDouble( args[4] ) );
            }
        }
        catch( Exception e )
        {
            // Last resort exception handling
            System.out.println( "An error occured: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}
