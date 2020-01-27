/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{
    /** Bubble sort ------------------------------------------------------ **/
    public static void bubbleSort( int[] A )
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
                if( A[i] > A[ i + 1 ] )
                {
                    // Then swap
                    temp = A[i]; // Temporarily store in variable
                    A[i] = A[ i + 1 ];
                    A[ i + 1 ] = temp;
                    sorted = false;
                    // To continue sorting
                }
            }
            pass++; // Increment to next pass
        }
        while( !sorted );
    }

    /** Selection Sort --------------------------------------------------- **/
    public static void selectionSort( int[] A )
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
    }

    /** Insertion Sort --------------------------------------------------- **/
    public static void insertionSort( int[] A )
    {
        int temp = 0;
        for( int i = 1; i <= A.length - 1; i++ )
        {
            int j = i;
            temp = A[j];
            while( ( j > 0 ) && ( A[j - 1] > temp ) )
            {
                A[j] = A[ j - 1 ];
                j--;
            }
            A[j] = temp;
        }
    }

    /** Merge Sort ------------------------------------------------------- **/
    // Wrapper Method for mergeSortRec()
    public static void mergeSort( int[] A )
    {
        mergeSortRec( A, 0, A.length - 1 );
    }

    // Merge Sort Private Recursive Algorithm
    private static void mergeSortRec( int[] A, int leftIdx, int rightIdx )
    {
        int midIdx;

        if( leftIdx < rightIdx )
        {
            midIdx = ( leftIdx + rightIdx ) / 2;

            mergeSortRec( A, leftIdx, midIdx );
                // Recurse Left
            mergeSortRec( A, midIdx + 1, rightIdx );
                // Recurse Right

            merge( A, leftIdx, midIdx, rightIdx );
        }

        // Recursive base case is if the `if` is never entered
    }

    // Private Method to Merge Sub-Arrays After Recursion
    private static void merge( int[] A, int leftIdx, int midIdx, 
        int rightIdx )
    {
        int[] tempArr = new int[ rightIdx - leftIdx + 1 ];
        int i = leftIdx;
        int j = midIdx + 1;
        int k = 0;

        while( ( i <= midIdx ) && ( j <= rightIdx ) )
        {
            if( A[i] <= A[j] )
            {
                tempArr[k] = A[i];
                i++;
            }
            else
            {
                tempArr[k] = A[j];
                j++;
            }
            k++;
        }

        for( int ii = i; i <= midIdx; i++ )
        {
            // Flush remainder from left sub-array
            tempArr[k] = A[i];
            k++;
        }

        for( int jj = j; j <= rightIdx; j++ )
        {
            // Flush remainder from right sub-array
            tempArr[k] = A[j];
            k++;
        }

        for( int kk = leftIdx; kk <= rightIdx; kk++ )
        {
            // Place sorted data from tempArr into A
            A[kk] = tempArr[ kk - leftIdx ];
        }
    }

    /** Quick Sort | Left-Most Pivot Selection Implementation ------------ **/
    // Wrapper method for quickSortRec()
    public static void quickSort( int[] A )
    {
        quickSortRec( A, 0, A.length - 1 );
    }

    private static void quickSortRec( int[] A, int leftIdx, int rightIdx )
    {
        int pivIdx;
        int newPivIdx;

        // Check that the array length is > 1
        if( rightIdx > leftIdx )
        {
            pivIdx = leftIdx;
                // Left-most pivot selection
            newPivIdx = doPartitioning( A, leftIdx, rightIdx, pivIdx );

            quickSortRec( A, leftIdx, newPivIdx - 1 );
                // Recurse Left
            quickSortRec( A, newPivIdx + 1, rightIdx );
                // Recurse Right
        }
        
        // Recursive base case is if the `if` is never entered   
    }

    /** Quick Sort | Median of Three Pivot Selection Implementation ------ **/
    // Wrapper method for quickSortMed3Rec()
    public static void quickSortMed3( int[] A )
    {
        quickSortMed3Rec( A, 0, A.length - 1 );
    }
 
    // Recursive algortithm for quick sort
    private static void quickSortMed3Rec( int[] A, int leftIdx, int rightIdx )
    {
        int pivIdx;
        int newPivIdx;

        // Check that the array length is > 1
        if( rightIdx > leftIdx )
        {
            pivIdx = med3( A, leftIdx, rightIdx, ( leftIdx + rightIdx ) / 2 );
                // Median of three pivot selection
            newPivIdx = doPartitioning( A, leftIdx, rightIdx, pivIdx );

            quickSortRec( A, leftIdx, newPivIdx - 1 );
                // Recurse Left
            quickSortRec( A, newPivIdx + 1, rightIdx );
                // Recurse Right
        }
        
        // Recursive base case is if the `if` is never entered   
    }

    // Find the median of 3 values from given index
    private static int med3( int[] A, int a, int b, int c )
    { 
        int med;

        if( A[a] > A[b] && A[a] > A[c] )
        {
            med = a;
        }
        else if( A[b] > A[a] && A[b] > A[c] )
        {
            med = b;
        }
        else
        {
            med = c;
        }

        return med;
    }
 
    /** Quick Sort | Partitioning Generic Algorithm ----------------------- */
    // Used by left-most and median-of-three quick sorts
    private static int doPartitioning( int[] A, int leftIdx, int rightIdx, 
        int pivIdx )
    {
        int currIdx;
        int pivVal;
        int temp;
        int newPivIdx;
        
        // Swap the pivotVal and the right-most element
        pivVal = A[pivIdx];
        A[pivIdx] = A[rightIdx];
        A[rightIdx] = pivVal;

        // Find all values that are smaller than the pivot and transfer them
        // to the left side of the array
        currIdx = leftIdx;

        for( int i = leftIdx; i <= rightIdx - 1; i++ )
        {
            if( A[i] < pivVal )
            {
                // Swap i'th and currIdx element
                temp = A[i];
                A[i] = A[currIdx];
                A[currIdx] = temp;

                currIdx++;
            }
        }

        newPivIdx = currIdx;
        A[rightIdx] =  A[newPivIdx];
        A[newPivIdx] = pivVal;

        return newPivIdx;
    }

    /** Quick Sort | 3-Way ----------------------------------------------- **/
    // Wrapper method for quickSort3WayRec()
    public static void quickSort3Way( int[] A )
    {
        quickSort3WayRec( A, 0, A.length - 1 );
    }

    private static void quickSort3WayRec( int[] A, int leftIdx, int rightIdx )
    {
        int pivotIdx;
        int temp;
        int[] ltgt;

        if( leftIdx < rightIdx )
        {
            pivotIdx = med3( A, leftIdx, rightIdx, ( leftIdx + rightIdx ) / 2 );

            // Swap left and pivot
            temp = A[leftIdx];
            A[leftIdx] = A[pivotIdx];
            A[pivotIdx] = temp;

            ltgt = partition3Way( A, leftIdx, rightIdx );

            quickSort3WayRec( A, leftIdx, ltgt[0] - 1 );
            quickSort3WayRec( A, ltgt[1] + 1, rightIdx );
        }
    }

    private static int[] partition3Way( int[] A, int leftIdx, int rightIdx )
    {
        int lt = 1;
        int i = 1;
        int gt = rightIdx;
        int pivot = A[leftIdx];
        int[] ltgt = new int[2];
        int temp;

        while( i <= gt )
        {
            if( A[i] < pivot )
            {
                // Swap lt and i
                temp = A[lt];
                A[lt] = A[i];
                A[i] = temp;

                lt++;
                i++;
            }
            else if( A[i] > pivot )
            {
                // Swap i and gt
                temp = A[i];
                A[i] = A[gt];
                A[gt] = temp;

                gt--;
            }
            else
            {
                i++;
            }
        }

        ltgt[0] = lt;
        ltgt[1] = gt;

        return ltgt;
    }

    /*
    private static void quickSort3WayRec( int[] A, int leftIdx, int rightIdx,
        Integer i, Integer j )
    {
        if( leftIdx < rightIdx )
        {
            partition3Way( A, leftIdx, rightIdx, i, j );
            // System.out.print( "left\n" );
            quickSort3WayRec( A, leftIdx, new Integer( i ) , i, j );
                // Recurse left
            // System.out.print( "right\n" );
            quickSort3WayRec( A, new Integer( j ), rightIdx, i ,j );
                // Recurse right
        }
    }
    */

    /*
    private static void partition3Way( int[] A, int leftIdx, int rightIdx,
        Integer i, Integer j )
    {
        int temp;
        int midIdx;
        int pivot;

        if( rightIdx - leftIdx <= 1 )
        {
            if( A[rightIdx] < A[leftIdx] )
            {
                // Swap left and right
                temp = A[rightIdx];
                A[rightIdx] = A[leftIdx];
                A[leftIdx] = temp;

                i = leftIdx;
                j = rightIdx;
            }
            System.out.println( "." );
        }
        else
        {

        midIdx = leftIdx;
        pivot = A[rightIdx];
        while( midIdx <= rightIdx )
        {
            // System.out.println( midIdx + ", " + rightIdx );
            if( A[midIdx] < pivot )
            {
                // Swap left + 1 and mid + 1
                leftIdx++;
                midIdx++;
                temp = A[leftIdx];
                A[leftIdx] = A[midIdx];
                A[midIdx] = temp;
            }
            else if( A[midIdx] == pivot )
            {
                midIdx++;
            }
            else if( A[midIdx] > pivot )
            {
                // Swap mid and right - 1
                rightIdx--;
                temp = A[midIdx];
                A[midIdx] = A[rightIdx];
                A[rightIdx] = temp;
            }
        }
        i = leftIdx - 1;
        j = midIdx;

        }
    }
    */

    /*
    private static void quickSort3WayRec( int[] A, int leftIdx, int rightIdx )
    {
        int piv, pivVal;
        int lt = leftIdx;
        int currIdx = leftIdx;
        int gt = rightIdx;
        int temp;
        int min;

        piv = med3( A, leftIdx, rightIdx, ( leftIdx + rightIdx ) / 2 );
            // Median of 3 pivot selection
        pivVal = A[piv];

        // 3-way partitioning
        while( currIdx < gt )
        {
            if( A[currIdx] < pivVal )
            {
                // Current index is less than the pivot, goes to the left
                temp = A[currIdx];
                A[currIdx] = A[lt];
                A[lt] = temp;

                currIdx++;
                lt++;
            }
            else if( A[currIdx] == pivVal )
            {
                // Current index is equal to the pivot, goes in the middle
                gt--;

                temp = A[currIdx];
                A[currIdx] = A[gt];
                A[gt] = temp;
            }
            else
            {
                // Current index is greater than the pivot, stays where it is
                currIdx++;
            }
        }

        if( gt - lt < rightIdx - gt + 1 )
            min = gt - lt;
        else
            min = rightIdx - gt + 1;

        for( int i = 0; i < min; i++ )
        {
            temp = A[ lt + i ];
            A[ lt + i ] = A[ rightIdx - min + 1 + i ];
            A[ rightIdx - min + 1 + i ] = temp;
        }

        if( leftIdx < rightIdx )
        {
            quickSort3WayRec( A, leftIdx, lt - 1 );
                // Recurse left
            quickSort3WayRec( A, rightIdx - gt + lt, rightIdx );
                // Recurse right
        }
    }
    */
}
