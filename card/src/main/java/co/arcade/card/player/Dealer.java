package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dealer implements Player {

	// Fields
	private List<Card> dealerCards;

	// Methods

	// Get first dealer cards
	@Override
	public List<Card> getCard(List<Card> cards) {
		List<Card> dealerCards = cards;
		return dealerCards;
	}

	// Draw one card from the deck
	@Override
	public List<Card> drawCard(List<Card> cards, Card card) {
		cards.add(card);
		return cards;
	}

	// Show Cards
	@Override
	public void showCard(List<Card> cards) {
		System.out.println("Dealer Card");
		for (int i = 0; i < cards.size(); i++) {
			if (i == cards.size() - 1) {
				System.out.print("*[*] ");
				break;
			}
			System.out.print(cards.get(i).toString() + " ");
		}
		System.out.println();
	}

}
