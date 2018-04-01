/**
 * 
 */
package com.vivebest.mall.merchant.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.entity.TradeProducts;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeBanks;
import com.vivebest.mall.merchant.entity.TradeProxy;
import com.vivebest.mall.service.TradeProductsService;

/**
 * @author liujc
 *
 */
public class TradeServiceTest extends BaseServiceTest {

	@Autowired
	private TradeProxyService tradeProxyService;
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeBanksService tradeBanksService;
	
	@Autowired
	private TradeProductsService tradeProductsService;
	
	/**
	 * Test method for
	 * {@link com.vivebest.mall.core.service.BaseService#update(java.lang.Object)}
	 * .
	 */
	@Test
	public void testSave()	 {
		Date date = new Date();
		
		TradeProxy tradeProxy2 = tradeProxyService.find(1L);

		assertEquals(DateUtil.formatDateTime(date), DateUtil.formatDateTime(tradeProxy2.getModifyDate()));
	}


}
