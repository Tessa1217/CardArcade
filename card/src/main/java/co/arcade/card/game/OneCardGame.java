package co.arcade.card.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import co.arcade.card.carddeck.Card;
import co.arcade.card.gameview.GameView;
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

	// 공격 카드 중첩용 불린형 변수
	private static int userChk = 0;
	private static int dealerChk = 0;

	// 실행 메소드
	public void execute(User currentUser) {
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
					betMoney = user.bet(user, money);

					if (betMoney != 0) {
						Map<String, List<Card>> cardMap = firstCards();
						displayCards(cardMap);
						boolean run = true;
						while (run) {
							if (cardMap.get("user").size() == 0 || cardMap.get("dealer").size() == 0) {
								System.out.println("게임 종료");
								betMoney = oc.winning(cardMap.get("user").size(), betMoney);
								returnMoney(betMoney);
								return;
							} else if (cardMap.get("user").size() >= 15) {
								System.out.println("유저가 파산했습니다.");
								betMoney = oc.winning(cardMap.get("user").size(), betMoney);
								returnMoney(betMoney);
								return;
							} else if (cardMap.get("dealer").size() >= 15) {
								System.out.println("딜러가 파산했습니다.");
								betMoney = oc.winning(0, betMoney);
								returnMoney(betMoney);
								return;
							}
							System.out.println("------------ ----------");
							System.out.println(" 1.카드 내기    2.카드 먹기");
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
							System.out.println("게임 진행창: ");
							if (choice == 1) {
								int idx = -1;
								do {
									System.out.println("몇 번째 카드를 내시겠습니까?");
									System.out.println("⚠ 가지고 있는 카드 수 밑으로 선택해주세요.");
									try {
										idx = Integer.parseInt(scn.next());
									} catch (NumberFormatException e2) {
										System.out.println("⚠ 다시 입력해주세요.");
									}
								} while (idx == -1 || idx > cardMap.get("user").size());
								Card user = oc.discard(cardMap.get("user").get(idx - 1));
								if (user != null) {
									cardMap.get("user").remove(user);
									System.out.println("유저가 " + user + "를 냈습니다.");
									userChk = chkAttack(user);
								} else if (user == null) {
									userChk = 0;
									System.out.println("카드를 잘못냈습니다. 한 장 먹습니다.");
									cardMap.get("user").add(oc.draw());
								}
								Card dealer = oc.autoPlaying(cardMap.get("dealer"));
								if (dealer != null) {
									dealerChk = chkAttack(dealer);
								} else if (dealer == null) {
									dealerChk = 0;
								}
							} else if (choice == 2) {
								cardMap.get("user").add(oc.draw());
								userChk = 0;
								System.out.println("유저가 한 장 먹습니다.");
								Card dealer = oc.autoPlaying(cardMap.get("dealer"));
								if (dealer != null) {
									dealerChk = chkAttack(dealer);
								}
							} else {
								System.out.println("메뉴를 다시 선택해주세요");
								continue;
							}
							if (userChk != 0 && dealerChk != 0) {
								if (userChk == 1 && dealerChk == 1 || dealerChk == 2) {
									System.out.print("유저가 공격했습니다. ");
									attack.addAll(oc.attack(userChk));
									System.out.print("딜러도 공격했습니다. \n");
									attack.addAll(oc.attack(dealerChk));
									System.out.println("현재 공격 카드 " + attack.size() + " 쌓여 있습니다.");
									userChk = dealerChk = 0;
								} else if (userChk == 2 && dealerChk == 2) {
									System.out.print("유저가 공격했습니다. ");
									attack.addAll(oc.attack(userChk));
									System.out.print("딜러도 공격했습니다. \n");
									attack.addAll(oc.attack(dealerChk));
									System.out.println("현재 공격 카드 " + attack.size() + " 쌓여 있습니다.");
									userChk = dealerChk = 0;
								}
							} else if (userChk == 0 && dealerChk != 0) {
								System.out.print("딜러가 공격했습니다. \n");
								attack.addAll(oc.attack(dealerChk));
								user.showCard(cardMap.get("user"));
								int idx = -1;
								do {
									System.out.println("===============================");
									System.out.println("  공격 방어할 카드가 있으면 선택해주세요.");
									System.out.println("   ⚠ 없으면 아무 카드나 선택하세요.");
									System.out.println("⚠ 가지고 있는 카드 수 밑으로 선택해주세요.");
									System.out.println("===============================");
									try {
										idx = Integer.parseInt(scn.next());
									} catch (NumberFormatException e) {
										System.out.println("⚠ 다시 입력해주세요.");
									}
								} while (idx == -1 || idx > cardMap.get("user").size());
								userChk = chkAttack(cardMap.get("user").get(idx - 1));
								if (userChk == 0) {
									System.out.print("\n유저가 방어하지 못해 " + attack.size() + "장 먹습니다.\n");
									cardMap.get("user").addAll(attack);
									attack.clear();
									dealerChk = 0;
								} else if (userChk != 0) {
									oc.discard(cardMap.get("user").get(idx - 1));
									attack.addAll(oc.attack(userChk));
									cardMap.get("user").remove(idx - 1);
									Card dealer = oc.autoPlaying(cardMap.get("dealer"));
									if (dealer != null) {
										dealerChk = chkAttack(dealer);
										attack.addAll(oc.attack(dealerChk));
									} else if (dealer == null) {
										dealerChk = 0;
									}
									if (dealerChk == 0) {
										cardMap.get("dealer").addAll(attack);
										attack.clear();
										System.out.println("딜러가 방어하지 못해 " + attack.size() + "장 먹었습니다.");
										userChk = dealerChk = 0;
									}
								}
							} else if (userChk != 0 && dealerChk == 0) {
								System.out.print("유저가 공격했습니다. ");
								attack.addAll(oc.attack(userChk));
								System.out.print("딜러가 " + attack.size() + "장 먹습니다.");
								cardMap.get("dealer").addAll(attack);
								userChk = 0;
								attack.clear();
							} else if (userChk == 0 && dealerChk == 0) {
								cardMap.get("user").addAll(attack);
								if (attack.size() > 0) {
									System.out.println("유저가 방어하지 못해 " + attack.size() + "장 먹습니다.");
								}
								attack.clear();
							}
							displayCards(cardMap);
						}

					} else if (betMoney == 0) {
						System.out.println("베팅을 하셔야 게임 진행이 가능합니다.");
					}
				}
			} else if (menu == 2) {
				System.out.println("게임을 종료합니다.");
				returnMoney(betMoney);
				return;
			}
		}
	}

	// 첫 카드 배분
	private Map<String, List<Card>> firstCards() {
		Map<String, List<Card>> cardMap = new HashMap<String, List<Card>>();
		cardMap = oc.firstHand();
		return cardMap;
	}

	// 카드 출력 메소드
	private void displayCards(Map<String, List<Card>> cardMap) {
		System.out.println();
		user.showCard(cardMap.get("user"));
		oc.openTopCard();
		dealer.showOCard(cardMap.get("dealer"));
	}

	// 공격 카드 여부 확인
	private int chkAttack(Card card) {
		int chk = 0;
		if (card.getCardNo().equals("2")) {
			return 1;
		} else if (card.getCardNo().equals("A")) {
			return 2;
		}
		return chk;
	}

	// 최종 금액
	private void returnMoney(int betMoney) {
		System.out.println("최종 금액: " + betMoney + "원");
		GameView.user.setMoney(user.getMoney() + betMoney);
	}

}
