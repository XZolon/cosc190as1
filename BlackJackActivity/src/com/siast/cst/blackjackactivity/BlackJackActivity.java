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

public class BlackJackActivity extends Activity
{
	// Initial Variables
	private TableRow dealerSpace; // Area where dealer cards are shown
	private TableRow playerSpace; // Area where player cards are shown
	private Deck deck;			  // The deck used in the game
	private int pot;			  // The Player's money
	private int dealerScore;      // The Dealer's Score
	private int playerScore;	  // The Player's Score
	private Player player; 		  // The human player
	private Player dealer;		  // The computer player
	private Button dealButton;
	private Button hitButton;
	private Button stayButton;
	private EditText potField;
	
	public final static int BLACKJACK = 21;
	private static final int DEALER_HOLD = 17;
	private final static int BET_AMOUNT = 50;
	private static final int POT_STARTING_AMOUNT = 500;
	
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
     * @param flags: Determines what to reset.
     * 					a: (All) Resets all to start of game level
     * 					d: (Deal) Resets views and scores but not the pot.
     */
    private void resetThings(String flags)
    {
    	if (flags.contains("a"))
    	{
    		// Game elements
    		deck = new Deck();
            pot = 0;
            dealerScore = 0;
            playerScore = 0;
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
            dealerScore = 0;
            playerScore = 0;
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

	private void dealCards() 
	{
		dealCardToPlayer(player, true, false);
		dealCardToPlayer(dealer, false, true);
		dealCardToPlayer(player, true, false);
		dealCardToPlayer(dealer, true, true);
	}

	private void newGameSetup() 
	{
		resetThings("a");
		pot = POT_STARTING_AMOUNT;
		
	}
	
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
	
	
	
	public void hitButtonPressed(View view)
	{
		dealCardToPlayer(player, true, false);
		if(checkBroke(player))
		{
			endOfGame("l");
		}
		else
		{
			if (dealer.getScore() < 17)
			{
				dealCardToPlayer(dealer, true, true);
				if (checkBroke(dealer))
				{
					endOfGame("w");
				}
			}
		}
	}
	
	private boolean checkBroke(Player player) 
	{
		boolean result = false;
		
		if (player.getScore() > BLACKJACK)
		{
			result = true;
		}
		
		return result;
	}

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

	public void stayButtonPressed(View view)
	{
		while (dealer.getScore() < 17)
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