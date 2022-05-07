package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;

public class Dealer implements Player {

	// Methods

	// Get first dealer cards
	@Override
	public List<Card> getCard(List<Card> cards) {
		List<Card> dealerCards = cards;
		if (cards.size() == 7) {
			showOneCard(cards);
		} else if (cards.size() == 2) {
			showBlackJackCard(cards);
		}
		return dealerCards;
	}

	// Draw one card from the deck
	@Override
	public List<Card> drawCard(List<Card> cards, Card card) {
		cards.add(card);
		return cards;
	}

	// Show current cards (One Card)
	@Override
	public void showOneCard(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			System.out.print("*[*]" + " ");
		}
		System.out.println();
	}

	// Show current cards (BlackJack)
	@Override
	public void showBlackJackCard(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			if (i == cards.size() - 1) {
				System.out.print("*[*]" + " ");
			}
			System.out.print(cards.get(i) + " ");
		}
	}
}
