package lc.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import lc.member.MemberController;

import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;

@RunWith(JMock.class)
public class MemberControllerTest {
	MemberController c = new MemberController();
	MockHttpServletRequest request = null;
	
	@Test
    public void testGetUsers() throws Exception {
		//request = new MockHttpServletRequest("POST", "/userform.html");
		ModelMap map = new ModelMap();
        String result = c.execute(map);
        System.out.println(result);
        assertFalse(map.isEmpty());
        assertNotNull(map.get("userList"));
        assertEquals("userList", result);
    }
}
