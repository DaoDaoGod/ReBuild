package service;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;

public class HandleRegister{

	public String register(@Pname(name="username") String name,@Pname(name="password")String password,HttpServletRequest request,HttpServletResponse response) {
	// TODO Auto-generated method stub
	try {
		
		if (name.compareTo("") != 0 && password.compareTo("") != 0) {

			UserDao userdao = new UserDao();
			Boolean state = userdao.save(name, password);
			if (state) {
				request.setAttribute("my-data2","Successful");
			} else {
				request.setAttribute("my-data2","Failed");
			}
		} else {
			    request.setAttribute("my-data2","User info error");
		}

	} catch (Exception e) {
		// TODO: handle exception
	}
	return "/logininfo.jsp";

}
	 

}
