package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User implements Player {

	// Fields
	private String id;
	private String pwd;
	private int money;

	// Methods
	// Bet
	public int bet(int money) {
		if (money > this.money) {
			System.out.println("잔액이 부족합니다.");
			return 0;
		}
		System.out.println("Your Bet: " + money);
		return money;
	}

	// Get cards (BlackJack, OneCard)
	@Override
	public List<Card> getCard(List<Card> cards) {
		List<Card> userCards = cards;
		return userCards;
	}

	// Draw one card from stack
	@Override
	public List<Card> drawCard(List<Card> cards, Card card) {
		cards.add(card);
		return cards;
	}

	// Show cards
	@Override
	public void showCard(List<Card> cards) {
		for (Card card : cards) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
	}

}
