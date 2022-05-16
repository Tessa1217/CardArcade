package co.arcade.card.player;

import java.util.List;
import java.util.Scanner;

public class RankServiceImpl {

	private static Scanner scn = new Scanner(System.in);
	private static RankService rs = new RankService();

	public static void execute(User user) {
		while (true) {
			System.out.println("1.전체 랭킹 조회 | 2.내 랭킹 조회 | 3. 나가기");
			System.out.print("메뉴: ");
			int menu = -1;
			try {
				menu = Integer.parseInt(scn.next());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요.");
			}
			if (menu == 1) {
				list();
			} else if (menu == 2) {
				myRank(user);
			} else if (menu == 3) {
				System.out.println("\n랭킹 창을 종료합니다.\n");
				break;
			} else {
				System.out.println("메뉴를 다시 입력해주세요.");
			}
		}
	}

	private static void list() {
		System.out.println("\t\t\t\t\t  전체 랭킹\n");
		List<Rank> rankList = rs.rankList();
		if (rankList.size() != 0) {
			for (Rank rank : rankList) {
				System.out.println("\t\t" + rank.toString());
			}
		}
	}

	private static void myRank(User user) {
		System.out.println("\t\t\t\t\t  내 랭킹\n");
		System.out.println("\t\t" + rs.myRank(user).toString());
	}
}
