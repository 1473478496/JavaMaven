package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.ws.foreignInterface.dao.IDealerInfoDao;
import cn.sunline.ws.foreignInterface.entity.DealearInfo;

@SuppressWarnings("unchecked")
@Repository
public class DealerInfoDaoImpl extends GenericBaseCommonDao implements IDealerInfoDao {

	@Override
	public void addDealerInfo(DealerInfoEntity DealerInfoEntity) {
		save(DealerInfoEntity);
	}
	@Override
	public boolean updateDelerInfo(DealerInfoEntity DealerInfoEntity) {
		try {
			updateEntitie(DealerInfoEntity);
			return true ;
		} catch (Exception e) {
			return false ;
		}
	}
	
	@Override
	public List getDealerInfoEntityList(String hql, Object[] params) {
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
			return list ; 			
		}
		return null ;
	}

	public DealerInfoEntity getDealerInfoEntity(String hql, Object[] params){
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
			return (DealerInfoEntity)list.get(0) ; 			
		}
		return new DealerInfoEntity() ;
	}
	
	public List<DealerInfoEntity> getDealerInfoList(String hql, Object[] params) {
		 return executeQuery(hql, params);
	}
	
	
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
