import java.util.Arrays;

public class QuickSort {
    public static void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            int pivot = array[mid];

            int i = low;
            int j = high;
            while (i <= j) {
                while (array[i] < pivot) i++;
                while (array[j] > pivot) j--;
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }

            if (low < j) quickSort(array, low, j);
            if (i < high) quickSort(array, i, high);
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5, 90};
        System.out.println("Original Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }
}
