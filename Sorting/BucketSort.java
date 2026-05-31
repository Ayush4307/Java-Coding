import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class BucketSort {
    public static void sort(int[] array) {
        int n = array.length;
        if (n <= 1) return;

        // Find min and max values
        int min = array[0];
        int max = array[0];
        for (int val : array) {
            if (val < min) min = val;
            if (val > max) max = val;
        }

        // If min equals max, all elements are identical
        if (min == max) return;

        // Create n buckets
        int bucketCount = n;
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute elements into buckets
        double range = (double) (max - min) / bucketCount;
        for (int val : array) {
            // Find bucket index
            int bucketIndex = (int) ((val - min) / range);
            if (bucketIndex >= bucketCount) {
                bucketIndex = bucketCount - 1; // Bound edge check for maximum value
            }
            buckets.get(bucketIndex).add(val);
        }

        // Sort each bucket and merge them back into the array
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            Collections.sort(buckets.get(i));
            for (int val : buckets.get(i)) {
                array[index++] = val;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {29, 10, 14, 37, 13, 2, 25, -5, 18};
        System.out.println("Original Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }
}
