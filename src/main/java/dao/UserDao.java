package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import service.Pmap;
import model.User;

public class UserDao extends ShareDao {
	
	public UserDao() {
		initConnection();
	}

	public boolean save(String email, String password) {

		try {
			String sql = "select * from user where email=" + email;
			if(getResultList(sql,User.class).size()==0)
			{
				System.out.println("Not exist such email");
				String insql = "insert into user(email,password) values("
						+ email + "," + password + ")";
				Statement stmt = this.getConn().createStatement();
				int num = stmt.executeUpdate(insql);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
   
	public User getUserByEmail(String email) {
		
			String sql = "select * from user where email=" + email;
		    List<User> list=getResultList(sql,User.class);
		    if(list==null||list.size()==0)
		    	return null;
		    else {
				return list.get(0);
			}

		
	}


}
