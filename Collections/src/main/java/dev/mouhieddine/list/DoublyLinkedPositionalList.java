package dev.mouhieddine.list;

import dev.mouhieddine.common.Position;

import java.lang.reflect.Array;

public class DoublyLinkedPositionalList<E> implements PositionalList<E> {

	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;

	public DoublyLinkedPositionalList() {
		header = new Node<>();
		trailer = new Node<>(null, header);
		header.setNext(trailer);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		return position(header.getNext());
	}

	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev());
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	public Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
		Node<E> newNode = new Node<>(e, pred, succ);
		pred.setNext(newNode);
		succ.setPrev(newNode);
		size++;
		return newNode;
	}

	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer.getPrev(), trailer);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);

		return addBetween(e, node.getPrev(), node);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);

		return addBetween(e, node, node.getNext());
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E value = node.getElement();
		node.setElement(e);
		return value;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> toRemove = validate(p);

		Node<E> predecessor = toRemove.getPrev();
		Node<E> successor = toRemove.getNext();

		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		E value = toRemove.getElement();

		//cleaning the node
		toRemove.setPrev(null);
		toRemove.setNext(null);
		toRemove.setElement(null);

		return value;
	}

	public Position<E>[] positions() {
		Position<E>[] positions = (Position<E>[]) Array.newInstance(Position.class, size);

		Node<E> node = header;
		for (int i = 0; i < size; i++) {
			node = node.getNext();
			positions[i] = node;
		}
		return positions;
	}

	private Position<E> position(Node<E> node) {
		if (node == header || node == trailer) return null;
		// don't expose the sentinels to the end user
		return node;
	}


	private Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node<E> node))
			throw new IllegalArgumentException("Invalid p");

		if (node.getNext() == null)
			throw new IllegalArgumentException("p is no longer in the list");

		return node;
	}

	private static class Node<E> implements Position<E> {
		private E element;
		private Node<E> prev;
		private Node<E> next;

		public Node() {
		}

		public Node(E element, Node<E> prev) {
			this.element = element;
			this.prev = prev;
		}

		public Node(E element, Node<E> prev, Node<E> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public E getElement() throws IllegalStateException {
			if (next == null)
				throw new IllegalStateException("Position no longer valid");

			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}
	}
}
