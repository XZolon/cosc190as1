package com.siast.cst.blackjackactivity;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class Deck 
{
	Vector<Card> deck;
	Vector<Card> discard;
	
	public final static char SUITS[] = { 'c', 'd', 'h', 's' };
	public final static char RANKS[] = { 'a', '2', '3', '4', '5', 
		'6', '7', '8', '9', 't', 'j', 'q', 'k' };
	
	public Deck()
	{
		//Create deck
		deck = new Vector<Card>();
		for(int i = 0; i < SUITS.length; i++ )
		{
			for( int j = 0; j < RANKS.length; j++ ) 
			{
				Card c = new Card( SUITS[i], RANKS[j] );
				deck.add( c );
			}
		}
		Collections.shuffle( deck );
		//Create discard 
		discard = new Vector<Card>();
	}
	
	public Card getNextCard()
	{
		if ( deck.size() == 0 )
		{
			if (discard.size() == 0 )
			{
				throw new IllegalStateException("Deck is empty and no cards in discard!" );
			}
			Collections.shuffle( discard );
			Iterator<Card> iter = discard.iterator();
			while ( iter.hasNext() )
			{
				deck.add( iter.next() );
				iter.remove();
			}
		}
		return deck.remove( 0 );
	}
	
	public void addToDiscard( Card c )
	{
		discard.add( c );
	}
}
