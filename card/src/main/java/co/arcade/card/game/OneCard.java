package co.arcade.card.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class OneCard implements GameRules {

	public static Stack<Card> cardStack = CardDeck.shuffleDeck(); // 게임 카드 덱
	public static Stack<Card> discardStack = new Stack<Card>(); // 내려놓은 카드 덱

	// 첫 카드 배분 (원 카드 7장)
	@Override
	public List<Card> firstHand() {
		List<Card> playerCard = new ArrayList<Card>();
		for (int i = 0; i < 7; i++) {
			playerCard.add(cardStack.pop());
		}
		System.out.println("현재 플레이어 카드");
		for (Card card : playerCard) {

			System.out.print(card.toString());
		}
		System.out.println();

		return playerCard;
	}

	// 게임 카드 덱이 빌 경우 내려놓은 카드 덱을 재셔플
	public Stack<Card> reshuffle(Stack<Card> discardStack) {
		if (cardStack.isEmpty() && discardStack.isEmpty() == false) {
			Collections.shuffle(discardStack);
		}
		for (Card card : discardStack) {
			cardStack.push(card);
		}
		return cardStack;
	}

	// 카드 한 장 가져가기
//	@Override
//	public List<Card> draw(List<Card> playerCard) {
//		if (cardStack.size() - 1 == 0) {
//			cardStack = reshuffle(discardStack);
//		}
//		playerCard.add(cardStack.pop());
//		return playerCard;
//	}

	// 카드 내려놓기

	public List<Card> discard(List<Card> playerCard, int discardIdx) {

		Card card;
		if (discardStack.isEmpty()) {
			card = cardStack.peek();
		} else {
			card = discardStack.peek();
		}

		String currentPattern = card.getCardPattern();
		String currentNo = card.getCardNo();
		String discardPattern = playerCard.get(discardIdx).getCardPattern();
		String discardNo = playerCard.get(discardIdx).getCardNo();

		if (currentPattern.equals(discardPattern) || currentNo.equals(discardNo)) {
			discardStack.add(playerCard.get(discardIdx));
			playerCard.remove(discardIdx);
		} else {
			System.out.println("해당 카드는 내려놓으실 수 없습니다.");
		}
		if (discardStack.isEmpty() == false) {
			System.out.println("현재 카드");
			System.out.println(discardStack.peek().toString());
		}
		System.out.println("현재 플레이어 카드");
		if (playerCard.size() == 0) {
			System.out.println("이기셨습니다!");
		} else {
			for (Card myCard : playerCard) {
				System.out.print(myCard + " ");
			}
		}
		return playerCard;
	}

	// 게임 후 베팅 금액 계산
	@Override
	public int winning(int result, int bet) {
//		if (result) {
//			bet *= 2;
//		} else if (result == false) {
//			bet *= -1;
//		}
		return 0;
	}

	@Override
	public Card draw() {
		// TODO Auto-generated method stub
		return null;
	}

}
