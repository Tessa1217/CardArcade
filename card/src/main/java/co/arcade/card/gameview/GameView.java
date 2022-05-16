package co.arcade.card.gameview;

import java.util.Scanner;

import co.arcade.card.game.BlackJackGame;
import co.arcade.card.game.GameRules;
import co.arcade.card.game.OneCardGame;
import co.arcade.card.manual.Manual;
import co.arcade.card.manual.TranslateService;
import co.arcade.card.player.RankServiceImpl;
import co.arcade.card.player.User;
import co.arcade.card.player.UserServiceImpl;

public class GameView {

	private static Scanner scn = new Scanner(System.in);
	public static User user;
	private static UserServiceImpl usi = new UserServiceImpl();
	private static BlackJackGame bjs = new BlackJackGame();
	private static OneCardGame ocg = new OneCardGame();

	public void execute() {
		while (true) {
			user = usi.accountExecute();
			if (user != null) {
				while (true) {
					System.out.println("환영합니다, " + user.getId() + "님!");
					boolean run = mainTitle(user);
					if (run == false) {
						user = new User();
						break;
					}
				}
			}
			if (user == null) {
				break;
			}
		}

	}

	private boolean mainTitle(User currentUser) {
		boolean run = true;
		user = currentUser;
		System.out.println(currentUser.getId() + "님의 현재 잔고: " + currentUser.getMoney() + "원");
		if (currentUser.getMoney() <= 0) {
			System.out.println("파산하셨습니다!");
			usi.deleteUser(currentUser);
			return false;
		}
		System.out.println("┌───────────────────────────────────────────────────────────────────────────────────┐");
		GameRules.t2.run();
		System.out.println("   ┌────────┐  ┌─────────┐ ┌─────────────┐  ┌─────────────┐ ┌────────┐ ┌─────────┐ ");
		System.out.println("   │ 1.블랙잭 │  │ 2.원 카드 │ │ 3.비밀번호 수정 │  │ 4.로그아웃 하기 │ │ 5.설명서 │ │ 6.현 랭킹 │ ");
		System.out.println("   └────────┘  └─────────┘ └─────────────┘  └─────────────┘ └────────┘ └─────────┘  ");
		System.out.println("└───────────────────────────────────────────────────────────────────────────────────┘");
		System.out.print("메뉴를 선택하세요 >>> ");
		int menu = -1;
		try {
			menu = Integer.parseInt(scn.next());
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요");
		}
		if (menu == 1) {
			bjs.execute(user);
		} else if (menu == 2) {
			ocg.execute(user);
		} else if (menu == 3) {
			int update = usi.update(currentUser);
			if (update == 1) {
				System.out.println("비밀번호가 수정되었습니다.");
			}
		} else if (menu == 4) {
			user = usi.logOut(currentUser);
			return false;
		} else if (menu == 5) {
			while (true) {

				System.out.println("\t┌─────────────────────────────────────────────────────────────────────────┐");
				System.out.println("\t                                  게임 메뉴얼");
				System.out.println("\t  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌─────────┐  ");
				System.out.println("\t  │ 1.블랙잭(KOR)│ │ 2.블랙적(ENG)│ │ 3.원카드(KOR)│ │ 4.원카드(ENG)│ │ 5.창 닫기 │  ");
				System.out.println("\t  └────────────┘ └────────────┘ └────────────┘ └────────────┘ └─────────┘  ");
				System.out.println("\t                                  WARNING");
				System.out.println("\t           한국어 버전은 파파고 번역기를 사용하여 번역이 매끄럽지 못할 수도 있습니다.");
				System.out.println("\t└─────────────────────────────────────────────────────────────────────────┘ ");
				int version = -1;
				try {
					version = Integer.parseInt(scn.next());
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력해주세요");
				}
				if (version == 1) {
					System.out.println(TranslateService.request("BlackJackmanual"));
				} else if (version == 2) {
					System.out.println(Manual.readManual("BlackJackmanual"));
				} else if (version == 3) {
					System.out.println(TranslateService.request("OneCardmanual"));
				} else if (version == 4) {
					System.out.println(Manual.readManual("OneCardmanual"));
				} else if (version == 5) {
					System.out.println("메뉴얼 창을 종료합니다.");
					break;
				}
			}
		} else if (menu == 6) {
			RankServiceImpl.execute(currentUser);
		} else {
			System.out.println("메뉴를 다시 선택해주세요.");
		}
		return run;
	}

}
