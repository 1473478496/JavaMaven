package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.QuestionTransferDao;
import com.vivebest.mall.entity.QuestionTransfer;
import com.vivebest.mall.service.QuestionTransferService;

@Service("questionTransferServiceImpl")
public class QuestionTransferServiceImpl extends BaseServiceImpl<QuestionTransfer, Long> implements QuestionTransferService {

	@Resource(name = "questionTransferDaoImpl")
	private QuestionTransferDao questionTransferDao;
	
	@Resource(name = "questionTransferDaoImpl")
	public void setBaseDao(QuestionTransferDao questionTransferDao){
		super.setBaseDao(questionTransferDao);
	}

	@Override
	public List<QuestionTransfer> findListByMobile(String mobile) {
		return questionTransferDao.findListByMobile(mobile);
	}
	
}