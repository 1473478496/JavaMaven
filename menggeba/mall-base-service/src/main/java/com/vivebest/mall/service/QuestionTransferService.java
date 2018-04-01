package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.QuestionTransfer;

public interface QuestionTransferService extends BaseService<QuestionTransfer, Long> {
	/**
	 * 根据手机查找
	 * @param mobile
	 * @return
	 */
	List<QuestionTransfer> findListByMobile(String mobile);
}
