package lc.util;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapConfig {
	private static SqlMapClient sqlMapper;
	
	static {
		try {
			Reader reader = Resources.getResourceAsReader("sql-map-config.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			// Fail fast.
			throw new RuntimeException(
					"Something bad happened while building the SqlMapClient instance."
							+ e, e);
		}
	}
	
	public static SqlMapClient getInstance () {
		return sqlMapper;
	}
}
