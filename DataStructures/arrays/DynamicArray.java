package DataStructures.arrays;

/**
 * DynamicArray.java
 *
 * A generic resizable (dynamic) array â€” similar to Java's ArrayList.
 *
 * Strategy:
 *  - Start with capacity = INITIAL_CAPACITY
 *  - When full, double the capacity (amortised O(1) append)
 *  - When quarter-full after removal, halve the capacity
 *
 * Time Complexities:
 *  - add(end)    : O(1) amortised
 *  - add(index)  : O(n)
 *  - remove      : O(n)
 *  - get / set   : O(1)
 *  - contains    : O(n)
 *
 * Space Complexity: O(n)
 */
@SuppressWarnings("unchecked")
public class DynamicArray<T> {

    private static final int INITIAL_CAPACITY = 4;
    private Object[] data;
    private int size;
    private int capacity;

    public DynamicArray() {
        capacity = INITIAL_CAPACITY;
        data = new Object[capacity];
        size = 0;
    }

    // â”€â”€â”€ Grow / Shrink â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private void grow() {
        capacity *= 2;
        Object[] newData = new Object[capacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void shrink() {
        if (capacity <= INITIAL_CAPACITY) return;
        capacity /= 2;
        Object[] newData = new Object[capacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    // â”€â”€â”€ Add â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** Appends element at the end. Amortised O(1). */
    public void add(T element) {
        if (size == capacity) grow();
        data[size++] = element;
    }

    /** Inserts element at given index. O(n). */
    public void add(int index, T element) {
        checkIndexForAdd(index);
        if (size == capacity) grow();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    // â”€â”€â”€ Get / Set â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public T set(int index, T element) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = element;
        return old;
    }

    // â”€â”€â”€ Remove â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** Removes element at index and returns it. O(n). */
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;                // help GC
        if (size > 0 && size == capacity / 4) shrink();
        return removed;
    }

    /** Removes first occurrence of element. O(n). */
    public boolean remove(Object element) {
        int idx = indexOf(element);
        if (idx == -1) return false;
        remove(idx);
        return true;
    }

    // â”€â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if (element == null ? data[i] == null : element.equals(data[i])) return i;
        }
        return -1;
    }

    public boolean contains(Object element) { return indexOf(element) != -1; }

    // â”€â”€â”€ Utilities â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }
    public int capacity()    { return capacity; }

    public void clear() {
        for (int i = 0; i < size; i++) data[i] = null;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        DynamicArray<Integer> da = new DynamicArray<>();

        // Add elements
        for (int i = 1; i <= 10; i++) da.add(i * 10);
        System.out.println("Array    : " + da);
        System.out.println("Size     : " + da.size());
        System.out.println("Capacity : " + da.capacity());

        // Insert at index 2
        da.add(2, 999);
        System.out.println("After insert 999 at idx 2: " + da);

        // Remove index 5
        System.out.println("Removed  : " + da.remove(5));
        System.out.println("After remove idx 5: " + da);

        // Contains
        System.out.println("Contains 30? " + da.contains(30));
        System.out.println("Index of 999: " + da.indexOf(999));

        // Set
        da.set(0, -1);
        System.out.println("After set(0,-1): " + da);
    }
}

