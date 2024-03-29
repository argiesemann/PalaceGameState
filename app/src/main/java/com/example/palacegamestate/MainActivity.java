/**
 * @author Andres Giesemann, Fredrik Olsson, Meredith Marcinko, Maximilian Puglielli
 */
package com.example.palacegamestate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the main frame of our program.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    private EditText stateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connects a Button object to the Button in the XML-file
        Button createState = findViewById(R.id.createState);
        createState.setOnClickListener(this);

        //Connects an EditText-object to the EditText-object in the XML-file
        stateDisplay = findViewById(R.id.stateDisplay);


    }//onCreate


    /**
     * This method listens to a ClickEvent and then
     * calls all the Use case methods in the PalaceGameState class
     *
     * @param view
     */
    @Override
    public void onClick(View view)
    {
        stateDisplay.setText("");

        PalaceGameState firstInstance = new PalaceGameState();

        //Makes a deep copy of the firstinstance using the copy constructor.
        PalaceGameState secondInstance = new PalaceGameState(firstInstance);


        firstInstance.changePalace(1);

        stateDisplay.append("changePalace gets called for player one, moving all upper palace cards to their hand\n");


        //selects three cards from the palace.
        int selectedCardCounter = 0;
        for(Pair p : firstInstance.the_deck) {
            if (p.get_location() == Location.PLAYER_ONE_HAND && selectedCardCounter < 3) {

                firstInstance.selectPalaceCards(1, p);
                stateDisplay.append("\nSelected a card from from player one's hand using select palace cards\n");
                selectedCardCounter++;

            }
        }

        //Confirms the three cards that were selected.
        firstInstance.confirmPalace(1);
        stateDisplay.append("\nAdded selected cards to player 1's upper palace using confirmPalace\n");


        //Player 2 selects a card from the player's hand.
        for (Pair p : firstInstance.the_deck)
        {
            if (p.get_location() == Location.PLAYER_TWO_HAND)
            {
                firstInstance.selectCards(2, p);
                stateDisplay.append("\nSelected a card from player two's hand\n");
                break;
            }
        }


        //player two plays the card that was selected.
        firstInstance.playCards(2);
        stateDisplay.append("\nPlayed player two's selected card\n");

        //Player 1 picks up the discard pile
        firstInstance.takeDiscardPile(1);

        stateDisplay.append("\n player one picked up the discard pile \n");


        PalaceGameState thirdInstance = new PalaceGameState();

        //Creates a deep copy of the thirdInstance
        PalaceGameState fourthInstance = new PalaceGameState(thirdInstance);

        stateDisplay.append("\nNOTE TO GRADER:\nIn our default constructor we shuffle the deck, making the second instance different than the fourth instance." + "\nThis is because the first instance and third instance are created with the default constructor\nwhere they are shuffled differently, then copied over to instances two and four\n");

        stateDisplay.append("\nSecond Instance: \n" + secondInstance.toString());

        stateDisplay.append("\nFourth Instance: \n" + fourthInstance.toString());

    }//onClick
}//class MainActivity