package lc.member;

import java.util.List;

import lc.util.SqlMapConfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.sqlmap.client.SqlMapClient;

@Controller
public class MemberController {
    @RequestMapping("/users.*")
    public String execute(ModelMap model) throws Exception{
        SqlMapClient sqlMap = SqlMapConfig.getInstance();
        List list = sqlMap.queryForList("selectUsers");
        model.addAttribute("userList", list);
        return "userList";
    }
}
