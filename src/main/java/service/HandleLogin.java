package service;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.UserDao;

public class HandleLogin {
	public String login(@Pname(name = "loginname") String name,
			@Pname(name = "loginpassword") String password,HttpServletRequest request,HttpServletResponse response) {
		try {
			FileOutputStream out = new FileOutputStream("/logininfo.jsp");
			PrintStream p = new PrintStream(out);
			if (name.compareTo("") != 0 && password.compareTo("") != 0) {
				name = "\"" + name + "\"";
				UserDao userdao = new UserDao();
				User newuser = userdao.getUserByEmail(name);
				if (newuser.getPassword().compareTo(password) == 0) {
					request.setAttribute("my-data2","Successful");
				} else {
					request.setAttribute("my-data2","Failed");
				}
			    } else {
			    	request.setAttribute("my-data2","User info error");

			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/logininfo.jsp";
	}

}
