package com.vivebest.mall.merchant.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.TradeAdmin;

public interface TradeAdminService extends BaseService<TradeAdmin, Long> {

	/**
	 * 根据商户名查询商户信息
	 * @param string
	 * @return
	 */
	TradeAdmin findByUsername(String username);

	/**
	 * 根据ID查找权限
	 * 
	 * @param id
	 *            ID
	 * @return 权限,若不存在则返回null
	 */
	List<String> findAuthorities(Long id);

	/**
	 * 获取当前登录的商户
	 * @return
	 */
	TradeAdmin getCurrent();

}
