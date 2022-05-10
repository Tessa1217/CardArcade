package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;

public interface Player {

	// Receive first cards (BlackJack = 2, OneCard = 7)
	List<Card> getCard(List<Card> cards);

	// Draw one card from deck
	List<Card> drawCard(List<Card> cards, Card card);

	// Display current cards (One Card)
	// Dealer should not open cards
	void showCard(List<Card> cards);

}
