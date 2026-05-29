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
