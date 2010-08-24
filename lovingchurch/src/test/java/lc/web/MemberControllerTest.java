package lc.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lc.controller.member.MemberController;
import lc.service.member.MemberService;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

//@RunWith(JMock.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext*.xml" })
public class MemberControllerTest {
	@Autowired
	MemberController c;
	//Mockery context = new JUnit4Mockery();
	//@Resource(name="MemberService")
	//MemberService memberService;
	//MockHttpServletRequest request = null;
	
//    @Before
//    public void setUp() {
//    	//memberService = context.mock(MemberService.class);
//        //c.memberService = memberService;
//    }
    
	@Test
    public void testGetUsers() throws Exception {
		//request = new MockHttpServletRequest("POST", "/userform.html");
		//System.out.println(c);
		//System.out.println(memberService);
		
		final Map map1 = new HashMap();
		final List list1 = null;
		
//		context.checking(new Expectations() {{
//            one(memberService).getUsers(map1);
//            will(returnValue(list1));
//        }});
		
		ModelMap map = new ModelMap();
        String result = c.execute(map);
        //System.out.println(result);
        assertFalse(map.isEmpty());
        //System.out.println(map.get("userList"));
        assertNotNull(map.get("userList"));
        assertEquals("userList", result);
    }
}
