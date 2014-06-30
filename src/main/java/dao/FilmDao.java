package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Proxy.MyProxyFactory;
import service.Pmap;
import model.Film;

public class FilmDao{
	private DaoInf dInf;
	public FilmDao()
	{
		DaoInf target=new ShareDao();
		dInf=(DaoInf)MyProxyFactory.getProxy(target);
	}
    public List<Film> getFilmByCon(String searchText)
    {
    	String sql = "SELECT * FROM film f WHERE f.name LIKE ?";
    	Object[] objects=new Object[1];
    	objects[0]="%" + searchText + "%";
    	return dInf.getResultList(sql,Film.class,objects,new int[]{Types.VARCHAR});
    }
    
    
 }
