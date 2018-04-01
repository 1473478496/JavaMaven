package com.vivebest.mall.merchant.service;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;

public class TradeShopServiceTest extends BaseServiceTest{
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeShopService tradeShopService;
	
	@Test
	public void testUpdateTradeShop(){
		Date date = new Date();
		TradeShop tradeShop = null;
		Trade trade = tradeService.find(8L);
		System.out.println(trade);
		Set<TradeShop> set = trade.getTradeShops();
		Iterator<TradeShop> iterator = set.iterator();
		if(iterator.hasNext()){
			tradeShop = iterator.next();
		}
		tradeShop.setName("update name");
		tradeShop.setAddr("不知道");
		tradeShop = tradeShopService.update(tradeShop);
		
		assertEquals("不知道", tradeShop.getAddr());
		
	}
	@Test
	public void testSaveTradeShop(){
		TradeShop tradeShop = new TradeShop();
		String tradeId = null;
		Long id = Long.parseLong(tradeId != null ? tradeId:"66");
		System.out.println(id);
		Trade trade =  tradeService.find(id);
		tradeShop.setTrade(trade);
		tradeShop.setAddr("江西省萍乡市莲花县sdaaaa");
		tradeShop.setApply_date(new Date());
		tradeShop.setName("张三");
		tradeShop.setPhone("15616224516");
		tradeShop.setLogo_url("/resources/images/uploadImg/Tulips.jpg");
		tradeShop.setStatus(Status.close);
		tradeShop.setPlatStatus(PlatStatus.open);
		tradeShopService.save(tradeShop);
		
	}
}
