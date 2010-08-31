package lc.controller.donation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcController;
import lc.service.donation.DonationService;
import lc.service.member.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DonationController extends LcController {
	@Resource(name="MemberService")
	public MemberService memberService;
	@Resource(name="DonationService")
	public DonationService donationService;
	
    @RequestMapping("/donationList.*")
    public String execute(ModelMap model) throws Exception{
    	Map map = new HashMap();
    	log.debug(memberService);
    	log.debug(model);
    	List list = memberService.getUsers(map);
        
        model.addAttribute("userList", list);
        return "member/memberList";
    }
}
