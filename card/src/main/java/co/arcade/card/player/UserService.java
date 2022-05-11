package co.arcade.card.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.arcade.card.dao.DataSource;

public class UserService {

	private static DataSource ds = DataSource.getInstance();
	private static Connection conn;
	private static PreparedStatement psmt;
	private static ResultSet rs;

	// 계정 생성
	public int signUp(User user) {

		int insert = -1;
		String sql = "INSERT INTO user_table VALUES (?, ?, default)";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getPwd());
			insert = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return insert;
	}

	// 로그인
	public User logIn(User user) {
		User player = new User();
		String sql = "SELECT * FROM user_table WHERE id = ? AND pwd = ?";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getPwd());
			rs = psmt.executeQuery();

			if (rs.next()) {
				player.setId(rs.getString("id"));
				player.setMoney(rs.getInt("money"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return player;
	}

	// 로그아웃
	public boolean logOut(User user) {

		int update = -1;
		String sql = "UPDATE user_table SET money = ? WHERE id = ?";

		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, user.getMoney());
			psmt.setString(2, user.getId());
			update = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		if (update == 1) {
			return true;
		}
		return false;
	}

	public int updateAccount(User user, String pwd) {
		int update = -1;
		String sql = "UPDATE user_table SET pwd = ? WHERE id = ? AND pwd = ?";

		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pwd);
			psmt.setString(2, user.getId());
			psmt.setString(3, user.getPwd());
			update = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return update;
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
