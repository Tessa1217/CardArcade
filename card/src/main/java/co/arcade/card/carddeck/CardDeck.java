package co.arcade.card.carddeck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CardDeck {

	private static List<Card> cardDeck() {

		List<Card> cardDeck = new ArrayList<Card>();
		Card card = new Card();
		for (CardPattern cardPattern : CardPattern.values()) {
			for (int i = 1; i <= 13; i++) {
				card = Card.getCard(cardPattern.pattern(), i);
				cardDeck.add(card);
			}
		}
		return cardDeck;
	}

	public static Stack<Card> shuffleDeck() {
		List<Card> cardDeck = cardDeck();
		Collections.shuffle(cardDeck);
		Stack<Card> cardStack = new Stack<Card>();
		cardStack.addAll(cardDeck);
		return cardStack;
	}

	public static Stack<Card> shuffleDeck(Stack<Card> cardDeck) {
		Collections.shuffle(cardDeck);
		Stack<Card> cardStack = new Stack<Card>();
		cardStack.addAll(cardDeck);
		return cardStack;

	}

}
