package lc.common;

import java.io.File;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

public class LcConstants {
	public enum SITE { front, customer, management };
	public enum LOC { main, list_top };
	public static final String BOARD_FILE_PATH = "/home/board_file";
	public static final String SUPER_AUTH_CD = "SP";
	
	//public static final String EXCEL_FILE_PATH = "D:/";
	
	public static String EXCEL_FILE_PATH() throws Exception{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContextResource scr = new ServletContextResource(webApplicationContext.getServletContext(), "/doc");
		String path = scr.getFile().getAbsolutePath()+File.separator;
		//System.out.println("path==>"+path);
		return path;
		
	}
}
