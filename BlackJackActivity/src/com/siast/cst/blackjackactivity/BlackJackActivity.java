/* 
 * Class: COSC195
 * Instructors: Rob Miller, Sharon McDonald
 * Assignment 1: BlackJack
 * Date: 5/9/2014
 */
package com.siast.cst.blackjackactivity;

import java.util.Vector;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  Purpose: To create the game "Blackjack" as an Android app.
 *
 * @author Christopher Stewart CST134
 * @version 1.0
 */

public class BlackJackActivity extends Activity
{
	
	// Initial Variables
	private Deck deck;			  // The deck used in the game
	private int pot;			  // The Player's money
	private Player player; 		  // The human player
	private Player dealer;		  // The computer player
	
	private TableRow dealerSpace; // Area where dealer cards are shown
	private TableRow playerSpace; // Area where player cards are shown
	private Button dealButton;	  // The Deal/New Game Button
	private Button hitButton;	  // The Hit Button
	private Button stayButton;	  // The Stay Button
	private EditText potField;	  // The area where the current pot is displayed
	
	public final static int BLACKJACK = 21;				// Victory condition
	private static final int DEALER_HOLD = 17;		    // The value at which the dealer holds
	private final static int BET_AMOUNT = 50;   		// The amount the player bets per deal
	private static final int POT_STARTING_AMOUNT = 500; // The amount the player starts with
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Initialize UI element references
        dealerSpace = (TableRow) findViewById( R.id.dealerCards );
        playerSpace = (TableRow) findViewById( R.id.playerCards );
        dealButton = (Button) findViewById(R.id.dealButton);
        hitButton = (Button) findViewById(R.id.hitButton);
        stayButton = (Button) findViewById(R.id.stayButton);
        potField = (EditText) findViewById(R.id.potField);
        player = new Player();
        dealer = new Player();
        
        // Initialize game elements
        resetThings("a");          
    }
    
    /**
     * Purpose: Used to reset specific attributes of the game, depending on the parameter
     * 			that is passed into the method. 
     * @param flags: Determines what to reset.
     * 					a: (All) Resets all to start of game state
     * 					d: (Deal) Resets views and scores but not the pot.
     */
    private void resetThings(String flags)
    {
    	if (flags.contains("a"))
    	{
    		// Game elements
    		deck = new Deck();
            pot = 0;
            dealerSpace.removeAllViews();
            playerSpace.removeAllViews();
        	player.removeCards();
        	dealer.removeCards();
        	
        	// UI elements
        	hitButton.setEnabled(false);
        	stayButton.setEnabled(false);
        	dealButton.setText(R.string.newGameText);
    	}
    	else if (flags.contains("d"))
    	{
            dealerSpace.removeAllViews();
            playerSpace.removeAllViews();
            for (Card c: player.removeCards())
            {
            	deck.addToDiscard(c);
            }
            for (Card c: dealer.removeCards())
            {
            	deck.addToDiscard(c);
            }
    	}
    }
    

    /**
     * Purpose: Event handler for when the Deal button is pressed.
     * 			If the pot is at 0, it treats the button as if it was a "New Game" button
     * 			If the pot is above 0, it treats the button as if it was a "Deal" button. 
     * @param view: the view that the action occurred in
     */
    public void dealButtonPressed(View view)
    {
    	// Check if New Game
    	if (pot == 0)
    	{
    		newGameSetup();
    	}
    	
    	resetThings("d");
    	dealCards();
    	pot = pot - BET_AMOUNT;
    	potField.setText("$" + pot);
    	dealButton.setEnabled(false);
    	dealButton.setText(R.string.dealButton);
    	hitButton.setEnabled(true);
    	stayButton.setEnabled(true);
    }


	/**
	 * Purpose: Deals the initial two cards to the player and dealer.
	 */
	private void dealCards() 
	{
		dealCardToPlayer(player, true, false);
		dealCardToPlayer(dealer, false, true);
		dealCardToPlayer(player, true, false);
		dealCardToPlayer(dealer, true, true);
	}

	/**
	 * Purpose: Called at the start of a new game. Resets all game attributes,
	 * 			then sets the pot to the starting amount.
	 */
	private void newGameSetup() 
	{
		resetThings("a");
		pot = POT_STARTING_AMOUNT;
		
	}
	

	/**
	 * Purpose: Used to deal a single card to a player, then display it.
	 * @param player The player object to give a card to.
	 * @param faceUp Whether the card should be displayed face up or face down.
	 * @param dealer Whether the player passed is the dealer or not (Used to determine where
	 * 				 to display the card)
	 */
	private void dealCardToPlayer(Player player, boolean faceUp, boolean dealer)
	{
		Card newCard = deck.getNextCard();
		player.addCard(newCard);
		
		ImageView iv = new ImageView( this );
		
		if (faceUp)
		{
			iv.setImageResource( newCard.getDrawableId() );
		}
		else
		{
			iv.setImageResource(R.drawable.b);
		}
        
		if (dealer)
		{
	        dealerSpace.addView(iv);
		}
		else
		{
			playerSpace.addView(iv);
		}
	}
	
	
	
	/**
	 * Purpose: the event handler for the hit button. Passes a card to the player,
	 * 			then determines if the player busted. If the player did not bust,
	 * 			it will check to see if the dealer want to take a card. If the
	 * 			dealer's score is below DEALER_HOLD, the dealer will take a card.
	 * 			If either player busts when drawing a card, it calls the end of game
	 * 			method.
	 * @param view: The view in which the action occurred.
	 */
	public void hitButtonPressed(View view)
	{
		dealCardToPlayer(player, true, false);
		if(checkBroke(player))
		{
			endOfGame("l");
		}
		else
		{
			if (dealer.getScore() < DEALER_HOLD)
			{
				dealCardToPlayer(dealer, true, true);
				if (checkBroke(dealer))
				{
					endOfGame("w");
				}
			}
		}
	}
	
	/**
	 * Purpose: Checks to see if a player broke (Their score is above BLACKJACK).
	 * @param player: The player to check
	 * @return true if the player broke, false otherwise.
	 */
	private boolean checkBroke(Player player) 
	{
		boolean result = false;
		
		if (player.getScore() > BLACKJACK)
		{
			result = true;
		}
		
		return result;
	}


	/**
	 * Purpose: Performs end of game logic, displaying toasts for win/loss/tie based on what is passed in
	 * 			as the parameter. If it detects that the pot has reached 0, it changes the text on the 
	 * 			deal button to the new game string, and toasts to the player that their game is over.
	 * @param endType A string value containing a single character flag.
	 * 				"w" = Win
	 * 				"l" = Loss
	 * 				"t" = Tie
	 */
	private void endOfGame(String endType) 
	{
		if (endType.equals("w"))
		{
			Toast.makeText(this, "You won! Your score: " + player.getScore() + ", "
						   + "Dealer Score: " + dealer.getScore() + ".", Toast.LENGTH_LONG).show();
			pot = pot + (BET_AMOUNT * 2);
		}
		else if (endType.equals("l"))
		{
			Toast.makeText(this, "You lost! Your score: " + player.getScore() + ", "
					   + "Dealer Score: " + dealer.getScore() + ".", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "You tied! Your score: " + player.getScore() + ", "
					   + "Dealer Score: " + dealer.getScore() + ".", Toast.LENGTH_LONG).show();
			pot = pot + BET_AMOUNT;
		}
		
		dealButton.setEnabled(true);
		hitButton.setEnabled(false);
		stayButton.setEnabled(false);
		
		if (pot == 0)
		{
			dealButton.setText(R.string.newGameText);
			Toast.makeText(this, "You are out of money. Press new game to start again!", Toast.LENGTH_LONG).show();
		}
		potField.setText("$" + pot);
	}


	/**
	 * Purpose: Stay button event handler. Allows the dealer to continue drawing cards until it reaches 
	 * 			DEALER_HOLD or busts. Then checks to see which player won, and fires that to the end of game
	 * 			method.
	 * @param view
	 */
	public void stayButtonPressed(View view)
	{
		while (dealer.getScore() < DEALER_HOLD)
		{
			dealCardToPlayer(dealer, true, true);
		}
		if (checkBroke(dealer))
		{
			endOfGame("w");
		}
		else if (player.getScore() > dealer.getScore())
		{
			endOfGame("w");
		}
		else if (player.getScore() < dealer.getScore())
		{
			endOfGame("l");
		}
		else
		{
			endOfGame("t");
		}
	}
}