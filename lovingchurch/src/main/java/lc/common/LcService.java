package lc.common;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public abstract class LcService{
	//protected SqlMapClient sqlMap = SqlMapConfig.getInstance();
	protected final Log log = LogFactory.getLog(getClass());
	@Resource(name="lcDAO")
	protected LcDAO lcDAO;
	
}
