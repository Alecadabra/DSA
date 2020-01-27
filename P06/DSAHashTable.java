import java.util.*;

public class DSAHashTable
{
    private class DSAHashEntry
    {
        /** ================== DSAHashEntry ================== **/

        /** Private Classfields **/
        private String key;
            // Key for accessing this entry
        private Object value;
            // Value stored
        private int state;
            // State of the entry; 0 for unused, 1 for used,
            // -1 for previously used but currently unused

        /** Constructors **/
        /*  --------------------------------------------------------------
        ** Constructor: Default
        **      Import: none
        **      Export: Memory adress of new DSAHashEntry
        **   Assertion: Sets key to an empty String, sets the state to 
        *                   unused, sets the value to null
        */
        private DSAHashEntry()
        {
            key = "";
            value = null;
            state = 0;
        }
 
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inKey (String), inValue (Object)
        **      Export: Memory adress of new DSAHashEntry
        **   Assertion: Sets key and value to given values, sets the state
        **                  to used
        */
        private DSAHashEntry( String inKey, Object inValue )
        {
            if( inKey == null )
            {
                throw new IllegalArgumentException( "Key cannot be null" );
            }

            key = inKey;
            value = inValue;
            state = 1;
        }

        public String toString()
        {
            return ( "Key: " + key + ", Value: " + value + ", State: " +
                state );
        }
    }

    /** ================== DSAHashTable ================== **/

    /** Private Classfields **/
    private DSAHashEntry[] hashArr;
        // The hash array
    private int count;
        // Number of used entries in array

    /** Class Constants **/
    private static final double TOL = 0.001;
        // Tolerance for comparing floating point numbers
    private static final double LOWER_LF = 0.4;
        // Minimum value for load factor to be before resizing
    private static final double UPPER_LF = 0.7;
        // Maximum value for load factor to be before resizings
    private static final int MAX_STEP = 5;
        // Prime number used in double hashing

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Alternate
    **      Import: tableSize (int)
    **      Export: Memory adress of new DSAHashTable
    **   Assertion: Finds the next prime number from the given table
    **                  size and constructs and initializes the array 
    **                  at that size and sets the count to zero
    */
    public DSAHashTable( int tableSize )
    {
        // Find next prime number from given size
        tableSize = nextPrime( tableSize );

        hashArr = new DSAHashEntry[ tableSize ];
        count = 0;

        for( int i = 0; i < tableSize; i++ )
        {
            // Initialize array
            hashArr[i] = new DSAHashEntry();
        }
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: get()
    **      Import: inKey (String)
    **      Export: (Object)
    **   Assertion: Finds the value of the entry at the given key
    */
    public Object get( String inKey )
    {
        int hashIdx = hash( inKey );
        int origIdx = hashIdx;
        boolean found = false, giveUp = false;

        while( !found && !giveUp )
        {
            if( hashArr[hashIdx].state == 0 )
            {
                // Could not find value at the key
                giveUp = true;
            }
            else if( hashArr[hashIdx].key.equals( inKey ) )
            {
                // Value found at the keya
                found = true;
            }
            else
            {
                // Double hashing
                hashIdx = 
                    ( hashIdx + stepHash( origIdx ) % hashArr.length);
                if( hashIdx == origIdx )
                {
                    // If wrapped around fully
                    giveUp = true;
                }
            }
        }

        if( !found )
        {
            throw new NoSuchElementException( "No entry found for key " +
                inKey );
        }

        return hashArr[hashIdx].value;
    }

    /*  --------------------------------------------------------------
    **   Submodule: hasKey()
    **      Import: inKey (String)
    **      Export: exists (boolean)
    **   Assertion: Returns true if the key has an asscociated value
    **                  in the table
    */
    public boolean hasKey( String inKey )
    {
        boolean exists = false;

        if( hashArr[ hash( inKey ) ].state == 1 )
        {
            exists = true;
        }

        return exists;
    }
 
    /*  --------------------------------------------------------------
    **   Submodule: getLoadFactor()
    **      Import: none
    **      Export: (double)
    **   Assertion: Returns the number of used array entries divided
    **                  by the size of the array
    */
    public double getLoadFactor()
    {
        return ( (double)count / (double)hashArr.length );
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: put()
    **      Import: inKey (String), inValue (Object)
    **      Export: none
    **   Assertion: Inserts an entry into the hash table
    */
    public void put( String inKey, Object inValue )
    {
        int hashIdx = hash( inKey );
        int origIdx = hashIdx;
        boolean found = false, giveUp = false;
        int i = 1;
            // Double hashing variables

        while( !found && !giveUp )
        {
            if( hashArr[hashIdx].state == 0 ||
                hashArr[hashIdx].state == -1 )
            {
                // If spot found to insert
                found = true;
            }
            else if( hashArr[hashIdx].state == 1 )
            {
                if( hashArr[hashIdx].key.equals( inKey ) )
                {
                    // If key is a duplicate
                    throw new IllegalArgumentException(
                        "Key " + inKey + " already exists in table" );
                }

                // Double hashing
                hashIdx = 
                    ( hashIdx +  stepHash( origIdx ) ) % hashArr.length;
                if( hashIdx == origIdx )
                {
                    // If wrapped around fully
                    giveUp = true;
                }
            }
        }

        if( !found )
        {
            throw new NoSuchElementException( 
                "No empty space found for key " + inKey );
        }

        // Sets new value of entry
        hashArr[hashIdx] = new DSAHashEntry( inKey, inValue );
        count++;

        // Resize if needed
        if( getLoadFactor() > UPPER_LF )
        {
            resize( hashArr.length * 2 );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: remove()
    **      Import: inKey (String)
    **      Export: none
    **   Assertion: Removes an entry from the hash table
    */
    public void remove( String inKey )
    {
        int hashIdx = hash( inKey );
        int origIdx = hashIdx;
        boolean found = false, giveUp = false;

        while( !found && !giveUp )
        {
            if( hashArr[hashIdx].state == 0 )
            {
                // Could not find value at the key
                giveUp = true;
            }
            else if( hashArr[hashIdx].key == inKey )
            {
                // Value found at the key
                found = true;
                count--;
            }
            else
            {
                // Double hashing
                hashIdx = 
                    ( hashIdx + stepHash( origIdx ) % hashArr.length);
                if( hashIdx == origIdx )
                {
                    // If wrapped around fully
                    giveUp = true;
                }
            }
        }

        if( !found )
        {
            throw new NoSuchElementException( 
                "No entry found for deletion for key " +
                inKey );
        }

        // Clears entry, sets as previosuly used
        hashArr[hashIdx].state = -1;
        hashArr[hashIdx].key = "";
        hashArr[hashIdx].value = null;

        // Resize of needed
        if( getLoadFactor() < LOWER_LF )
        {
            resize( hashArr.length / 2 );
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: export()
    **      Import: none
    **      Export: lines (String[])
    **   Assertion: Exports class state as a string array
    */
    public String[] export()
    {
        String[] lines = new String[ count ];
        int j = 0;
            // Index for lines

        for( int i = 0; i < hashArr.length; i++ )
        {
            if( hashArr[i].state == 1 )
            {
                // If entry is being used
                lines[j] = ( hashArr[i].key + "," + hashArr[i].value );
                j++;
            }
        }

        return lines;
    }

    /** Private Helper Functions **/
    /*  --------------------------------------------------------------
    **   Submodule: hash()
    **      Import: inKey (String)
    **      Export: hashIdx (int)
    **   Assertion: Hashes given key
    */
    private int hash( String inKey )
    {
        int a = 63689;
        int b = 378551;
        int hashIdx = 0;

        for( int i = 0; i < inKey.length(); i++ )
        {
            hashIdx = ( hashIdx * a ) + inKey.charAt(i);
            a *= b;
        }

        return Math.abs( hashIdx % hashArr.length );

        /* Old simpler hash function
        int hashIdx = 0;

        for( int i = 0; i < inKey.length(); i++ )
        {
            hashIdx = ( 31 * hashIdx ) + inKey.charAt(i);
        }

        return Math.abs( hashIdx % hashArr.length );
        */
    }

    /*  --------------------------------------------------------------
    **   Submodule: resize()
    **      Import: size (int)
    **      Export: none
    **   Assertion: Resizes the array to the size of the next prime
    **                  number from the given size
    */
    private void resize( int size )
    {

        DSAHashEntry[] oldArr = hashArr;
            // Make a copy of the original array
        size = nextPrime( size );
        int newCount = 0;

        hashArr = new DSAHashEntry[ size ];
            // Replace the hash table with a new empty one
        for( int i = 0; i < size; i++ )
        {
            // Initialize hash table
            hashArr[i] = new DSAHashEntry();
        }

        for( int i = 0; i < oldArr.length; i++ )
        {
            if( oldArr[i].state == 1 )
            {
                // Populate existing entries
                put( oldArr[i].key, oldArr[i].value );
                newCount++;
            }
        }

        count = newCount;
    }

    /*  --------------------------------------------------------------
    **   Submodule: stepHash()
    **      Import: inIndex (int)
    **      Export: (int)
    **   Assertion: Double hashing method
    */
    private int stepHash( int inIndex )
    {
        return MAX_STEP - ( inIndex % MAX_STEP );
    }

    /*  --------------------------------------------------------------
    **   Submodule: nextPrime
    **      Import: inNum (int)
    **      Export: prime (int)
    **   Assertion: Finds the next prime number from the given number
    */ 
    private int nextPrime( int inNum )
    {
        int prime = inNum;
        boolean isPrime = false;
        int i;
        double rootVal;

        if( inNum % 2 == 0 )
        {
            // Even numbers are not prime, so make it odd
            prime--;
        }

        // Test if prime is prime
        do
        {
            prime += 2;
            i = 3;
            isPrime = true;
            rootVal = Math.sqrt( (double)prime );
            do
            {
                if( prime % i == 0 )
                {
                    isPrime = false;
                }
                else
                {
                    // Increment as to skip testing even numbers
                    i += 2;
                }
            }
            while( ( ( (double)i < rootVal ) || 
                ( Math.abs( rootVal - (double)i ) < TOL ) ) && isPrime );
        }
        while( !isPrime );

        return prime;
    }
}
