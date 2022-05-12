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
			System.out.println("======================================");
			System.out.println(" ♠ ♦ ♥ ♣ ♠ ♦ CARD ARCADE ♠ ♦ ♥ ♣ ♠ ♦ ♠");
			System.out.println(" --------  ------- ------------ -----");
			System.out.println(" 1.회원가입   2.로그인  3.비밀번호 찾기  4.종료");
			System.out.println(" --------  ------- ------------ -----");
			System.out.println(" ♦ ♥ ♣ ♠ ♦ ♥ ♣ ♠ ♦ ♥ ♣ ♠ ♦ ♥ ♣ ♠ ♦ ♥ ♠");
			System.out.println("======================================\n");
			System.out.print("♠ 메뉴를 선택하세요: ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			}
			if (menu == 1) {
				user = new User();
				user = signingUp(user);
				int insert = us.signUp(user);
				if (insert != 0) {
					System.out.println("계정 생성이 완료되었습니다.");
					System.out.println("초기 지급금 100,000원이 지급되었습니다.");
				} else if (insert == 0) {
					System.out.println("⚠ 계정 생성에 실패하였습니다.");
				}
			} else if (menu == 2) {
				user = new User();
				User currentUser = new User();
				int cnt = 0;
				do {
					user = loggingIn(user);
					currentUser = us.logIn(user);
					cnt++;
					if (currentUser.getId() != null) {
						break;
					}
					System.out.println("⚠ 계정 정보가 틀립니다. 다시 로그인을 시도해주세요.");
				} while (cnt < 3 && currentUser.getId() == null);
				if (currentUser.getId() != null) {
					System.out.println(currentUser.getId() + "님, 환영합니다.");
					return currentUser;
				} else if (cnt >= 3) {
					System.out.print("⚠ 비밀번호 찾기를 진행하시겠습니까? (yes/no 또는 네/아니오)");
					String answer = scn.next();
					if (answer.startsWith("y") || answer.startsWith("네")) {
						System.out.println("비밀번호 찾기로 이동합니다.\n");
						findPassword(user);
					}
				}
			} else if (menu == 3) {
				user = new User();
				findPassword(user);
			} else if (menu == 4) {
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
		String id = "";
		String pwd = "";
		String pwdChk = "";

		boolean idCheck;
		do {
			System.out.print("생성할 ID: ");
			id = scn.next();
			idCheck = isSameId(id);
		} while (idCheck);

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
				System.out.println("⚠ 패스워드를 규격에 맞게 설정해주세요.");
			}
		}

		do {
			System.out.println("♠ 이메일: ");
			String email = scn.next();
			if (email.contains("@") == false) {
				System.out.println("⚠ 올바른 이메일 규격을 입력해주세요.");
			} else if (email.contains("@")) {
				user.setEmail(email);
			}
		} while (user.getEmail() == null);

		user.setId(id);
		user.setPwd(pwd);
		return user;
	}

	// 아이디 중복 체크
	private boolean isSameId(String id) {
		boolean check = false;
		int idCnt = us.findId(id);
		if (idCnt > 0) {
			System.out.println("⚠ 동일한 아이디가 있습니다.");
			return true;
		}
		return check;
	}

	// 8자~20자 특수문자, 영대소문자, 숫자 포함하는 비밀번호
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

	// 이전 비밀번호와 동일한지 여부 확인
	private boolean isSamePassword(User user, String pwd) {
		boolean check = true;
		user = us.findPwd(user);
		if (user.getPwd().equals(pwd)) {
			return false;
		}
		return check;
	}

	// 로그인
	private User loggingIn(User user) {
		System.out.print("♠ 아이디: ");
		String id = scn.next();
		System.out.print("♠ 패스워드: ");
		String pwd = scn.next();
		user.setId(id);
		user.setPwd(pwd);
		return user;
	}

	// 로그아웃
	private User loggingOut(User user) {
		boolean update = us.logOut(user);
		if (update) {
			user = new User();
		} else if (update == false) {
			System.out.println("⚠ 로그아웃이 정상적으로 진행되지 않았습니다.");
		}
		return user;
	}

	// 비밀번호 수정
	public int update(User user) {
		String firstPwd = "";
		String finalPwd = "";
		System.out.print("♠ 수정 전 패스워드 확인: ");
		user.setPwd(scn.next());
		do {
			System.out.print("♠ 새로운 패스워드: ");
			firstPwd = scn.next();
			boolean checkValid = isValidPassword(firstPwd);
			boolean checkSame = isSamePassword(user, firstPwd);
			if (checkValid && checkSame) {
				System.out.println("⚠ 아래 패스워드가 위 패스워드와 일치하지 않을 시 다시 입력해주셔야 합니다.");
				System.out.print("새로운 패스워드 확인 >>> ");
				finalPwd = scn.next();
			} else if (checkValid == false) {
				System.out.println("⚠ 수정된 패스워드가 규격에 맞지 않습니다.");
			} else if (checkSame == false) {
				System.out.println("⚠ 이전 비밀번호와 동일합니다. 재설정해주세요.");
			}
		} while (firstPwd.equals(finalPwd) == false);
		int update = us.updateAccount(user, finalPwd);
		return update;
	}

	// 비밀번호 찾기 (아이디와 가입 시 인증된 이메일 맞으면 확인 가능)
	private void findPassword(User user) {
		user = new User();
		System.out.println("------------------");
		System.out.println("   ♠ 비밀번호 찾기");
		System.out.println("------------------");
		System.out.print("아이디 >> ");
		user.setId(scn.next());
		System.out.print("가입 시 인증된 이메일 >> ");
		user.setEmail(scn.next());
		user = us.findPwd(user);
		String pwd = user.getPwd();
		password(pwd);
	}

	// 찾은 비밀번호 출력
	private void password(String pwd) {
		if (pwd != null) {
			System.out.println("계정 비밀번호는 '" + pwd + "' 입니다.");
		} else if (pwd == null) {
			System.out.println("⚠ 해당 아이디에 대한 비밀번호를 찾을 수 없습니다.");
		}
	}

}
