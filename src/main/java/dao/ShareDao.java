package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import service.Pmap;

public class ShareDao implements DaoInf {
	private static Logger logger = Logger.getLogger(ShareDao.class);  
	
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

	private Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myfilm", "root", "1234");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
    public <T> List<T> getResultList(String sql, Class<T> type,Object parms[],int types[]){
		
	    try {
	    	PreparedStatement ps=conn.prepareStatement(sql);
	    	logger.debug(sql);
	    	for(int i=0;i<types.length;i++)
	    	{
	    		if(types[i]==Types.VARCHAR)
	    		{
	    			ps.setString(i+1, (String)parms[i]);
	    			logger.debug((String)parms[i]);
	    		}
	    		else if(types[i]==Types.INTEGER)
	    		{
					ps.setInt(i+1,(Integer)parms[i]);
				}
	    		else if(types[i]==Types.DOUBLE)
	    		{
	    			 ps.setDouble(i+1,(Double)parms[i]);
	    			 
	    		}
	    		else {
					 ps.setNull(i+1,Types.NULL);
				}
	    	}
	    	
	    	
	    	List<T> list=new ArrayList<T>();
	    	//Statement stmt=conn.createStatement();
			ResultSet rs = ps.executeQuery() ;
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
	
			logger.debug("size = "+list.size());
			ps.close();
	    	rs.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
    public int Save(String sql,Object []parms,int []types)
    {
    	
    	try {
    		PreparedStatement ps=conn.prepareStatement(sql);
	    	for(int i=0;i<parms.length;i++)
	    	{
	    		if(types[i]==Types.VARCHAR)
	    		{
	    			ps.setString(i+1, (String)parms[i]);
	    		}
	    		else if(types[i]==Types.INTEGER)
	    		{
					ps.setInt(i+1,(Integer)parms[i]);
				}
	    		else if(types[i]==Types.DOUBLE)
	    		{
	    			 ps.setDouble(i+1,(Double)parms[i]);
	    			 
	    		}
	    		else {
					 ps.setNull(i+1,Types.NULL);
				}
	    	}
    		int num = ps.executeUpdate();
    	    ps.close();
    		if(num>0)return 0;
    		else return -1;
		  } catch (Exception e) {
			return 0;
		}
    	
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
