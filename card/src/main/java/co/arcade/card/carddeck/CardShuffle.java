package co.arcade.card.carddeck;

public class CardShuffle implements Runnable {

	String str = "카드를 섞는 중입니다···";

	@Override
	public void run() {
		System.out.print("\t\t\t");
		for (int i = 0; i < str.length(); i++) {
			System.out.print(str.charAt(i));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.print("\n\n");

	}
}
