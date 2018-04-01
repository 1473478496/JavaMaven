package cn.sunline.ws.foreignInterface.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.ws.foreignInterface.dao.RepairServiceUserDao;
import cn.sunline.ws.foreignInterface.entity.RepairServiceUser;


@SuppressWarnings("unchecked")
@Repository
@Service("repairServiceUserDaoImpl")
public class RepairServiceUserDaoImpl extends GenericBaseCommonDao implements RepairServiceUserDao {

	public RepairServiceUser getRepairServiceUser(String user) {
		return (RepairServiceUser)get((new RepairServiceUser().getClass()) , user);
	}

}
