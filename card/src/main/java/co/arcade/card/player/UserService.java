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
		String sql = "INSERT INTO USER_TABLE VALUES (?, ?, DEFAULT, ?, ?)";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getPwd());
			psmt.setString(3, user.getEmail());
			psmt.setString(4, user.getContact());
			insert = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return insert;
	}

	// 아이디 중복 찾기
	public int findId(String id) {
		int idCnt = 0;
		String sql = "SELECT COUNT(ID) FROM USER_TABLE WHERE ID = ?";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				idCnt = rs.getInt("count(id)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return idCnt;
	}

	// 로그인
	public User logIn(User user) {
		User player = new User();
		String sql = "SELECT * FROM USER_TABLE WHERE ID = ? AND PWD = ?";
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

	// 로그아웃하거나 게임 끝나면 돈 세팅
	public static boolean setFinalMoney(User user) {

		int update = -1;
		String sql = "UPDATE USER_TABLE SET MONEY = ? WHERE ID = ?";

		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, user.getMoney());
			psmt.setString(2, user.getId());
			update = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		if (update == 1) {
			return true;
		}
		return false;
	}

	// 비밀번호 변경
	public int updateAccount(User user, String pwd) {
		int update = -1;
		String sql = "UPDATE USER_TABLE SET PWD = ? WHERE ID = ? AND PWD = ?";

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

	// 비밀번호 찾기
	public User findPwd(User user) {
		String sql = "SELECT PWD, CONTACT FROM USER_TABLE WHERE ID = ? AND CONTACT = ?";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			psmt.setString(2, user.getContact());
			rs = psmt.executeQuery();
			if (rs.next()) {
				user.setPwd(rs.getString("pwd"));
				user.setContact(rs.getString("contact"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return user;
	}

	public int deleteUser(User user) {
		int delete = -1;
		String sql = "DELETE FROM USER_TABLE WHERE MONEY <= 0 AND ID = ?";
		try {
			conn = ds.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getId());
			delete = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return delete;
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
