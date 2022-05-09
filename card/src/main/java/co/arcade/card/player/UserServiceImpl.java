package co.arcade.card.player;

import java.util.Scanner;

public class UserServiceImpl {

	private static Scanner scn = new Scanner(System.in);
	private static User user;
	private static UserService us = new UserService();

	public User accountExecute() {
		while (true) {
			System.out.println("*****  CARD ARCADE  *****");
			System.out.println("1.회원가입 | 2.로그인 | 3.종료");
			System.out.println("*************************");
			System.out.println("메뉴를 선택하세요 >>> ");
			int menu = Integer.parseInt(scn.next());
			if (menu == 1) {
				user = new User();
				user = signingUp(user);
				int insert = us.signUp(user);
				if (insert != 0) {
					System.out.println("계정 생성이 완료되었습니다.");
					System.out.println("초기 지급금 100,000원이 지급되었습니다.");
				} else if (insert == 0) {
					System.out.println("계정 생성에 실패하였습니다.");
				}
			} else if (menu == 2) {
				user = new User();
				user = loggingIn(user);
				User currentUser = us.logIn(user);
				if (currentUser != null) {
					System.out.println(currentUser.getId() + "님, 돌아오신 걸 환영합니다.");
					return currentUser;
				}
			}
		}
	}

	public User logOut(User user) {
		user = loggingOut(user);
		return user;
	}

	private User signingUp(User user) {
		System.out.println("생성할 아이디 >>> ");
		String id = scn.next();
		System.out.println("생성할 패스워드 >>> ");
		String pwd = scn.next();
		user.setId(id);
		user.setPwd(pwd);
		return user;
	}

	private User loggingIn(User user) {
		System.out.println("아이디 >>> ");
		String id = scn.next();
		System.out.println("패스워드 >>> ");
		String pwd = scn.next();
		user.setId(id);
		user.setPwd(pwd);
		return user;
	}

	private User loggingOut(User user) {
		boolean update = us.logOut(user);
		if (update) {
			user = new User();
		} else if (update == false) {
			System.out.println("로그아웃이 정상적으로 진행되지 않았습니다.");
		}
		return user;
	}

}
