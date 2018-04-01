
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.QuestionTransfer;

public interface QuestionTransferDao extends BaseDao<QuestionTransfer, Long> {

	/**
	 * 根据手机号查找
	 * @param mobile
	 * @return
	 */
	List<QuestionTransfer> findListByMobile(String mobile);

}

