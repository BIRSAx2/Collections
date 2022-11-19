package dev.mouhieddine.queue;

import dev.mouhieddine.common.Entry;

public interface PriorityQueue<K, V> {

	int size();

	Entry<K, V> insert(K key, V value) throws IllegalArgumentException;

	Entry<K, V> min();

	Entry<K, V> removeMin();
}
