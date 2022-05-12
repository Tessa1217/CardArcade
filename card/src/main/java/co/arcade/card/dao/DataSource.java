package co.arcade.card.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource { // Singleton

	// 인스턴스 생성
	private static DataSource ds = new DataSource();

	// private 생성자
	private DataSource() {

	}

	// getInstance 메소드로 생성된 인스턴스 호출
	public static DataSource getInstance() {
		return ds;
	}

	private Connection conn;
	private String driver;
	private String url;
	private String id;
	private String pwd;

	// DB 연결
	public Connection getConnection() {

		Properties properties = new Properties();
		String resource = getClass().getResource("/db.properties").getPath();

		try {
			properties.load(new FileReader(resource));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			id = properties.getProperty("id");
			pwd = properties.getProperty("pwd");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException | SQLException e2) {
			e2.printStackTrace();
		}
		return conn;
	}

}
