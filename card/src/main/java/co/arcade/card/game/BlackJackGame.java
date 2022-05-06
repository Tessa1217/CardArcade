package co.arcade.card.game;

import java.util.List;

import co.arcade.card.carddeck.Card;
import co.arcade.card.player.Dealer;
import co.arcade.card.player.User;

public class BlackJackGame {

	User user = new User();
	Dealer dealer = new Dealer();
	BlackJack bj = new BlackJack();
	List<Card> playerCard = null;
	List<Card> dealerCard = null;

	public void execute() {
		firstHand();

	}

	private void firstHand() {
		playerCard = user.getBlackJackCard(bj.firstHand());
		dealerCard = dealer.getBlackJackCard(bj.firstHand());
	}

	private void playerDraw(int choice) {
		if (choice == 1) {
			playerCard = bj.draw(playerCard);
			dealerCard = bj.draw(playerCard);
		} else if (choice == 2) {
			dealer.openDealerCard(dealerCard);
		}
	}

}
