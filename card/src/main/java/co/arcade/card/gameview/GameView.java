package co.arcade.card.gameview;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.arcade.card.game.BlackJackGame;
import co.arcade.card.game.OneCardGame;
import co.arcade.card.player.User;
import co.arcade.card.player.UserServiceImpl;

public class GameView {

	private static Scanner scn = new Scanner(System.in);
	private static User user;
	private static UserServiceImpl usi = new UserServiceImpl();
	private static BlackJackGame bjs = new BlackJackGame();
	private static OneCardGame ocg = new OneCardGame();

	public void execute() {
		while (true) {
			user = usi.accountExecute();
			if (user != null) {
				while (true) {
					mainTitle(user);
				}
			}
		}

	}

	private void mainTitle(User currentUser) {
		user = currentUser;
		System.out.println(currentUser.getId() + "님의 잔고: " + currentUser.getMoney() + "원");
		System.out.println("====================================");
		System.out.println("1.블랙잭 | 2.원카드 | 3.계정수정 | 4.로그아웃");
		System.out.println("====================================");
		System.out.println("메뉴를 선택하세요 >>> ");
		int menu = -1;
		try {
			menu = Integer.parseInt(scn.next());
		} catch (InputMismatchException e) {
			e.printStackTrace();
		}
		if (menu == 1) {
			int betMoney = bjs.execute(user);
			if (betMoney != 0) {
				user.setMoney(user.getMoney() + betMoney);
			}
		} else if (menu == 2) {
			int betMoney = ocg.execute(user);

		} else if (menu == 3) {

		} else if (menu == 4) {
			user = usi.logOut(currentUser);
		}
	}

}
