package co.arcade.card.game;

import java.util.List;

import co.arcade.card.carddeck.Card;

public interface GameRules {

	int bet(int money);

	List<Card> firstHand();

	List<Card> draw(List<Card> playerCard);

	int winning(int bet, boolean result);

}
