package com.example.palacegamestate;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Card
{
	private Suit suit;
	private Rank rank;
//	private BitmapDrawable image;

	public Card(Rank rank, Suit suit) /*BitmapDrawable image)*/
	{
		this.suit = suit;
		this.rank = rank;
//		this.image = image;
	}

	public void draw(Canvas c)
	{
		// TODO: Draw the Card's BitmapDrawable
	}

	public Rank get_rank() {
		return this.rank;
	}

	public String toString()
	{
		String return_str = "";
		switch(rank)
		{
			case THREE:
				return_str += "Three";
				break;
			case FOUR:
				return_str += "Four";
				break;
			case FIVE:
				return_str += "Five";
				break;
			case SIX:
				return_str += "Six";
				break;
			case SEVEN:
				return_str += "Seven";
				break;
			case EIGHT:
				return_str += "Eight";
				break;
			case NINE:
				return_str += "Nine";
				break;
			case JACK:
				return_str += "Jack";
				break;
			case QUEEN:
				return_str += "Queen";
				break;
			case KING:
				return_str += "King";
				break;
			case ACE:
				return_str += "Ace";
				break;
			case TWO:
				return_str += "Two";
				break;
			case TEN:
				return_str += "Ten";
				break;
			default:
				Log.d("Card.java:61", "ERROR: (rank) variable corrupted");
				return null;
		}
		return_str += " of ";
		switch(suit)
		{
			case SPADES:
				return_str += "Spades";
				break;
			case CLUBS:
				return_str += "Clubs";
				break;
			case DIAMONDS:
				return_str += "Diamonds";
				break;
			case HEARTS:
				return_str += "Hearts";
				break;
			default:
				Log.d("Card.java:74", "ERROR: (suit) variable corrupted");
				return null;
		}
		return(return_str);
	}
}
