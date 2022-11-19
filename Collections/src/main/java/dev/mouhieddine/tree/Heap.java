package dev.mouhieddine.tree;

import java.util.Collection;
import java.util.Iterator;

public interface Heap<E> {
	void insert(E element);

	E peek();

	E pop();

	int size();

	boolean isEmpty();

	Iterator<E> iterator();

	boolean add(E e);

	boolean remove(Object o);

	@SuppressWarnings("unchecked")
	void clear();
}
