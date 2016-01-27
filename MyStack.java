//Siyuan ZHou

public class MyStack<E> {

	private Node<E> node;
	private int size;

	/**
	 * initialize mystack
	 */
	public MyStack() {
		this.node = null;
		this.size = 0;
	}

	/**
	 * judge mystack is empty or not
	 * 
	 * @return true:is empty ; false:not empty
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * get the size of mystack
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * push one item to mystack
	 * 
	 * @param item
	 */
	public void push(E item) {
		if (isEmpty()) {
			this.node = new Node<E>(item);
			this.node.setNext(null);
		} else {
			Node<E> temp = new Node<E>(item);
			temp.setNext(this.node);
			this.node = temp;
		}
		this.size++;
	}

	/**
	 * remove the top item of mystack and return the item.
	 * 
	 * @return the top item of mystack
	 */
	public E pop() {
		if (isEmpty()) {
			return null;
		}
		this.size--;
		Node<E> temp = this.node;
		this.node = this.node.next;
		return temp.getItem();
	}

	/**
	 * get the top item of mystack
	 * 
	 * @return the top item of mystack
	 */
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return this.node.getItem();
	}

	public String toString() {
		String result = "";
		Node<E> temp = this.node;
		while (temp != null) {
			result += temp.getItem().toString();
			temp = temp.next;
		}
		return result;
	}

	private class Node<E> {
		E item;
		Node<E> next;

		public Node(E item) {
			this.item = item;
		}

		public E getItem() {
			return item;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

	}

}
