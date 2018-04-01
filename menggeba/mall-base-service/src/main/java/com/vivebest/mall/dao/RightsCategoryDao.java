package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsCategoryDao - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsCategoryDao extends BaseDao<RightsCategory, Long>{

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

	/**查询所有权益分类*/
	List<RightsCategory> findRoots(Integer count);

	/**根据名称查询权益分类*/
	RightsCategory findByName(String name);
	
	/**根据ID查询权益分类*/
	RightsCategory findById(int id);

}
