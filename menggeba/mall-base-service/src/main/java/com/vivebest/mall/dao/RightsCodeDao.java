package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.RightsCode;

/**
 * RightsDao - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsCodeDao extends BaseDao<RightsCode, Long> {

	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	RightsCode findById(Long id);
	
	/**
	 * 权益码列表
	 * @param brand
	 * @return
	 */
	Page<RightsCode> findList(RightsCode rightsCode,Pageable pageable);
	
	
	List<RightsCode> asignCode(Long rightsId,Integer num);
	
	
	/**
	 * 根据权益订单获取权益码
	 */
	List<RightsCode>findByRigthsOrderId(Long rightsOrderId);
	
	RightsCode findByCode(String code);
	
}
