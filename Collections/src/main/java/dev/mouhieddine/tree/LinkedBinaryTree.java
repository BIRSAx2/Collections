package dev.mouhieddine.tree;

import dev.mouhieddine.common.Position;

import java.util.Iterator;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
	protected BinaryTreeNode<E> root = null;
	private int size = 0;

	public LinkedBinaryTree() {
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		return null;
	}

	@Override
	public Position<E> root() {
		return root;
	}

	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		BinaryTreeNode<E> node = validate(p);

		return node.getParent();
	}

	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		BinaryTreeNode<E> node = validate(p);
		return node.getLeft();
	}

	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		BinaryTreeNode<E> node = validate(p);
		return node.getRight();
	}

	/**
	 * Places element e at the root of an empty tree and return its new Position
	 */
	public Position<E> addRoot(E e) throws IllegalArgumentException {
		if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}

	/**
	 * Create a new left child of Position p storing element e, returns its Position
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		BinaryTreeNode<E> parent = validate(p);

		if (parent.getLeft() != null) throw new IllegalArgumentException("p has already a left child");
		BinaryTreeNode<E> child = createNode(e, parent);
		parent.setLeft(child);
		size++;
		return child;
	}

	/**
	 * Create a new right child of Position p storing element e; returns its Position
	 **/
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		BinaryTreeNode<E> parent = validate(p);
		if (parent.getRight() != null)
			throw new IllegalArgumentException("p already has a right child");

		BinaryTreeNode<E> child = createNode(e, parent);
		parent.setRight(child);
		size++;
		return child;
	}

	/**
	 * Replaces the element at Position p with e
	 *
	 * @param p
	 * @param e
	 * @return The replaced element
	 * @throws IllegalArgumentException
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		BinaryTreeNode<E> node = validate(p);
		E value = node.getItem();
		node.setItem(e);
		return value;
	}


	public E remove(Position<E> p) throws IllegalArgumentException {
		BinaryTreeNode<E> node = validate(p);

		if (numChildren(p) == 2) throw new IllegalArgumentException("p has two children");
		BinaryTreeNode<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());

		if (child != null)
			child.setParent(node.getParent()); // child's grandparent becomes its parent

		if (node == root) root = child; // child becomes the new root
		else {
			BinaryTreeNode<E> parent = node.getParent();
			// removing the p node and replacing it with its child
			if (node == parent.getLeft()) parent.setLeft(child);
			else parent.setRight(child);
		}

		size--;

		E value = node.getItem();
		// cleaning the deleted node
		node.setItem(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(null);

		return value;
	}

	protected BinaryTreeNode<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof BinaryTreeNode<E> node))
			throw new IllegalArgumentException("Not valid position type");

		if (node.getParent() == null)
			throw new IllegalArgumentException("p is no longer int he tree");

		return node;
	}

	/**
	 * Factory function to creare a new node storing element e
	 **/
	protected BinaryTreeNode<E> createNode(E e, BinaryTreeNode<E> parent, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
		return new BinaryTreeNode<>(e, parent, left, right);
	}

	protected BinaryTreeNode<E> createNode(E e, BinaryTreeNode<E> parent) {
		return new BinaryTreeNode<>(e, parent, null, null);
	}

	protected static class BinaryTreeNode<E> implements Position<E> {
		private E item;
		private BinaryTreeNode<E> parent;
		private BinaryTreeNode<E> left;
		private BinaryTreeNode<E> right;

		public BinaryTreeNode(E item, BinaryTreeNode<E> parent, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
			this.item = item;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		@Override
		public E getElement() {
			return item;
		}

		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

		public BinaryTreeNode<E> getParent() {
			return parent;
		}

		public void setParent(BinaryTreeNode<E> parent) {
			this.parent = parent;
		}

		public BinaryTreeNode<E> getLeft() {
			return left;
		}

		public void setLeft(BinaryTreeNode<E> left) {
			this.left = left;
		}

		public BinaryTreeNode<E> getRight() {
			return right;
		}

		public void setRight(BinaryTreeNode<E> right) {
			this.right = right;
		}
	}
}
