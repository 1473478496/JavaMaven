package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.repair.entity.BrandEntity;
import cn.sunline.ws.foreignInterface.dao.IBrandEntityDao;
@SuppressWarnings("unchecked")
@Repository
@Service("brandEntityDaoImpl")
public class BrandEntityDaoImpl extends GenericBaseCommonDao implements IBrandEntityDao{

	@Override
	public List<BrandEntity> getBrandEntityList(String hql, Object[] params) {
		return executeQuery(hql, params);
	}

}
