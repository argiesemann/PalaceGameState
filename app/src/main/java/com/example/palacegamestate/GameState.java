package com.example.palacegamestate;

import java.util.ArrayList;
import java.util.Collections;


/*Within the same package as the MainActivity class, create a new game state
class for your game. (Eventually this class will be a subclass of the GameState class in
the game framework but it doesn't have to be for this assignment.) Your game state class'
instance variables should encompass all the information you will need about the current
state of the game in order to display it properly for a human user or allow a computer
player to make decisions. Things to consider:
◦ Information about the resources each player has (e.g., cards, pawns, money)
◦ Information about the state of any resources (e.g., card is visible, pawn is yellow)
◦ Whose turn is it?
◦ Detailed information about shared resources (e.g., the game board, contents of a draw
pile)
◦ The visibility of certain information from the perspective of each player
◦ Current score of each player
◦ Current state of a timer
◦ Current state of the dice
◦ What stage of the game you are at (e.g., setup phase, placement phase, buy phase,
main play stage, etc.).
◦ You may need to create additional classes to describe specific elements of the game
state (e.g., a playing card, a pawn, a tile, etc.)*/
public class GameState
{

	public ArrayList<Pair> the_deck;
	private ArrayList<Pair> selectedCards;
	private ArrayList<Pair> discardPile;


	private int turn;


	/**
	 * Constructor for the objects in the GameState
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

	}

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
			the_deck.add(p);
		}

		selectedCards = new ArrayList<>();
		for (Pair p : state.selectedCards) {
			selectedCards.add(p);
		}

		discardPile = new ArrayList<>();
		for (Pair p : state.discardPile) {
			discardPile.add(p);
		}
	}

	/**
	 *
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
	}

	/**
	 * shuffle Deck
	 * <p>
	 * Method to shuffle the deck
	 */
	//function wont be implemented until the arrayList for theDeck is made
	public void shuffleTheDeck()
	{
		Collections.shuffle(the_deck);
	}

    /* What do the methods do?
       ◦ verify the move is a legal move for the current game state. If it’s not, return false.
       ◦ modify the game state to reflect that a given player has taken that action. Then, return true.
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
	}

	public boolean selectPalaceCards(int playerID, Pair userSelectedCard){

		if (selectedCards.contains(userSelectedCard)) {
			selectedCards.remove(userSelectedCard);
			return true;
		}

		if (selectedCards.size() < 3) {
			selectedCards.add(userSelectedCard);
			return true;
		}

		return false;


	}

	public boolean playCards(int playerID)
	{
		for (int i = 0; i < selectedCards.size(); i++)
		{
			discardPile.add(selectedCards.get(i));
		}

		for (Pair p : the_deck)
		{
			if (selectedCards.contains(p))
			{
				p.set_location(Location.DISCARD_PILE);
			}
		}

		selectedCards.clear();

		//bomb the discard pile if there at least 4 cards and the top four are of the same rank
		if (discardPile.size() >= 4)
		{
			if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 2).get_card().get_rank()
					&& discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 3).get_card().get_rank()
					&& discardPile.get(discardPile.size() - 1).get_card().get_rank() == discardPile.get(discardPile.size() - 4).get_card().get_rank()
					|| discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.TEN)
			{
				bombDiscardPile();
			}
		}
		return false;
	}

	/**
	 * changePalace
	 * method to let the user change their palace at the beginning of the
	 * game
	 *
	 * @param playerID
	 * @return
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
					return true;
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
	}


	/**
	 * confirmPalace
	 * <p>
	 * method that lets the user confirm their selected palace
	 *
	 * @param playerID
	 * @return
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
	}

	/**
	 * takeDiscardPile
	 * <p>
	 * reassigns location of cards in DISCARD_PILE to the hand of
	 * the user passed in the parameter. Also clears the discardPile
	 * ArrayList in GameState
	 *
	 * @return true if discard pile isn't empty and the method was called by a valid player
	 */
	//TODO: Mere, change the location in the Pair class of the cards from the discard ArrayList to the user's hand
	//TODO: use get location and set location
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
	 * dealTheDeck method that draws cards in for the hands
	 *
	 * @param
	 */
	//this method is done
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

	private boolean isLegal(Pair selectedCard)
	{
		if (discardPile.isEmpty())
		{
			return true;
		} else if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.TWO)
		{
			return true;
		} else if (discardPile.get(discardPile.size() - 1).get_card().get_rank() == Rank.SEVEN && (selectedCard.get_card().get_rank().get_int_value() <= Rank.SEVEN_INT))
		{
			return true;
		} else if (discardPile.get(discardPile.size() - 1).get_card().get_rank().get_int_value() <= selectedCard.get_card().get_rank().get_int_value())
		{
			return true;
		}

		return false;
	}

	/**
	 * Empties the discardPile ArrayList, all cards in pair list with location DiscardPile get changed to Trash pile
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
	}

	public String toString()
	{
		//TODO implement toString method which converts all of GameState's data to a String
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
	}
}
