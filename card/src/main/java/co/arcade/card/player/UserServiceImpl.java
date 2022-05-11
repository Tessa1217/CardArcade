package co.arcade.card.player;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl {

	private static Scanner scn = new Scanner(System.in);
	private static User user;
	private static UserService us = new UserService();

	public User accountExecute() {
		while (true) {
			System.out.println("*****  CARD ARCADE  *****");
			System.out.println("1.회원가입 | 2.로그인 | 3.종료");
			System.out.println("*************************");
			System.out.print("메뉴를 선택하세요: ");
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
				if (currentUser.getId() != null) {
					System.out.println(currentUser.getId() + "님, 환영합니다.");
					return currentUser;
				} else if (currentUser.getId() == null) {
					System.out.println("해당 계정이 없습니다. 다시 로그인해주세요.");
					continue;
				}
			} else if (menu == 3) {
				System.out.println("다음에도 방문해주세요.");
				break;
			}
		}
		return null;
	}

	public User logOut(User user) {
		user = loggingOut(user);
		return user;
	}

	private User signingUp(User user) {
		System.out.print("생성할 ID: ");
		String id = scn.next();
		String pwd = "";
		String pwdChk = "";
		System.out.println("\n=================================");
		System.out.println("           *패스워드 규칙*");
		System.out.println("  패스워드는 8자에서 20자 사이, 특수문자,");
		System.out.println("영문 대소문자, 숫자 조합으로 구성해야 합니다.");
		System.out.println("=================================");
		while (isValidPassword(pwd) == false) {
			System.out.print("생성할 비밀번호: ");
			pwd = scn.next();
			if (isValidPassword(pwd)) {
				while (pwd.equals(pwdChk) == false) {
					System.out.print("패스워드 확인 >>> ");
					pwdChk = scn.next();
					if (pwd.equals(pwdChk)) {
						System.out.println("\n");
					} else if (pwd.equals(pwdChk) == false) {
						System.out.println("비밀번호가 일치하지 않습니다.");
					}
				}
			} else if (isValidPassword(pwd) == false) {
				System.out.println("패스워드를 규격에 맞게 설정해주세요.");
			}
		}
		user.setId(id);
		user.setPwd(pwd);
		return user;
	}

	private boolean isValidPassword(String pwd) {
		boolean check = false;
		String REGEX = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";
		Matcher matcher;
		matcher = Pattern.compile(REGEX).matcher(pwd);
		if (matcher.find()) {
			return true;
		}
		return check;
	}

	private User loggingIn(User user) {
		System.out.print("아이디 >>> ");
		String id = scn.next();
		System.out.print("패스워드 >>> ");
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

	public int update(User user) {
		String firstPwd = "";
		String finalPwd = "";
		System.out.print("수정 전 패스워드 확인 >>> ");
		user.setPwd(scn.next());
		do {
			System.out.print("새로운 패스워드 >>> ");
			firstPwd = scn.next();
			System.out.println("** 아래 패스워드가 위 패스워드와 일치하지 않을 시 다시 입력해주셔야 합니다. **");
			System.out.print("새로운 패스워드 확인 >>> ");
			finalPwd = scn.next();
		} while (firstPwd.equals(finalPwd) == false);
		int update = us.updateAccount(user, finalPwd);
		return update;
	}

}
