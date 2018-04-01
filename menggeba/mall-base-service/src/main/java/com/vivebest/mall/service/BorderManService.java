package com.vivebest.mall.service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.BorderMan;

/**
 * Service  边民
 * @author vnb shop Team
 * @version 3.0
 */
public interface BorderManService extends BaseService<BorderMan, Long> {
	
	/**
	 * 查找边民分页
	 * @param status 申请状态
	 * @param pageable 分布信息
	 * @return
	 */
	Page<BorderMan> findPage(int checkStatus, Pageable pageable);
	
	/**
	 * 批准边民注册
	 * @param border
	 * @param admin
	 * @param auditDesc
	 */
	void approve(BorderMan borderMan,Admin admin, String auditDesc);
	
	/**
	 * 根据边民证件编号查询
	 * @param tradeId
	 * @return
	 */
	 BorderMan findByTradeId(Long tradeId);
}
