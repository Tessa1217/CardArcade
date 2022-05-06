package co.arcade.card.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class BlackJack implements GameRules {

	// 한 회차동안 사용할 카드 덱
	public static Stack<Card> cardStack = CardDeck.shuffleDeck();

	// 베팅 금액 받기
	@Override
	public int bet(int money) {
		System.out.print("베팅 금액 : ");
		return money;

	}

	// 첫번째 카드 배분
	@Override
	public List<Card> firstHand() {

		List<Card> playerCard = new ArrayList<Card>();

		for (int i = 0; i < 2; i++) {
			Card card = cardStack.pop();
			playerCard.add(card);
		}
		int sum = sumCard(playerCard);
		System.out.print("현재 점수: ");
		System.out.println(sum);
		System.out.println(result(sum));

		return playerCard;
	}

	// 카드 추가
	@Override
	public List<Card> draw(List<Card> playerCard) {
		if (cardStack.isEmpty() == false) {
			playerCard.add(cardStack.pop());
		}
		int sum = sumCard(playerCard);
		System.out.print("현재 점수: ");
		System.out.println(sum);
		System.out.println(result(sum));
		return playerCard;

	}

	// 카드의 합
	public int sumCard(List<Card> playerCard) {
		int score = 0;
		boolean containA = false;
		for (int i = 0; i < playerCard.size(); i++) {
			if (playerCard.get(i).getCardNo() == "J" || playerCard.get(i).getCardNo() == "A") {
				score += 11;
				if (playerCard.get(i).getCardNo() == "A") {
					containA = true;
				}
			} else if (playerCard.get(i).getCardNo() == "Q") {
				score += 12;
			} else if (playerCard.get(i).getCardNo() == "K") {
				score += 13;
			} else {
				score += Integer.parseInt(playerCard.get(i).getCardNo());
			}
		}
		if (score > 21 && containA) {
			score -= 10;
		}
		return score;
	}

	public String result(int sum) {
		String result = "";
		if (sum > 21) {
			result += "Bust";
		}
		return result;
	}

	// 최종 금액 계산
	@Override
	public int winning(int bet, boolean result) {
		if (result) {
			bet *= 2;
		} else if (result) {
			bet *= -1;
		}
		return bet;
	}

}
