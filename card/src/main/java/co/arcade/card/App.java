package co.arcade.card;

import co.arcade.card.gameview.GameView;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		GameView gv = new GameView();
		gv.execute();

//		for (int i = 99; i > 1; i -= 10) {
//			int ran = (int) (Math.random() * 4) + 1;
//			System.out.println("Ran " + ran);
//			int a[] = new int[ran];
//			for (int j = 0; j < ran; j++) {
//				int floor = (int) (Math.random() * 9) + 1;
//				int ten = ((i / 10) * 10) + floor;
//				System.out.println(ten);
//				a[j] = ten;
//				for (int k = 0; k < j; k++) {
//					if (a[j] == a[k]) {
//						j--;
//					}
//				}
//
//			}
//			for (int aa : a) {
//				System.out.println("a no: " + aa);
//			}
//		}

	}
}
