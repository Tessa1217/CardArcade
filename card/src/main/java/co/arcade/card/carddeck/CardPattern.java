package co.arcade.card.carddeck;

public enum CardPattern {

	SPADE("♠"), DIAMOND("♦"), HEART("♥"), CLOVER("♣");

	private final String pattern;

	CardPattern(String pattern) {
		this.pattern = pattern;
	}

	public String pattern() {
		return pattern;
	}

}
