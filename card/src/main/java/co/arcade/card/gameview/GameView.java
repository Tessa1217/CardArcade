package co.arcade.card.gameview;

import java.util.Scanner;
import java.util.regex.Pattern;

import co.arcade.card.game.BlackJackGame;
import co.arcade.card.player.User;
import co.arcade.card.player.UserService;

public class GameView {

	private static Scanner scn = new Scanner(System.in);
	public static String userId = "";
	BlackJackGame bjg = new BlackJackGame();

	private void blackJack() {
		while (true) {
			System.out.println("*** Play BlackJack! ***");
			System.out.println("Proceed? >>> 1 Quit? >>> 2");
			int menu = Integer.parseInt(scn.next());
			if (menu == 1) {
				bjg.execute();
			} else if (menu == 2) {

			}
		}
	}

	private void oneCard() {
		System.out.println("*** Play One Card! ***");
		System.out.println("Proceed? >>> 1 Quit? >>> 2");
		int menu = Integer.parseInt(scn.next());
		if (menu == 1) {

		} else if (menu == 2) {
		}
	}

	private void userService() {
		UserService us = new UserService();
		User user = null;
		while (true) {
			System.out.println("***** Welcome to Card Game Arcade *****");
			System.out.println("1.SignUp   2.LogIn   3.LogOut   4.Quit");
			int menu = Integer.parseInt(scn.next());
			if (menu == 1) {
				Pattern pwdPattern = Pattern
						.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
				System.out.println("New ID >>> ");
				String id = scn.next();
				System.out.println("New Password >>> ");
				String pwd = scn.next();
				user = new User();
				user.setId(id);
				user.setPwd(pwd);
				us.signUp(user);
			} else if (menu == 2) {
				user = new User();
				System.out.println("Log In");
				System.out.println("ID >>> ");
				user.setId(scn.next());
				System.out.println("Password >>> ");
				user.setPwd(scn.next());
				User player = us.logIn(user);
				if (player != null) {
					System.out.println("Welcome Back to Game Arcade");
					userId = player.getId();
				}

			} else if (menu == 3) {
				System.out.println("Log Out");
				System.out.println("Do you want to log out?");
				String answer = scn.next();
				if (answer.startsWith("y")) {
					user = new User();
					user.setId(userId);
					boolean logOut = us.logOut(user);
					if (logOut) {
						System.out.println("Successfully Logged Out");
						userId = null;
					}
				}

			} else if (menu == 4) {
				System.out.println("Quit Game Arcade");
				break;
			}
		}

	}

}
