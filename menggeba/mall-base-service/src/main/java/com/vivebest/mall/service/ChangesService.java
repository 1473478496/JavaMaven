package com.vivebest.mall.service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.OrderItem;

public interface ChangesService extends BaseService<Changes, Long> {

	/**
	 * 通过订单项id查找换货单
	 * 
	 * @param orderItemId
	 * @return
	 */
	public Changes findByOrderItem(Long orderItemId);

	/**
	 * 创建换货单
	 * 
	 * @param order
	 * @param orderItem
	 * @param changes
	 * @return
	 */
	public Changes create(Order order, OrderItem orderItem, String changeCause,String changeDesc,String changeVoucher);

	/**
	 * 根据编号查找换货单
	 * 
	 * @param sn
	 * @return
	 */
	Changes findBySn(String sn);
	/**
	 * 查找退货单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Changes> findPage(Member member, Pageable pageable);
}
