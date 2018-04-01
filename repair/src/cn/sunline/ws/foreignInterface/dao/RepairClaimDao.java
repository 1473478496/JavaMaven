package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.ws.foreignInterface.entity.DealearInfo;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;

public interface RepairClaimDao {
	
	public List<RepairMainInfo> getRClist(String hql, Object[] parameter);

	public RepairMainInfo getRCInfo(String hql, Object[] parameters);

	public void addRCinfo(RepairMainInfo rcInfo);
	
	public boolean updateRCInfo(  RepairMainInfo repairClaimInfo);

}
