package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsTrade;

public interface RightsTradeService extends BaseService<RightsTrade, Long>{
	/**查询所有权益商户*/
	List<RightsTrade> findAll();

	/**根据名称查询权益商户*/
	RightsTrade findByName(String name);
	
	/**
	 * 查询所有
	 * @param limit 
	 * 			false不限制，true只查询状态已启用的
	 * @return
	 */
	List<RightsTrade> findAllRightsTrade(boolean limit);

	/**
	 * 判断账户唯一
	 * @param previousRlogin
	 * 			修改前账户
	 * @param rlogin
	 * 			当前账户
	 * @return 是否唯一
	 */
	boolean rloginUnique(String previousRlogin, String rlogin);
	
	/**
	 * @param pageable
	 * 			分页信息
	 * @param areaName
	 * 			地区
	 * @param brandName
	 * 			商家
	 * @return
	 */
	Page<RightsTrade> findPage(Pageable pageable, String areaName, String brandName);

	/**
	 * @param pageable  分页信息
	 * @param rights  权益
	 * @param area  地区
	 * @return
	 */
	Page<RightsTrade> findPage(Pageable pageable, Rights rights, Area area);
}
