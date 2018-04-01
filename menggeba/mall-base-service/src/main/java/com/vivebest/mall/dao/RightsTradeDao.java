package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsTrade;

/**
 * RightsTradeDao - 权益商户
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsTradeDao extends BaseDao<RightsTrade, Long>{
	
	

		/**
		 * 查询所有
		 * @param limit 
		 * 			false不限制，true只查询状态已启用的
		 * @return
		 */
		List<RightsTrade> findAllRightsTrade(boolean limit);
		/**
		 * 通过名称查询
		 * @return
		 */
		RightsTrade findByName(String name);

		/**
		 * 判断账户唯一
		 * @param rlogin
		 * 			账户
		 * @return 是否唯一
		 */
		boolean snExists(String rlogin);
		
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
