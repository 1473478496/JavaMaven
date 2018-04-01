package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.impl.BusniessCooperateDaoImpl;
import com.vivebest.mall.entity.BusniessCooperate;
import com.vivebest.mall.service.BusniessCooperateService;

@Service("busniessCooperateServiceImpl")
public class BusniessCooperateServiceImpl  extends BaseServiceImpl<BusniessCooperate, Long> implements BusniessCooperateService {

	
	@Resource(name = "busniessCooperateDaoImpl")
	public void setBaseDao(BusniessCooperateDaoImpl bsDao) {
		super.setBaseDao(bsDao);
	}


}
