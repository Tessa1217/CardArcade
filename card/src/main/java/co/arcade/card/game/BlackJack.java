package co.arcade.card.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class BlackJack implements GameRules {

	// 한 회차동안 사용할 카드 덱
	public static Stack<Card> cardStack = CardDeck.shuffleDeck();

	// 첫번째 카드 배분
	@Override
	public List<Card> firstHand() {

		List<Card> playerCard = new ArrayList<Card>();

		for (int i = 0; i < 2; i++) {
			Card card = cardStack.pop();
			playerCard.add(card);
		}
		return playerCard;
	}

	// 카드 추가
	@Override
	public Card draw() {
		Card card = null;
		if (cardStack.isEmpty() == false) {
			card = cardStack.pop();
		}
		return card;
	}

	public double blackJack(List<Card> playerCards, List<Card> dealerCards) {
		double bj = 0;
		int playerSum = sumCard(playerCards);
		int dealerSum = sumCard(playerCards);
		if (playerSum == 21) {
			System.out.println("User BlackJack!");
			bj = 1.5;
		} else if (dealerSum == 21) {
			System.out.println("Dealer BlackJack!");
			bj = -2.0;
		} else if (playerSum == 21 && dealerSum == 21) {
			System.out.println("User, Dealer BlackJack. Push.");
			return bj;
		}
		return bj;
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

	// 카드의 합을 이용하여 결과 산출
	public int result(int playerSum, int dealerSum) {
		int result = 0;
		if (playerSum == 21) {
			if (dealerSum == 21) {
				System.out.println("User, Dealer PUSH");
				return 2;
			} else if (dealerSum < 21) {
				System.out.println("User Wins");
				return 1;
			} else if (dealerSum > 21) {
				System.out.println("Dealer Busted, User Wins");
				return 1;
			}
		} else if (playerSum > 21) {
			if (dealerSum == 21) {
				System.out.println("User Busted, Dealer Wins");
				return 3;
			} else if (dealerSum < 21) {
				System.out.println("User Busted, Dealer Wins");
				return 3;
			} else if (dealerSum > 21) {
				System.out.println("User, Dealer Busted, Dealer Wins");
				return 3;
			}

		} else if (playerSum < 21) {
			if (dealerSum == 21) {
				System.out.println("Dealer Wins");
				return 3;
			} else if (dealerSum < 21) {
				if (playerSum > dealerSum) {
					System.out.println("Player Wins");
					return 1;
				} else if (playerSum < dealerSum) {
					System.out.println("Dealer Wins");
					return 3;
				} else if (playerSum == dealerSum) {
					System.out.println("Player, Dealer PUSH");
					return 2;
				}
			} else if (dealerSum > 21) {
				System.out.println("Dealer Busted, User Wins");
				return 1;
			}
		}
		return result;
	}

	public int bust(int userSum, int dealerSum) {
		int result = 0;

		if (userSum > 21) {
			System.out.println("** User Busted **");
			result = 3;
		} else if (dealerSum > 21) {
			System.out.println("** Dealer Busted **");
			result = 1;
		} else if (userSum > 21 && dealerSum > 21) {
			System.out.println("** User, Dealer Busted, User Loses! **");
			result = 2;
		}
		return result;
	}

	// 베팅 금액 계산
	@Override
	public int winning(int result, int bet) {
		if (result == 1) {
			bet *= 2;
		} else if (result == 2) {
			return bet;
		} else if (result == 3) {
			bet *= -1;
		}
		return bet;
	}

}
