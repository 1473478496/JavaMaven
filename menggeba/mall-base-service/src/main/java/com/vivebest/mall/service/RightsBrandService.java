package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsBrandService - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsBrandService extends BaseService<RightsBrand, Long> {

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	RightsBrand findById(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	List<RightsBrand> findAllRightsBrand();
	
	/**
	 * @param pageable
	 * 			分页信息
	 * @param rightsBrand
	 * 			权益品牌
	 * @param rightsCategory
	 * 			权益分类
	 * @return
	 */
	Page<RightsBrand> findPage(Pageable pageable, RightsBrand rightsBrand, RightsCategory rightsCategory);
	
	/**
	 * 查询商家 
	 * @param pageable
	 * @param type
	 * @return
	 */
	Page<RightsBrand> findList(Pageable pageable, RightsType type,Long area,RightsBrand rightsBrand);

	/**
	 * 关注商家分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return
	 */
	Page<RightsBrand> findPage(Pageable pageable, Member member);

	/**
	 * @param pageable
	 * @param rightsCategoryId
	 * @param rightsBrandId
	 * @param areaId
	 * @return
	 */
	Page<RightsBrand> findPage(Pageable pageable, Long rightsCategoryId,
			Long rightsBrandId, Long areaId);

}
