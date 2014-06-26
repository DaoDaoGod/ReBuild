package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Film;

public class FilmDao {
	private String sql;
	private Connection conn;
    
	public FilmDao()
	{
		initConnection();
	}
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

	public void initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myfilm", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Film>  getFilmByCondition(String sql)
	{
		
		if(conn!=null&&sql!="")
		{
			try{
				
			Statement stmt=conn.createStatement();
		     //	PreparedStatement pstmt=conn.prepareStatement();
		    ResultSet rs = stmt.executeQuery(sql) ;
		    if(!rs.wasNull())
		    {
		    	List<Film> films=new ArrayList<Film>();
		    	while(rs.next())
		    	{
		    		Film newFilm=new Film();
		    		newFilm.setName(rs.getString("name"));
		    		newFilm.setFilmId(Integer.parseInt(rs.getString("film_id")));
		    		newFilm.setDescription("description");
		    		newFilm.setDouBanUrl("filmurl");
		    		films.add(newFilm);
		    	    //System.out.println(rs.getString("name"));
		    	  
		    	}
		    	stmt.close();
		    	rs.close();
		    	closeConnection();
		    	return films;
		    }
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		closeConnection();
		return null;
	}
	public void closeConnection()
	{
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
