/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.constants.CreditEventEnum;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.QuestionnaireAnswerDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.QuestionTransfer;
import com.vivebest.mall.entity.QuestionnareAnswer;
import com.vivebest.mall.service.CreditService;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.QuestionTransferService;
import com.vivebest.mall.service.QuestionnareAnswerService;


/**
 * Service - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("questionnareAnswerServiceImpl")
public class QuestionnareAnswerServiceImpl extends BaseServiceImpl<QuestionnareAnswer, Long> implements QuestionnareAnswerService {
	
	private static Logger logger = Logger.getLogger(QuestionnareAnswerServiceImpl.class);

	@Resource(name = "creditServiceImpl")
	private CreditService creditService;
	
	@Resource(name = "questionnaireAnswerDaoImpl")
	private QuestionnaireAnswerDao questionnareAnswerDao;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "questionTransferServiceImpl")
	private QuestionTransferService questionTransferService;
	
	@Resource(name = "questionnaireAnswerDaoImpl")
	public void setBaseDao(QuestionnaireAnswerDao questionnaireAnswerDao) {
		super.setBaseDao(questionnaireAnswerDao);
	}
	
	@Override
	@Transactional
	public void save(QuestionnareAnswer questionnareAnswer) {
		
		//用户答案{1:a}
	//	String custAnswers = questionnareAnswer.getAnswer();
		//正确答案{1:a}
	//	String realAnswers = questionnareAnswer.getQuestionnaire().getAnswers();
		//题目分数{1:10}
		//String questionScores = questionnareAnswer.getQuestionnaire().getSocres(); 
		/*
		JSONObject custJson = JSONObject.fromObject(custAnswers);
		JSONObject realJson = JSONObject.fromObject(realAnswers);
		JSONObject scoreJson = JSONObject.fromObject(questionScores);
		
		Iterator<String> keys = custJson.keys();
		String key;
		int totalScore = 0;
		while(keys.hasNext()){
			key = keys.next();
			Integer custAnswer = (Integer) custJson.get(key);
			Integer realAnswer = (Integer) realJson.get(key);
			
			//答对分数计算
			if(custAnswer == realAnswer){
				totalScore = totalScore + (Integer)scoreJson.get(key);
			}
			
		}
		questionnareAnswer.setSocre(totalScore);
		Map<String, Object> result =creditService.engineMengTopUp(questionnareAnswer.getMember().getSn(), Long.parseLong(totalScore+""), "答题", CreditEventEnum.GameGift);
		if(result !=null){
			super.save(questionnareAnswer);
		}else{
			logger.error("[答题活动]:调用赠送积分接口失败");
		}
		*/
		 super.save(questionnareAnswer);
	}

	@Override
	@Transactional
	public QuestionnareAnswer update(QuestionnareAnswer questionnareAnswer) {
		return super.update(questionnareAnswer);
	}

	@Override
	@Transactional
	public QuestionnareAnswer update(QuestionnareAnswer questionnareAnswer, String... ignoreProperties) {
		return super.update(questionnareAnswer, ignoreProperties);
	}
	
	

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(QuestionnareAnswer questionnareAnswer) {
		super.delete(questionnareAnswer);
	}

	@Override
	public boolean saveQuestionnareAnswer(QuestionnareAnswer questionnareAnswer) {
		
		//是否答题过
		if(questionnareAnswerDao.findByMobileAndName(questionnareAnswer.getMobile(), questionnareAnswer.getName())!=null){
			logger.info("[答题活动]:用户已经答过题");
			return false;
		}
		
		Member member = memberService.findByMobile(questionnareAnswer.getMobile());
		//判断是否注册过用户
		if(member != null){
			//积分赠送
			Map<String, Object> result =creditService.engineMengTopUp(member.getSn(), Long.parseLong(questionnareAnswer.getScore()+""), "答题", CreditEventEnum.GameGift);
			if(result !=null){
				super.save(questionnareAnswer);
				return true;
			}
			logger.error("[答题活动]:调用赠送积分接口失败");
			return false;
		}else{
			//调用积分转存接口
			QuestionTransfer questionTransfer = new QuestionTransfer();
			questionTransfer.setMobile(questionnareAnswer.getMobile());
			questionTransfer.setPoint(questionnareAnswer.getScore());
			questionTransferService.save(questionTransfer);
			super.save(questionnareAnswer);
			logger.info("[答题活动]:非注册用户，积分转存成功");
			return true;
		}
	}

}