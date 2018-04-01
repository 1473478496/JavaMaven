package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsCategoryService - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsCategoryService extends BaseService<RightsCategory, Long>{
	
	/**查询所有权益品牌*/
	List<RightsCategory> findRoots();

	/**根据名称查询权益品牌*/
	RightsCategory findByName(String name);
	
	/**根据ID查询权益品牌*/
	RightsCategory findById(int id);
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	RightsCategory findById(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	List<RightsCategory> findAllRightsCategory();

}
