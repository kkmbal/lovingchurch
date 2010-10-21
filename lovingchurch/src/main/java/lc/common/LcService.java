package lc.common;

import lc.util.SqlMapConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public abstract class LcService{
	protected SqlMapClient sqlMap = SqlMapConfig.getInstance();
	protected final Log log = LogFactory.getLog(getClass());
	//@Resource(name="lcDAO")
	//protected LcDAO lcDAO;
	
}
