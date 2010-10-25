package lc.controller.download;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lc.common.FileDownView;
import lc.common.LcController;
import lc.service.download.DownloadService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloadController  extends LcController {
	protected Log log = LogFactory.getLog(getClass().getName());
	
	@Resource(name="DownloadService")
	public DownloadService downloadService;
	
	@RequestMapping("/downloadList.*")
    public String execute(ModelMap model) throws Exception{
    	Map map = new HashMap();
    	log.debug(model);
        
    	model.addAttribute("downloadlList", downloadService.getDownloadList(map));
        return "download/downloadList";
    }
	
	@RequestMapping("/download.*")
	public ModelAndView download(ModelMap model) throws Exception{
		Map map = new HashMap();
		log.debug(model);
		
		//ServletContextResource scr = new ServletContextResource(this.getServletContext(), "/upload");
		//String path = scr.getFile().getAbsolutePath();
		String path = "D:\\apache-tomcat-6.0.26-windows-x86\\webapps\\lc\\upload";
		model.put("filePath", path);
		model.put("fileName", "Book1.xls");
		log.debug(path);
		model.addAttribute("downloadlList", downloadService.getDownloadList(map));
		return new ModelAndView(new FileDownView(), model);
	}
	
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
//		String userId = request.getParameter("id");
//
//		if ((userId != null) && !userId.equals("")) {
//			return userManager.getUser(userId);
//		} else {
//			return new User();
//		}
		return new Object();
	}
}
