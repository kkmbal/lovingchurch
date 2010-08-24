package lc.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcController;
import lc.service.member.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController extends LcController {
	@Resource(name="MemberService")
	public MemberService memberService;
	
    @RequestMapping("/users.*")
    public String execute(ModelMap model) throws Exception{
    	Map map = new HashMap();
    	log.debug(memberService);
    	log.debug(model);
    	List list = memberService.getUsers(map);
        
        model.addAttribute("userList", list);
        return "userList";
    }
}
