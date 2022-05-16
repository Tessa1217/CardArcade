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
import co.arcade.card.player.UserService;

public class OneCardGame {

	// 플레이어 생성
	private static User user = new User();
	private static Dealer dealer = new Dealer();

	// 스캐너 생성
	private static Scanner scn = new Scanner(System.in);

	// 원카드 생성
	private static OneCard oc = new OneCard();

	// 공격 카드 리스트 생성
	public static List<Card> attack = new ArrayList<Card>();

	// 공격 카드 중첩용 불린형 변수
	private static int userChk = 0;
	private static int dealerChk = 0;

	// 실행 메소드
	public void execute(User currentUser) {
		user = currentUser;
		int betMoney = 0;
		while (true) {
			System.out.println("\t\t\t\t┌───────────┐ ┌──────┐");
			System.out.println("\t\t\t\t│1.Play Game│ │2.Exit│");
			System.out.println("\t\t\t\t└───────────┘ └──────┘");
			System.out.print("메뉴: ");
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
							if (emptyOrFull(cardMap, betMoney) == false) {
								mainRun = false;
								break;
							}
							System.out.println("\t\t\t┌──────────┐  ┌───────────┐");
							System.out.println("\t\t\t│1.Put Card│  │2.Draw Card│");
							System.out.println("\t\t\t└──────────┘  └───────────┘");
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
							System.out.println("\n게임 진행창: ");
							userChk = dealerChk = 0;
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
									if (cardMap.get("user").size() == 0) {
										if (emptyOrFull(cardMap, betMoney) == false) {
											mainRun = false;
											break;
										}
									}
									System.out.println("유저가 " + user + "를 냈습니다.");
									userChk = chkAttack(user);
								} else if (user == null) {
									userChk = 0;
									System.out.println("카드를 잘못냈습니다. 한 장 먹습니다.");
									cardMap.get("user").add(oc.draw());
								}
								if (userChk == 0) {
									if (attack.size() > 0) {
										System.out.println("유저가 " + attack.size() + " 장을 먹습니다!");
										cardMap.get("user").addAll(attack);
										attack.clear();
									}
								}
								Card dealer = oc.autoPlaying(cardMap.get("dealer"));
								if (dealer != null) {
									if (emptyOrFull(cardMap, betMoney) == false) {
										mainRun = false;
										break;
									}
									dealerChk = chkAttack(dealer);
								} else if (dealer == null) {
									dealerChk = 0;
								}
							} else if (choice == 2) {
								cardMap.get("user").add(oc.draw());
								userChk = 0;
								System.out.println("유저가 한 장 먹습니다.");
								if (userChk == 0) {
									if (attack.size() > 0) {
										System.out.println("유저가 " + attack.size() + " 장을 먹습니다!");
										cardMap.get("user").addAll(attack);
										attack.clear();
									}
								}
								Card dealer = oc.autoPlaying(cardMap.get("dealer"));
								if (dealer != null) {
									if (emptyOrFull(cardMap, betMoney) == false) {
										mainRun = false;
										break;
									}
									dealerChk = chkAttack(dealer);
								} else if (dealer == null) {
									dealerChk = 0;
								}
							} else {
								System.out.println("메뉴를 다시 선택해주세요");
								continue;
							}

							if (userChk > 0) {
								System.out.println("(∩ ͡° ͜ʖ ͡°)⊃━☆ﾟ. * ･ ｡ﾟ 유저가 공격했습니다.");
								attack.addAll(oc.attack(userChk));
								if (dealerChk == 0) {
									if (attack.size() > 0) {
										System.out.println("딜러가 " + attack.size() + "장을 먹습니다!");
										cardMap.get("dealer").addAll(attack);
										attack.clear();
									}
								}
							}
							if (dealerChk > 0) {
								System.out.println("(∩ ͡° ͜ʖ ͡°)⊃━☆ﾟ. * ･ ｡ﾟ 딜러가 공격했습니다.");
								attack.addAll(oc.attack(dealerChk));
							}

							displayCards(cardMap);
						}

					} else if (betMoney == 0) {
						System.out.println("베팅을 하셔야 게임 진행이 가능합니다.");
					}
				}
			} else if (menu == 2) {
				System.out.println("게임을 종료합니다.");
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
		System.out.println("이번 판 금액: " + betMoney + "원");
		GameView.user.setMoney(user.getMoney() + betMoney);
		UserService.setFinalMoney(GameView.user);
	}

	// 카드 비어있는지 여부
	private boolean emptyOrFull(Map<String, List<Card>> cardMap, int betMoney) {
		if (cardMap.get("user").size() == 0 || cardMap.get("dealer").size() == 0) {
			System.out.println("게임 종료");
			betMoney = oc.winning(cardMap.get("user").size(), betMoney);
			returnMoney(betMoney);
			return false;
		} else if (cardMap.get("user").size() >= 15) {
			System.out.println("💰 유저가 파산했습니다.");
			betMoney = oc.winning(cardMap.get("user").size(), betMoney);
			returnMoney(betMoney);
			return false;
		} else if (cardMap.get("dealer").size() >= 15) {
			System.out.println("💰 딜러가 파산했습니다.");
			betMoney = oc.winning(0, betMoney);
			returnMoney(betMoney);
			return false;
		}
		return true;
	}

}
