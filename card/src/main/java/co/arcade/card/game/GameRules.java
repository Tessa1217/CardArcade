package co.arcade.card.game;

import java.util.List;
import java.util.Map;

import co.arcade.card.carddeck.Card;

public interface GameRules {

	static String[] cardOwner = { "user", "dealer" };

	Map<String, List<Card>> firstHand();

	Card draw();

	int winning(int result, int bet);

}
