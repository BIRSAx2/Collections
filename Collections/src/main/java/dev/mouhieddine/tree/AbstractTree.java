package dev.mouhieddine.tree;

import dev.mouhieddine.common.Position;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) > 0;
	}

	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0;
	}

	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p == root();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int depth(Position<E> v) {
		if (isRoot(v)) return 0;
		return 1 + depth(parent(v));
	}

	@Override
	public int iterativeDepth(Position<E> v) {
		int depth = 0;

		while (!isRoot(v)) {
			Position<E> parent = parent(v);
			depth++;
		}

		return depth;
	}

	@Override
	public int height(Position<E> v) {
		int height = 0;

		for (Position<E> p : children(v)) {
			height = Math.max(height, 1 + height(p));
		}

		return height;
	}

}
