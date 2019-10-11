package com.example.palacegamestate;

/**
 * @author Maximilian
 * <p>
 * This class is a Stack data structure, with some extra methods specifically designed for our needs.
 * We plan to use this for the discard pile, because the discard pile is most easily represented as
 * as stack.
 */
public class Stack
{
	/**
	 * @author Maximilian
	 *
	 * This class is only used inside the Stack.java class. The stack object created by the Stack.java
	 * class is essentially a linked list where the nodes are always prepended at the head of the list.
	 * Therefore, a node class was needed.
	 */
	private class StackNode
	{
		private Pair data;
		private StackNode next;

		/**
		 * default constructor for the StackNode class
		 * @param data
		 */
		public StackNode(Pair data)
		{
			this.data = data;
			this.next = null;
		}//END: StackNode() constructor
	}//END: StackNode class

	StackNode head; // This is the head/top of the Stack. It is the first Node in the stack's linked list.

	/**
	 * default constructor for the Stack.java class
	 */
	public Stack()
	{
		this.head = null;
	}//END: Stack() constructor

	public void push(Pair data)
	{
		StackNode new_head = new StackNode(data);
		new_head.next = this.head;
		this.head = new_head;
	}//END: push() method

	public Pair pop()
	{
		if (this.head == null)
			return null;
		Pair data = this.head.data;
		this.head = this.head.next;
		return data;
	}//END: pop() method

	public Pair peek()
	{
		if (this.head == null)
			return null;
		return this.head.data;
	}//END: peek() method

	public boolean are_next_four_equal()
	{
		final int FOUR = 4;
		if (this.head == null)
			return false;
		StackNode current = this.head;
		for (int i = 1; i <= (FOUR - 1); i++)
		{
			if (current.next == null)
				return false;
			if (current.data.get_card().get_rank() != current.next.data.get_card().get_rank())
				return false;
			current = current.next;
		}
		return true;
	}//END: are_next_four_equal() method

	public boolean is_empty()
	{
		return (this.head == null);
	}//END: is_empty() method
}//END: Stack class
