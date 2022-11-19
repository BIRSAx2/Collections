package dev.mouhieddine.queue;

import dev.mouhieddine.common.Entry;
import dev.mouhieddine.common.Position;
import dev.mouhieddine.list.DoublyLinkedPositionalList;

import java.util.Comparator;

public class SortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

	private DoublyLinkedPositionalList<Entry<K, V>> list = new DoublyLinkedPositionalList<>();

	public SortedPriorityQueue() {
		super();
	}

	public SortedPriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);

		Entry<K, V> node = new PriorityQueueEntry<>(key, value);
		Position<Entry<K, V>> position = list.last();
		while (position != null && compare(node, position.getElement()) < 0) {
			position = list.before(position);
		}
		if (position == null) list.addFirst(node);
		else list.addAfter(position, node);

		return node;
	}

	@Override
	public Entry<K, V> min() {
		if (list.isEmpty()) return null;
		return list.first().getElement();
	}

	@Override
	public Entry<K, V> removeMin() {
		if (list.isEmpty()) return null;
		return list.remove(list.first());
	}

	public void TraversePriorityQueue() {
		for (Position<Entry<K, V>> position : list.positions()) {
			System.out.print(position.getElement().getValue() + " ");
		}
	}

	public void TraversePositionalList() {
		TraversePriorityQueue();
	}
}

