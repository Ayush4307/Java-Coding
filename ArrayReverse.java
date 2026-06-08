public class ArrayReverse {
    public static void reverseArray(int[] arr) {
        if (arr == null || arr.length <= 1) return;
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

        int[] arr3 = {42};
        System.out.println("\nOriginal array (single element): " + java.util.Arrays.toString(arr3));
        reverseArray(arr3);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr3));

        int[] arr4 = {};
        System.out.println("\nOriginal array (empty): " + java.util.Arrays.toString(arr4));
        reverseArray(arr4);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr4));
    }
}

//Time Complexity

//The algorithm uses two pointers (start and end) that move toward the center of the array. In each iteration, one pair of elements is swapped. Since approximately n/2 swaps are performed for an array of size n, the running time grows linearly with the size of the array.

//Time Complexity: O(n)

//Space Complexity

//The algorithm performs the reversal directly within the original array and uses only three additional variables (start, end, and temp) regardless of the input size.

//Space Complexity: O(1)

//Why This Approach is Used

//This approach is known as the Two-Pointer In-Place Reversal Technique. It is preferred because:

//It reverses the array without creating an additional array.
//It requires only constant extra memory.
//It achieves optimal linear-time performance.
//It is simple to implement and easy to understand.

//Thus, this method provides an efficient solution for reversing an array while minimizing both execution time and memory usage.
