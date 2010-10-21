package lc.service.donation;

import java.util.List;
import java.util.Map;

import lc.common.LcException;

public interface DonationService{
	public List<Map> getDonationList(Map map) throws LcException;
}
