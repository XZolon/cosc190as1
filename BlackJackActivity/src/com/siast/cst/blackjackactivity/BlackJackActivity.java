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
	private Card playerHiddenCard; // The face-down card for the player
	private Card dealerHiddenCard; // The face-down card for the dealer
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
        
        // Initialize game elements
        resetThings("a");
        
        //Create an image View with a card and add to the UI
        
//        Card card1 = deck.getNextCard();
//        Card card2 = deck.getNextCard();
//        
//        ImageView iv = new ImageView( this );
//        iv.setImageResource( card1.getDrawableId() );
//        
//        dealerSpace.addView( iv );
//        
//        iv = new ImageView( this );
//        iv.setImageResource( card2.getDrawableId() );
//        
//        dealerSpace.addView( iv );
//        
//        //Now add card back
//        iv = new ImageView( this );
//        iv.setImageResource( R.drawable.b );
//        
//        dealerSpace.addView( iv );
          
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
            Card playerHiddenCard = null;
        	Card dealerHiddenCard = null;
        	
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
            Card playerHiddenCard = null;
        	Card dealerHiddenCard = null;
    	}
    }
    
    public void dealButtonPressed(View view)
    {
    	// Check if New Game
    	if (pot == 0)
    	{
    		newGameSetup();
    	}
    	
    	dealCards();
    	dealButton.setEnabled(false);
    	dealButton.setText(R.string.dealButton);
    	hitButton.setEnabled(true);
    	stayButton.setEnabled(true);
    }

	private void dealCards() {
		// TODO Auto-generated method stub
		
	}

	private void newGameSetup() 
	{
		// TODO Auto-generated method stub
		
	}
    
}