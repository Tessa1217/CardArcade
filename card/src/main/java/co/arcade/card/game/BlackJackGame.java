package co.arcade.card.game;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import co.arcade.card.carddeck.Card;
import co.arcade.card.player.Dealer;
import co.arcade.card.player.User;

public class BlackJackGame {

	// 플레이어 생성
	private static User user = new User();
	private static Dealer dealer = new Dealer();
	// 스캐너 생성
	private static Scanner scn = new Scanner(System.in);
	// 블랙잭 생성
	private static BlackJack bj = new BlackJack();

	// 실행 메소드
	public int execute(User currentUser) {
		user = currentUser;
		int betMoney = 0;
		while (true) {
			System.out.println("메뉴를 선택해주세요 >> ");
			System.out.println("1.Play Game | 2.Exit");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (InputMismatchException e) {
				e.printStackTrace();
			}

			if (menu == 1) {
				System.out.println("베팅 금액을 선택해주세요 >>> ");
				int money = Integer.parseInt(scn.next());
				betMoney = user.bet(money);
				if (betMoney != 0) {
					Map<String, List<Card>> cardMap = firstCards();
					System.out.println("================");
					System.out.println("1.Hit | 2.Stand");
					System.out.println("================");
					int choice = -1;
					try {
						choice = Integer.parseInt(scn.next());
					} catch (InputMismatchException e) {
						e.printStackTrace();
					}
					if (choice == 1) {
						for (String mapkey : cardMap.keySet()) {
							Card card = bj.draw();
							cardMap.get(mapkey).add(card);
						}
						int userSum = bj.sumCard(cardMap.get("userCards"));
						int dealerSum = bj.sumCard(cardMap.get("dealerCards"));
						int result = bj.bust(userSum, dealerSum);
						if (result != 0) {
							displayUserCards(cardMap.get("userCards"));
							openDealerCards(cardMap.get("dealerCards"));
							betMoney = bj.winning(result, betMoney);
							return betMoney;
						}
						if (result == 0) {
							continue;
						}
					} else if (choice == 2) {
						displayUserCards(cardMap.get("userCards"));
						openDealerCards(cardMap.get("dealerCards"));
						int playerSum = bj.sumCard(cardMap.get("userCards"));
						int dealerSum = bj.sumCard(cardMap.get("dealerCards"));
						int result = bj.result(playerSum, dealerSum);
						betMoney = bj.winning(result, betMoney);
						System.out.println("최종 금액 >>> " + betMoney);
						return betMoney;

					} else {
						System.out.println("다시 입력해주세요.");
					}
				}
			} else if (menu == 2) {
				System.out.println("게임을 종료합니다.");
				return 0;

			} else {

			}
		}

	}

	// 첫 카드 맵으로 저장 및 출력
	private Map<String, List<Card>> firstCards() {
		Map<String, List<Card>> cardMap = new HashMap<String, List<Card>>();
		List<Card> userCards = bj.firstHand();
		List<Card> dealerCards = bj.firstHand();
		double result = bj.blackJack(userCards, dealerCards);
		if (result == 0) {
			displayUserCards(userCards);
			displayDealerCards(dealerCards);
			cardMap.put("userCards", userCards);
			cardMap.put("dealerCards", dealerCards);
		} else if (result != 0) {
			return null;
		}
		return cardMap;
	}

	// 플레이어 카드 출력 메소드
	private void displayUserCards(List<Card> userCards) {
		user.showBlackJackCard(userCards);
		System.out.println("유저 합계: " + bj.sumCard(userCards));
	}

	// 딜러 카드 출력 메소드 (마지막 1장은 보이지 않게 유지)
	private void displayDealerCards(List<Card> dealerCards) {
		dealer.showBlackJackCard(dealerCards);
	}

	// 딜러 카드 오픈 메소드
	private void openDealerCards(List<Card> dealerCards) {
		user.showBlackJackCard(dealerCards);
		System.out.println(bj.sumCard(dealerCards));
	}

}
