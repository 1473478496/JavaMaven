package com.vivebest.mall.admin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.TieInSale;
import com.vivebest.mall.entity.TieInSaleCatetory;
import com.vivebest.mall.entity.TieInSaleCatetory.Category;
import com.vivebest.mall.entity.TieInSaleTitle;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.TieInSaleCatetoryService;
import com.vivebest.mall.service.TieInSaleService;
import com.vivebest.mall.service.TieInSaleTitleService;

/**
 * Controller - 推荐商品
 * 
 * @author junly
 * @version 3.0
 */
@Controller("adminTieInSaleController")
@RequestMapping("/admin/tieInSale")
public class TieInSaleController extends BaseController {
	@Resource(name = "tieInSaleCatetoryServiceImpl")
	private TieInSaleCatetoryService TieInSaleCatetoryService;
	@Resource(name = "tieInSaleTitleServiceImpl")
	private TieInSaleTitleService tieInSaleTitleService;
	@Resource(name = "tieInSaleServiceImpl")
	private TieInSaleService tieInSaleService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 组合商品列表
	 * 
	 * @param productId
	 *            主商品Id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tieInSaleList", method = RequestMethod.GET)
	public String tieInSaleList(Long productId, ModelMap model) {
		List<TieInSaleCatetory> tieInSaleCatetories = TieInSaleCatetoryService.findTieCatetoryByProId(productId);
		Map<Long, List<TieInSaleTitle>> tieTitles = new HashMap<Long, List<TieInSaleTitle>>();
		Map<Long, List<TieInSale>> tieInSales = new HashMap<Long, List<TieInSale>>();
		Map<Long, BigDecimal> originalPrices = new HashMap<Long, BigDecimal>();
		Map<Long, Long> originalPointPrices = new HashMap<Long, Long>();

		for (TieInSaleCatetory tieInSaleCatetory : tieInSaleCatetories) {
			List<TieInSaleTitle> tieInSaleTitles = tieInSaleTitleService
					.findTieSaleTitleByCatetory(tieInSaleCatetory.getId(), productId);
			tieTitles.put(tieInSaleCatetory.getId(), tieInSaleTitles);
			for (TieInSaleTitle tieInSaleTitle : tieInSaleTitles) {
				List<TieInSale> tieSales = tieInSaleService.findProductByCyAndTlAndPId(tieInSaleCatetory.getId(),
						tieInSaleTitle.getId(), productId);
				tieInSales.put(tieInSaleTitle.getId(), tieSales);
				BigDecimal originalPrice = productService.find(productId).getPrice();
				Long originalPointPrice = productService.find(productId).getPricePoint();
				for (TieInSale tieSale : tieSales) {
					originalPrice = originalPrice.add(tieSale.getTelIn().getPrice());
					originalPointPrice += tieSale.getTelIn().getPricePoint();
				}
				originalPrices.put(tieInSaleTitle.getId(), originalPrice);
				originalPointPrices.put(tieInSaleTitle.getId(), originalPointPrice);
			}
		}
		model.addAttribute("originalPrices", originalPrices);
		model.addAttribute("originalPointPrices", originalPointPrices);
		model.addAttribute("tieInSaleCatetories", tieInSaleCatetories);
		model.addAttribute("tieTitles", tieTitles);
		model.addAttribute("tieInSales", tieInSales);
		return null;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productId, ModelMap model) {
		List<TieInSale> tieInSales = tieInSaleService.findByProductId(productId);
		List<TieInSale> tieRecs = new ArrayList<TieInSale>();
		// List<TieInSale> tiePres = new ArrayList<TieInSale>();
		List<TieInSale> tieBess = new ArrayList<TieInSale>();
		// BigDecimal originalPrice=productService.find(productId).getPrice();
		// Long
		// originalPointPrice=productService.find(productId).getPricePoint();
		for (TieInSale tie : tieInSales) {
			// 推荐配件
			if (tie.getTieInSaleTitle() != null && Category.recommendAccessories
					.equals(tie.getTieInSaleTitle().getTieInSaleCatetory().getCategory())) {
				tieRecs.add(tie);
			}
			// // 优惠套装
			// if (tie.getTieInSaleTitle() != null
			// &&
			// Category.preferentialSuit.equals(tie.getTieInSaleTitle().getTieInSaleCatetory().getCategory()))
			// {
			// tiePres.add(tie);
			// }
			// 最佳组合
			if (Category.bestCombination.equals(tie.getTieInSaleTitle().getTieInSaleCatetory().getCategory())) {
				tieBess.add(tie);
			}
		}
		model.addAttribute("tieRecs", tieRecs);
		// model.addAttribute("tiePres", tiePres);
		model.addAttribute("tieBess", tieBess);
		return null;
	}

	/**
	 * 显示优惠套装商品详细页面
	 * 
	 * @param id
	 *            套裝商品id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/suit", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> suit(Long id, ModelMap model) {
		List<TieInSale> tieInSales = tieInSaleService.findProductByPSuitId(id);
		Map<String, Object> data = new HashMap<String, Object>();
		BigDecimal originalPrice = productService.find(tieInSales.get(0).getProduct().getId()).getPrice();
		Long originalPointPrice = productService.find(tieInSales.get(0).getProduct().getId()).getPricePoint();
		for (TieInSale tieSale : tieInSales) {
			originalPrice.add(tieSale.getTelIn().getPrice());
			originalPointPrice += tieSale.getTelIn().getPricePoint();
		}
		data.put("originalPrice", originalPrice);
		data.put("originalPointPrice", originalPointPrice);
		data.put("tieInSales", tieInSales);
		return data;
	}

	/**
	 * 添加套装商品
	 * 
	 * @param productSuit
	 *            套装商品
	 * @param proId
	 *            主商品id
	 * @param productId3
	 *            添加参与商品id集合
	 * @param tieInSaleTitle
	 *            套装商品标题
	 * @return
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value = "/addSuit", method = RequestMethod.POST)
	public String addTieInSales(Product productSuit, Long proId, String productId3, TieInSaleTitle tieInSaleTitle) {
		productSuit.setSuit(true);
		productSuit.setAllocatedStock(0);
		productSuit.setHits(0L);
		productSuit.setIsGift(false);
		productSuit.setIsTop(false);
		productSuit.setIsList(true);
		productSuit.setIsMarketable(true);
		productSuit.setMarketPrice(new BigDecimal(0));
		productSuit.setMonthHits(0L);
		productSuit.setMonthHitsDate(new Date());
		productSuit.setMonthSales(0L);
		productSuit.setMonthSalesDate(new Date());
		productSuit.setName(productSuit.getFullName());
		productSuit.setSales(0L);
		productSuit.setScore(0f);
		productSuit.setScoreCount(0L);
		productSuit.setTotalScore(0L);
		productSuit.setWeekHits(0L);
		productSuit.setWeekSales(0L);
		productSuit.setWeekHitsDate(new Date());
		productSuit.setWeekSalesDate(new Date());
		productSuit.setIsNewFirst(false);
		productSuit.setPoint(0L);
		tieInSaleTitle.setTieInSaleCatetory(TieInSaleCatetoryService.findTieCatetoryByCatetory("优惠套装"));
		Set<TieInSale> tieInSales = tieInSaleTitle.getTieInSales();
		if (productId3 != null) {
			String[] idStr = productId3.split(",");
			Long[] suitId = new Long[idStr.length];
			for (int i = 0; i < idStr.length; i++) {
				suitId[i] = Long.parseLong(idStr[i]);
				TieInSale tieInSale = new TieInSale();
				tieInSale.setProductSuit(productSuit);
				tieInSale.setProduct(productService.find(proId));
				tieInSale.setTelIn(productService.find(suitId[i]));
				tieInSale.setTieInSaleTitle(tieInSaleTitle);
				tieInSales.add(tieInSale);
			}
			try {
				productService.save(productSuit);
				tieInSaleTitleService.save(tieInSaleTitle);
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}

		 //return null;
		return "redirect:/admin/product/edit.jhtml?id=" + proId;
	}

	/**
	 * 修改套装商品
	 * 
	 * @param productSuit
	 *            套装商品
	 * @param proId
	 *            主商品id
	 * @param proSuitId
	 *            套装商品id
	 * @param titleId
	 *            标题id
	 * @param productId3
	 *            添加参与商品id集合
	 * @param productDeleteId3
	 *            删除参与商品id集合
	 * @param tieInSaleTitle
	 *            套装商品标题
	 * @return
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value = "/editSuit", method = RequestMethod.POST)
	public String editTieInSales(Product productSuit, Long proId, Long proSuitId, Long titleId, String productId3,
			String productDeleteId3, TieInSaleTitle tieInSaleTitle) {
		Product proSuit = productService.find(proSuitId);
		proSuit.setFullName(productSuit.getFullName());
		proSuit.setName(productSuit.getFullName());
		proSuit.setPrice(productSuit.getPrice());
		proSuit.setPricePoint(productSuit.getPricePoint());
		TieInSaleTitle tieTitle = tieInSaleTitleService.find(titleId);
		tieTitle.setTitle(tieInSaleTitle.getTitle());

		Set<TieInSale> tieInSales = tieTitle.getTieInSales();
		Set<TieInSale> ts = tieInSales;
		if (productDeleteId3 != null) {
			String[] idStr2 = productDeleteId3.split(",");
			Long[] suitId2 = new Long[idStr2.length];
			for (int i = 0; i < idStr2.length; i++) {
				suitId2[i] = Long.parseLong(idStr2[i]);
				for (TieInSale tieInSale : ts) {
					if (suitId2[i].equals(tieInSale.getTelIn().getId())) {
						try {
							tieInSales.remove(tieInSale);
							tieInSaleService.delete(tieInSale.getId());
						} catch (Exception e) {
							return ERROR_VIEW;
						}
						break;
					}
				}
			}
		}
		if (productId3 != null) {
			String[] idStr = productId3.split(",");
			Long[] suitId = new Long[idStr.length];
			for (int i = 0; i < idStr.length; i++) {
				suitId[i] = Long.parseLong(idStr[i]);
				TieInSale tieInSale = new TieInSale();
				tieInSale.setProductSuit(proSuit);
				tieInSale.setProduct(productService.find(proId));
				tieInSale.setTelIn(productService.find(suitId[i]));
				tieInSale.setTieInSaleTitle(tieTitle);
				tieInSales.add(tieInSale);
			}
		}
		tieInSaleTitle.setTieInSales(tieInSales);
		try {
			if (tieTitle.getTieInSales().size()>0) {
				tieInSaleTitleService.update(tieTitle);
				productService.update(proSuit);
			}
		} catch (Exception e) {
			return ERROR_VIEW;
		}
		// return null;
		return "redirect:/admin/product/edit.jhtml?id=" + proId;
	}

	/**
	 * 删除套装
	 * 
	 * @param suitId
	 * @param titleId
	 * @return
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value = "/deleteSuit", method = RequestMethod.POST)
	public @ResponseBody Message deleteSuit(Long suitId, Long titleId) {
		try {
			tieInSaleTitleService.delete(titleId);
			productService.delete(suitId);
		} catch (Exception e) {
			return ERROR_MESSAGE;
		}
		return SUCCESS_MESSAGE;
	}

}
