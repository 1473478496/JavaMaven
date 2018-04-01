package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightOrderItemDao;
import com.vivebest.mall.entity.RightOrderItem;
import com.vivebest.mall.service.RightOrderItemService;

/**
 * RightOrderItemServiceImpl - 权益订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsOrderItemServiceImpl")
public class RightOrderItemServiceImpl extends BaseServiceImpl<RightOrderItem, Long> implements RightOrderItemService {
	
	@Resource(name = "rightOrderItemDaoImpl")
	public void setBaseDao(RightOrderItemDao rightOrderItemDao){
		super.setBaseDao(rightOrderItemDao);
	}

}
