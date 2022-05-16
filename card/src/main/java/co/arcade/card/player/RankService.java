package co.arcade.card.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.arcade.card.dao.DataSource;

public class RankService {

	private static DataSource ds = DataSource.getInstance();
	private static Connection conn;
	private static PreparedStatement psmt;
	private static ResultSet rs;

	public List<Rank> rankList() {
		String sql = "SELECT * FROM RANK_V";
		List<Rank> rankList = new ArrayList<Rank>();
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Rank r = new Rank();
				r.setId(rs.getString("id"));
				r.setMoney(rs.getInt("money"));
				r.setRank(rs.getInt("rank"));
				rankList.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rankList;

	}

	public Rank myRank(User user) {
		Rank rank = new Rank();
		String sql = "SELECT * FROM RANK_V WHERE ID = ?";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			rs = psmt.executeQuery();
			if (rs.next()) {
				rank.setId(rs.getString("id"));
				rank.setMoney(rs.getInt("money"));
				rank.setRank(rs.getInt("rank"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rank;
	}

	// DB Connection 끊기
	private void closeConnection() {
		try {
			if (rs != null) {
				rs.close();
			}

			if (psmt != null) {
				psmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
