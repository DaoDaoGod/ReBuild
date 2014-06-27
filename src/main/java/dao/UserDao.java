package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import service.Pmap;
import model.User;

public class UserDao extends ShareDao {
	public boolean save(String email, String password) {

		try {
			String sql = "select * from user where email = ?";
			Object[] objects=new Object[2];
			objects[0]=email;
			if(getResultList(sql,User.class,objects,new int[]{Types.VARCHAR}).size()==0)
			{
				
				System.out.println("Not exist such email");
				String insql = "insert into user(email,password) values(?,?)";
				objects[0]=email;
				objects[1]=password;
				
				if(Save(insql,objects,new int[]{Types.VARCHAR,Types.VARCHAR})==0)
			    	return true;
				else {
					return false;
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
   
	public User getUserByEmail(String email) {
		
			String sql = "select * from user u where u.email = ?";
			Object[] objects=new Object[1];
			objects[0]=email;
		    List<User> list=getResultList(sql,User.class,objects,new int[]{Types.VARCHAR});
		    System.out.println(list.size());
		    if(list==null||list.size()==0)
		    	return null;
		    else {
				return list.get(0);
			}

		
	}


}
