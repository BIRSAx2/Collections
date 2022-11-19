package dev.mouhieddine.tree;

import java.nio.BufferUnderflowException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryHeap<E> implements Heap<E> {

	private final static int DEFAULT_CAPACITY = 13;
	protected int size;
	protected E[] heap;
	protected boolean isMinHeap;
	protected Comparator<E> comparator;

	public BinaryHeap(int size, boolean isMinHeap) {
		this.size = size;
		this.isMinHeap = isMinHeap;
	}

	public BinaryHeap(int size, Comparator<E> comparator) {
		this(size);
		this.comparator = comparator;
	}

	public BinaryHeap(boolean isMinHeap) {
		this(DEFAULT_CAPACITY, isMinHeap);
	}

	public BinaryHeap(int size) {
		this(size, true);
	}

	public BinaryHeap(Comparator<E> comparator) {
		this(DEFAULT_CAPACITY, true);
		this.comparator = comparator;
	}

	/**
	 * @param element
	 */
	@Override
	public void insert(E element) {
		if (isFull()) ensureCapacity();
		if (isMinHeap) bubbleUpMinHeap(element);
		else bubbleUpMaxHeap(element);

	}

	/**
	 * @return
	 */
	@Override
	public E peek() {
		if (isEmpty()) return null;
		return heap[1];
	}

	/**
	 * @return
	 */
	@Override
	public E pop() {
		if (isEmpty()) return null;
		E element = heap[1];
		heap[1] = heap[size];
		heap[size] = null;
		size--;

		if (!isEmpty()) {
			if (isMinHeap) bubbleDownMinHeap(1);
			else bubbleDownMaxHeap(1);
		}
		return element;
	}

	/**
	 * @return
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * @return
	 */
	@Override
	public Iterator<E> iterator() {
		return new HeapIterator();
	}

	public boolean isFull() {
		return size + 1 == heap.length;
	}

	/**
	 * @param e element whose presence in this collection is to be ensured
	 * @return
	 */
	@Override
	public boolean add(E e) {
		insert(e);
		return true;
	}

	/**
	 * @param o element to be removed from this collection, if present
	 * @return
	 */
	@Override
	public boolean remove(Object o) {
		try {
			pop();
		} catch (NoSuchElementException ex) {
			throw new BufferUnderflowException();
		}

		return true;
	}

	/**
	 *
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		heap = (E[]) new Object[heap.length];
		size = 0;
	}

	protected void bubbleDownMinHeap(final int index) {
		final E element = heap[index];
		int hole = index;
		while (hole * 2 <= size) {
			int child = getLeftChildIndex(index);

			// if we have a right child and that child can not be bubbled up then move onto the other child

			if (child != size && compare(getRightChild(index), getLeftChild(index)) < 0) child = getRightChildIndex(index);

			// if we found resting place of bubble then terminate search
			if (compare(heap[child], element) >= 0) break;

			heap[hole] = heap[child];
			hole = child;

		}

		heap[hole] = element;
	}

	protected void bubbleDownMaxHeap(final int index) {
		final E element = heap[index];
		int hole = index;
		while (hole * 2 <= size) {
			int child = getLeftChildIndex(index);

			// if we have a right child and that child can not be bubbled up then move onto the other child

			if (child != size && compare(getRightChild(index), getLeftChild(index)) >= 0) child = getRightChildIndex(index);

			// if we found resting place of bubble then terminate search
			if (compare(heap[child], element) <= 0) break;

			heap[hole] = heap[child];
			hole = child;

		}

		heap[hole] = element;
	}

	protected void bubbleUpMinHeap(final int index) {
		int hole = index;
		E element = heap[index];

		while (hole > 1 && compare(element, getParent(hole)) < 0) {
			final int next = getParentIndex(hole);
			heap[hole] = heap[next];
			hole = next;
		}
		heap[hole] = element;
	}

	protected void bubbleUpMinHeap(final E element) {
		size++;
		heap[size] = element;
		bubbleUpMinHeap(size);
	}

	protected void bubbleUpMaxHeap(final int index) {
		int hole = index;
		E element = heap[hole];

		while (hole > 0 && compare(element, getParent(hole)) > 0) {
			final int next = getParentIndex(hole);
			heap[hole] = heap[next];
			hole = next;
		}
		heap[hole] = element;
	}

	protected void bubbleUpMaxHeap(final E element) {
		size++;
		heap[size] = element;
		bubbleUpMaxHeap(size);
	}

	private int compare(E a, E b) {
		if (comparator != null) return comparator.compare(a, b);

		return ((Comparable<E>) a).compareTo(b);
	}


	private E getLeftChild(final int parentIndex) {
		return heap[getLeftChildIndex(parentIndex)];
	}

	private E getRightChild(final int parentIndex) {
		return heap[getRightChildIndex(parentIndex)];
	}

	private int getLeftChildIndex(int parentIndex) {
		return 2 * parentIndex;
	}

	private int getRightChildIndex(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	private E getParent(int childIndex) {
		return heap[getParentIndex(childIndex)];
	}

	private int getParentIndex(int childIndex) {
		return childIndex / 2;
	}

	@SuppressWarnings("unchecked")
	protected void ensureCapacity() {
		final E[] newHeap = (E[]) new Object[heap.length * 2];
		System.arraycopy(heap, 0, newHeap, 0, heap.length);
		heap = newHeap;
	}


	private class HeapIterator implements Iterator<E> {
		private int index;
		private int lastReturnedIndex = -1;

		/**
		 * @return
		 */
		@Override
		public boolean hasNext() {
			return index <= size;
		}

		/**
		 * @return
		 */
		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			lastReturnedIndex = index;
			index++;
			return heap[lastReturnedIndex];
		}

		/**
		 *
		 */
		@Override
		public void remove() {
			if (lastReturnedIndex == -1) {
				throw new IllegalStateException();
			}
			heap[lastReturnedIndex] = heap[size];
			heap[size] = null;
			size--;
			if (size != 0 && lastReturnedIndex <= size) {
				int compareToParent = 0;
				if (lastReturnedIndex > 1) {
					compareToParent = compare(heap[lastReturnedIndex], heap[lastReturnedIndex / 2]);
				}
				if (isMinHeap) {
					if (lastReturnedIndex > 1 && compareToParent < 0) {
						bubbleUpMinHeap(lastReturnedIndex);
					} else {
						bubbleDownMinHeap(lastReturnedIndex);
					}
				} else { // max heap
					if (lastReturnedIndex > 1 && compareToParent > 0) {
						bubbleUpMaxHeap(lastReturnedIndex);
					} else {
						bubbleDownMaxHeap(lastReturnedIndex);
					}
				}
			}
			index--;
			lastReturnedIndex = -1;
		}
	}

}
