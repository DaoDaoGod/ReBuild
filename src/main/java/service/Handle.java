package service;

import java.awt.peer.SystemTrayPeer;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Handle
 */
public class Handle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getRequestURI().substring(request.getContextPath().length());
		String classname=path.split("/")[1];
		String methodname=path.split("/")[2];
		methodname=methodname.substring(0,methodname.length()-3);
	    classname="service."+classname;
		Object action = null;
		try {
			action = Class.forName(classname).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method m = null;
		Method methods[]=new Method[10];
		try {
		    methods=action.getClass().getMethods();
		    for(int i=0;i<methods.length;i++)
		    {
		    	System.out.println(methods[i].getName());
		    	if(methods[i].getName().compareTo(methodname)==0)
		    	{
		    		m=methods[i];	
		    		continue;
		    	}
		    	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsp= null;
		try {
			Class<?> parms[]=new Class<?>[10];
			parms=m.getParameterTypes();
			Object argument[]=new Object[parms.length];
			Annotation[][] array=m.getParameterAnnotations();
			for(int i=0;i<parms.length-2;i++)
			{
				 String parmName=((Pname)array[i][0]).name();
				 argument[i]=request.getParameter(parmName);
			}
			argument[parms.length-2]=request;
			argument[parms.length-1]=response;
			if(m!=null)
			jsp = (String) m.invoke(action,argument);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(jsp!=null)
		getServletContext().getRequestDispatcher(jsp).forward(request, response);
	}

}
