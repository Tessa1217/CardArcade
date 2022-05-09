package co.arcade.card.game;

import java.util.List;

import co.arcade.card.carddeck.Card;

public interface GameRules {

	List<Card> firstHand();

	Card draw();

	int winning(int result, int bet);

}
