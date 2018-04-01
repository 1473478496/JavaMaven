package com.vivebest.mall.wap.robot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Filter.Operator;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.wap.robot.dao.RebotKeywordDao;
import com.vivebest.mall.wap.robot.entity.RobotKeyword;
import com.vivebest.mall.wap.robot.service.RebotKeywordService;

@Service("rebotKeywordServiceImpl")
public class RebotKeywordServiceImpl extends BaseServiceImpl<RobotKeyword, Long> implements RebotKeywordService{
	
	@Resource(name = "robotKeywordDaoImpl")
	private RebotKeywordDao rebotKeywordDao;
	
	@Resource(name = "robotKeywordDaoImpl")
	public void setBaseDao(RebotKeywordDao robotKeywordDao) {
		super.setBaseDao(robotKeywordDao);
	}

	public String findKeyword(String keywords){
		
		List<Filter> filters = new ArrayList<Filter>();
		Filter keyFilter = new Filter();
		keyFilter.setOperator(Operator.eq);
		keyFilter.setProperty("name");
		keyFilter.setValue(keywords);
		Filter stateFilter = new Filter();
		stateFilter.setOperator(Operator.eq);
		stateFilter.setProperty("state");
		stateFilter.setProperty("0");
		filters.add(keyFilter);
		
		List<RobotKeyword> list = rebotKeywordDao.findList(0, 1, filters, null);
		if(list.size()>0){
			RobotKeyword rk = list.get(0);
			return rk.getRebotMessage().getContent();
		}else{
			return null;
		}
		
	}
	
}
