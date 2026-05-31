import java.util.Arrays;

public class CountingSort {
    public static void sort(int[] array) {
        if (array.length == 0) return;

        int max = array[0];
        int min = array[0];
        for (int val : array) {
            if (val > max) max = val;
            if (val < min) min = val;
        }

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        for (int val : array) {
            count[val - min]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1, -2, 5};
        System.out.println("Original Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }
}
