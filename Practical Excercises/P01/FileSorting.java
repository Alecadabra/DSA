import java.util.*;
import java.io.*;

public class FileSorting
{
    public static void main(String[] args)
    {
        String[] stringArray = new String[7000];
            //Array of data directly from file
        int[] array = new int[7000];
            //Array of numbers from data
        int[] sorted;
            //Array to store sorted data

        readFile( stringArray, "RandomNames7000.csv" );

        for( int i = 0; i < array.length; i++ )
        {
            array[i] = Integer.parseInt( stringArray[i].split(",")[0] );
            /* For each element in the array of Strings in format 
             * "<number>,<name>", splits it by the comma to create array 
             * [ <number>, <name> ], parses index 0 (<number>) to integer 
             * and stores into a different array. */
        }

        //Bubble sort
        sorted = new int[7000]; //Creates array of 0's to add sorted data to
        sorted = bubbleSort( array.clone() ); //Sorts unsorted data into array
        saveFile( sorted, "BubbleSorted.txt" ); //Saves sorted data to file

        //Selection sort
        sorted = new int[7000];
        sorted = selectionSort( array.clone() );
        saveFile( sorted, "SelectionSorted.txt" );

        //Insertion sort
        sorted = new int[7000];
        sorted = insertionSort( array.clone() );
        saveFile( sorted, "InsertionSorted.txt" );
    }

    /******************************
     * File IO
     ******************************/

    // File Reading
    public static void readFile( String[] stringArray, String fileName )
    {
        FileInputStream strm = null;
        InputStreamReader rdr;
        BufferedReader bfr;
        int lineNum;

        try
        {
            strm = new FileInputStream( fileName );
            rdr = new InputStreamReader( strm );
            bfr = new BufferedReader( rdr );

            lineNum = 0;
            do
            {
                stringArray[ lineNum ] = bfr.readLine();
                lineNum++;
            }
            while( ( lineNum < stringArray.length ) && 
                ( stringArray[ lineNum - 1 ] != null ) );

            bfr.close();
        }
        catch( IOException e )
        {
            System.out.println( "Error in file reading: " + e.getMessage() );
        }
    }

    // File Writing
    public static void saveFile( int[] sorted, String fileName )
    {
        FileOutputStream strm = null;
        PrintWriter pw;

        try
        {
            strm = new FileOutputStream( fileName );
            pw = new PrintWriter( strm );

            for( int i = 0; i < sorted.length; i++ )
            {
                pw.println( sorted[i] );
            }
            System.out.println( "Created " + fileName );

            pw.close();
        }
        catch( IOException e )
        {
            System.out.println( "Error in file writing: " + e.getMessage() );
        }
    }   

    /******************************
     * Sorting Algorithms
     ******************************/

    // Bubble sort
    public static int[] bubbleSort(int[] A)
    {
        int pass = 0;
        boolean sorted = false, swapped = false;
        int temp = 0;
        do
        {
            sorted = true;
                // Assume sorted and change if not true
            for( int i = 0; i < ( A.length - 1 ) - pass ; i++ )
            {
                if( A[i] > A[i + 1] )
                {
                    // Then swap
                    temp = A[i]; // Temporarily store in variable
                    A[i] = A[i + 1];
                    A[i + 1] = temp;
                    sorted = false;
                    // To continue sorting
                }
            }
            pass++; // Increment to next pass
        }
        while( !sorted );
        return A;
    }

    // Selection sort
    public static int[] selectionSort(int[] A)
    {
        int minIdx = 0, temp = 0;
        for( int i = 0; i < A.length; i++ )
        {
            minIdx = i;
            for( int j = i + 1; j <= A.length - 1; j++ )
            {
                if( A[j] < A[minIdx] )
                {
                    minIdx = j;
                }
            }
            temp = A[minIdx];
            A[minIdx] = A[i];
            A[i] = temp;
        }
        return A;
    }

    // Insertion sort
    public static int[] insertionSort(int[] A)
    {
        int temp = 0;
        for( int i = 1; i <= A.length - 1; i++ )
        {
            int j = i;
            temp = A[j];
            while( ( j > 0 ) && ( A[j - 1] > temp ) )
            {
                A[j] = A[j - 1];
                j--;
            }
            A[j] = temp;
        }
        return A;
    }
}
