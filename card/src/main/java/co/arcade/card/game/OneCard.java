package co.arcade.card.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class OneCard implements GameRules {

	private Stack<Card> cardStack = CardDeck.shuffleDeck(); // 게임 카드 덱
	private Stack<Card> discardStack = new Stack<Card>();
	private Card topCard = discardStack.size() > 0 ? discardStack.peek() : firstOpenCard();

	// 첫 카드 배분 (원 카드 7장)
	@Override
	public List<Card> firstHand() {

		List<Card> playerCard = new ArrayList<Card>();

		for (int i = 0; i < 7; i++) {
			playerCard.add(cardStack.pop());
		}

		return playerCard;
	}

	private Card firstOpenCard() {
		if (discardStack.size() == 0) {
			discardStack.push(cardStack.pop());
		}
		Card card = discardStack.peek();
		return card;
	}

	public void openTopCard() {
		System.out.print("TOP CARD: ");
		System.out.println(topCard.toString());
	}

	// 게임 카드 덱이 빌 경우 내려놓은 카드 덱을 재셔플
	public Stack<Card> reshuffle() {
		if (cardStack.isEmpty()) {
			cardStack = CardDeck.shuffleDeck(discardStack);
			firstOpenCard();
		}
		return cardStack;
	}

	// 카드 추가하기
	@Override
	public Card draw() {

		Card card = null;
		if (cardStack.isEmpty() == false) {
			card = cardStack.pop();
		}
		if (cardStack.isEmpty()) {
			cardStack = reshuffle();
			card = draw();
		}
		return card;
	}

	// 카드 내려놓기

	public List<Card> discard(List<Card> playerCard, int discardIdx) {
		Card card = topCard;
		List<Card> attack = new ArrayList<Card>();
		String currentPattern = card.getCardPattern();
		String currentNo = card.getCardNo();
		String discardPattern = playerCard.get(discardIdx).getCardPattern();
		String discardNo = playerCard.get(discardIdx).getCardNo();

		if (currentPattern.equals(discardPattern) || currentNo.equals(discardNo)) {
			Card discardCard = playerCard.remove(discardIdx);
			discardStack.push(discardCard);
			topCard = discardStack.peek();
			System.out.println("유저가 " + discardCard + "를 내려놨습니다.");
			attack = attack(discardCard);
			if (attack.size() != 0) {
				return attack;
			}
			if (playerCard.size() == 0) {
				System.out.println("Win!");
				return playerCard;
			}
		} else {
			System.out.println("해당 카드는 내려놓으실 수 없습니다. 한 장 먹습니다.");
			playerCard.add(draw());
		}

		return attack;
	}

	// 게임 후 베팅 금액 계산
	@Override
	public int winning(int playerCard, int bet) {
		if (playerCard == 0) {
			bet *= 2;
		} else if (playerCard > 0) {
			bet *= -1;
		}
		return bet;
	}

	// 딜러
	public List<Card> autoPlaying(List<Card> dealerCard) {
		Card card = null;
		List<Card> attack = new ArrayList<Card>();
		for (int i = 0; i < dealerCard.size(); i++) {
			Card discard = dealerCard.get(i);
			if (discard.getCardPattern().equals(topCard.getCardPattern())
					|| discard.getCardNo().equals(topCard.getCardNo())) {
				card = dealerCard.remove(i);
				discardStack.push(card);
				System.out.println("딜러가 " + card.toString() + "를 냈습니다.");
				attack = attack(card);
				topCard = discardStack.peek();
				if (attack.size() != 0) {
					return attack;
				}
				break;
			}
			if (i == dealerCard.size() - 1) {
				card = draw();
				dealerCard.add(card);
				System.out.println("딜러가 한 장 먹습니다.");
				break;
			}
		}
		return attack;
	}

	public List<Card> attack(Card card) {
		List<Card> attackCard = new ArrayList<Card>();
		if (card.getCardNo().equals("A")) {
			for (int i = 0; i < 3; i++) {
				attackCard.add(cardStack.pop());
			}
		} else if (card.getCardNo().equals("2")) {
			for (int i = 0; i < 2; i++) {
				attackCard.add(cardStack.pop());
			}
		}
		return attackCard;
	}
}
