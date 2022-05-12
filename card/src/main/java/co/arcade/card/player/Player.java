package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;

public interface Player {

	// Display current cards (One Card)
	// Dealer should not open cards
	void showCard(List<Card> cards);

}
