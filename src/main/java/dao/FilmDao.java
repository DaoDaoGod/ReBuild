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
import java.util.ArrayList;
import java.util.List;

import service.Pmap;
import model.Film;

public class FilmDao extends ShareDao {
	
    public List<Film> getFilmByCon(String sql)
    {
    	return getResultList(sql,Film.class);
    }
    
 }
