package lc.service.download;

import java.util.List;
import java.util.Map;

import lc.common.LcException;

public interface DownloadService {
	public List<Map> getDownloadList(Map map) throws LcException;
}
