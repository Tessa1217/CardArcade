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
	private int bet(int money) {
		System.out.println("Your Bet: " + money);
		return money;
	}

	// Betting result
	// If wins, User earns twice the initial bet
	// If loses, User loses the amount of initial bet
	public int betResult(int money, boolean result) {
		int returnBet = bet(money);
		if (result) {
			returnBet *= 2;
		} else if (result == false) {
			returnBet *= -1;
		}
		System.out.println("Bet result: " + money);
		return returnBet;
	}

	// Get cards
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

	// Show cards (One Card)
	@Override
	public void showOneCard(List<Card> cards) {
		System.out.println("User Cards");
		for (Card card : cards) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
	}

	// Show cards (BlackJack)
	@Override
	public void showBlackJackCard(List<Card> cards) {
		System.out.println("User Cards");
		for (Card card : cards) {
			System.out.println(card.toString() + " ");
		}
		System.out.println();
	}

}
