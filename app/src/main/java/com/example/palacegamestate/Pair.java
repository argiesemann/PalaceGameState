package com.example.palacegamestate;

import android.util.Log;

/**
 * @author Maximilian
 * <p>
 * This class combines both a card object and a location enumeration to create a pair object. These
 * pair objects simply represent the both a card and where it is in the game.
 */
public class Pair
{
	private Card card;
	private Location location;

	public Pair(Card card, Location location)
	{
		this.card = card;
		this.location = location;
	}//END: Pair() constructor

	public Pair(Pair that)
	{
		this.card = new Card(that.card);
		this.location = that.location;
	}//END: Pair() copy constructor

	public Card get_card()
	{
		return card;
	}//END: get_card() method

	public Location get_location()
	{
		return location;
	}//END: get_location() method

	public void set_location(Location location)
	{
		this.location = location;
	}//END: set_location() method

	@Override
	public String toString() {

		String return_str = "";

		return_str = card.toString() + " in ";

		switch(location)
		{
			case PLAYER_ONE_HAND:
				return_str += "Player one's hand";
				break;
			case PLAYER_TWO_HAND:
				return_str += "Player two's hand";
				break;
			case PLAYER_ONE_UPPER_PALACE:
				return_str += "Player one's upper palace";
				break;
			case PLAYER_TWO_UPPER_PALACE:
				return_str += "Player two's upper palace";
				break;
			case PLAYER_ONE_LOWER_PALACE:
				return_str += "Player one's lower palace";
				break;
			case PLAYER_TWO_LOWER_PALACE:
				return_str += "Player two's lower palace";
				break;
			case DEAD_PILE:
				return_str += "Dead pile";
				break;
			case DISCARD_PILE:
				return_str += "Discard pile";
				break;
			case DRAW_PILE:
				return_str += "Draw pile";
				break;
			default:
				Log.d("Pair.java", "ERROR: invalid location");
				return null;
		}

		return return_str;
	}//END: toString() method
}//END: Pair class
