package com.vivebest.mall.wap.robot.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.wap.robot.dao.RebotMessageDao;
import com.vivebest.mall.wap.robot.entity.RebotMessage;
import com.vivebest.mall.wap.robot.entity.RobotKeyword;
import com.vivebest.mall.wap.robot.service.RebotMessageService;

@Service("rebotMessageServiceImpl")
public class RebotMessageServiceImpl extends BaseServiceImpl<RebotMessage, Long> implements RebotMessageService{
	
	@Resource(name = "rebotMessageDaoImpl")
	private RebotMessageDao rebotMessageDao;
	
	@Resource(name = "rebotMessageDaoImpl")
	public void setBaseDao(RebotMessageDao rebotMessageDao) {
		super.setBaseDao(rebotMessageDao);
	}
	
	public void addMessage(){
		RebotMessage rm = new RebotMessage();
		rm.setName("售后服务");
		rm.setContent("亲爱的客人，您好！感谢您购买NICI品牌产品，我是您的专属售后顾问小妮！很高兴为您服务！请将您的问题详细描述并发送给我，并留下您在购买我们的产品的时候的联系电话，我们将在24小时之内联系您，为您解决问题！");
		rm.setState(RebotMessage.MessageState.valid);
		RobotKeyword rk = new RobotKeyword();
		rk.setName("客服");
		rk.setState(RobotKeyword.KeywordState.valid);
		rk.setRebotMessage(rm);
		Set<RobotKeyword> keywords = new HashSet<RobotKeyword>();
		keywords.add(rk);
		rm.setKeyword(keywords);
		rebotMessageDao.persist(rm);
		
	}
	
}
