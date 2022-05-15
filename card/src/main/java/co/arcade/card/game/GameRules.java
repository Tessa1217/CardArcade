package co.arcade.card.game;

import java.util.List;
import java.util.Map;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardShuffle;
import co.arcade.card.gameview.Title;

public interface GameRules {

	static Runnable r = new CardShuffle();
	static Runnable r2 = new Title();
	static Thread t = new Thread(r);
	static Thread t2 = new Thread(r2);

	static String[] cardOwner = { "user", "dealer" };

	Map<String, List<Card>> firstHand();

	Card draw();

	int winning(int result, int bet);

}
