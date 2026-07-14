/**
 * HashMap.java
 *
 * Custom HashMap implementation using Separate Chaining (Linked List at each bucket).
 *
 * Operations:
 *  - put(key, value)  : O(1) average, O(n) worst
 *  - get(key)         : O(1) average, O(n) worst
 *  - remove(key)      : O(1) average, O(n) worst
 *  - containsKey(key) : O(1) average
 *  - size()           : O(1)
 *  - keys() / values(): O(n)
 *
 * Resizing:
 *  - Load factor threshold = 0.75
 *  - When exceeded: double capacity and rehash all entries
 *
 * Hash function: Java's hashCode() with bit-spread like HashMap
 *
 * Space Complexity: O(n + m) where m = number of buckets
 */
@SuppressWarnings("unchecked")
public class HashMap<K, V> {

    // ─── Entry ────────────────────────────────────────────────────────────────
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        Entry(K key, V value) { this.key = key; this.value = value; }
    }

    // ─── Constants ────────────────────────────────────────────────────────────
    private static final int    DEFAULT_CAPACITY   = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    // ─── Fields ───────────────────────────────────────────────────────────────
    private Entry<K, V>[] buckets;
    private int size;
    private int capacity;
    private final double loadFactor;

    // ─── Constructors ─────────────────────────────────────────────────────────
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    public HashMap(int capacity, double loadFactor) {
        this.capacity   = capacity;
        this.loadFactor = loadFactor;
        buckets = new Entry[capacity];
    }

    // ─── Hash ─────────────────────────────────────────────────────────────────
    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        h ^= (h >>> 16);            // bit-spread to reduce collisions
        return Math.abs(h % capacity);
    }

    // ─── Put ─────────────────────────────────────────────────────────────────
    public void put(K key, V value) {
        int idx = hash(key);
        Entry<K, V> cur = buckets[idx];
        while (cur != null) {
            if (keysEqual(cur.key, key)) { cur.value = value; return; }  // update
            cur = cur.next;
        }
        // Insert at head of chain
        Entry<K, V> entry = new Entry<>(key, value);
        entry.next = buckets[idx];
        buckets[idx] = entry;
        size++;
        if ((double) size / capacity >= loadFactor) resize();
    }

    // ─── Get ─────────────────────────────────────────────────────────────────
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    // ─── Remove ──────────────────────────────────────────────────────────────
    public V remove(K key) {
        int idx = hash(key);
        Entry<K, V> cur = buckets[idx], prev = null;
        while (cur != null) {
            if (keysEqual(cur.key, key)) {
                if (prev == null) buckets[idx] = cur.next;
                else prev.next = cur.next;
                size--;
                return cur.value;
            }
            prev = cur; cur = cur.next;
        }
        return null;
    }

    // ─── Contains ────────────────────────────────────────────────────────────
    public boolean containsKey(K key) { return findEntry(key) != null; }

    // ─── Keys / Values ────────────────────────────────────────────────────────
    public java.util.List<K> keys() {
        java.util.List<K> list = new java.util.ArrayList<>();
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> cur = bucket;
            while (cur != null) { list.add(cur.key); cur = cur.next; }
        }
        return list;
    }

    public java.util.List<V> values() {
        java.util.List<V> list = new java.util.ArrayList<>();
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> cur = bucket;
            while (cur != null) { list.add(cur.value); cur = cur.next; }
        }
        return list;
    }

    // ─── Utilities ───────────────────────────────────────────────────────────
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    private Entry<K, V> findEntry(K key) {
        Entry<K, V> cur = buckets[hash(key)];
        while (cur != null) {
            if (keysEqual(cur.key, key)) return cur;
            cur = cur.next;
        }
        return null;
    }

    private boolean keysEqual(K a, K b) {
        return a == null ? b == null : a.equals(b);
    }

    // ─── Resize / Rehash ─────────────────────────────────────────────────────
    private void resize() {
        capacity *= 2;
        Entry<K, V>[] old = buckets;
        buckets = new Entry[capacity];
        size = 0;
        for (Entry<K, V> bucket : old) {
            Entry<K, V> cur = bucket;
            while (cur != null) { put(cur.key, cur.value); cur = cur.next; }
        }
    }

    public void print() {
        System.out.println("HashMap (size=" + size + ", capacity=" + capacity + "):");
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] == null) continue;
            System.out.print("  [" + i + "]: ");
            Entry<K, V> cur = buckets[i];
            while (cur != null) {
                System.out.print(cur.key + "=" + cur.value);
                if (cur.next != null) System.out.print(" -> ");
                cur = cur.next;
            }
            System.out.println();
        }
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple",  5);
        map.put("banana", 3);
        map.put("cherry", 8);
        map.put("date",   2);
        map.put("apple",  10);    // update

        map.print();

        System.out.println("\nget(apple)  : " + map.get("apple"));
        System.out.println("get(grape)  : " + map.get("grape"));
        System.out.println("containsKey(banana): " + map.containsKey("banana"));
        System.out.println("containsKey(mango) : " + map.containsKey("mango"));

        map.remove("banana");
        System.out.println("\nAfter remove(banana):");
        map.print();

        System.out.println("\nKeys  : " + map.keys());
        System.out.println("Values: " + map.values());

        // Word frequency counter
        System.out.println("\n-- Word Frequency Counter --");
        String[] words = {"the", "cat", "sat", "on", "the", "mat", "the"};
        HashMap<String, Integer> freq = new HashMap<>();
        for (String w : words) freq.put(w, freq.get(w) == null ? 1 : freq.get(w) + 1);
        freq.print();
    }
}
