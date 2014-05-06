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
	TableRow cards;
	
	
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
        
        
        cards = (TableRow) findViewById( R.id.cards );
        
        Deck deck = new Deck();
        
        //Create an image View with a card and add to the UI
        
        Card card1 = deck.getNextCard();
        Card card2 = deck.getNextCard();
        
        ImageView iv = new ImageView( this );
        iv.setImageResource( card1.getDrawableId() );
        
        cards.addView( iv );
        
        iv = new ImageView( this );
        iv.setImageResource( card2.getDrawableId() );
        
        cards.addView( iv );
        
        //Now add card back
        iv = new ImageView( this );
        iv.setImageResource( R.drawable.b );
        
        cards.addView( iv );
        
        
    }
    
}