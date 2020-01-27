/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{
    // bubble sort
    public static void bubbleSort(int[] A)
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
    }//bubbleSort()

    // selection sort
    public static void selectionSort(int[] A)
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
    }// selectionSort()

    // insertion sort
    public static void insertionSort(int[] A)
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
    }// insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {
    }//mergeSort()
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//mergeSortRecurse()
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
    }//merge()


    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {
    }//quickSort()
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//quickSortRecurse()
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, int pivotIdx)
    {
		return 0;	// TEMP - Replace this when you implement QuickSort
    }//doPartitioning


}//end Sorts calss
