package co.arcade.card.game;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import co.arcade.card.carddeck.Card;
import co.arcade.card.player.Dealer;
import co.arcade.card.player.User;

public class OneCardGame {

	// 플레이어 생성
	private static User user = new User();
	private static Dealer dealer = new Dealer();

	// 스캐너 생성
	private static Scanner scn = new Scanner(System.in);

	// 원카드 생성
	private static OneCard oc = new OneCard();

	// 실행 메소드
	public int execute(User currentUser) {
		user = currentUser;
		int betMoney = 0;
		while (true) {
			System.out.println("1.Play Game | 2.Exit");
			System.out.println("메뉴를 선택해주세요 >> ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (InputMismatchException e) {
				e.printStackTrace();
			}
			if (menu == 1) {
				System.out.println("베팅 금액을 선택해주세요 >> ");
				int money = Integer.parseInt(scn.next());
				betMoney = user.bet(money);
				if (betMoney != 0) {
					Map<String, List<Card>> cardMap = firstCards();
					boolean run = true;
					while (run) {
						if (cardMap.get("userCards").size() == 0) {
							System.out.println("유저가 승리하셨습니다.");
							betMoney = oc.winning(cardMap.get("userCards").size(), betMoney);
							run = false;
							return betMoney;
						}
						if (cardMap.get("dealerCards").size() == 0) {
							System.out.println("딜러가 승리하셨습니다.");
							betMoney = oc.winning(cardMap.get("userCards").size(), betMoney);
							run = false;
							return betMoney;
						}
						System.out.println("====================");
						System.out.println("1. Discard | 2. Draw");
						System.out.println("====================");
						int choice = -1;
						try {
							choice = Integer.parseInt(scn.next());
						} catch (InputMismatchException e) {
							e.printStackTrace();
						}
						if (choice == 1) {

							System.out.println("몇 번째 카드를 내시겠습니까?");
							int idx = Integer.parseInt(scn.next());
							System.out.println("게임 진행: ");
							List<Card> playerAttack = oc.discard(cardMap.get("userCards"), idx - 1);
							if (playerAttack.size() != 0) {
								cardMap.get("dealerCards").addAll(playerAttack);
								System.out.println("딜러가 " + playerAttack.size() + "장 먹었습니다.");
							}
							List<Card> attack = oc.autoPlaying(cardMap.get("dealerCards"));
							if (attack.size() != 0) {
								cardMap.get("userCards").addAll(attack);
								System.out.println("유저가 " + attack.size() + "장 먹었습니다.");
							}
							System.out.println();
							displayCards(cardMap);

						} else if (choice == 2) {
							System.out.println("게임 진행: ");
							cardMap.get("userCards").add(oc.draw());
							System.out.println("유저가 한 장 먹습니다.");
							oc.autoPlaying(cardMap.get("dealerCards"));
							System.out.println();
							displayCards(cardMap);
						}

					}

				}
			} else if (menu == 2) {
				System.out.println("게임을 종료합니다.");
				return betMoney;
			}
		}

	}

	private Map<String, List<Card>> firstCards() {
		Map<String, List<Card>> cardMap = new HashMap<String, List<Card>>();
		List<Card> userCards = user.getCard(oc.firstHand());
		List<Card> dealerCards = dealer.getCard(oc.firstHand());
		System.out.print("유저 카드: ");
		user.showCard(userCards);
		oc.openTopCard();
		System.out.print("딜러 카드: ");
		user.showCard(dealerCards);
		cardMap.put("userCards", userCards);
		cardMap.put("dealerCards", dealerCards);
		return cardMap;
	}

	private void displayCards(Map<String, List<Card>> cardMap) {
		System.out.print("유저 카드: ");
		user.showCard(cardMap.get("userCards"));
		oc.openTopCard();
		System.out.print("딜러 카드: ");
		user.showCard(cardMap.get("dealerCards"));
	}

}
