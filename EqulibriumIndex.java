public class EqulibriumIndex {
    public static int findEquilibrium(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        int totalSum = 0;
        for (int x : arr) {
            totalSum += x;
        }
        int leftSum = 0;
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
