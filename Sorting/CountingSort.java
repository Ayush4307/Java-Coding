import java.util.Arrays;

public class CountingSort {
    public static void sort(int[] array) {
        if (array.length == 0) return;

        // Find min and max values to handle negative numbers and offset ranges
        int max = array[0];
        int min = array[0];
        for (int val : array) {
            if (val > max) max = val;
            if (val < min) min = val;
        }

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        // Store counts of each unique element
        for (int val : array) {
            count[val - min]++;
        }

        // Change count[i] so that count[i] now contains the actual
        // position of this element in output array
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Build the output character array
        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        // Copy the output array to original array
        System.arraycopy(output, 0, array, 0, array.length);
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1, -2, 5};
        System.out.println("Original Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }
}
