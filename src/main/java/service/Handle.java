package service;

import java.awt.peer.SystemTrayPeer;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.InvokeHandler;

import Proxy.MyInvokationHandler;

/**
 * Servlet implementation class Handle
 */
public class Handle extends HttpServlet {
	private static Logger logger = Logger.getLogger(Handle.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	  Enumeration enums=request.getHeaderNames();
	  while(enums.hasMoreElements())
	  {
		  logger.debug(enums.nextElement());
		  logger.debug(request.getHeader(enums.nextElement().toString()));
	  }
		
		String path = request.getRequestURI().substring(
				request.getContextPath().length());
		logger.debug(path);
		String classname = path.split("/")[1];
		String methodname = path.split("/")[2];
		methodname = methodname.substring(0, methodname.length() - 3);
		classname = "service." + classname;
		Object action = null;
		try {
			action = Class.forName(classname).newInstance();
			Field[] fields = action.getClass().getDeclaredFields();
			for (Field f : fields) {
				logger.debug(f);
				Annotation[] ans = f.getDeclaredAnnotations();
				logger.debug("the length of " + ans.length);
				if (ans.length > 0 && ans[0] instanceof Pdao) {
					String typename = ((Pdao) ans[0]).Dao();
					String methodna = ((Pdao) ans[0]).Method();
					logger.debug("Method: " + methodna);
					Method m = action.getClass().getMethod(methodna,
							Class.forName(typename));
					m.invoke(action, Class.forName(typename).newInstance());
				}
			}
			// for(Annotation ty:anss) logger.debug(anss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method m = null;
		Method methods[] = new Method[10];
		try {
			methods = action.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				logger.debug(methods[i].getName());
				if (methods[i].getName().compareTo(methodname) == 0) {
					m = methods[i];
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsp = null;
		try {
			Class<?> parms[] = new Class<?>[10];
			parms = m.getParameterTypes();
			Object argument[] = new Object[parms.length];
			Annotation[][] array = m.getParameterAnnotations();
			for (int i = 0; i < parms.length - 2; i++) {
				String parmName = ((Pname) array[i][0]).name();
				argument[i] = request.getParameter(parmName);
			}
			argument[parms.length - 2] = request;
			argument[parms.length - 1] = response;
			if (m != null)
				jsp = (String) m.invoke(action, argument);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsp != null)
			getServletContext().getRequestDispatcher(jsp).forward(request,
					response);
	}

}
