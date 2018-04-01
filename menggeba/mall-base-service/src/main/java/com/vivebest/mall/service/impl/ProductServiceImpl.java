/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.entity.Attribute;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.entity.Goods;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Product.OrderType;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.entity.ProductImage;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Tag;
import com.vivebest.mall.entity.Tag.Type;
import com.vivebest.mall.service.BrandService;
import com.vivebest.mall.service.GoodsService;
import com.vivebest.mall.service.ProductCategoryService;
import com.vivebest.mall.service.ProductImageService;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.StaticService;
import com.vivebest.mall.service.TagService;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Service - 商品
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, Long>implements ProductService, DisposableBean {

	/** 查看点击数时间 */
	private long viewHitsTime = System.currentTimeMillis();

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService pdCategoryService;

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;

	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;

	@Resource(name = "productDaoImpl")
	public void setBaseDao(ProductDao productDao) {
		super.setBaseDao(productDao);
	}

	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return productDao.snExists(sn);
	}

	@Transactional(readOnly = true)
	public Product findBySn(String sn) {
		return productDao.findBySn(sn);
	}

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	private String inclusionErrList;

	@Transactional(readOnly = true)
	public boolean snUnique(String previousSn, String currentSn) {
		if (StringUtils.equalsIgnoreCase(previousSn, currentSn)) {
			return true;
		} else {
			if (productDao.snExists(currentSn)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		return productDao.search(keyword, isGift, count);
	}

	@Transactional(readOnly = true)
	public List<Product> searchSuit(String keyword, Boolean suit, Integer count) {
		return productDao.searchSuit(keyword, suit, count);
	}

	@Transactional(readOnly = true)
	public Page<Product> searchAll(String keyword, OrderType orderType, Boolean isStore, Pageable pageable) {
		return productDao.searchAll(keyword, orderType, isStore, pageable);
	}

	@Transactional(readOnly = true)
	public List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, Boolean isSuit) {
		return productDao.findList(productCategory, brand, promotion, tags, attributeValue, startPrice, endPrice,
				isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, orderType, count, filters, orders, isSuit);
	}

	@Transactional(readOnly = true)
	@Cacheable("product")
	public List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion, Boolean isSuit) {
		return productDao.findList(productCategory, brand, promotion, tags, attributeValue, startPrice, endPrice,
				isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, orderType, count, filters, orders, isSuit);
	}

	@Transactional(readOnly = true)
	public List<Product> findList(ProductCategory productCategory, Date beginDate, Date endDate, Integer first,
			Integer count) {
		return productDao.findList(productCategory, beginDate, endDate, first, count);
	}

	@Transactional(readOnly = true)
	public List<Object[]> findSalesList(Date beginDate, Date endDate, Boolean isPoint, Integer count) {
		return productDao.findSalesList(beginDate, endDate, isPoint, count);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPage(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Boolean isStore, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice,
			Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean isNewFirst, OrderType orderType, Pageable pageable, Boolean suit) {
		return productDao.findPage(productCategory, brand, promotion, tags, isStore, attributeValue, startPrice,
				endPrice, isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, isNewFirst, orderType, pageable, suit);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPage(Member member, Pageable pageable) {
		return productDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift,
			Boolean isOutOfStock, Boolean isStockAlert) {
		return productDao.count(favoriteMember, isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert);
	}

	@Transactional(readOnly = true)
	public boolean isPurchased(Member member, Product product) {
		return productDao.isPurchased(member, product);
	}

	public long viewHits(Long id) {
		Ehcache cache = cacheManager.getEhcache(Product.HITS_CACHE_NAME);
		Element element = cache.get(id);
		Long hits;
		if (element != null) {
			hits = (Long) element.getObjectValue();
		} else {
			Product product = productDao.find(id);
			if (product == null) {
				return 0L;
			}
			hits = product.getHits();
		}
		hits++;
		cache.put(new Element(id, hits));
		long time = System.currentTimeMillis();
		if (time > viewHitsTime + Product.HITS_CACHE_INTERVAL) {
			viewHitsTime = time;
			updateHits();
			cache.removeAll();
		}
		return hits;
	}

	public void destroy() throws Exception {
		updateHits();
	}

	/**
	 * 更新点击数
	 */
	@SuppressWarnings("unchecked")
	private void updateHits() {
		Ehcache cache = cacheManager.getEhcache(Product.HITS_CACHE_NAME);
		List<Long> ids = cache.getKeys();
		for (Long id : ids) {
			Product product = productDao.find(id);
			if (product != null) {
				productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
				Element element = cache.get(id);
				long hits = (Long) element.getObjectValue();
				long increment = hits - product.getHits();
				Calendar nowCalendar = Calendar.getInstance();
				Calendar weekHitsCalendar = DateUtils.toCalendar(product.getWeekHitsDate());
				Calendar monthHitsCalendar = DateUtils.toCalendar(product.getMonthHitsDate());
				if (nowCalendar.get(Calendar.YEAR) != weekHitsCalendar.get(Calendar.YEAR)
						|| nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekHitsCalendar.get(Calendar.WEEK_OF_YEAR)) {
					product.setWeekHits(increment);
				} else {
					product.setWeekHits(product.getWeekHits() + increment);
				}
				if (nowCalendar.get(Calendar.YEAR) != monthHitsCalendar.get(Calendar.YEAR)
						|| nowCalendar.get(Calendar.MONTH) > monthHitsCalendar.get(Calendar.MONTH)) {
					product.setMonthHits(increment);
				} else {
					product.setMonthHits(product.getMonthHits() + increment);
				}
				product.setHits(hits);
				product.setWeekHitsDate(new Date());
				product.setMonthHitsDate(new Date());
				productDao.merge(product);
			}
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void save(Product product) {
		Assert.notNull(product);

		super.save(product);
		productDao.flush();
		staticService.build(product);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Product update(Product product) {
		Assert.notNull(product);

		Product pProduct = super.update(product);
		productDao.flush();
		staticService.build(pProduct);
		return pProduct;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Product update(Product product, String... ignoreProperties) {
		return super.update(product, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Product product) {
		if (product != null) {
			staticService.delete(product);
		}
		super.delete(product);
	}

	@Override
	public List<Product> readExcelProduct(InputStream inp, Goods gdx) {
		// TODO Auto-generated method stub
		List<Product> productList = new ArrayList<Product>();
		StringBuilder strErrAll = new StringBuilder();
		Map<Integer, String> mapIdentity = new HashMap<Integer, String>();

		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);
			StringBuilder eachLineDesc = new StringBuilder();
			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i); // 获取行(row)对象
				if (row == null || row.getCell(0) == null) {
					continue;
				}

				// 先行根据SN来确定Product;
				String sn = null;
				if (row.getCell(0) != null) {

					Cell cell = row.getCell(0); // ForMat带E的情况
					if (cell != null)
						sn = ConvertCellStr(cell, cellStr);
					if (StringUtils.isNotEmpty(sn)) {
						if (mapIdentity.containsValue(sn)) {
							eachLineDesc.append("产品编号重复;");
						}
						mapIdentity.put(i, sn);
					} else {
						// row为空的处理
						sheet.removeRow(row);
						continue;
					}

				}
				Goods gd = null;
				boolean is_flag = false;
				Product product = productDao.findBySn(sn);
				if (product != null) {
					gd = product.getGoods();
					is_flag = true;
				} else {

					gd = new Goods();
					product = new Product();

					product.setSuit(false);
					product.setFullName(null);
					product.setAllocatedStock(0);
					product.setScore(0F);
					product.setTotalScore(0L);
					product.setScoreCount(0L);
					product.setHits(0L);
					product.setIsGift(false);
					product.setIsList(false);
					product.setMarketPrice(new BigDecimal(0));
					product.setPoint(0L);
					product.setPricePoint(0L);
					product.setPrice(new BigDecimal(0));
					product.setIsMarketable(false);
					product.setIsNewFirst(false);
					product.setIsTop(false);
					product.setWeekHits(0L);
					product.setMonthHits(0L);
					product.setSales(0L);
					product.setWeekSales(0L);
					product.setMonthSales(0L);
					product.setWeekHitsDate(new Date());
					product.setMonthHitsDate(new Date());
					product.setWeekSalesDate(new Date());
					product.setMonthSalesDate(new Date());
					product.setReviews(null);
					product.setConsultations(null);
					product.setFavoriteMembers(null);
					product.setPromotions(null);
					product.setCartItems(null);
					product.setOrderItems(null);
					product.setGiftItems(null);
					product.setProductNotifies(null);
					product.setGoods(gd);

				}

				Product addproduct = new Product();

				List<ProductImage> ProductImgageAll = new ArrayList<ProductImage>();

				for (int j = 0; j < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j); // 获得单元格(cell)对象

					if (cell != null) {
						// 转换接收的单元格
						cellStr = ConvertCellStr(cell, cellStr);
						if (cellStr != null && !StringUtils.isEmpty(cellStr)) {
							try {
								// 将单元格的数据添加至一个对象
								addproduct = addingproduct(j, product, cellStr, ProductImgageAll);
								cellStr = ""; // 清空变量值

							} catch (Exception ex) {
								eachLineDesc.append(cellStr + "数据内容格式有误;");
							}
						}
					} else
						continue;

				}
				// 处理完以后最后处理
				if (ProductImgageAll != null && !ProductImgageAll.isEmpty())
					addproduct.setProductImages(ProductImgageAll);

				if (addproduct.getSn() == null)
					eachLineDesc.append("商品编号为空;");
				if (addproduct.getProductCategory() == null)
					eachLineDesc.append("系统无此商品分类;");
				if (addproduct.getName() == null)
					eachLineDesc.append("商品名称为空;");
				try {
					if(gd.getProducts().size()>1)
					{
						this.update(addproduct);
						//eachLineDesc.append(i + "行数据已经存在");
					}else
					{
						gd.getProducts().clear();
						gd.getProducts().add(addproduct);
						if(!is_flag){
							goodsService.save(gd); 
						}else{
							eachLineDesc.append(i + "行数据已经存在");
							System.out.println("------>"+i + "行数据已经存在");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					eachLineDesc.append(i + "行插入数据失败");
				}

				if (eachLineDesc.length() > 1) {
					strErrAll.append("第" + i + "行:");
					strErrAll.append(eachLineDesc);
					strErrAll.append("\r\n");
				}

				// 将添加数据后的对象填充至list中
				productList.add(addproduct);
				eachLineDesc.delete(0, eachLineDesc.length());// 清空

				cellStr = null;

			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					this.setInclusionErrList(strErrAll.toString());
					System.out.println(strErrAll.toString());
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

			}
		}
		return productList;
	}

	/**
	 * 把单元格内的类型转换至String类型
	 */
	private String ConvertCellStr(Cell cell, String cellStr) {

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:

			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {

				// 读取日期格式
				cellStr = cell.getDateCellValue().toString();

			} else {

				// 读取数字
				cellStr = String.valueOf(cell.getNumericCellValue());
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			// 读取公式

			DecimalFormat df = new DecimalFormat("0");

			cellStr = df.format(cell.getNumericCellValue());

			break;
		}
		return cellStr;
	}

	/**
	 * 获得单元格的数据添加至product
	 * 
	 * @param j
	 *            列数
	 * @param product
	 *            添加对象
	 * @param cellStr
	 *            单元格数据
	 * @return
	 */
	private Product addingproduct(int j, Product product, String cellStr, List<ProductImage> ProductImages) {
		switch (j) {

		case 0:
			// 编号 保证唯一不允许重复
			product.setSn(cellStr.trim());
			break;
		case 1:
			// 商品大分类做处理

			ProductCategory PParentCategory = pdCategoryService.findByName(cellStr);
			product.setProductCategory(PParentCategory);
			break;
		case 2: // 商品二级分类 才真实录入数据库中 MGB不做处理

		 
		/*	ProductCategory PChildCategory = pdCategoryService.findByName(cellStr);
			product.setProductCategory(PChildCategory);*/
			 
			break;
		case 3:
			product.setName(cellStr); // 名称

			break;
		case 4:
			product.setPrice((new BigDecimal(cellStr)));// 销售价

			break;
		case 5:

			long l4 = new BigDecimal(cellStr).longValue();

			product.setPricePoint(l4);// 所需萌值

			break;
		case 6:
			product.setCost((new BigDecimal(cellStr)));// 成本价

			break;
		case 7:
			product.setMarketPrice((new BigDecimal(cellStr))); // 市场价

			break;
		case 8:
			product.setUnit(cellStr); // 单位

			break;
		case 9:
			Integer it8 = new BigDecimal(cellStr).intValue();
			if (it8 == 0) // 实际情况，货物重量小于1取证整0
				it8 = 1;
			product.setWeight(new BigDecimal(it8)); // 重量
			break;
		case 10:
			Integer it9 = new BigDecimal(cellStr).intValue();

			product.setStock(it9); // 库存

			break;
		case 11:
			product.setStockMemo(cellStr); // 库存备注

			break;
		case 12:

			long l11 = new BigDecimal(cellStr).longValue();
			product.setPoint(l11);// 赠送萌值

			break;
		case 13: // 品牌
			Brand brand = brandService.findByName(cellStr);
			product.setBrand(brand);
			break;
		case 14:
			// 标签

			List<Tag> TagAll = tagService.findList(Type.product);
			List<Tag> proudctTag = null;
			if (TagAll != null && !TagAll.isEmpty()) {
				for (Tag tag : TagAll) {
					if (tag.getName().equals(cellStr)) {
						Long[] longtagId = new Long[] { tag.getId() };
						proudctTag = tagService.findList(longtagId);
						break;
					}

				}
			}
			if (proudctTag != null)
				product.setTags(new HashSet<Tag>(proudctTag));

			break;

		case 15: // 设置项是否上架
			product.setIsMarketable(cellStr.equals("是") ? true : false);

			break;
		case 16: // 设置项是否列出
			product.setIsList(cellStr.equals("是") ? true : false);
			break;

		case 17: // 设置项是否置顶
			product.setIsTop(cellStr.equals("是") ? true : false);
			break;
		case 18: // 设置项是否为赠品
			product.setIsGift(cellStr.equals("是") ? true : false);
			break;

		case 19: // 设置项是否新品首发
			product.setIsNewFirst(cellStr.equals("是") ? true : false);
			break;

		case 20:
			product.setMemo(cellStr); // 备注
			break;
		case 21:
			product.setKeyword(cellStr); // 搜索关键词

			break;
		case 22:
			product.setSeoTitle(cellStr); // 页面标题

			break;
		case 23:
			product.setSeoKeywords(cellStr); // 页面关键词

			break;
		case 24:
			product.setSeoDescription(cellStr); // 页面描述

			break;

		case 25: // 商品正面组图

			List<ProductImage> productImages = productImageService.buildSource(cellStr);

			if (productImages != null && !productImages.isEmpty())

				ProductImages.addAll(productImages);

			break;
		case 26: // 商品正面组图

			List<ProductImage> productImages1 = productImageService.buildSource(cellStr);

			if (productImages1 != null && !productImages1.isEmpty())

				ProductImages.addAll(productImages1);

			break;
		case 27: // 商品正面组图

			List<ProductImage> productImages2 = productImageService.buildSource(cellStr);

			if (productImages2 != null && !productImages2.isEmpty())

				ProductImages.addAll(productImages2);
			break;
		case 28: // 商品正面组图

			List<ProductImage> productImages3 = productImageService.buildSource(cellStr);

			if (productImages3 != null && !productImages3.isEmpty())

				ProductImages.addAll(productImages3);

			break;
		case 29: // 商品正面组图

			List<ProductImage> productImages4 = productImageService.buildSource(cellStr);

			if (productImages4 != null && !productImages4.isEmpty())

				ProductImages.addAll(productImages4);

			break;

		}

		return product;
	}

	@Override
	public String getInclusionErrList() {
		return inclusionErrList;
	}

	public void setInclusionErrList(String err) {
		this.inclusionErrList = err;
	}

	@Override
	public Page<Product> findList(ProductCategory productCategory, Brand brand, Boolean integral, OrderType orderType,
			Pageable pageable) {

		return productDao.findList(productCategory, brand, integral, orderType, pageable);
	}

	@Override
	public Page<Product> searchIntegral(String keyword, Boolean integral, OrderType orderType, Pageable pageable) {
		return productDao.searchIntegral(keyword, integral, orderType, pageable);
	}

	@Override
	public Page<Product> findIsNewFirstList(ProductCategory productCategory, Brand brand, Boolean isNewFirst,
			OrderType orderType, Pageable pageable) {
		return productDao.findIsNewFirstList(productCategory, brand, isNewFirst, orderType, pageable);
	}

	@Override
	public Page<Product> searchIsNewFirst(String keyword, Boolean isNewFirst, OrderType orderType, Pageable pageable) {
		return productDao.searchIsNewFirst(keyword, isNewFirst, orderType, pageable);
	}

	/**
	 * 根据商品ID取得商品列表，不包括套装商品
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Product> findListNoSuit(Long... ids) {
		List<Product> result = new ArrayList<Product>();
		if (ids != null) {
			for (Long id : ids) {
				Product entity = find(id);
				if (entity != null && !entity.getSuit()) {
					result.add(entity);
				}
			}
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<Product> findListNoSuit(Integer count, List<Order> orders) {
		return productDao.findListNoSuit(count, orders);
	}
	
	@Transactional(readOnly = true)
	@Cacheable("hotProduct")
	public List<Product> findListBySaleCount(Integer count,String cacheRegion){
		return  productDao.findListBySaleCount(count);
	}

	@Override
	 public Page<Product> findPageByTradeId(Long tradeShopId, Pageable pageable,String sn,ProductCategory productCategory, Brand brand,Boolean isMarketable,OrderType orderType) {
		return productDao.findPageByTradeId(tradeShopId, pageable,sn,productCategory,brand,isMarketable, orderType);
	}
	
}