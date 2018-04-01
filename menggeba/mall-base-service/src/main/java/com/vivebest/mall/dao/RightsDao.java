package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Rights;

/**
 * RightsDao - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsDao extends BaseDao<Rights, Long> {

	
	
	/**
	 * 条件查询
	 * @param brand
	 * @return
	 */
	Page<Rights> findList(Rights rights,Pageable pageable);
	
	/**
	 * 设置权益是否推荐
	 * @param rights
	 */
	void setIsPopularity(Rights rights);

	/**
	 * 热销排行
	 * @return
	 */
	List<Rights> rightsHotList();

	/**
	 * 通过权益品牌id查询
	 * @param rightsBrandId
	 * @return
	 */
	List<Rights> findByBrandId(Long rightsBrandId);
	
	/**
	 * @param isHome 首页展示
	 * @param isPopularity 人气展示
	 * @return
	 */
	List<Rights> rightsPopular(Boolean isHome, Boolean isPopularity);
	
	

	/**
	 * 判断权益商品编号是否存在
	 * 
	 * @param currentSn
	 *            权益商品编号(忽略大小写)
	 * @return 权益商品编号是否存在
	 */
	boolean snExists(String currentSn);
	
	/**
	 * 根据权益名称查找权益
	 * @param name
	 * @return
	 */
	public Rights findByName(String name);

}
