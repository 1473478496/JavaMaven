package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.ws.foreignInterface.entity.DealearInfo;


public interface DealearInfoDao {

	public List<DealearInfo> getDelist(String hql, Object[] parameter);

	public void addDeInfo(DealearInfo deInfo);
	public DealearInfo getDealerInfo(String hql, Object[] params) ;

	public void addAll(List<DealearInfo> deInfoList);
	
	public boolean updateDelerInfo(  DealearInfo dealearInfo);


}
