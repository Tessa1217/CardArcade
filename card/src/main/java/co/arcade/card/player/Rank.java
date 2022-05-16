package co.arcade.card.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rank {

	private String id;
	private int money;
	private int rank;

	@Override
	public String toString() {
		return String.format("│ RANK: %3d │ ID: %15s │ MONEY: %10d원 │", rank, id, money);
	}

}
