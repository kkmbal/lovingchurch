package lc.common.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
*
* <p>
* Spring Bean을 얻어오기 위한 Util
* </p>
* @author bkseo
* @since 2010. 7. 7.
*/
public class BeanUtil {

	/**
	 * <p>
	 * Service Bean의 이름을 받아서 해당 Object를 return해 준다.
	 *  - Context를 이용해 getBean()하는 방식이다.
	 * </p>
	 * @param name Service Bean 이름
	 * @return Object 해당 Service Bean의 Object
	 * @throws Exception
	 */
	public static Object getServiceBean(String name) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		Object object = null;
		object = (Object) webApplicationContext.getBean(name);

		return object;
	}

	/**
	 * <p>
	 * Class의 이름을 받아서 해당 Object를 return해 준다.
	 *  - Reflection 방식을 사용한다.
	 * </p>
	 * @param name Class 이름
	 * @return Object 해당 Class의 Object
	 * @throws Exception
	 */
	public static Object getObjectByReflection(String name) throws Exception {
		Object object = null;
		object = Class.forName(name).newInstance();

		return object;
	}

}
