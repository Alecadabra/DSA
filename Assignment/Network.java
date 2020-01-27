/*  ==========================================================================
**   Author: Alec Maughan 19513869
**  Purpose: Class representing a social network. Contains all implementation
**              for social network operations such as adding/removing users,
**              follows and posts and propagating the posts through the
**              network through 'timesteps' as well as collecting statistics
**              and logs for data analysis
**     Date: 27/10/19
**  ==========================================================================
*/

import java.util.*;
import java.text.SimpleDateFormat;

public class Network
{
    private class Post
    {
        /** ========================== Post ========================== **/

        /** Private Classfields **/ 
        private String userName;
            // User name of user
        private String text;
            // Text of the post
        private int time;
            // Total real-world time taken to propagate (microseconds)
        private int likes;
            // Number of likes
        private int postId;
            // ID of post, generated by Network method
        private int clickbait;
            // Clickbait of the post, a multiplier for the like chance
        private DSALinkedList seen;
            // Linked list of all users that have seen this post
        private DSALinkedList connections;
            // The users that will see this post on the next timestep

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inUserName (String), inText (String), inClickbait (int)
        **      Export: Memory address of new Post object
        **   Assertion: Initialises fields, constructs ADTs and sets username,
        **                  text and clickbait to their given values
        */
        private Post( String inUserName, String inText, int inClickbait )
        {
            userName = inUserName;
            text = inText;
            time = 0;
            likes = 0;
            postId = generatePostId();
            clickbait = inClickbait;
            seen = new DSALinkedList();
            connections = new DSALinkedList();
        }
    }

    private class User
    {
        /** ========================== User ========================== **/

        /** Private Classfields **/
        private String userName;
            // Username, must be unique
        private int followers;
            // Number of followers
        private int following;
            // Number of people following
        private int posts;
            // Number of posts

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inUserName (String)
        **      Export: Memory address of new User object
        **   Assertion: Initialises fields and sets userName to given value
        */
        private User( String inUserName )
        {
            userName = inUserName;
            followers = 0;
            following = 0;
            posts = 0;
        }

        /** Accessors **/
        public String toString()
        {
            // Used by DSADigraph.export()
            return userName;
        }

        public boolean equals( User inUser )
        {
            // Used in seenBy()
            return userName == inUser.userName;
        }
    }

    private class Follow
    {
        /** ========================== Follow ========================== **/

        /** Private Classfields **/
        private String follower;
            // The person following the other (Start of the graph edge)
        private String following;
            // The person being followed

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inFollowing (String), inFollower (String)
        **      Export: Memory address of new Follow object
        **   Assertion: Sets names to given values
        */
        private Follow( String inFollowing, String inFollower )
        {
            follower = inFollower;
            following = inFollowing;
        }

        /** Accessor **/
        public String toString()
        {
            // Used by DSADigraph.export()
            return following + ":" + follower;
        }
    }

    /** ========================== Network ========================== **/

    /** Private Classfields **/
    private String logFileName;
        // Filename to save logs to
    private double probLike;
        // Probability to like a post (0-1)
    private double probFoll;
        // Probability to follow a user (0-1)
    private int numPosts;
        // Number of posts that exist, used to generate post IDs
    private int time;
        // Current timestep
    private DSAQueue events;
        // All events
    private DSADigraph graph;
        // Directed graph of users. An edge signifies a follow between users
    private DSALinkedList posts;
        // Linked list of all active posts, operates as a queue. In simulation
        // mode this will only ever hold one value, but in interactive mode it
        // could carry multiple. This only stores posts that are currently
        // propagating through the network
    private DSAHeap popularPosts;
        // Max priority heap of all previously propagated posts prioritised by
        // no. of likes
    private boolean interactive;
        // True if running in interactive mode, false if in simulation mode
    private int likesDiff;
        // Slight modification made to the program to automate date collection
        // See report for details


    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Alternate
    **      Import: inInteractive (boolean)
    **      Export: Memory address of new Network object
    **   Assertion: Initialises fields, constructs ADTs and sets interactive
    **                  boolean to given value
    */
    public Network( boolean inInteractive )
    {
        graph = new DSADigraph();
        events = new DSAQueue();
        posts = new DSALinkedList();
        probLike = 0.0;
        probFoll = 0.0;
        numPosts = 0;
        time = 1;
        logFileName = generateLogFileName();
        popularPosts = new DSAHeap( 50 );
            // 50 chosen as a max size for this heap as it would never be
            // exceeded in normal use with the given sample data
        interactive = inInteractive;
        likesDiff = 0;
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: setProb
    **      Import: inProbLike (double), inProbFoll (double)
    **      Export: none
    **   Assertion: Sets the like and follow probabilites
    */
    public void setProb( double inProbLike, double inProbFoll )
    {
        probLike = inProbLike;
        probFoll = inProbFoll;
    }

    /*  --------------------------------------------------------------
    **   Submodule: newUser
    **      Import: userName (String)
    **      Export: none
    **   Assertion: Construts a new User from given username and adds it
    **                  to the graph
    */
    public void newUser( String userName )
    {
        graph.addVertex( new User( userName ) );
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeUser
    **      Import: userName (String)
    **      Export: none
    **   Assertion: Removes a given user from the graph
    */
    public void removeUser( String userName )
    {
        graph.removeVertex( userName );
    }

    /*  --------------------------------------------------------------
    **   Submodule: newFollow
    **      Import: following (String), follower (String)
    **      Export: none
    **   Assertion: Makes follower follow following. Works by adding a
    **                  new directed edge to the graph and incrementing
    **                  each user's followers/following counts
    */
    public void newFollow( String following, String follower )
    {
        graph.addEdge( following, follower );
        ( (User)( graph.getValue( following ) ) ).followers++;
            // Increment followers
        ( (User)( graph.getValue( follower ) ) ).following++;
            // Increment following
    }

    /*  --------------------------------------------------------------
    **   Submodule: newFollow
    **      Import: inFollow (Follow)
    **      Export: none
    **   Assertion: Makes follower follow following. Works by adding a
    **                  new directed edge to the graph and incrementing
    **                  each user's followers/following counts
    */
    public void newFollow( Follow inFollow )
    {
        graph.addEdge( inFollow.following, inFollow.follower );
        ( (User)( graph.getValue( inFollow.following ) ) ).followers++;
            // Increment followers
        ( (User)( graph.getValue( inFollow.follower ) ) ).following++;
            // Increment following
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeFollow
    **      Import: following (String), follower (String)
    **      Export: none
    **   Assertion: Makes follower unfollow following. Works by removing a
    **                  directed edge from the graph and decrementing each
    **                  user's followers/following counts
    */
    public void removeFollow( String following, String follower )
    {
        graph.removeEdge( following, follower );
        ( (User)( graph.getValue( following ) ) ).followers--;
            // Decrement followers
        ( (User)( graph.getValue( follower ) ) ).following--;
            // Decrement following
    }

    /*  --------------------------------------------------------------
    **   Submodule: removeFollow
    **      Import: inFollow (Follow)
    **      Export: none
    **   Assertion: Makes follower unfollow following. Works by removing a
    **                  directed edge from the graph and decrementing each
    **                  user's followers/following counts
    */
    public void removeFollow( Follow inFollow )
    {
        graph.removeEdge( inFollow.following, inFollow.follower );
        ( (User)( graph.getValue( inFollow.following ) ) ).followers--;
            // Decrement followers
        ( (User)( graph.getValue( inFollow.follower ) ) ).following--;
            // Decrement following
    }

    /*  --------------------------------------------------------------
    **   Submodule: newPost
    **      Import: inPost (Post)
    **      Export: none
    **   Assertion: Sets a given post to active, and doing initial
    **                  propagation to ready it for timestep propagation.
    */
    public void newPost( Post inPost )
    {
        posts.insertLast( inPost );
        
        // Do initial propagation to all followers
        for( Object o : graph.getVerticies() )
        {
            if( graph.isAdjacent( inPost.userName, ( (User)o ).userName ) )
            {
                inPost.connections.insertLast( (User)o );
            }
        }
 
        // Original poster has already seen post
        inPost.seen.insertLast( graph.getValue( inPost.userName ) );

        // Increment num posts for user
        ( (User)( graph.getValue( inPost.userName ) ) ).posts++;

    }

    /*  --------------------------------------------------------------
    **   Submodule: newEvent
    **      Import: args (String[] varargs)
    **      Export: none
    **   Assertion: Adds a new event to the event queue based on multiple
    **                  String parameters
    */
    public void newEvent( String... args )
    {
        char type = args[0].charAt( 0 );
            /* Type of event:
             * A: Add user
             * R: Remove user
             * F: New follow
             * U: Unfollow
             * P: Post */

        if( type == 'A' || type == 'R' )
        {
            // Add/remove a new user
            events.enqueue( new User( args[1] ) );
        }
        else if( type == 'F' || type == 'U' )
        {
            // Follow/unfollow a user
            events.enqueue( new Follow( args[1], args[2] ) );
        }
        else if( type == 'P' || args.length == 3 )
        {
            // Post with no clickbait provided
            events.enqueue( new Post( args[1], args[2], 1 ) );
        }
        else if( type == 'P' || args.length == 4 )
        {
            // Post with clickbait provided
            events.enqueue( new Post( args[1], args[2],
                Integer.parseInt( args[3] ) ) );
        }
        else
        {
            throw new IllegalArgumentException( "Invalid event" );
        }
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: export
    **      Import: outVerticies (DSALinkedList), outAdjacency (DSALinkedList)
    **      Export: none
    **   Assertion: Exports state of graph to two linked lists, one of
    **                  all verticies, another of all adjacencys in the
    **                  format "<label>:<adjacent label>"
    */
    public void export( DSALinkedList outVerticies, 
        DSALinkedList outAdjacency )
    {
        graph.export( outVerticies, outAdjacency );
    }

    /*  --------------------------------------------------------------
    **   Submodule: displayGraph
    **      Import: none
    **      Export: none
    **   Assertion: Uses DSADigraph.displayAsList() to display the
    **                  adjacency list of the graph
    */
    public void displayGraph()
    {
        System.out.println( 
            "Adjacency List of Network.' A | B ' means B follows A" );
        graph.displayAsList();
    }

    /*  --------------------------------------------------------------
    **   Submodule: getPopularity
    **      Import: none
    **      Export: display (String)
    **   Assertion: Creates a String listing all posts and users by
    **                  popularity to be printed. Utilizes the partially-
    **                  sorted abstract data type priority heap
    */
    public String getPopularity()
    {
        DSAHeap copyPopularPosts = new DSAHeap( popularPosts );
            // Use a copy so we dont clear it incase we want to use it again
        DSAHeap popularUsers = new DSAHeap( graph.getVertexCount() );
            // Max priority heap of users, ordered by no. followers, for the
            // array size, use the number of users in the graph
        User currUser;
            // Current user we're getting stats of
        String display = "--------------------------------\n\n";
            // String to return to caller

        // Add popular posts heap (Already populated each time a post
        // finishes propagating)
        display += "Posts in order of likes:\n";
        while( copyPopularPosts.getCount() > 0 )
        {
            // The heap is prioritised by no. likes and it's values are strings
            // formatted for this context
            display += copyPopularPosts.remove() + "\n";
        }

        // Populate popular users (Not automatically populated)
        // Iterate through users (All graph verticies)
        for( Object o : graph.getVerticies() )
        {
            currUser = (User)o;
            popularUsers.add( currUser.followers, new String(
                "User " + currUser.userName + " with " + 
                currUser.followers + " followers" ) );
        }

        // Print out users in order of likes
        display += "\nUsers in order of followers" + "\n";
        while( popularUsers.getCount() > 0 )
        {
            // Just like with the posts heap, this is prioritised by no. of
            // likes and with values as strings formatted for this context
            display += popularUsers.remove() + "\n";
        }

        return display;
    }

    /*  --------------------------------------------------------------
    **   Submodule: displayUserStats
    **      Import: inUserName (String)
    **      Export: none
    **   Assertion: Prints the statistics for the given user, their number
    **                  of posts, followers and users they follow
    */
    public void displayUserStats( String inUserName )
    {
        User inUser = (User)graph.getValue( inUserName );

        System.out.println( "Statistics for " + inUserName + ":" +
            "\nNo. of posts: " + inUser.posts +
            "\nNo. of followers: " + inUser.followers +
            "\nNo. following: " + inUser.following );
    }

    /*  --------------------------------------------------------------
    **   Submodule: getLogFileName
    **      Import: none
    **      Export: logFileName (String)
    **   Assertion: Returns the file name for the log file
    */
    public String getLogFileName()
    {
        return logFileName;
    }

    /** Post Propagation **/
    /*  --------------------------------------------------------------
    **   Submodule: run
    **      Import: none
    **      Export: none
    **   Assertion: Used by simulation mode. Continuously runs timesteps
    **                  until there are no events or posts left to simulate
    */
    public void run()
    {
        String stats = "";
            // String to append to the log file

        // Run until there is no events left
        while( !events.isEmpty() )
        {
            // Run a timestep
            step();

            // If there are no posts, iterate through events until we get one
            if( posts.isEmpty() )
            {
                stats = nextPost();
                    // nextPost() returns details of what happened
                FileIO.appendStats( logFileName, stats );
            }
        }

        // Once events is empty, there may be posts that have not finished
        // propagating yet
        while( !posts.isEmpty() )
        {
            step();
        }

        // Append the popularity of users/posts to the end of the log file
        FileIO.appendStats( logFileName, getPopularity() );
    }

    /*  --------------------------------------------------------------
    **   Submodule: step
    **      Import: none
    **      Export: none
    **   Assertion: Runs a single timestep. A timestep is used to propagate
    **                  each active post one level out, and to run events
    **                  until a post is found if there are no active posts
    */
    public void step()
    {
        String stats = ( "--------------------------------\n" +
            "Timestep " + time + "\n\n" + events.getCount() +
            " event(s) to get through, " + posts.getCount() + 
            " post(s) to get through.\n\n" );
            // String to append to the log file
        long startTime;
            // Real-world time, used to measure how long it takes for
            // the current post to propagate
        Post currPost;
            // Current post from list of active posts
        DSALinkedList nextConnections;
            // The next list of connections for this post to use on the next
            // step

        // Propagate each post
        for( Object o : posts )
        {
            currPost = (Post)o;

            FileIO.appendStats( logFileName, ( stats +
                "For post ID " + currPost.postId + " by " + 
                currPost.userName + ":\n" ) );
            stats = "";
 
            startTime = System.nanoTime() / 1000;
                // Convert nanoseconds to microseconds
            nextConnections = propagate( currPost );
                // populate nextConnections
            currPost.time += ( System.nanoTime() / 1000 - startTime );
                // Find time difference from start to finish and add to the
                // total time of this post
            if( nextConnections.isEmpty() )
            {
                // Post is finished propagating
                stats = ( "Post " + currPost.postId + 
                    " finished propagating after " + currPost.time + 
                    " microseconds\n" );
                posts.removeNode( currPost );
                popularPosts.add( currPost.likes, new String(
                    "Post ID " + currPost.postId + " by " + currPost.userName
                    + " with " + currPost.likes + " likes" ) );
                    // Add details of this post to the priority heap

                // Before deleting the data, store the number of likes if we
                // are using the report test data netTest.txt and
                // eventsTest.txt. See report for more details
                if( currPost.postId == 1 &&
                    currPost.text.equals( "Post One" ) )
                {
                    likesDiff = currPost.likes;
                }
                else if( currPost.postId == 6 &&
                    currPost.text.equals( "Post Six" ) )
                {
                    likesDiff = currPost.likes - likesDiff;
                    System.out.println( "Likes difference: " + likesDiff );
                }
        
            }
            else
            {
                // If the post hasn't finished propagating, set it's
                // connections to the connections for it's next timestep
                currPost.connections = nextConnections;
            }
        }

        FileIO.appendStats( logFileName, stats );

        // In interactive mode, we print the stats as we go
        if( interactive )
        {
            System.out.println( stats );
        }
   
        // Increment timestep
        time++;
    }

    /*  --------------------------------------------------------------
    **   Submodule: nextPost
    **      Import: none
    **      Export: String
    **   Assertion: Dequeues the events queue until a post is made active
    */
    public String nextPost()
    {
        Object currEvent;
            // Current event dequeued from events
        User currUser;
        Post currPost = null;
        Follow currFollow;
        String stats = "\nIterating through events to find next post:\n";
            // Stats to append to the log file

        // Work through events until a new post is made
        while( currPost == null && !events.isEmpty() )
        {
            currEvent = events.dequeue();
            if( currEvent instanceof User )
            {
                // Typecast to useful datatype
                currUser = (User)currEvent;

                if( graph.getVerticies().hasNode( currUser ) )
                {
                    // Removing this user
                    graph.removeVertex( currUser.userName );
                    stats += "Removed user " + currUser.userName + "\n";
                }
                else
                {
                    // Adding a user
                    graph.addVertex( currUser );
                    stats += "Added user " + currUser.userName + "\n";
                }
            }
            else if( currEvent instanceof Follow )
            {
                currFollow = (Follow)currEvent;

                if( graph.isAdjacent( currFollow.following, 
                    currFollow.follower ) )
                {
                    // Removing this follow
                    removeFollow( currFollow );
                    stats += currFollow.follower + " unfollowed " + 
                        currFollow.following + "\n";
                }
                else
                {
                    // Adding this follow
                    newFollow( currFollow );
                    stats += currFollow.follower + " followed " + 
                        currFollow.following + "\n";
                }
            }
            else if( currEvent instanceof Post )
            {
                // New post
                currPost = (Post)currEvent;

                // Add the post to the posts list
                newPost( currPost );
                stats += "New post by " + currPost.userName + " (ID: " +
                    currPost.postId + ", Clickbait: " + currPost.clickbait +
                    "):\n" + currPost.text + "\n\n";
            }
            else
            {
                throw new IllegalArgumentException( "Invalid event" );
            }
        }

        return stats;
    }

    /*  --------------------------------------------------------------
    **   Submodule: propagate
    **      Import: inPost (Post)
    **      Export: connections (DSALinkedList)
    **   Assertion: Propagates one level out on a given post using it's
    **                  connections list and creates the connections list
    **                  for the next timestep
    */
    private DSALinkedList propagate( Post inPost )
    {
        DSALinkedList connections = new DSALinkedList();
            // The connections list for the next timestep of this post,
            // it is populated in this method
        String stats = "";
            // Stats to append to the log file
        User currUser;
            // The current user from the connections list

        // Iterate through each user connected to this post
        for( Object o : inPost.connections )
        {
            // If not already seen by this user
            if( !seenBy( inPost, (User)o ) )
            {
                currUser = (User)o;

                // Mark as seen by them
                inPost.seen.insertLast( currUser );
                stats += "Post seen by " + currUser.userName + "\n";

                // Test if liked
                if( Math.random() < (double)inPost.clickbait * probLike )
                {
                    inPost.likes++;
                    stats += "Post liked by " + currUser.userName + "\n";

                    // Add each of currUser's followers to the post
                    // connections for the next timestep
                    for( Object o2 : graph.getAdjacent( currUser.userName ) )
                    {
                        if( !seenBy( inPost, (User)o2 ) )
                        {
                            connections.insertLast( (User)o2 );
                        }
                    }
                    
                    // Test if followed
                    if( Math.random() < probFoll &&
                        !graph.isAdjacent( 
                        inPost.userName, currUser.userName ) )
                    {
                        newFollow( inPost.userName, currUser.userName );
                        stats += currUser.userName + " followed " +
                            inPost.userName + "\n";
                    }
                }
            }
        }

        if( !stats.equals( "" ) )
        {
            // Append the stats to log file if it was written to
            FileIO.appendStats( logFileName, stats );
        }

        return connections;
    }

    /** Private Helper Submodules **/
    /*  --------------------------------------------------------------
    **   Submodule: generateLogFileName
    **      Import: none
    **      Export: (String)
    **   Assertion: Uses the current date and time to make a unique file
    **                  name for the progrram to store logs in
    */
    private String generateLogFileName()
    {
        // Variables for getting the current date/time
        SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM_HH-mm-ss" );
        Date date = new Date( System.currentTimeMillis() );

        return ( "SocialSimLogs_" + formatter.format( date ) + ".txt" );
    }

    /*  --------------------------------------------------------------
    **   Submodule: seenBy
    **      Import: inPost (Post), inUser (User)
    **      Export: seen (boolean)
    **   Assertion: Returns true if the given user has previously seen the
    **                  given post
    */
    private boolean seenBy( Post inPost, User inUser )
    {
        boolean seen = false;

        for( Object o : inPost.seen )
        {
            if( ( (User)o ).equals( inUser ) )
            {
                seen = true;
            }
        }

        return seen;
    }

    /*  --------------------------------------------------------------
    **   Submodule: generatePostId
    **      Import: none
    **      Export: (int)
    **   Assertion: Increments the number of posts and returns it, used to
    **                  create unique, sequential identifiers for posts
    */
    private int generatePostId()
    {
        return ++numPosts;
    }

}
