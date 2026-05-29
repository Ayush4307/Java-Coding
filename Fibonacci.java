public class Fibonacci {
    /**
     * Calculates the nth Fibonacci number using Dynamic Programming.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibDP(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }   
        return dp[n];
    }
    /**
     * Calculates the nth Fibonacci number with optimized space.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibOptimized(int n) {
        if (n <= 1) return n;
        int prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
    public static void main(String[] args) {
        int n = 10;   
        System.out.println("Using DP array:");
        System.out.println("F(" + n + ") = " + fibDP(n));
        System.out.println("\nUsing optimized space:");
        System.out.println("F(" + n + ") = " + fibOptimized(n));
        System.out.println("\nFirst 10 Fibonacci numbers:");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibDP(i) + " ");
        }
    }
}
