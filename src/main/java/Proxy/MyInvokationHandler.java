package Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import dao.ShareDao;

public class MyInvokationHandler implements InvocationHandler {

	private static Logger logger = Logger.getLogger(MyInvokationHandler.class);
	private Object target;

	public void setTarget(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		logger.debug("Invoke "+method.getName()+"\n");
		Type[] paramTypeList = method.getGenericParameterTypes();
		for (Type parmType : paramTypeList) {
			logger.debug(" " + parmType);
			if (parmType instanceof ParameterizedType) {
				Type[] types = ((ParameterizedType) parmType)
						.getActualTypeArguments();
				logger.debug("TypeArguments: ");

				for (Type type : types) {
					logger.debug(" " + type);
				}
			}
		}

		logger.debug("returnType");
		Type returnType = method.getGenericReturnType();
		logger.debug(" " + returnType);
		if (returnType instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) returnType)
					.getActualTypeArguments();
			logger.debug("TypeArgument");
			for (Type type : types) {
				logger.debug(" " + type);
			}
		}
		method.invoke(target,args);
		logger.debug("Invoke Finsh");
		return null;
	}

}
