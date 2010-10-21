package lc.controller.donation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lc.common.LcController;
import lc.service.code.CodeService;
import lc.service.donation.DonationService;
import lc.service.member.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("restriction")
@Controller
public class DonationController extends LcController {
	@Resource(name="MemberService")
	public MemberService memberService;
	@Resource(name="DonationService")
	public DonationService donationService;
	@Resource(name="CodeService")
	public CodeService codeService;
	
    @SuppressWarnings("unchecked")
	@RequestMapping("/donationList.*")
    public String execute(ModelMap model) throws Exception{
    	Map map = new HashMap();
    	log.debug(model);
        
    	model.addAttribute("userList", memberService.getUsers(map));
        model.addAttribute("donationList", donationService.getDonationList(map));
        model.addAttribute("codeList", codeService.getCodeList(map));
        return "donation/donationList";
    }
}
