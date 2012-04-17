package lc.common.web.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.common.exception.LcException;
import lc.common.util.BeanUtil;
import lc.common.util.ByteUtil;
import lc.common.util.DateConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonController<T> extends HttpServlet {

	private static final long serialVersionUID = -4046879528473666L;
	GsonBuilder gsonBuilder = null;
	Map<String,Method> cachedMethod = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cachedMethod = Collections.synchronizedMap(new HashMap<String,Method>());
		gsonBuilder = new GsonBuilder()
							.registerTypeAdapter(Date.class, new DateConverter())
							.serializeNulls();
	}

	/**
	 * get방식 요청에 의해 수행되는 메소드 --> post방식으로 전환
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			response.setContentType("application/json; charset=UTF-8");
			String[] requestUris = request.getRequestURI().substring(
					request.getRequestURI().lastIndexOf('/')+1).split("\\.");
			if (requestUris.length != 3 ) {
				response.sendError(500, "요청한 URL이 올바르지 않습니다. - " + requestUris.toString());
				return;
			}

			Object service = BeanUtil.getServiceBean(requestUris[0]);
			if (service == null) {
				response.sendError(500, requestUris[0]
						+ "가 존재하지 않습니다.");
				return;
			}

			Method method = findMethod(service.getClass(), requestUris[1]);
			if (method == null) {
				response.sendError(500, requestUris[1] + " 메소드가 존재하지 않습니다.");
				return;
			}

			System.out.println("**********Service Name:" + service.toString());
			System.out.println("**********Method Name:" + method.getName());
			
			Class<?>[] paramTypes = method.getParameterTypes();
			Gson gson = gsonBuilder.create();
			Object[] paramValues = null;
			if (paramTypes != null && paramTypes.length > 0) {
				byte[] jsonBytes = ByteUtil.readBytes(request.getInputStream());
				String jsonString = new String(jsonBytes, "UTF-8");
				System.out.println("in**********Json String:" + jsonString);
				paramValues = new Object[paramTypes.length]; 
				int i = 0;
				for (Class<?> paramType : paramTypes) {
				    if(paramType == HashMap.class){
				        Type type = (new TypeToken<Map<String, String>>(){}).getType();
				        paramValues[i] = gson.fromJson(jsonString, type);
				    }else{
				        paramValues[i] = gson.fromJson(jsonString, paramType);
				    }
					i++;
				}
			}
			Object returnObject = method.invoke(service, paramValues);

			if (returnObject != null) {
				String jsonString = gson.toJson(returnObject);
				System.out.println("out**********Json String:" + jsonString);
				response.getWriter().write(jsonString);
				response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (e.getCause() != null
						&& e.getCause() instanceof LcException) {
					response.sendError(500, ((LcException) e
							.getCause()).getMessage());
				} else {
					response.sendError(500,
							"서버 프로그램 실행중 에러발생 : " + e.getCause().getMessage());
				}
			} catch (Exception ex) {
				return;
			}
		}

	}

	@SuppressWarnings("unchecked")
	private Method findMethod(Class type, String methodName) {
		Method rtnMethod = null;
		String cacheKey = type.getName() + "." + methodName;
		if(cachedMethod.containsKey(cacheKey)) {
			rtnMethod = cachedMethod.get(cacheKey);
		} else {
			Method[] methods = type.getMethods();
			for(Method method : methods) {
				cachedMethod.put(type.getName() + "." + method.getName(), method);
			}
			if(cachedMethod.containsKey(cacheKey)) {
				rtnMethod = cachedMethod.get(cacheKey);
			}
		}
		return rtnMethod;
	}

}
