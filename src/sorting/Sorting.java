package sorting;

import java.util.Date;
import java.util.Random;

public class Sorting {
    public static void main(String[] args) {
        Random random = new Random();

        // Create random array to use on sorting algorithms
        int[] unsorted = new int[100000];
        for(int i = 0; i < unsorted.length; i++) {
            unsorted[i] = random.nextInt(1000000);
        }

        // We keep track of timestamps before and after running each algorithm, and compare difference to see total running time
        long startTime = new Date().getTime();
        System.out.println("Starting Selection Sort");
        selection_sort(unsorted.clone());
        System.out.printf("Selection sort: %d ms%n", new Date().getTime() - startTime);

        startTime = new Date().getTime();
        System.out.println("Starting Merge Sort");
        merge_sort(unsorted.clone());
        System.out.printf("Merge sort: %d ms%n", new Date().getTime() - startTime);
    }

    public static void selection_sort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            // Find min
            int min_idx = i;

            for(int j = i+1; j < arr.length; j++) {
                if(arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }

            // Swap
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;
        }
    }

    public static int[] merge_sort(int[] unsorted) {
        // Base case
        if(unsorted.length == 1) {
            return unsorted;
        }

        // Split
        int mid = unsorted.length / 2;

        int[] arr0 = new int[mid];
        int[] arr1 = new int[unsorted.length - mid];

        System.arraycopy(unsorted, 0, arr0, 0, mid);
        System.arraycopy(unsorted, mid, arr1, 0, unsorted.length - mid);

        // Recursively call merge_sort on each sub-array
        arr0 = merge_sort(arr0);
        arr1 = merge_sort(arr1);

        // Merge
        int[] sorted = new int[arr0.length + arr1.length];
        int j = 0;
        int k = 0;
        for(int i = 0; i < sorted.length; i++) {
            if(j == arr0.length) { // copy rest of arr1 if all of arr0 has been copied
                System.arraycopy(arr1, k, sorted, i, arr1.length - k);
                break;
            }

            if(k == arr1.length) { // copy rest of arr0 if all of arr1 has been copied
                System.arraycopy(arr0, j, sorted, i, arr0.length - j);
                break;
            }

            // Add minimum of arr0 and arr1 to sorted array
            if(arr0[j] < arr1[k]) {
                sorted[i] = arr0[j];
                j++;
            } else {
                sorted[i] = arr1[k];
                k++;
            }
        }

        return sorted;
    }
}
