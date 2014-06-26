package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDao;
import model.Film;

public class HandleSearch {

	
	public String search(@Pname(name="searchText")String searchText,HttpServletRequest request,HttpServletResponse response)
	{
		
		try {
			if (searchText.compareTo("") != 0) {
				String sql = "select * from film where name like '%"
						+ searchText + "%'";
				FilmDao filmDao = new FilmDao();
				List<Film> films = new ArrayList<Film>();
				Film newfilm=new Film();
				//films = filmDao.getFilmByCondition(sql);
				films=filmDao.getResultList(sql,Film.class);
				if(films!=null)
				{
				List<String> strs=new ArrayList<String>();
				for (int i = 0; i < films.size(); i++) {
					strs.add(films.get(i).getName());
					System.out.println(films.get(i).getName());
				}
				  request.setAttribute("my-data1",strs);
				}
			} else {
	
			      request.setAttribute("my-data2","search is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/my.jsp";
	}

}
