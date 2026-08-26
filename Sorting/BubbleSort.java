package Sorting;

import java.util.Arrays;

public class BubbleSort {
    /**
     * Sorts an array using the Bubble Sort algorithm.
     * Time Complexity: O(n^2) worst/average, O(n) best (already sorted).
     * Space Complexity: O(1) in-place.
     * @param array the array to sort
     */
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        int lastUnsorted = array.length - 1;
        while (lastUnsorted > 0) {
            boolean swapped = false;
            int lastSwap = 0;
            for (int j = 0; j < lastUnsorted; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                    lastSwap = j + 1;
                }
            }
            if (!swapped) break;
            lastUnsorted = lastSwap;
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }
}
