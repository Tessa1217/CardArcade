package co.arcade.card.game;

import java.util.ArrayList;
import java.util.HashMap;
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

	// 공격 카드 리스트 생성
	private List<Card> attack = new ArrayList<Card>();

	// 실행 메소드
	public int execute(User currentUser) {
		user = currentUser;
		int betMoney = 0;
		while (true) {
			System.out.println("---------------------");
			System.out.println("1.Play Game | 2.Exit");
			System.out.println("---------------------");
			System.out.print("메뉴: ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			}
			if (menu == 1) {
				boolean mainRun = true;
				while (mainRun) {

					System.out.print("베팅 금액: ");
					int money = -1;
					try {
						money = Integer.parseInt(scn.next());
					} catch (NumberFormatException e) {
						System.out.println("숫자를 입력해주세요.");
					}
					betMoney = user.bet(money);

					if (betMoney != 0) {
						Map<String, List<Card>> cardMap = firstCards();
						boolean run = true;
						while (run) {
							if (cardMap.get("user").size() == 0 || cardMap.get("dealer").size() == 0) {
								System.out.println("게임 종료");
								run = false;
								return betMoney = oc.winning(cardMap.get("user").size(), betMoney);
							}
							System.out.println("------------ ----------");
							System.out.println(" 1. Discard   2. Draw");
							System.out.println("------------ ----------");
							int choice = -1;
							try {
								choice = Integer.parseInt(scn.next());
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							boolean empty = oc.cardStackEmpty();
							if (empty) {
								oc.reshuffle(empty);
							}
							if (choice == 1) {
								System.out.println("몇 번째 카드를 내시겠습니까?");
								int idx = -1;
								try {
									idx = Integer.parseInt(scn.next());
								} catch (NumberFormatException e) {
									System.out.println("숫자를 입력해주세요.");
								}
								Card userCard = oc.discard(cardMap.get("user").get(idx - 1));
								if (userCard != null) {
									attack = oc.attack(userCard);
								}
								if (attack.size() != 0) {
									Card dealerCard = oc.autoPlaying(cardMap.get("dealer"));
									if 
								}

								System.out.println("게임 진행: ");

								List<Card> playerAttack = oc.discard(cardMap.get("user"), idx - 1);
								if (playerAttack.size() != 0) {
									cardMap.get("dealer").addAll(playerAttack);
									System.out.println("딜러가 " + playerAttack.size() + "장 먹었습니다.");
								}
								List<Card> attack = oc.autoPlaying(cardMap.get("dealer"));
								if (attack.size() != 0) {
									cardMap.get("user").addAll(attack);
									System.out.println("유저가 " + attack.size() + "장 먹었습니다.");
								}
								System.out.println();
								displayCards(cardMap);

							} else if (choice == 2) {
								System.out.println("게임 진행: ");
								cardMap.get("user").add(oc.draw());
								System.out.println("유저가 한 장 먹습니다.");
								List<Card> attack = oc.autoPlaying(cardMap.get("dealer"));
								if (attack.size() != 0) {
									cardMap.get("user").addAll(attack);
									System.out.println("유저가 " + attack.size() + "장 먹었습니다.");
								}
								System.out.println();
								displayCards(cardMap);
							}

						}

					} else if (betMoney == 0) {
						System.out.println("베팅을 하셔야 게임 진행이 가능합니다.");
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
		cardMap = oc.firstHand();
		user.showCard(cardMap.get("user"));
		oc.openTopCard();
		dealer.showOCard(cardMap.get("dealer"));
		return cardMap;
	}

	private void displayCards(Map<String, List<Card>> cardMap) {
		user.showCard(cardMap.get("user"));
		oc.openTopCard();
		dealer.showOCard(cardMap.get("dealer"));
	}

}
