package co.arcade.card.gameview;

public class Title implements Runnable {
	String str = "         ♠   ♦   ♥   ♣   ♦   ♥   ♣   CARD GAME  ♠   ♦   ♥   ♣   ♦   ♥   ♣";

	public void run() {
		for (int i = 0; i < str.length(); i++) {
			try {
				System.out.print(str.charAt(i));
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}

}
