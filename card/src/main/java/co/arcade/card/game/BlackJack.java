package co.arcade.card.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import co.arcade.card.carddeck.Card;
import co.arcade.card.carddeck.CardDeck;

public class BlackJack implements GameRules {

	// 한 회차동안 사용할 카드 덱
	public static Stack<Card> cardStack = CardDeck.shuffleDeck();

	// 첫번째 카드 배분
	@Override
	public Map<String, List<Card>> firstHand() {
		cardStack = CardDeck.shuffleDeck();
		Map<String, List<Card>> cardMap = new HashMap<String, List<Card>>();
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				Card card = cardStack.pop();
				cards.add(card);
			}
			cardMap.put(GameRules.cardOwner[i], cards);
			cards = new ArrayList<Card>();
		}
		return cardMap;
	}

	public int firstRound(Map<String, List<Card>> cardMap) {
		int natural = blackJack(cardMap);
		if (natural != 0) {
			return natural;
		}
		int bust = bust(cardMap);
		if (bust != 0) {
			return bust;
		}
		if (natural == bust) {
			return 0;
		}
		return 0;
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

	public Card draw(Map<String, List<Card>> cardMap) {
		if (sumCard(cardMap)[1] > 17) {
			return null;
		} else if (sumCard(cardMap)[1] <= 16) {
			return draw();
		}
		return null;
	}

	// 1.유저 승리, 2.푸쉬, 3.딜러 승리, 4.딜러 버스트, 5.유저 버스트

	// 블랙잭 여부
	private int blackJack(Map<String, List<Card>> cardMap) {
		int[] natural = new int[2];
		natural = sumCard(cardMap);
		int result = 0;
		if (natural[0] == 21 && natural[1] == 21) {
			System.out.println("플레이어, 딜러 둘 다 블랙잭입니다.");
			result = 2;
		} else if (natural[0] == 21) {
			System.out.println("플레이어가 블랙잭입니다.");
			result = 1;
		} else if (natural[1] == 21) {
			System.out.println("딜러가 블랙잭입니다.");
			result = 3;
		}
		return result;
	}

	// 버스트 여부
	private int bust(Map<String, List<Card>> cardMap) {
		int[] bust = sumCard(cardMap);
		int result = 0;
		if (bust[0] > 21 && bust[1] > 21) {
			System.out.println("둘 다 버스트입니다.");
			result = 5;
		} else if (bust[0] > 21) {
			System.out.println("유저 버스트입니다.");
			result = 5;
		} else if (bust[1] > 21) {
			System.out.println("딜러 버스트입니다.");
			result = 4;
		}
		return result;
	}

	// 카드의 합
	private int[] sumCard(Map<String, List<Card>> cardMap) {
		int[] scores = new int[2];
		for (String mapkey : cardMap.keySet()) {
			List<Card> cards = cardMap.get(mapkey);
			int score = 0;
			boolean containA = false;
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getCardNo() == "J" || cards.get(i).getCardNo() == "A") {
					score += 11;
					if (cards.get(i).getCardNo() == "A") {
						containA = true;
					}
				} else if (cards.get(i).getCardNo() == "Q") {
					score += 12;
				} else if (cards.get(i).getCardNo() == "K") {
					score += 13;
				} else {
					score += Integer.parseInt(cards.get(i).getCardNo());
				}
			}
			if (score > 21 && containA) {
				score -= 10;
			}
			if (mapkey.equals("user")) {
				scores[0] = score;
			}
			if (mapkey.equals("dealer")) {
				scores[1] = score;
			}
		}
		return scores;
	}

	public int[] displaySum(Map<String, List<Card>> cardMap) {
		int[] scores = new int[2];
		scores = sumCard(cardMap);
		return scores;
	}

	// 카드의 합을 이용하여 결과 산출
	// 1.유저 승리, 2.푸쉬, 3.딜러 승리, 4.딜러 버스트, 5.유저 버스트
	public int result(Map<String, List<Card>> cardMap) {
		int[] scores = sumCard(cardMap);
		int result = 0;
		if (scores[0] > 21 && scores[1] > 21) {
			System.out.println("유저, 딜러 버스트! 딜러가 이겼습니다.");
			return 5;
		} else if (scores[0] == 21) {
			if (scores[1] == 21) {
				System.out.println("PUSH");
				return 2;
			} else if (scores[1] < 21) {
				System.out.println("유저가 이겼습니다.");
				return 1;
			}
		} else if (scores[0] > 21) {
			System.out.println("유저 버스트! 딜러가 이겼습니다.");
			return 5;
		} else if (scores[0] < 21) {
			if (scores[1] == 21) {
				System.out.println("딜러가 이겼습니다.");
				return 3;
			} else if (scores[0] == scores[1]) {
				System.out.println("PUSH");
				return 2;
			} else if (scores[0] < scores[1]) {
				System.out.println("딜러가 이겼습니다.");
				return 3;
			} else if (scores[0] > scores[1]) {
				System.out.println("유저가 이겼습니다.");
				return 1;
			}
		} else if (scores[1] > 21) {
			System.out.println("딜러 버스트! 유저가 이겼습니다.");
			return 4;
		}
		return result;
	}

	// 베팅 금액 계산
	@Override
	public int winning(int result, int bet) {
		if (result == 1) {
			bet *= 1.5;
		} else if (result == 2) {
			return bet;
		} else if (result == 3) {
			bet *= -0.5;
		} else if (result == 4) {
			bet *= 2;
		} else if (result == 5) {
			bet *= -1;
		}
		return bet;
	}

}
