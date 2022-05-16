package co.arcade.card.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class OneCard implements GameRules {

	private Stack<Card> cardStack; // 게임 카드 덱
	private Stack<Card> discardStack;
	private Card topCard;
	private Scanner scn = new Scanner(System.in);

	// 첫 카드 배분 (원 카드 7장)
	@Override
	public Map<String, List<Card>> firstHand() {
		GameRules.t.run();
		cardStack = CardDeck.shuffleDeck();
		discardStack = new Stack<Card>();
		topCard = firstOpenCard();
		Map<String, List<Card>> cardMap = new HashMap<String, List<Card>>();
		List<Card> cards;
		for (int i = 0; i < 2; i++) {
			cards = new ArrayList<Card>();
			for (int j = 0; j < 7; j++) {
				cards.add(cardStack.pop());
			}
			cardMap.put(GameRules.cardOwner[i], cards);
		}
		return cardMap;
	}

	// 첫 탑 카드 오픈
	private Card firstOpenCard() {
		if (discardStack.size() == 0) {
			discardStack.push(cardStack.pop());
		}
		Card card = discardStack.peek();
		return card;
	}

	// 탑 카드 출력
	public void openTopCard() {
		System.out.println("\t\t\t\t\t┌────┐");
		System.out.println("\t\t\t\t\t│    │");
		System.out
				.println("\t\t\t\t\t│" + String.format("%2s%2s", topCard.getCardPattern(), topCard.getCardNo()) + "│");
		System.out.println("\t\t\t\t\t│    │");
		System.out.println("\t\t\t\t\t└────┘");
	}

	// 덱 비어있는지 확인
	public boolean cardStackEmpty() {
		if (cardStack.isEmpty()) {
			return true;
		}
		return false;
	}

	// 게임 카드 덱이 빌 경우 내려놓은 카드 덱을 재셔플
	public Stack<Card> reshuffle(boolean empty) {
		if (empty) {
			System.out.println("\t\t\t카드 덱이 비었습니다. ");
			GameRules.t.run();
			cardStack = CardDeck.shuffleDeck(discardStack);
			discardStack = new Stack<Card>();
			topCard = firstOpenCard();
			for (Card card : discardStack) {
				System.out.println(card.toString());
			}
		}
		return cardStack;
	}

	// 카드 추가하기
	@Override
	public Card draw() {

		Card card = null;
		if (cardStack.isEmpty() == false) {
			card = cardStack.pop();
		} else if (cardStack.isEmpty()) {
			cardStack = reshuffle(cardStack.isEmpty());
			card = cardStack.pop();
		}
		return card;
	}

	// 카드 내려놓기
	public Card discard(Card discardCard) {
		String discardP = discardCard.getCardPattern();
		String discardNo = discardCard.getCardNo();
		String currentP = topCard.getCardPattern();
		String currentNo = topCard.getCardNo();

		if (currentP.equals(discardP) || currentNo.equals(discardNo)) {
			discardStack.push(discardCard);
			topCard = discardStack.peek();
			return discardCard;
		}

		return null;
	}

	// 공격 카드
	public List<Card> attack(int num) {
		List<Card> attackCard = new ArrayList<Card>();
		if (cardStack.size() < OneCardGame.attack.size()) {
			for (int i = 0; i < cardStack.size(); i++) {
				discardStack.push(cardStack.pop());
			}
			cardStack = reshuffle(cardStack.isEmpty());
		}
		if (num == 2) {
			for (int i = 0; i < 3; i++) {
				attackCard.add(cardStack.pop());
			}
		} else if (num == 1) {
			for (int i = 0; i < 2; i++) {
				attackCard.add(cardStack.pop());
			}
		}
		return attackCard;
	}

	// 게임 후 베팅 금액 계산
	@Override
	public int winning(int cnt, int bet) {
		if (cnt == 0) {
			bet *= 2;
			System.out.println("유저 승리. 최종 금액은 " + bet + "입니다.");
		} else if (cnt > 0) {
			bet *= -2;
			System.out.println("딜러 승리. 최종 금액은 " + bet + "입니다.");
		}
		return bet;
	}

	// 딜러 오토 플레이
	public Card autoPlaying(List<Card> dealerCard) {
		for (int i = 0; i < dealerCard.size(); i++) {
			Card card = dealerCard.get(i);
			if (card.getCardPattern().equals(topCard.getCardPattern())
					|| card.getCardNo().equals(topCard.getCardNo())) {
				for (Card card1 : discardStack) {
					System.out.println(card1.toString());
				}
				card = dealerCard.remove(i);
				System.out.println("딜러가 " + card.toString() + "를 냈습니다.");
				discardStack.push(card);
//				for (Card card1 : discardStack) {
//					System.out.println(card1.toString());
//				}
				topCard = discardStack.peek();
				return card;
			}
			if (i == dealerCard.size() - 1) {
				card = draw();
				dealerCard.add(card);
				System.out.println("딜러가 한 장 먹습니다.");
				return null;
			}
		}
		return null;
	}

}
