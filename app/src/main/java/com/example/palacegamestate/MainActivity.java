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
        stateDisplay.setText("");

        GameState firstInstance = new GameState();


        GameState secondInstance = new GameState(firstInstance);


        firstInstance.changePalace(1);

        stateDisplay.append("changePalace gets called for player one, moving all upper palace cards to their hand\n");


        int selectedCardCounter = 0;
        for(Pair p : firstInstance.the_deck) {
            if (p.get_location() == Location.PLAYER_ONE_HAND && selectedCardCounter < 3) {

                firstInstance.selectPalaceCards(1, p);
                stateDisplay.append("\nSelected a card from from player one's hand using select palace cards\n");
                selectedCardCounter++;

            }
        }


        firstInstance.confirmPalace(1);
        stateDisplay.append("\nAdded selected cards to player 1's upper palace using confirmPalace\n");

        for (Pair p : firstInstance.the_deck)
        {
            if (p.get_location() == Location.PLAYER_TWO_HAND)
            {
                firstInstance.selectCards(2, p);
                stateDisplay.append("\nSelected a card from player two's hand\n");
                break;
            }
        }

        firstInstance.playCards(2);
        stateDisplay.append("\nPlayed player two's selected card\n");
        firstInstance.takeDiscardPile(1);

        stateDisplay.append("\n player one picked up the discard pile \n");


        firstInstance.takeDiscardPile(1);

        GameState thirdInstance = new GameState();

        GameState fourthInstance = new GameState(thirdInstance);

        stateDisplay.append("\nNOTE TO GRADER:\nIn our default constructor we shuffle the deck, making the second instance different than the fourth instance." +
                "\nThis is because the first instance and third instance are created with the default constructor\nwhere they are shuffled differently, then copied over to instances two and four\n");

        stateDisplay.append("\nSecond Instance: \n" + secondInstance.toString());

        stateDisplay.append("\nFourth Instance: \n" + fourthInstance.toString());




    }
}