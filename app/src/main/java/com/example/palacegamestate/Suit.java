package com.example.palacegamestate;

import android.util.Log;

public enum Suit
{
	SPADES(1),
	CLUBS(2),
	DIAMONDS(3),
	HEARTS(4);

	public static final int SPADES_INT =   1;
	public static final int CLUBS_INT =    2;
	public static final int DIAMONDS_INT = 3;
	public static final int HEARTS_INT =   4;

	private int suit_num;
	Suit(int suit_num) { this.suit_num = suit_num; }



	public int get_suit_num() { return this.suit_num; }

	public static Suit int_to_suit(int num)
	{
		switch(num)
		{
			case SPADES_INT:   return SPADES;
			case CLUBS_INT:    return CLUBS;
			case DIAMONDS_INT: return DIAMONDS;
			case HEARTS_INT:   return HEARTS;

			default:
				Log.d("Suit.java:34", "ERROR: Incorrect integer passed to the 'int_to_suit()' function");
				return null;
		}
	}
}
