package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsBrandDao - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsBrandDao extends BaseDao<RightsBrand, Long> {

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
	 * 
	 * @param pageable
	 * @param rightsBrand
	 * @param rightsCategory
	 * @return
	 */
	Page<RightsBrand> findList(Pageable pageable, RightsType type,Long area,RightsBrand rightBrand);

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
