/**
 * HashSet.java
 *
 * Custom HashSet implementation using Open Addressing (Linear Probing).
 *
 * Differs from HashMap:
 *  - Stores only keys, no values.
 *  - Uses linear probing instead of chaining.
 *
 * Operations:
 *  - add(key)      : O(1) average
 *  - remove(key)   : O(1) average
 *  - contains(key) : O(1) average
 *  - size()        : O(1)
 *  - toArray()     : O(n)
 *
 * Load factor threshold = 0.5 (lower than HashMap because probing degrades faster)
 * On resize: capacity doubles, all keys rehashed.
 *
 * Space Complexity: O(n)
 */
public class HashSet<K> {

    // ─── Sentinel for deleted slots ───────────────────────────────────────────
    private static final Object DELETED = new Object();

    // ─── Fields ───────────────────────────────────────────────────────────────
    private Object[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.5;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public HashSet() { this(16); }

    public HashSet(int capacity) {
        this.capacity = capacity;
        table = new Object[capacity];
    }

    // ─── Hash ────────────────────────────────────────────────────────────────
    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        h ^= (h >>> 16);
        return Math.abs(h % capacity);
    }

    // ─── Add ─────────────────────────────────────────────────────────────────
    /** Returns true if key was newly added. O(1) amortised. */
    @SuppressWarnings("unchecked")
    public boolean add(K key) {
        if ((double)(size + 1) / capacity > LOAD_FACTOR) resize();
        int idx = hash(key);
        int firstDeleted = -1;
        for (int i = 0; i < capacity; i++) {
            int probe = (idx + i) % capacity;
            if (table[probe] == null) {
                table[firstDeleted >= 0 ? firstDeleted : probe] = key;
                size++;
                return true;
            }
            if (table[probe] == DELETED) {
                if (firstDeleted < 0) firstDeleted = probe;
            } else if (table[probe].equals(key)) {
                return false; // already exists
            }
        }
        // Use first deleted slot
        table[firstDeleted] = key;
        size++;
        return true;
    }

    // ─── Remove ──────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        int idx = hash(key);
        for (int i = 0; i < capacity; i++) {
            int probe = (idx + i) % capacity;
            if (table[probe] == null) return false;
            if (table[probe] != DELETED && table[probe].equals(key)) {
                table[probe] = DELETED;
                size--;
                return true;
            }
        }
        return false;
    }

    // ─── Contains ────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public boolean contains(K key) {
        int idx = hash(key);
        for (int i = 0; i < capacity; i++) {
            int probe = (idx + i) % capacity;
            if (table[probe] == null) return false;
            if (table[probe] != DELETED && table[probe].equals(key)) return true;
        }
        return false;
    }

    // ─── Utilities ───────────────────────────────────────────────────────────
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    @SuppressWarnings("unchecked")
    public java.util.List<K> toList() {
        java.util.List<K> list = new java.util.ArrayList<>();
        for (Object o : table) {
            if (o != null && o != DELETED) list.add((K) o);
        }
        return list;
    }

    // ─── Resize ──────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private void resize() {
        Object[] old = table;
        capacity *= 2;
        table = new Object[capacity];
        size = 0;
        for (Object o : old) {
            if (o != null && o != DELETED) add((K) o);
        }
    }

    // ─── Set Operations ──────────────────────────────────────────────────────
    /** Union: elements in either set. */
    public HashSet<K> union(HashSet<K> other) {
        HashSet<K> result = new HashSet<>();
        for (K k : this.toList())  result.add(k);
        for (K k : other.toList()) result.add(k);
        return result;
    }

    /** Intersection: elements in both sets. */
    public HashSet<K> intersection(HashSet<K> other) {
        HashSet<K> result = new HashSet<>();
        for (K k : this.toList()) if (other.contains(k)) result.add(k);
        return result;
    }

    /** Difference: elements in this but NOT in other. */
    public HashSet<K> difference(HashSet<K> other) {
        HashSet<K> result = new HashSet<>();
        for (K k : this.toList()) if (!other.contains(k)) result.add(k);
        return result;
    }

    public void print() {
        System.out.println("HashSet " + toList());
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        HashSet<Integer> setA = new HashSet<>();
        for (int x : new int[]{1, 2, 3, 4, 5, 5, 3}) setA.add(x); // duplicates ignored
        setA.print();

        HashSet<Integer> setB = new HashSet<>();
        for (int x : new int[]{3, 4, 5, 6, 7}) setB.add(x);
        setB.print();

        System.out.println("Union       : " + setA.union(setB).toList());
        System.out.println("Intersection: " + setA.intersection(setB).toList());
        System.out.println("Difference  : " + setA.difference(setB).toList());

        System.out.println("\ncontains(3): " + setA.contains(3));
        setA.remove(3);
        System.out.println("After remove(3): " + setA.toList());
        System.out.println("contains(3): " + setA.contains(3));

        // Find duplicates in array using HashSet
        System.out.println("\n-- Find duplicates in {1,2,3,1,4,2,5} --");
        int[] arr = {1, 2, 3, 1, 4, 2, 5};
        HashSet<Integer> seen = new HashSet<>();
        java.util.List<Integer> dupes = new java.util.ArrayList<>();
        for (int x : arr) { if (!seen.add(x)) dupes.add(x); }
        System.out.println("Duplicates: " + dupes);
    }
}
