package com.vivebest.mall.wap.robot.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.wap.robot.entity.RebotMessage;

public interface RebotMessageService extends BaseService<RebotMessage, Long>{
	
	void addMessage();
	
}
