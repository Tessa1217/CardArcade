package co.arcade.card.game;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import co.arcade.card.carddeck.Card;
import co.arcade.card.gameview.GameView;
import co.arcade.card.player.Dealer;
import co.arcade.card.player.User;
import co.arcade.card.player.UserService;

public class BlackJackGame {

	// 플레이어 생성
	private static User user = new User();
	private static Dealer dealer = new Dealer();
	// 스캐너 생성
	private static Scanner scn = new Scanner(System.in);
	// 블랙잭 생성
	private static BlackJack bj = new BlackJack();

	// 실행 메소드
	public void execute(User currentUser) {
		user = currentUser;
		boolean run = true;
		while (run) {
			int betMoney = 0;
			System.out.println("\t\t\t\t┌───────────┐  ┌──────┐");
			System.out.println("\t\t\t\t│1.Play Game│  │2.Exit│");
			System.out.println("\t\t\t\t└───────────┘  └──────┘");
			System.out.print("메뉴를 선택해주세요: ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			}
			if (GameView.user.getMoney() <= 0) {
				System.out.println("현재 잔액이 " + user.getMoney() + "원입니다.");
				break;
			}
			if (menu == 1) {
				while (betMoney == 0) {
					System.out.print("베팅 금액: ");
					try {
						betMoney = Integer.parseInt(scn.next());
						betMoney = user.bet(user, betMoney);
					} catch (NumberFormatException e) {
						System.out.println("숫자를 입력해주세요.");
					}
					if (betMoney == 0) {
						System.out.println("베팅을 하셔야 게임이 가능합니다.");
					}
				}
				if (betMoney != 0) {
					System.out.println("🔸 베팅 후 플레이어의 잔액은 " + GameView.user.getMoney() + "입니다. 주의해서 배팅해주세요!\n\n");
					Map<String, List<Card>> cardMap = firstCards();
					int firstResult = bj.firstRound(cardMap);
					if (firstResult == 0) {
						while (true) {
							display(cardMap);
							System.out.println("\t\t\t┌─────┐  ┌───────┐");
							System.out.println("\t\t\t│1.Hit│  │2.Stand│");
							System.out.println("\t\t\t└─────┘  └───────┘");
							int choice = -1;
							try {
								choice = Integer.parseInt(scn.next());
							} catch (NumberFormatException e) {
								System.out.println("숫자를 입력해주세요.");
							}
							if (choice == 1) {
								cardMap.get("user").add(bj.draw());
								Card card = bj.draw(cardMap);
								if (card != null) {
									cardMap.get("dealer").add(card);
								}
								int result = bj.firstRound(cardMap);
								if (result != 0) {
									finalResult(cardMap, betMoney, result);
									break;
								}
							} else if (choice == 2) {
								if (bj.draw(cardMap) != null) {
									cardMap.get("dealer").add(bj.draw(cardMap));
								}
								int result = bj.result(cardMap);
								finalResult(cardMap, betMoney, result);
								break;
							} else {
								System.out.println("메뉴를 다시 입력해주세요.");
							}
						}
					} else if (firstResult != 0) {
						int result = firstResult;
						finalResult(cardMap, betMoney, result);
					}
				}
			}
			if (menu == 2) {
				System.out.println("게임을 종료합니다.");
				break;
			} else {
				System.out.println("메뉴를 다시 입력해주세요.");
			}
		}

	}

	// 첫 카드 맵으로 저장 및 출력
	private Map<String, List<Card>> firstCards() {
		Map<String, List<Card>> cardMap = bj.firstHand();
		return cardMap;
	}

	// 최종 합 + 카드 출력 메소드
	private void finalResult(Map<String, List<Card>> cardMap, int betMoney, int result) {
		System.out.println("\t\t\t▶ 게임 결과: ");
		finalDisplay(cardMap);
		betMoney = bj.winning(result, betMoney);
		returnMoney(betMoney);
	}

	// 카드 출력
	private void display(Map<String, List<Card>> cardMap) {
		int[] scores = bj.displaySum(cardMap);
		user.showCard(cardMap.get("user"));
		dealer.showBJCard(cardMap.get("dealer"));
		System.out.println("\t\t\t┌───────────────┐");
		System.out.println("\t\t\t│    USER: " + String.format("%2d", scores[0]) + "   │");
		System.out.println("\t\t\t└───────────────┘");
	}

	// ─ │ ┌ ┐ ┘ └
	private void finalDisplay(Map<String, List<Card>> cardMap) {
		int[] scores = bj.displaySum(cardMap);
		user.showCard(cardMap.get("user"));
		dealer.showCard(cardMap.get("dealer"));
		System.out.println("\t\t\t┌────────────────────────────┐");
		System.out.print("\t\t\t│    USER:" + String.format("%2d", scores[0]) + " vs. DEALER:"
				+ String.format("%2d", scores[1]) + "   │\n");
		System.out.println("\t\t\t└────────────────────────────┘");
	}

	private void returnMoney(int betMoney) {
		System.out.println("\t\t\t최종 금액: " + betMoney + "원\n");
		GameView.user.setMoney(user.getMoney() + betMoney);
		UserService.setFinalMoney(GameView.user);

	}

}
