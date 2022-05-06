package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;

public class Dealer implements Player {

	@Override
	public Card hit() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Card> getBlackJackCard(List<Card> dealerCard) {
		System.out.println("딜러의 현재 카드");
		System.out.print("*[*] ");
		for (int i = 1; i < dealerCard.size(); i++) {
			System.out.print(dealerCard.get(i).toString());
		}
		System.out.println();
		return dealerCard;
	}

	public void openDealerCard(List<Card> dealerCard) {
		for (Card card : dealerCard) {
			System.out.println(card.toString());
		}
	}

}
