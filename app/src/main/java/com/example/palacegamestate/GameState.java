/**
 * @author Andres Giesemann, Fredrik Olsson, Meredith Marcinko, Maximilian Puglielli
 */
package com.example.palacegamestate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Data representation of a game of Palace for use with the CS301 Game Framework.
 */
public class GameState
{

	public ArrayList<Pair> the_deck;
	private ArrayList<Pair> selectedCards;
	private ArrayList<Pair> discardPile;


	private int turn;


	/**
	 * Default Constructor for GameState
	 *
	 * Creates a deck of cards, shuffles it and deals it
	 */
	public GameState()
	{
		the_deck = new ArrayList<>();
		selectedCards = new ArrayList<>();
		discardPile = new ArrayList<>();
		initialize_the_deck();
		shuffleTheDeck();
		turn = 0;
		dealTheDeck();

	}//constructor

	/**
	 * Deep Copy Constructor
	 *
	 * @param state The one true state of the game, to be copied
	 */
	public GameState(GameState state)
	{
		turn = state.turn;
		the_deck = new ArrayList<>();

		for (Pair p : state.the_deck) {
			the_deck.add(new Pair(p));
		}

		selectedCards = new ArrayList<>();
		for (Pair p : state.selectedCards) {
			selectedCards.add(new Pair(p));
		}

		discardPile = new ArrayList<>();
		for (Pair p : state.discardPile) {
			discardPile.add(new Pair(p));
		}
	}//deep copy constructor

	/**
	 * Creates a deck of 52 Pair objects. All Pair objects have initial location of DRAW_PILE
	 */
	private void initialize_the_deck()
	{
		for (int i = Rank.THREE_INT; i <= Rank.TEN_INT; i++)
		{
			for (int j = Suit.SPADES_INT; j <= Suit.HEARTS_INT; j++)
			{
				the_deck.add(new Pair(new Card(Rank.int_to_rank(i), Suit.int_to_suit(j)), Location.DRAW_PILE));
			}
		}
	}//initialize_the_deck

	/**
	 * Simply shuffles the deck using the shuffle method from Collections
	 */
	//function wont be implemented until the arrayList for theDeck is made
	public void shuffleTheDeck()
	{
		Collections.shuffle(the_deck);
	}//shuffleTheDeck

	/**
	 * Adds legal, user-selected cards to the selectedCards ArrayList
	 *
	 * @param playerID ID of player who called the method
	 * @param userSelectedCard card that user attempted to select
	 * @return true if a card was successfully selected OR deselected
	 */
	public boolean selectCards(int playerID, Pair userSelectedCard)
	{
		if (isLegal(userSelectedCard))
		{
			if (selectedCards.size() == 0)
			{
				selectedCards.add(userSelectedCard);
				return true;
			}
			//also select the card if the other selected cards are of the same rank
			else if (!selectedCards.contains(userSelectedCard) && userSelectedCard.get_card().get_rank() == selectedCards.get(selectedCards.size() - 1).get_card().get_rank())
			{
				selectedCards.add(userSelectedCard);
				return true;
			}
			//deselect a card that is already selected
			else if (selectedCards.contains(userSelectedCard))
			{
				selectedCards.remove(userSelectedCard);
				return true;
			}
			return false;
		}
		return false;
	}//selectCards

	/**
	 * Selects cards to be placed into the palace of the player who called the method.
	 * Different from selectCards because any cards can be legally added to the palace.
	 *
	 * @param playerID ID of player who called the method
	 * @param userSelectedCard card that user attempted to select
	 * @return true if a card was successfully selected OR deselected
	 */
	public boolean selectPalaceCards(int playerID, Pair userSelectedCard){

		//deselects the card if it is already selected
		if (selectedCards.contains(userSelectedCard)) {
			selectedCards.remove(userSelectedCard);
			return true;
		}

		//selects a card if there are not already three selected cards
		if (selectedCards.size() < 3) {
			selectedCards.add(userSelectedCard);
			return true;
		}

		return false;

	}//selectPalaceCards

	/**
	 * Places all selected cards into the discard pile. Bombs the discard pile (bombDiscardPile()) if
	 * 4 of a kind are on top of the discard pile or a ten is played.
	 *
	 * @param playerID player who called this method,
	 * @return true if cards have been selected for play
	 */
	public boolean playCards(int playerID)
	{
		if (selectedCards.size() != 0) {
			for (int i = 0; i < selectedCards.size(); i++) {
				discardPile.add(selectedCards.get(i));
			}

			for (Pair p : the_deck) {
				if (selectedCards.contains(p)) {
					p.set_location(Location.DISCARD_PILE);
				}
			}

			selectedCards.clear();

			//bomb the discard pile if there at least 4 cards and the top four are of the same rank
			if (discardPile.size() >= 4) {
				if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 2).get_card().get_rank() && discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 3).get_card().get_rank() && discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 4).get_card().get_rank() || discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.TEN) {
					bombDiscardPile();
				}
			}
			return true;
		}
		return false;
	}//playCards

	/**
	 * Places cards from player's upper palace to their hand.
	 *
	 * @param playerID player who called this method
	 * @return true if called by a valid player
	 */
	public boolean changePalace(int playerID)
	{
		/*An array to store the selected cards in the players
		 * hand that will be changed with the palacecards*/
		if (playerID == 1)
		{
			for (Pair p : the_deck)
			{
				if (p.get_location() == Location.PLAYER_ONE_UPPER_PALACE)
				{
					p.set_location(Location.PLAYER_ONE_HAND);
				}
			}
			return true;
		}
		if (playerID == 2)
		{
			for (Pair p : the_deck)
			{
				if (p.get_location() == Location.PLAYER_TWO_UPPER_PALACE)
				{
					p.set_location(Location.PLAYER_TWO_HAND);
				}
			}
			return true;
		}
		return false;
	}//changePalace


	/**
	 * places selected cards into the upper palace of the player with playerID
	 *
	 * @param playerID player who called this method
	 * @return true if called by a valid player and there are three selected cards
	 */
	public boolean confirmPalace(int playerID)
	{
		if (playerID == 1)
		{
			if (selectedCards.size() == 3) {

				for (Pair p : the_deck) {

					if (p.get_location() == Location.PLAYER_ONE_HAND && selectedCards.contains(p)) {

						p.set_location(Location.PLAYER_ONE_UPPER_PALACE);

					}
				}
				selectedCards.clear();
				return true;
			}
		} else if (playerID == 2)
		{
			if (selectedCards.size() == 3) {

				for (Pair p : the_deck) {

					if (p.get_location() == Location.PLAYER_TWO_HAND && selectedCards.contains(p)) {

						p.set_location(Location.PLAYER_TWO_UPPER_PALACE);

					}
				}
				selectedCards.clear();
				return true;
			}
		}
		return false;
	}//confirmPalace

	/**
	 * Reassigns location of cards in discard pile to the
	 * player with the PlayerID passed as parameter.
	 *
	 * @param playerID player who called the method
	 *
	 * @return true if called by a valid player
	 */
	public boolean takeDiscardPile(int playerID)
	{

		if (!discardPile.isEmpty())
		{

			if (playerID == 1)
			{

				for (Pair p : the_deck)
				{

					if (p.get_location() == Location.DISCARD_PILE)
					{
						p.set_location(Location.PLAYER_ONE_HAND);
					}
				}
				discardPile.clear();
				return true;

			} else if (playerID == 2)
			{

				for (Pair p : the_deck)
				{

					if (p.get_location() == Location.DISCARD_PILE)
					{
						p.set_location(Location.PLAYER_TWO_HAND);
					}
				}
				discardPile.clear();
				return true;
			}
		}
		return false;
	}//takeDiscardPile


	/**
	 * Deals cards from DRAW_PILE to palaces and hands of players
	 */
	public void dealTheDeck()
	{

		for (int i = 0; i < 52; i++)
		{

			if (i < 10)
			{

				if (i % 2 == 0)
				{
					the_deck.get(i).set_location(Location.PLAYER_ONE_HAND);
				} else
				{
					the_deck.get(i).set_location(Location.PLAYER_TWO_HAND);
				}
			}
			if (10 <= i && i < 16)
			{
				if (i % 2 == 0)
				{
					the_deck.get(i).set_location(Location.PLAYER_ONE_LOWER_PALACE);
				} else
				{
					the_deck.get(i).set_location(Location.PLAYER_TWO_LOWER_PALACE);
				}
			}
			if (16 <= i && i < 22)
			{
				if (i % 2 == 0)
				{
					the_deck.get(i).set_location(Location.PLAYER_ONE_UPPER_PALACE);
				} else
				{
					the_deck.get(i).set_location(Location.PLAYER_TWO_UPPER_PALACE);
				}
			}
		}


	}//dealTheDeck

	/**
	 * Checks if playing the selected card is legal according to the rules of Palace
	 *
	 * @param selectedCard the card that is selected
	 *
	 * @return true if the move is legal, else false
	 */
	private boolean isLegal(Pair selectedCard)
	{
		if (discardPile.isEmpty())
		{
			return true;
		}
		//playing a two is always legal
		else if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.TWO)
		{
			return true;
		}
		//cards of equal or lower rank are allowed on top of sevens
		else if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.SEVEN && (selectedCard.get_card().get_rank().get_int_value() <= Rank.SEVEN_INT))
		{
			return true;
		}
		//otherwise, a card is only legal if its rank is higher than the top card of the discard pile
		else if (discardPile.get(discardPile.size() - 1).get_card().get_rank().get_int_value() <= selectedCard.get_card().get_rank().get_int_value())
		{
			return true;
		}

		return false;
	}//isLegal

	/**
	 * Removes the discardPile from play by moving it to the dead pile.
	 */
	private void bombDiscardPile()
	{
		discardPile.clear();
		for (Pair p : the_deck)
		{
			if (p.get_location() == Location.DISCARD_PILE)
			{
				p.set_location(Location.DEAD_PILE);
			}
		}
	}//bombDiscardPile


	/**
	 * Creates a String representation of the results of the Use Case methods.
	 *
	 * @return A string representation of the result of the Use Case methods.
	 */
	@Override
	public String toString()
	{
		String gameStateString = "";

		gameStateString += "Turn is: " + turn + "\n";

		gameStateString += "Deck contains:\n";

		for (Pair p : the_deck)
		{
			gameStateString += p.toString() + "\n";
		}

		gameStateString += "Discard pile contains:\n";

		for (Pair p : discardPile)
		{
			gameStateString += p.toString() + "\n";
		}

		gameStateString += "Selected cards contains:\n";

		for (Pair p : selectedCards)
		{
			gameStateString += p.toString() + "\n";
		}

		return gameStateString;
	}//toString
}//class GameState
