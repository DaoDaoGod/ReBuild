package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDao {
	private String sql;
	private Connection conn;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public UserDao() {
		initConnection();
	}

	public void initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myfilm", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean save(String email, String password) {

		try {
			String sql = "select * from user where email=" + email;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("Exist such email");
				stmt.close();
				rs.close();
				closeConnection();
				return false;
			} else {
				System.out.println("Not exist such email");
				String insql = "insert into user(email,password) values("
						+ email + "," + password + ")";
				int num = stmt.executeUpdate(insql);
				if (num > 0) {
					stmt.close();
					rs.close();
					closeConnection();
					return true;
				} else {
					stmt.close();
					rs.close();
					closeConnection();

					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public User getUserByEmail(String email) {
		try {
			String sql = "select * from user where email=" + email;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				User user = new User();
				user.setUserId(Integer.parseInt(rs.getString("user_id")));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				stmt.close();
				rs.close();
				closeConnection();
				return user;
			} else {
				stmt.close();
				rs.close();
				closeConnection();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
