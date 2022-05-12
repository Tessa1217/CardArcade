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
	private String email;
	private String contact;

	// Methods
	// Bet
	public int bet(int money) {
		if (money > this.money) {
			System.out.println("잔액이 부족합니다.");
			return 0;
		}
		System.out.println("플레이어의 베팅 금액: " + money);
		return money;
	}

	// Show cards
	@Override
	public void showCard(List<Card> cards) {
		System.out.println("유저 카드: ");
		for (int i = 0; i < cards.size(); i++) {
			if (i % 7 == 0) {
				System.out.println();
			}
			System.out.print((i + 1) + ":" + cards.get(i).toString() + " ");
		}
		System.out.println();
	}

}
