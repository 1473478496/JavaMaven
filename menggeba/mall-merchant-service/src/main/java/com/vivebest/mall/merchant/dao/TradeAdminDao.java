package com.vivebest.mall.merchant.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.merchant.entity.TradeAdmin;

public interface TradeAdminDao extends BaseDao<TradeAdmin, Long> {
	/**
	 * 根据商户名称查询商户信息
	 * @param username
	 * @return
	 */
	TradeAdmin findByUsername(String username);
	

}
