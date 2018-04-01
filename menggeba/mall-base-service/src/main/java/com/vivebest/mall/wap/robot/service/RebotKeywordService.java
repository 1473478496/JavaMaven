package com.vivebest.mall.wap.robot.service;


import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.wap.robot.entity.RobotKeyword;

public interface RebotKeywordService extends BaseService<RobotKeyword, Long>{
	
	String findKeyword(String keywords);
	
}
