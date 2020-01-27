/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: User Interface for SocialSim
**     Date: 27/10/19
**  ==========================================================================
*/

import java.util.*;

public class UI
{
    /*  --------------------------------------------------------------
    **   Submodule: InteractiveMode
    **      Import: none
    **      Export: none
    **   Assertion: Runs the interactive mode of SocialSim, including the
    **                  main menu
    */
    public static void InteractiveMode()
    {
        boolean menuLoop = true;
            // Boolean for looping over the main menu
        int menuSelect;
            // User selection for the main menu
        Network net = new Network( true );
            // Network variable, initialised to interactive mode

        // Main Menu
        while( menuLoop )
        {
            System.out.println( "\n\n" +
                "(1)  Load network\n" +
                "(2)  Set probabilities\n" +
                "(3)  Add a user\n" +
                "(4)  Remove a user\n" +
                "(5)  Add a follow\n" +
                "(6)  Remove a follow\n" +
                "(7)  Add a new post\n" +
                "(8)  Display network\n" +
                "(9)  Update (run a timestep)\n" +
                "(10) Display popularity stats\n" +
                "(11) Display stats on a user\n" +
                "(12) Save network\n" +
                "(0)  Exit" );
            menuSelect = scanInt( "Select option" );
                // Gets a user input int
            System.out.println( "\n" );

            switch( menuSelect )
            {
                case 1:
                {
                    // Load Network
                    FileIO.loadNet( scanString( "Enter filename" ), net );
                    break;
                }
                case 2:
                {
                    // Set probabilites
                    net.setProb( scanDouble( "Probability to like (0-1)" ),
                        scanDouble( "Probability to follow" ) );
                    break;
                }
                case 3:
                {
                    // Add a user
                    net.newUser( scanString( "Enter username" ) );
                    break;
                }
                case 4:
                {
                    // Remove a user
                    net.removeUser( scanString( "Enter username" ) );
                    break;
                }
                case 5:
                {
                    // Add a follow
                    net.newFollow( scanString( "Enter following user" ),
                        scanString( "Enter followed user" ) );
                    break;
                }
                case 6:
                {
                    // Remove a follow
                    net.removeFollow( scanString( "Enter following user" ),
                        scanString( "Enter followed user" ) );
                    break;
                }
                case 7:
                {
                    // New post
                    net.newEvent( "P", scanString( "Enter username" ),
                        scanString( "Enter post text" ),
                        scanString( "Enter clickbait (1 for none)" ) );

                    // Bump post to posts list in network and append the
                    // stats returned to the log file
                    FileIO.appendStats( net.getLogFileName(), new String(
                        "--------------------------------\nInteractive mode\n"
                        + net.nextPost() ) );
                    System.out.println( "Post added and made active" );
                    break;
                }
                case 8:
                {
                    // Display network (Adjacency list)
                    net.displayGraph();
                    break;
                }
                case 9:
                {
                    // Run a timestep
                    net.step();
                    break;
                }
                case 10:
                {
                    // Display popularity stats
                    System.out.println( net.getPopularity() );
                    break;
                }
                case 11:
                {
                    // Display user stats
                    net.displayUserStats( scanString( "Enter username" ) );
                    break;
                }
                case 12:
                {
                    // Save network
                    FileIO.saveNet( scanString( "Enter filename" ), net );
                    break;
                }
                case 0:
                {
                    // Exit
                    menuLoop = false;
                    System.out.println( "Logs saved to " + 
                        net.getLogFileName() + ". Goodbye" );
                    break;
                }
                default:
                {
                    System.out.println( "Invalid input '" + menuSelect + "'" );
                }
            }
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: SimulationMode
    **      Import: netFileName (String), eventFileName (String),
    **                  probLike (double), probFoll (double)
    **      Export: none
    **   Assertion: Runs the simulation mode of SocialSim, automatically
    **                  loading and running the network
    */
    public static void SimulationMode( String netFileName, 
        String eventFileName, double probLike, double probFoll )
    {
        Network net = new Network( false );
            // Initialises to simulation mode

        // Set probabilites
        net.setProb( probLike, probFoll );

        // Load files
        FileIO.loadNet( netFileName, net );
        FileIO.loadEvents( eventFileName, net );

        // Run until finished
        net.run();

        System.out.println( "Logs saved to " + net.getLogFileName() + 
            ". Goodbye" );
    }

    /** User Input Methods **/
    /*  --------------------------------------------------------------
    **   Submodule: scanString
    **      Import: message (String)
    **      Export: (String)
    **   Assertion: Gets a user input string
    */
    private static String scanString( String message )
    {
        Scanner sc = new Scanner( System.in );

        System.out.print( message + ": " );

        return sc.nextLine();
    }

    /*  --------------------------------------------------------------
    **   Submodule: scanInt
    **      Import: message (String)
    **      Export: retValue (int)
    **   Assertion: Gets a user input int
    */
    private static int scanInt( String message )
    {
        Scanner sc = new Scanner( System.in );
        String input;
        boolean valid = false;
        int retValue = 0;

        while( !valid )
        {
            System.out.print( message + ": " );
            input = sc.nextLine();
            try
            {
                retValue = Integer.parseInt( input );
                valid = true;
            }
            catch( NumberFormatException e )
            {
                System.out.println( "Invalid input '" + input + "'" );
            }
        }

        return retValue;
    }

    /*  --------------------------------------------------------------
    **   Submodule: scanDouble
    **      Import: message (String)
    **      Export: retValue (double)
    **   Assertion: Gets a user input double
    */
    private static double scanDouble( String message )
    {
        Scanner sc = new Scanner( System.in );
        String input;
        boolean valid = false;
        double retValue = 0.0;

        while( !valid )
        {
            System.out.print( message + ": " );
            input = sc.nextLine();
            try
            {
                retValue = Double.parseDouble( input );
                valid = true;
            }
            catch( NumberFormatException e )
            {
                System.out.println( "Invalid input '" + input + "'" );
            }
        }

        return retValue;
    }
}
