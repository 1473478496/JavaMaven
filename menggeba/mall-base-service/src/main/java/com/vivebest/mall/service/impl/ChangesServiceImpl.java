package com.vivebest.mall.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ChangesDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Changes.Status;
import com.vivebest.mall.entity.ChangesItem;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.ChangesService;

@Service("changesServiceImpl")
public class ChangesServiceImpl extends BaseServiceImpl<Changes, Long>implements ChangesService {

	@Resource(name = "changesDaoImpl")
	private ChangesDao changesDao;

	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Resource(name = "changesDaoImpl")
	public void setBaseDao(ChangesDao changesDao) {
		super.setBaseDao(changesDao);
	}

	@Override
	public Changes findByOrderItem(Long orderItemId) {
		return changesDao.findByOrderItem(orderItemId);
	}

	@Transactional(readOnly = true)
	public Changes findBySn(String sn) {
		return changesDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public Changes create(Order order, OrderItem orderItem, String changeCause, String changeDesc,
			String changeVoucher) {
		Assert.notNull(order);
		Assert.notNull(orderItem);
		Assert.notNull(changeCause);

		Changes changes = new Changes();
		changes.setOrderItem(orderItem);
		changes.setOrders(order);
		changes.setChangeCause(changeCause);
		changes.setChangeDesc(changeDesc);
		changes.setChangeVoucher(changeVoucher);
		changes.setExpire(order.getExpire());
		changes.setStatus(Status.pending);
		changes.setSn("m"+snDao.generate(Sn.Type.changes));
		changes.setChangeDate(new Date());
		changes.setAddress(order.getAddress());
		changes.setArea(order.getArea());
		changes.setPhone(order.getPhone());
		changes.setZipCode(order.getZipCode());
		changes.setShippingMethod(order.getShippingMethod());
		changes.setFreight(new BigDecimal(0));
		changes.setMember(order.getMember());
		// changes.setTradeId(orderItem.getProduct().get);
		ChangesItem changesItem = new ChangesItem();
		changesItem.setChanges(changes);
		changesItem.setName(orderItem.getProduct().getName());
		changesItem.setQuantity(orderItem.getQuantity());
		changesItem.setSn(orderItem.getProduct().getSn());
		List<ChangesItem> changesItems = changes.getChangesItems();
		try {
			changesItems.add(changesItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changes;
	}

	@Transactional(readOnly = true)
	public Page<Changes> findPage(Member member, Pageable pageable) {
		return changesDao.findPage(member, pageable);
	}
	
	@Transactional(readOnly = true)
	public long count() {
		Status [] statuses = {Status.pending,Status.delivered};
		return changesDao.count(null, statuses);
	}

}