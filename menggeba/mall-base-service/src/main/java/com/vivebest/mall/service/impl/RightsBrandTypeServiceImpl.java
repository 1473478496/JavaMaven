package com.vivebest.mall.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsBrandTypeDao;
import com.vivebest.mall.entity.RightsBrandType;
import com.vivebest.mall.service.RightsBrandTypeService;

/**
 * RightsBrandServiceImpl - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsBrandTypeServiceImpl")
public class RightsBrandTypeServiceImpl extends BaseServiceImpl<RightsBrandType, Long> implements RightsBrandTypeService {

	
	@Resource(name = "rightsBrandTypeDaoImpl")
	public void setBaseDao(RightsBrandTypeDao rightsBrandTypeDao) {
		super.setBaseDao(rightsBrandTypeDao);
	}
	
	@Resource(name="rightsBrandTypeDaoImpl")
	private RightsBrandTypeDao rightsBrandTypeDao;

	@Override
	public List<RightsBrandType> findAllType() {
		return rightsBrandTypeDao.findAll();
	}

}
