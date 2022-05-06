package co.arcade.card.player;

import java.util.List;

import co.arcade.card.carddeck.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User implements Player {

	private String id;
	private String pwd;
	private int money;

	@Override
	public Card hit() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Card> getBlackJackCard(List<Card> playerCard) {
		System.out.println("플레이어의 현재 카드");
		for (Card card : playerCard) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
		return playerCard;
	}

}
