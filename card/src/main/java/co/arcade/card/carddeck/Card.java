package co.arcade.card.carddeck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

	private String cardPattern;
	private String cardNo;

	static Card getCard(String cardPattern, int cardNo) {
		Card card = new Card();
		card.setCardPattern(cardPattern);
		if (cardNo == 1) {
			card.setCardNo("A");
		} else if (cardNo == 11) {
			card.setCardNo("J");
		} else if (cardNo == 12) {
			card.setCardNo("Q");
		} else if (cardNo == 13) {
			card.setCardNo("K");
		} else {
			card.setCardNo(Integer.toString(cardNo));
		}
		return card;
	}

	@Override
	public String toString() {

//		─ │ ┌ ┐ ┘ └

		String card = "┌────┐";
		card += "\n│    │";
		card += "\n│" + String.format("%2s%2s", cardPattern, cardNo) + "│";
		card += "\n│    │";
		card += "\n└────┘";
		return card; // cardPattern + "[" + cardNo + "]";
	}

}
