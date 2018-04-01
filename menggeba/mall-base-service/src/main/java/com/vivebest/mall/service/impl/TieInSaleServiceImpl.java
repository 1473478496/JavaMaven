package com.vivebest.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.dao.TieInSaleCatetoryDao;
import com.vivebest.mall.dao.TieInSaleDao;
import com.vivebest.mall.dao.TieInSaleTitleDao;
import com.vivebest.mall.entity.TieInSale;
import com.vivebest.mall.entity.TieInSaleCatetory;
import com.vivebest.mall.entity.TieInSaleTitle;
import com.vivebest.mall.service.TieInSaleService;

/**
 * 商品搭配销售标题 service的实现
 * 
 * @author junly
 *
 */
@Service("tieInSaleServiceImpl")
public class TieInSaleServiceImpl extends BaseServiceImpl<TieInSale, Long>implements TieInSaleService {

	@Resource(name = "tieInSaleTitleDaoImpl")
	private TieInSaleTitleDao tieInSaleTitleDao;
	@Resource(name = "tieInSaleDaoImpl")
	private TieInSaleDao tieInSaleDao;
	@Resource(name = "tieInSaleCatetoryDaoImpl")
	private TieInSaleCatetoryDao tieInSaleCatetoryDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "tieInSaleDaoImpl")
	public void setBaseDao(TieInSaleDao tieInSaleDao) {
		super.setBaseDao(tieInSaleDao);
	}

	@Override
	public List<TieInSale> findProductByCyAndTlAndPId(Long catetoryId, Long tieTitleId, Long productId) {
		TieInSaleCatetory tieCatetory = tieInSaleCatetoryDao.find(catetoryId);
		List<TieInSale> tieInSales = tieInSaleDao.findTieSaleByPId(productId);
		List<TieInSale> tieSales= new ArrayList<TieInSale>();
		for (TieInSale tieInSale : tieInSales) {
			TieInSaleTitle tieTitle = tieInSale.getTieInSaleTitle();
			if (tieInSale != null && tieTitle.equals(tieInSaleTitleDao.find(tieTitleId))
					&& tieTitle.getTieInSaleCatetory().equals(tieCatetory)) {
				//Product product=productDao.find(tieInSale.getTelIn().getId());
				tieSales.add(tieInSale);
			}
		}
		return tieSales;
	}

	public List<TieInSale> findProductByPSuitId(Long productSuitId) {
		return  tieInSaleDao.findTieSaleByPSuitId(productSuitId);
	}
	
	public List<TieInSale> findByProductId(Long ProductId) {
		return  tieInSaleDao.findByProductId(ProductId);
	}

	@Transactional
	public void deleteProductByPSuitId(Long productSuitId) {
		//删除
		
	}
	
}
