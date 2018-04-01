package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.entity.TradeProducts;
import com.vivebest.mall.service.TradeProductsService;
import com.vivebest.mall.dao.TradeProductsDao;
/**
 * 商户商品分类service实现类
 * @author Terry
 *
 */
@Service("tradeProductsServiceImpl")
public class TradeProductsServiceImpl extends BaseServiceImpl<TradeProducts, Long> implements TradeProductsService {

	@Resource(name="tradeProductsDaoImpl")
	private TradeProductsDao tradeProductsDao;
	
	@Resource(name = "tradeProductsDaoImpl")
	public void setBaseDao(TradeProductsDao tradeProductsDao) {
		super.setBaseDao(tradeProductsDao);
	}
	
	/**
	 * 根据商户id查询列表
	 * 
	 */
	 
	public List<Long> findList(Long tradeId) {
		return tradeProductsDao.findList(tradeId);
	}

}
