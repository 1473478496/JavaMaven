package com.vivebest.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.TieInSaleCatetoryDao;
import com.vivebest.mall.dao.TieInSaleDao;
import com.vivebest.mall.dao.TieInSaleTitleDao;
import com.vivebest.mall.entity.TieInSale;
import com.vivebest.mall.entity.TieInSaleCatetory;
import com.vivebest.mall.entity.TieInSaleTitle;
import com.vivebest.mall.service.TieInSaleCatetoryService;

/**
 * 商品搭配分类 service的实现
 * 
 * @author junly
 *
 */
@Service("tieInSaleCatetoryServiceImpl")
public class TieInSaleCatetoryServiceImpl extends BaseServiceImpl<TieInSaleCatetory, Long>
		implements TieInSaleCatetoryService {

	@Resource(name = "tieInSaleCatetoryDaoImpl")
	private TieInSaleCatetoryDao tieInSaleCatetoryDao;
	@Resource(name = "tieInSaleTitleDaoImpl")
	private TieInSaleTitleDao tieInSaleTitleDao;
	@Resource(name = "tieInSaleDaoImpl")
	private TieInSaleDao tieInSaleDao;

	@Resource(name = "tieInSaleCatetoryDaoImpl")
	public void setBaseDao(TieInSaleCatetoryDao tieInSaleCatetoryDao) {
		super.setBaseDao(tieInSaleCatetoryDao);
	}

	public TieInSaleCatetory findTieCatetoryByCatetory(String name) {
		return tieInSaleCatetoryDao.findTieCatetoryByCatetory(name);
	}

	public List<TieInSaleCatetory> findTieCatetoryByProId(Long productId) {
		List<TieInSale> tieInSales = tieInSaleDao.findTieSaleByPId(productId);
		List<TieInSaleCatetory> tieInSaleCatetories = new ArrayList<TieInSaleCatetory>();
		if (tieInSales != null) {
			for (TieInSale tieInSale : tieInSales) {
				TieInSaleTitle tieInSaleTitle = tieInSale.getTieInSaleTitle();
				if (!tieInSaleCatetories.contains(tieInSaleTitle.getTieInSaleCatetory())) {
					tieInSaleCatetories.add(tieInSaleTitle.getTieInSaleCatetory());
				}
			}
		}
		return tieInSaleCatetories;
	}
}
