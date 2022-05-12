package co.arcade.card.game;

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
			System.out.println("-------------  --------");
			System.out.println(" 1.Play Game    2.Exit");
			System.out.println("-------------  --------");
			System.out.print("메뉴를 선택해주세요: ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			}

			if (menu == 1) {
				System.out.print("베팅 금액: ");
				try {
					betMoney = Integer.parseInt(scn.next());
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력해주세요.");
				}

				if (betMoney != 0) {
					betMoney = user.bet(betMoney);
					Map<String, List<Card>> cardMap = firstCards();
					int firstResult = bj.firstRound(cardMap);
					if (firstResult == 0) {
						while (true) {
							display(cardMap);

							System.out.println("-----  --------");
							System.out.println("1.Hit  2.Stand");
							System.out.println("-----  --------");
							int choice = -1;
							try {
								choice = Integer.parseInt(scn.next());
							} catch (NumberFormatException e) {
								System.out.println("숫자를 입력해주세요.");
							}
							if (choice == 1) {
								for (String mapkey : cardMap.keySet()) {
									cardMap.get(mapkey).add(bj.draw());
								}
								int result = bj.firstRound(cardMap);
								if (result != 0) {
									finalDisplay(cardMap);
									betMoney = bj.winning(result, betMoney);
									System.out.println("최종 금액: " + betMoney);
									return betMoney;
								}
							} else if (choice == 2) {
								finalDisplay(cardMap);
								int result = bj.result(cardMap);
								betMoney = bj.winning(result, betMoney);
								System.out.println("최종 금액: " + betMoney);
								return betMoney;
							} else {
								System.out.println("메뉴를 다시 입력해주세요.");
							}
						}
					} else if (firstResult != 0) {
						betMoney = bj.winning(firstResult, betMoney);
						System.out.println("최종 금액: " + betMoney);
						return betMoney;
					}
				} else if (menu == 2) {
					System.out.println("게임을 종료합니다.");
					return 0;

				} else {
					System.out.println("메뉴를 다시 입력해주세요.");
				}
			}
		}

	}

	// 첫 카드 맵으로 저장 및 출력
	private Map<String, List<Card>> firstCards() {
		Map<String, List<Card>> cardMap = bj.firstHand();
		return cardMap;
	}

	// 카드 출력
	private void display(Map<String, List<Card>> cardMap) {
		int[] scores = bj.displaySum(cardMap);
		user.showCard(cardMap.get("user"));
		dealer.showBJCard(cardMap.get("dealer"));
		System.out.println("---------------");
		System.out.println("현재 유저 합계: " + scores[0]);
		System.out.println("---------------");
	}

	private void finalDisplay(Map<String, List<Card>> cardMap) {
		int[] scores = bj.displaySum(cardMap);
		user.showCard(cardMap.get("user"));
		dealer.showCard(cardMap.get("dealer"));
		System.out.println("--------------------------");
		System.out.println("유저 합계: " + scores[0] + " vs. 딜러 합계: " + scores[1]);
		System.out.println("--------------------------");
	}

}
