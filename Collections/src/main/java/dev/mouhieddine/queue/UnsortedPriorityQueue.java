package dev.mouhieddine.queue;

import dev.mouhieddine.common.Entry;
import dev.mouhieddine.common.Position;
import dev.mouhieddine.list.DoublyLinkedPositionalList;

import java.util.Comparator;

public class UnsortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
	private DoublyLinkedPositionalList<Entry<K, V>> list = new DoublyLinkedPositionalList<>();

	public UnsortedPriorityQueue() {
		super();
	}

	public UnsortedPriorityQueue(Comparator<K> comparator) {
		super(comparator);
	}

	// only called when non empty
	private Position<Entry<K, V>> findMin() {

		Position<Entry<K, V>> small = list.first();
		for (Position<Entry<K, V>> walk : list.positions()) {
			if (compare(walk.getElement(), small.getElement()) < 0)
				small = walk;
		}

		return small;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);

		Entry<K, V> entry = new PriorityQueueEntry<>(key, value);
		list.addLast(entry);
		return entry;
	}

	@Override
	public Entry<K, V> min() {
		if (list.isEmpty()) return null;
		return findMin().getElement();
	}

	@Override
	public Entry<K, V> removeMin() {
		if(list.isEmpty()) return null;

		return list.remove(findMin());
	}

	@Override
	public int size() {
		return list.size();
	}

	public void TraversePositionalList(){
		for(Position<Entry<K,V>> item: list.positions())
			System.out.print(item.getElement().getValue() + " ");
	}

	public void TraversePriorityQueue(){
		while(list.size() > 0){
			System.out.print(removeMin().getValue() + " ");
		}
	}

}
