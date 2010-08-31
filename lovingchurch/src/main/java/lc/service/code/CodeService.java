package lc.service.code;

import java.util.List;
import java.util.Map;

import lc.common.LcException;

public interface CodeService{
	public List<Map> getCodeList(Map map) throws LcException;
}
