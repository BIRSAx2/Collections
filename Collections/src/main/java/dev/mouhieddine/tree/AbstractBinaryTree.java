package dev.mouhieddine.tree;

import dev.mouhieddine.common.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

	/**
	 * Returns the Positions of p's sibling or null if no sibling exists
	 **/
	@Override
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Position<E> parent = parent(p);
		if (parent == null) return null;

		return p == left(parent) ? right(parent) : left(parent);
	}

	/**
	 * Returns the number of childre of Position p
	 **/
	@Override
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		if (left(p) != null && right(p) != null) return 2;
		if (left(p) != null || right(p) != null) return 1;
		return 0;
	}

	/**
	 * Returns an iterable collection of Positions representing p's children
	 **/

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		List<Position<E>> children = new ArrayList<>(2);
		if (left(p) != null) children.add(left(p));
		if (right(p) != null) children.add(right(p));
		return children;
	}
}
