/**
 * 
 */
package com.vivebest.mall.merchant.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.merchant.entity.TradeAdmin;

/**
 * @author liujc
 *
 */
public class TradeAdminServiceTest extends BaseServiceTest {

	@Autowired
	private TradeAdminService tradeAdminService;

	/**
	 * Test method for
	 * {@link com.vivebest.mall.core.service.BaseService#update(java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateT() {

		TradeAdmin tradeAdmin = tradeAdminService.findByUsername("mer001");
		System.out.println(DateUtil.formatDateTime(tradeAdmin.getModifyDate()));
		Date date = new Date();
		tradeAdmin.setModifyDate(date);
		int loginFailureCount = tradeAdmin.getLogin_failure_count() + 1;
		tradeAdmin.setLogin_failure_count(loginFailureCount);
		tradeAdminService.update(tradeAdmin);

		TradeAdmin tradeAdmin2 = tradeAdminService.findByUsername("mer001");
		System.out.println(DateUtil.formatDateTime(tradeAdmin2.getModifyDate()));

		assertEquals(DateUtil.formatDateTime(date), DateUtil.formatDateTime(tradeAdmin2.getModifyDate()));
	}

	/**
	 * Test method for
	 * {@link com.vivebest.mall.core.service.BaseService#update(java.lang.Object, java.lang.String[])}
	 * .
	 */
	@Test
	public void testUpdateTStringArray() {
		fail("Not yet implemented"); // TODO
	}

}
