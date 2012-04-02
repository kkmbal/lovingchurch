package lc.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.ast.Call;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServiceConnector {
	private static final ServiceConnector serviceConnector = new ServiceConnector();
	private GsonBuilder gsonBuilder = null;
	//private String urlJson = "http://"+""+"/";
	protected Log logger = LogFactory.getLog(getClass());
	
	private ServiceConnector() {
		gsonBuilder = new GsonBuilder().registerTypeAdapter(Object.class, new ReciInfoObjectAdapter()).serializeNulls();
	}
	
	public static ServiceConnector getInstance() {
		return ServiceConnector.serviceConnector;
	}
//	
//	public <T> T callService(ServiceCallInfo callInfo, Type type) throws Exception {
//		BufferedWriter outputStream = null;
//		BufferedReader inputStream = null;
//
//		try {
//			logger.info(">>>>>>>> url==>"+callInfo.getUrl());
//			logger.info(">>>>>>>> method==>"+callInfo.getMethod());
//			logger.info(">>>>>>>> param==>"+callInfo.getParam());
//			//System.out.println(">>>>>>>> param==>"+callInfo.getParam());
//			
//			URL url = null;
//			if (StringUtils.isEmpty(callInfo.getParam())){
//				url = new URL(callInfo.getUrl());
//			}else if( "GET".equals(callInfo.getMethod()) || "DELETE".equals(callInfo.getMethod()) ){
//					url = new URL(callInfo.getUrl()+"?"+callInfo.getParam());
//			}else{
//				url = new URL(callInfo.getUrl());
//			}
//			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			conn.setReadTimeout(ServiceCallManager.BILL_TMEOUT);
//			conn.setConnectTimeout(ServiceCallManager.BILL_TMEOUT);
//			conn.setRequestMethod(callInfo.getMethod());
//			
//			if( !"GET".equals(callInfo.getMethod()) && !"DELETE".equals(callInfo.getMethod()) ){
//				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			}
//			//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			
//			//conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
//			//conn.setRequestProperty("Content-Type", "application/octet-stream; charset=UTF-8");
//
//			
//			if (!StringUtils.isEmpty(callInfo.getParam()) && !"GET".equals(callInfo.getMethod()) && !"DELETE".equals(callInfo.getMethod())) {
//				outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
//				//Gson gson = gsonBuilder.create();
//				//outputStream.write(gson.toJson(param));
//				outputStream.write(callInfo.getParam());
//				outputStream.flush();
//				logger.info(">>>>>>>> parma send" );
//			}
//			logger.info("<<<<<<<<<< receive" );
//			
//			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//				logger.info(">>>>>>>>>>>>>"+conn.getResponseCode() + " : " + conn.getResponseMessage());
//				throw new Exception(conn.getResponseCode() + " : " + URLDecoder.decode(conn.getResponseMessage(),"UTF-8"));
//				
//			} else {
//				inputStream = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8 * 1024);
//				String line = null;
//				StringBuffer outputMessage = new StringBuffer();
//				
//				while ((line = inputStream.readLine()) != null) {
//					outputMessage.append(line);
//				}
//				
//				logger.info("*****************************************");
//				logger.info("<<<<<<<<<< ("+callInfo.getUrl()+")("+callInfo.getMethod()+")("+callInfo.getParam()+"): "+outputMessage.toString());
//				logger.info("*****************************************");
//				//System.out.println("*****************************************");
//				//System.out.println("<<<<<<<<<< : "+outputMessage.toString());
//				//System.out.println("*****************************************");
//				
//				if (type != null) {
//					Gson gson = gsonBuilder.create();
//					return (T)gson.fromJson(outputMessage.toString(), type);
//				}
//			}
//			return null;
//		} catch (SocketTimeoutException e){
//			e.printStackTrace();
//			throw e;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
//		} finally {
//			if (outputStream != null) {
//				try { outputStream.close(); } catch (Exception e) { e.printStackTrace(); }
//			}
//			if (inputStream != null) {
//				try { inputStream.close(); } catch (Exception e) { e.printStackTrace(); }
//			}
//		}
//	}
	
	public static void main(String[] args){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "a");
		map.put("2", "b");
		
		GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
		Gson gson = gsonBuilder.create();
		System.out.println(gson.toJson(map));
		
		String a = 
			 "{"
			+ "\"result\":{"
			+ "\"code\":0,"
			+ "\"message\":\"Success\","
			+ "\"retdata\":["
			+ "{"
			+ "\"LOGIN_ID\":\"dreamer\","
			+ "\"CUSTOMERID\":\"90000001\","
			+ "\"CUSTOMERNM\":\"Kim, Sang Taek\""
			+ "},"
			+ "{"
			+ "\"LOGIN_ID\":\"dreamer\","
			+ "\"CUSTOMERID\":\"90000002\","
			+ "\"CUSTOMERNM\":\"Kim, Sang Hoon\""
			+ "}"
			+ "]"
			+ "}"
			+ "}"	;		
		
		Call c = gson.fromJson(a, new TypeToken<Call>(){}.getType());
//		System.out.println(c.getResult().getCode());
//		System.out.println(c.getResult().getMessage());
//		System.out.println(c.getResult().getRetdata());
	}
}
