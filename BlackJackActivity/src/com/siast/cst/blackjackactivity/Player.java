package com.siast.cst.blackjackactivity;

import java.util.Vector;


public class Player
{
	Vector<Card> cards;
	
	public Player()
	{
		cards = new Vector<Card>();
	}
	
	public void addCard( Card c )
	{
		cards.add( c );
	}
	
	
	// NOTE: Modified to include Soft Ace functionality
	public int getScore()
	{
		int score = 0;
		int aces = 0;
		for( Card c : cards )
		{
			score += c.getValue();
			if (c.getValue() == 11)
			{
				aces += 1;
			}
		}
		
		while (score > 21 && aces > 0)
		{
			score -= 10;
			aces -= 1;
		}
		
		return score;
	}
	
	public Vector<Card> removeCards()
	{
		Vector<Card> discardedCards = new Vector<Card>( cards );
		cards.clear();
		return discardedCards;
	}

}
