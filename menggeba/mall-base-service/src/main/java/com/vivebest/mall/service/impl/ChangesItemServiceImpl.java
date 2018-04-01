package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ChangesItemDao;
import com.vivebest.mall.entity.ChangesItem;
import com.vivebest.mall.service.ChangesItemService;

@Service("changesItemServiceImpl")
public class ChangesItemServiceImpl extends BaseServiceImpl<ChangesItem, Long> implements ChangesItemService{
	
	@Resource(name = "changesItemDaoImpl")
	public void setBaseDao(ChangesItemDao changeItemDao) {
		super.setBaseDao(changeItemDao);
	}
}
