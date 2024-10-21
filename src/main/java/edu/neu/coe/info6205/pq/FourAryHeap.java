package edu.neu.coe.info6205.pq;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class FourAryHeap<K> implements Iterable<K> {

    public FourAryHeap(boolean max, Object[] heap, int first, int last, Comparator<K> comparator, boolean floyd) {
        this.max = max;
        this.first = first;
        this.comparator = comparator;
        this.last = last;
        this.heap = (K[]) heap;
        this.floyd = floyd;
    }

    public FourAryHeap(int n, int first, boolean max, Comparator<K> comparator, boolean floyd) {
        this(max, new Object[n + first], first, 0, comparator, floyd);
    }

    public FourAryHeap(int n, boolean max, Comparator<K> comparator, boolean floyd) {
        this(n, 1, max, comparator, floyd);
    }

    public FourAryHeap(int n, boolean max, Comparator<K> comparator) {
        this(n, 1, max, comparator, false);
    }

    public FourAryHeap(int n, Comparator<K> comparator) {
        this(n, 1, true, comparator, true);
    }

    public boolean isEmpty() {
        return last == 0;
    }

    public int size() {
        return last;
    }

    public void give(K key) {
        if (key == null) {
            throw new NullPointerException("Cannot insert null element into the heap");
        }
        if (last == heap.length - first)
            last--;
        heap[++last + first - 1] = key;
        swimUp(last + first - 1);
    }

    public K take() throws PQException {
        if (isEmpty()) throw new PQException("Priority queue is empty");
        if (floyd) return doTake(this::snake);
        else return doTake(this::sink);
    }

    K doTake(Consumer<Integer> f) {
        K result = heap[first];
        swap(first, last-- + first - 1);
        f.accept(first);
        heap[last + first] = null;
        return result;
    }

    void sink(int k) {
        doHeapify(k, (a, b) -> !unordered(a, b));
    }

    private int doHeapify(int k, BiPredicate<Integer, Integer> p) {
        int i = k;
        while (firstChild(i) <= last + first - 1) {
            int j = firstChild(i);
            int maxChild = j;
            for (int c = 1; c < 4 && j + c <= last + first - 1; c++) {
                if (unordered(maxChild, j + c)) maxChild = j + c;
            }
            if (p.test(i, maxChild)) break;
            swap(i, maxChild);
            i = maxChild;
        }
        return i;
    }

    void snake(int k) {
        swimUp(doHeapify(k, (a, b) -> !unordered(a, b)));
    }

    void swimUp(int k) {
        int i = k;
        while (i > first && unordered(parent(i), i)) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void swap(int i, int j) {
        K tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    boolean unordered(int i, int j) {
        if (heap[i] == null || heap[j] == null) {
            return false;
        }
        return (comparator.compare(heap[i], heap[j]) > 0) ^ max;
    }

    private int parent(int k) {
        return (k + 1 - first) / 4 + first - 1;
    }

    private int firstChild(int k) {
        return (k + 1 - first) * 4 + first - 1;
    }

    public Iterator<K> iterator() {
        Collection<K> copy = new ArrayList<>(Arrays.asList(Arrays.copyOf(heap, last + first)));
        Iterator<K> result = copy.iterator();
        if (first > 0) result.next();
        return result;
    }

    private final boolean max;
    private final int first;
    private final Comparator<K> comparator;
    private final K[] heap;
    private int last;
    private final boolean floyd;
}
