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
import com.vivebest.mall.entity.TieInSaleTitle;
import com.vivebest.mall.service.TieInSaleTitleService;

/**
 * 商品搭配销售标题 service的实现
 * 
 * @author junly
 *
 */
@Service("tieInSaleTitleServiceImpl")
public class TieInSaleTitleServiceImpl extends BaseServiceImpl<TieInSaleTitle, Long>implements TieInSaleTitleService {

	@Resource(name = "tieInSaleTitleDaoImpl")
	private TieInSaleTitleDao tieInSaleTitleDao;
	@Resource(name = "tieInSaleDaoImpl")
	private TieInSaleDao tieInSaleDao;
	@Resource(name = "tieInSaleCatetoryDaoImpl")
	private TieInSaleCatetoryDao tieInSaleCatetoryDao;

	@Resource(name = "tieInSaleTitleDaoImpl")
	public void setBaseDao(TieInSaleTitleDao tieInSaleTitleDao) {
		super.setBaseDao(tieInSaleTitleDao);
	}

	@Override
	public List<TieInSaleTitle> findTieSaleTitleByTitle(String title) {

		List<TieInSaleTitle> tieInSaleTitles = tieInSaleTitleDao.findTieSaleTitleByTitle(title);
		return tieInSaleTitles;
	}

	public List<TieInSaleTitle> findTieInSaleTitleByCatetoryId(Long catetoryId) {
		List<TieInSaleTitle> tieInSaleTitles = null;
		if (catetoryId != null) {
			tieInSaleTitles = tieInSaleTitleDao.findTieSaleTitleByCatetory(catetoryId);
		}
		return tieInSaleTitles;
	}
	
	public List<TieInSaleTitle> findByCatetoryTitle(Long catetoryId, String title){
		return tieInSaleTitleDao.findByCatetoryTitle(catetoryId, title);
	}
	
	public List<TieInSaleTitle> findTieSaleTitleByCatetory(Long catetoryId, Long productId) {
		List<TieInSaleTitle> tieInSaleTitles = tieInSaleTitleDao.findTieSaleTitleByCatetory(catetoryId);
		List<TieInSaleTitle> tSaleTitles = new ArrayList<TieInSaleTitle>();
		List<TieInSale> tieInSales = tieInSaleDao.findTieSaleByPId(productId);
		if (tieInSales != null&&tieInSaleTitles!=null) {
			for (TieInSaleTitle tieInSaleTitle : tieInSaleTitles) {
				for (TieInSale tieInSale : tieInSales) {
					if (tieInSale != null && tieInSale.getTieInSaleTitle().equals(tieInSaleTitle)) {
						tSaleTitles.add(tieInSaleTitle);
						break;
					}
				}
			}
		}
		return tSaleTitles;
	}
}
