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

/*		public Pair get_data()
		{
			return this.data;
		}

		public StackNode go_to_next()
		{
			return this.next;
		}

		public void set_next(StackNode next)
		{
			this.next = next;
		}
*/
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

/*	public boolean peek_4_equals()
	{
		if (this.head == null)
			return false;
		StackNode current = this.head;
		for (int i = 1; i <= 4 && current.next != null; i++)
		{
			if (current.data.get_card().get_rank() != current.next.data.get_card().get_rank());
		}
	}
*/
	public boolean is_empty()
	{
		return (this.head == null);
	}
}
