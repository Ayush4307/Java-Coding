/**
 * EqulibriumIndex contains utility methods to find equilibrium points in arrays.
 */
public class EqulibriumIndex {
    /**
     * Finds the equilibrium index of an array.
     * An equilibrium index is an index where the sum of elements at lower indexes
     * is equal to the sum of elements at higher indexes.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param arr The input array
     * @return The equilibrium index, or -1 if no such index exists
     */
    public static int findEquilibrium(int[] arr) {
        // First, calculate the total sum of the array
        int totalSum = 0;
        for (int x : arr) {
            totalSum += x;
        }
        // Left sum is initialized to 0
        int leftSum = 0;
        // Iterate through the array to find equilibrium index
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (leftSum == totalSum) {
                return i;
            }
            leftSum += arr[i];
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("Equilibrium Index: " + findEquilibrium(arr));

        int[] arr2 = {1, 2, 3};
        System.out.println("Equilibrium Index (no equilibrium): " + findEquilibrium(arr2));

        int[] arr3 = {5};
        System.out.println("Equilibrium Index (single element): " + findEquilibrium(arr3));
    }
}
