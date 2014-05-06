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
	
	public int getScore()
	{
		//TODO deal with soft ace
		int score = 0;
		for( Card c : cards )
		{
			score += c.getValue();
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
