package com.example.palacegamestate;

public class Stack
{
	private class StackNode
	{
		private Pair data;
		private StackNode next;

		public StackNode(Pair data)
		{
			this.data = data;
			this.next = null;
		}
	}

	StackNode head;

	public Stack()
	{
		this.head = null;
	}

	public void push(Pair data)
	{
		StackNode new_head = new StackNode(data);
		new_head.next = this.head;
		this.head = new_head;
	}

	public Pair pop()
	{
		if (this.head == null)
			return null;
		Pair data = this.head.data;
		this.head = this.head.next;
		return data;
	}

	public Pair peek()
	{
		if (this.head == null)
			return null;
		return this.head.data;
	}

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
	}

	public boolean is_empty()
	{
		return (this.head == null);
	}
}
