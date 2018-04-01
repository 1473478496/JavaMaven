package com.vivebest.mall.service;


import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.Rights.ConvertOrderType;
import com.vivebest.mall.entity.Rights.PriceOrderType;
import com.vivebest.mall.entity.Rights.PutAwayOrderType;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsService - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsService extends BaseService<Rights, Long> {

	/**
	 * 查找商品分页
	 * 
	 * @param rightsCategory
	 *            权益分类
	 * @param rightsBrand
	 *            权益品牌
	 * @param pageable
	 *            分页信息
	 * @param convertOrderType
	 * 				兑换数量排序类型
	 * @param putAwayOrderType
	 * 				最新上架排序类型
	 * @param priceOrderType
	 * 				价格排序类型
	 * @param searchValue
	 * 				搜索值
	 * @param rightsType
	 * 				权益类型
	 * @return 权益分页
	 */
	Page<Rights> findPage(RightsCategory rightsCategory, RightsBrand rightsBrand, Pageable pageable, ConvertOrderType convertOrderType
			, PutAwayOrderType putAwayOrderType, PriceOrderType priceOrderType, String searchValue, RightsType rightsType);

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	Rights findById(Long id);

	/**
	 * 热销排行
	 * @return
	 */
	List<Rights> rightsHotList();
	
	/**
	 * @param isHome 首页展示
	 * @param isPopularity 人气展示
	 * @return
	 */
	List<Rights> rightsPopular(Boolean isHome,Boolean isPopularity);

	/**
	 * 通过权益品牌id查询
	 * @param rightsBrandId
	 * @return
	 */
	List<Rights> findByBrandId(Long rightsBrandId);
	
	/**
	 * 查询权益列表
	 * @param rightsBrandId
	 * @return
	 */
	Page<Rights> findList(Rights rights,Pageable pageable);
	
	void setIsPopularity(Rights rights);
	

	/**
	 * 判断权益商品编号是否唯一
	 * 
	 * @param previousSn
	 *            修改前权益商品编号(忽略大小写)
	 * @param currentSn
	 *            当前权益商品编号(忽略大小写)
	 * @return 权益商品编号是否唯一
	 */
	boolean snUnique(String previousSn, String currentSn);

}
