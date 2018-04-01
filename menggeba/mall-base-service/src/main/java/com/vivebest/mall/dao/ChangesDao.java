package com.vivebest.mall.dao;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Changes.Status;
import com.vivebest.mall.entity.Member;

/**
 * 换货单-dao
 * 
 * @author junly
 *
 */
public interface ChangesDao extends BaseDao<Changes, Long> {
	/**
	 * 通过购物项的id查找换货单
	 * 
	 * @param id
	 * @return
	 */
	Changes findByOrderItem(Long id);

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 订单，若不存在则返回null
	 */
	Changes findBySn(String sn);
	/**
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 退货单分页
	 */
	Page<Changes> findPage(Member member, Pageable pageable);
	
	/**
	 *
	 * 查询订单数目
	 * 
	 * @param changes
	 * @param statues
	 * @return
	 */
	Long count(Changes changes, Status [] statues);
}
