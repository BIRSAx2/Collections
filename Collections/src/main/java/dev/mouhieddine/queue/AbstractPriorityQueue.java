package dev.mouhieddine.queue;

import dev.mouhieddine.common.DefaultComparator;
import dev.mouhieddine.common.Entry;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {
	private Comparator<K> comparator;

	protected AbstractPriorityQueue(Comparator<K> comparator) {
		this.comparator = comparator;
	}

	protected AbstractPriorityQueue() {
		this(new DefaultComparator<K>());
	}

	protected int compare(Entry<K, V> a, Entry<K, V> b) {
		return comparator.compare(a.getKey(), b.getKey());
	}

	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			// checks if the key can be compared with itself
			return (comparator.compare(key, key)) == 0;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	protected static class PriorityQueueEntry<K, V> implements Entry<K, V> {
		private K k;
		private V v;


		public PriorityQueueEntry(K key, V value) {
			this.k = key;
			this.v = value;
		}

		public K getKey() {
			return k;
		}

		public void setKey(K k) {
			this.k = k;
		}

		public V getValue() {
			return v;
		}

		public void setValue(V v) {
			this.v = v;
		}
	}
}
