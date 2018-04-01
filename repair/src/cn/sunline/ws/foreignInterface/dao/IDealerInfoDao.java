package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.ws.foreignInterface.entity.DealearInfo;

public interface IDealerInfoDao {
	/**
	 * @描述:操作车商信息使用
	 * @参数： @return   
	 * @返回类型： List<DealerInfo>  
	 * @作者： 李怡
	 * @日期： 2016年9月22日
	 */
	
	public void addDealerInfo(DealerInfoEntity dealerInfo);


	public boolean updateDelerInfo(DealerInfoEntity dealerInfo);
	
	public List getDealerInfoEntityList(String hql, Object[] params) ;
	
	public DealerInfoEntity getDealerInfoEntity(String hql, Object[] params) ;

	public List<DealerInfoEntity>  getDealerInfoList(String hql,Object[] param);
	
	public List<DealearInfo> getDelist(String hql, Object[] parameter);

	public void addDeInfo(DealearInfo deInfo);
	
	public DealearInfo getDealerInfo(String hql, Object[] params) ;

	public void addAll(List<DealearInfo> deInfoList);
	
	public boolean updateDelerInfo(  DealearInfo dealearInfo);
	
}
