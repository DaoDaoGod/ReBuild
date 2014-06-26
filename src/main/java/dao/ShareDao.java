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

public class ShareDao {
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
	
	public ShareDao()
	{
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
    public <T> List<T> getResultList(String sql, Class<T> type){
		
	    try {
	    	List<T> list=new ArrayList<T>();
	    	Statement stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql) ;
			if(!rs.wasNull())
		    {
			   while(rs.next())
			   {
				   T tempobj= (T)type.newInstance();
				   Method []methods=tempobj.getClass().getMethods();
				   for(Method m:methods)
				   {
					   Annotation [] ans= m.getAnnotations();
					   if(ans.length>0)
					   {
						 Annotation Tag=ans[0];
					   if(Tag instanceof Pmap) 
					   {
						 String tableRowName=((Pmap)Tag).TableName();
						 String rowType=((Pmap)Tag).TypeName();
						 if(rowType.equals("String"))
						 {
							 String parm=rs.getString(tableRowName);
							 m.invoke(tempobj,parm);
						 }
						 else if(rowType.equals("Integer")) 
						 {
							 Integer parm=Integer.parseInt(rs.getString(tableRowName));
							 m.invoke(tempobj,parm);
						 }
					   }
					  
					   }
					   
				   }
				   list.add(tempobj);
				   
			   }
		    }
			System.out.println("size = "+list.size());
			stmt.close();
	    	rs.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
