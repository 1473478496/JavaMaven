package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.ws.foreignInterface.dao.IRepairMainDao;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;


@SuppressWarnings("unchecked")
@Repository
@Service
public class RepairMainDaoImpl extends GenericBaseCommonDao implements IRepairMainDao {

	@Override
	public List<RepairMainInfo> getRClist(String hql, Object[] parameter) {
		return executeQuery(hql, parameter);
	}
	
	public RepairMainInfo getRCInfo(String hql, Object[] parameters){
		List list = executeQuery(hql, parameters);
		if(list != null && list.size() > 0){
			this.getSession().evict((RepairMainInfo)list.get(0));
			return (RepairMainInfo)list.get(0);			
		}
		return new RepairMainInfo();
	}
	@Override
	public void addRCinfo(RepairMainInfo rcInfo) {
		saveOrUpdate(rcInfo);

	}
	@Override
	public boolean updateRCInfo( RepairMainInfo repairClaimInfo){
		try {
			updateEntitie(repairClaimInfo);
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}
	public <T> List<T> getListbySql(final String sql, Object[]para) {
		return super.findListbySql(sql , para); 
	}

}
