public class Exam
{
    public static void main(String[] args)
    {
        int[] Arr = new int[6];
        Arr[0] = 9;
        Arr[1] = 5;
        Arr[2] = 8;
        Arr[3] = 10;
        Arr[4] = -1;
        Arr[5] = 14;

        MergeSort(Arr, 0, 5);
        System.out.println("Line 1 //////");
        printArray(Arr);
    }

    public static void MergeSort(int Ar[], int first, int last)
    {
        if (first < last)
        {
            int mid = (first + last) / 2;

            MergeSort(Ar, first, mid);
            MergeSort(Ar, mid + 1, last);

            Merge(Ar, first, mid , last);
        }
    }

    public static void Merge(int Ar[], int first, int mid, int last)
    {
        int[] tempArray = new int[Ar.length];

        int first1 = first;
        int last1 = mid;
        int first2 = mid + 1;
        int last2 = last;

        int index = first1;
        for(; (first1 <= last1) && (first2 <= last2); ++index)
        {
            if(Ar[first1] < Ar[first2])
            {
                tempArray[index] = Ar[first1];
                ++first1;
            }
            else
            {
                tempArray[index] = Ar[first2];
                ++first2;
            }
        }

        for(; first1 <= last1; ++first1, ++index)
            tempArray[index] = Ar[first1];
        
        for(; first2 <= last2; ++first2, ++index)
            tempArray[index] = Ar[first2];

        for(index = first; index <= last; ++index)
            Ar[index] = tempArray[index];
    }
    

        

    

    private static void printArray(int[] anArray) {
        for (int i = 0; i < anArray.length; i++) {
           if (i > 0) {
              System.out.print(", ");
           }
           System.out.print(anArray[i]);
        }
     }
}