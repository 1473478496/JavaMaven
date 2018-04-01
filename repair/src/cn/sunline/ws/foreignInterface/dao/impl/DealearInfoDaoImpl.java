package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.ws.foreignInterface.dao.DealearInfoDao;
import cn.sunline.ws.foreignInterface.entity.DealearInfo;


@SuppressWarnings("unchecked")
@Repository
@Service("dealearInfoDaoImpl")
public class DealearInfoDaoImpl extends GenericBaseCommonDao implements DealearInfoDao  {

	@Override
	public List<DealearInfo> getDelist(String hql, Object[] parameter) {
		return executeQuery(hql, parameter);
	}

	@Override
	public void addDeInfo(DealearInfo deInfo) {
		saveOrUpdate(deInfo);

	}
	
	public DealearInfo getDealerInfo(String hql, Object[] params){
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
			this.getSession().evict((DealearInfo)list.get(0));
			return (DealearInfo)list.get(0);			
		}
		return null;
	}

	@Override
	public void addAll(List<DealearInfo> deInfoList) {
		batchSave(deInfoList);
	}
	public boolean updateDelerInfo(DealearInfo dealearInfo){
		try {
			updateEntitie(dealearInfo);
			return true ;
		} catch (Exception e) {
			return false ;
		}
	}
	
}
