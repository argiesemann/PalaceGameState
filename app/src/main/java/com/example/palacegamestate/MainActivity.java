/**
 * @author Andres Giesemann, Fredrik Olsson, Meredith Marcinko, Maximilian Puglielli
 *
 */
package com.example.palacegamestate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    private EditText stateDisplay;//test comment

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createState = findViewById(R.id.createState);
        createState.setOnClickListener(this);

        stateDisplay = findViewById(R.id.stateDisplay);


    }

    @Override
    public void onClick(View view)
    {

        GameState firstInstance = new GameState();
        GameState secondInstance = new GameState(firstInstance);

        firstInstance.changePalace(1);

        stateDisplay.setText("changePalace gets called for player one, moving all upper palace cards to their hand\n" + firstInstance.toString());


        int selectedCardCounter = 0;
        for(Pair p : firstInstance.the_deck) {
            if (p.get_location() == Location.PLAYER_ONE_HAND && selectedCardCounter < 3) {

                firstInstance.selectPalaceCards(1, p);
                stateDisplay.append("\nSelected a card from from player one's hand using select palace cards\n");
                selectedCardCounter++;

            }
        }

        stateDisplay.append(firstInstance.toString());

        firstInstance.confirmPalace(1);
        stateDisplay.append("Added selected cards to player 1's upper palace using confirmPalace\n" + firstInstance.toString());

        for (Pair p : firstInstance.the_deck)
        {
            if (p.get_location() == Location.PLAYER_ONE_HAND)
            {
                firstInstance.selectCards(2, p);
                stateDisplay.append("\nSelected a card from player two's hand\n");
                break;
            }
        }

        firstInstance.playCards(2);
        stateDisplay.append("\nPlayed player two's selected card\n" + firstInstance.toString());
        //firstInstance.takeDiscardPile(2);
        //stateDisplay.setText(firstInstance.toString());

        stateDisplay.append("\n player one picked up the discard pile \n");


        firstInstance.takeDiscardPile(1);

        stateDisplay.append("\n" + firstInstance.toString());

    }
}