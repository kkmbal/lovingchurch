package lc.controller.download;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcController;
import lc.service.download.DownloadService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadController  extends LcController {
	@Resource(name="DownloadService")
	public DownloadService downloadService;
	
	@RequestMapping("/downloadList.*")
    public String execute(ModelMap model) throws Exception{
    	Map map = new HashMap();
    	log.debug(model);
        
    	model.addAttribute("downloadlList", downloadService.getDownloadList(map));
        return "download/downloadList";
    }
}
