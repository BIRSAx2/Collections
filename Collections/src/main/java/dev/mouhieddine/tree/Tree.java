package dev.mouhieddine.tree;

import dev.mouhieddine.common.Position;

import java.util.ArrayList;
import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {
	Position<E> root();

	Position<E> parent(Position<E> p) throws IllegalArgumentException;

	Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;

	int numChildren(Position<E> p) throws IllegalArgumentException;

	boolean isInternal(Position<E> p) throws IllegalArgumentException;

	boolean isExternal(Position<E> p) throws IllegalArgumentException;

	boolean isRoot(Position<E> p) throws IllegalArgumentException;

	int size();

	boolean isEmpty();

	Iterator<E> iterator();

	Iterable<Position<E>> positions();

	int depth(Position<E> v);
	int iterativeDepth(Position<E> v);
	int height(Position<E> v);
}
