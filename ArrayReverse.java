/**
 * ArrayReverse contains utility methods for reversing arrays in-place.
 */
public class ArrayReverse {
    /**
     * Reverses an array of integers in-place.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param arr The array to be reversed
     */
    public static void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original array: " + java.util.Arrays.toString(arr));
        reverseArray(arr);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr));

        int[] arr2 = {1, 2, 3, 4};
        System.out.println("\nOriginal array (even elements): " + java.util.Arrays.toString(arr2));
        reverseArray(arr2);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr2));
    }
}
