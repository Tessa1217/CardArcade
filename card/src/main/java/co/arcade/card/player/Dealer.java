package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dealer implements Player {

	// Methods

	// Show Cards
	@Override
	public void showCard(List<Card> cards) {
		System.out.println("딜러 카드 오픈: ");
		for (Card card : cards) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
	}

	public void showBJCard(List<Card> cards) {
		System.out.println("딜러 카드: ");
		for (int i = 0; i < cards.size(); i++) {
			if (i == cards.size() - 1) {
				System.out.print("*[*] ");
				break;
			}
			System.out.print(cards.get(i).toString() + " ");
		}
		System.out.println();
	}

	public void showOCard(List<Card> cards) {
		System.out.print("딜러 카드: ");
		for (int i = 0; i < cards.size(); i++) {
			if (i % 7 == 0) {
				System.out.println();
			}
			System.out.print((i + 1) + ":*[*] ");
		}
		System.out.println();
	}

}
