package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.ws.foreignInterface.dao.IRepairpacketDao;
import cn.sunline.ws.foreignInterface.entity.RepairpacketInfo;


@SuppressWarnings("unchecked")
@Repository
@Service("repairpacketDao")
public class RepairpacketDaoImpl extends GenericBaseCommonDao implements IRepairpacketDao {

	@Override
	public List<RepairpacketInfo> getRPlist(String hql, Object[] parameter) {

		return executeQuery(hql, parameter);
	}

	@Override
	public void addRPinfo(RepairpacketInfo rpinfo) {

		saveOrUpdate(rpinfo);
	}

}
