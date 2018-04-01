/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Attribute;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.entity.Goods;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Product.OrderType;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Tag;

/**
 * Service - 商品
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ProductService extends BaseService<Product, Long> {

	/**
	 * 判断商品编号是否存在
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品编号是否存在
	 */
	boolean snExists(String sn);

	/**
	 * 根据商品编号查找商品
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */
	Product findBySn(String sn);

	/**
	 * 判断商品编号是否唯一
	 * 
	 * @param previousSn
	 *            修改前商品编号(忽略大小写)
	 * @param currentSn
	 *            当前商品编号(忽略大小写)
	 * @return 商品编号是否唯一
	 */
	boolean snUnique(String previousSn, String currentSn);

	/**
	 * 通过ID、编号、全称查找商品
	 * 
	 * @param keyword
	 *            关键词
	 * @param isGift
	 *            是否为赠品
	 * @param count
	 *            数量
	 * @return 商品
	 */
	List<Product> search(String keyword, Boolean isGift, Integer count);

	/**
	 * 通过ID、编号、全称查找商品
	 * 
	 * @param keyword
	 *            关键词
	 * @param suit
	 *            是否为套装商品
	 * @param count
	 *            数量
	 * @return 商品
	 */
	List<Product> searchSuit(String keyword, Boolean suit, Integer count);
	
	/**
	 * 商品搜索
	 * @param keyword
	 * 			关键词
	 * @param orderType
	 * 			排序类型
	 * @param isStore
	 * 			库存
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<Product> searchAll(String keyword, OrderType orderType,Boolean isStore, Pageable pageable);
	
	/**
	 * 查找商品
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param brand
	 *            品牌
	 * @param promotion
	 *            促销
	 * @param tags
	 *            标签
	 * @param attributeValue
	 *            属性值
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isGift
	 *            是否为赠品
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @param orderType
	 *            排序类型
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品
	 */
	List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, Boolean isSuit);

	/**
	 * 查找商品(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param brand
	 *            品牌
	 * @param promotion
	 *            促销
	 * @param tags
	 *            标签
	 * @param attributeValue
	 *            属性值
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isGift
	 *            是否为赠品
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @param orderType
	 *            排序类型
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品(缓存)
	 */
	List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion, Boolean isSuit);

	/**
	 * 查找已上架商品
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 已上架商品
	 */
	List<Product> findList(ProductCategory productCategory, Date beginDate, Date endDate, Integer first, Integer count);

	/**
	 * 查找纯萌值积分商品
	 * 
	 * @param productCategory
	 *            分类
	 * @param brand
	 *            品牌
	 * @param integral
	 *            纯积分
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 纯萌值商品列表
	 */
	Page<Product> findList(ProductCategory productCategory, Brand brand, Boolean integral, OrderType orderType,
			Pageable pageable);

	/**
	 * 通过ID、编号、全称查找纯萌值积分商品
	 * 
	 * @param keyword
	 *            关键词
	 * @param suit
	 *            是否为套装商品
	 * @param count
	 *            数量
	 * @return 商品
	 */
	Page<Product> searchIntegral(String keyword, Boolean integral, OrderType orderType, Pageable pageable);

	/**
	 * 查找商品销售信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 商品销售信息
	 */
	List<Object[]> findSalesList(Date beginDate, Date endDate, Boolean isPoint, Integer count);

	/**
	 * 查找商品分页
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param brand
	 *            品牌
	 * @param promotion
	 *            促销
	 * @param tags
	 *            标签
	 * @param attributeValue
	 *            属性值
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isGift
	 *            是否为赠品
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @param isNewFirst
	 *            是否新品首发
	 * @param orderType
	 *            排序类型
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Product> findPage(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Boolean isStore, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice,
			Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean isNewFirst, OrderType orderType, Pageable pageable, Boolean suit);

	/**
	 * 查找收藏商品分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收藏商品分页
	 */
	Page<Product> findPage(Member member, Pageable pageable);

	/**
	 * 查询商品数量
	 * 
	 * @param favoriteMember
	 *            收藏会员
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isGift
	 *            是否为赠品
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @return 商品数量
	 */
	Long count(Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift,
			Boolean isOutOfStock, Boolean isStockAlert);

	/**
	 * 判断会员是否已购买该商品
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @return 是否已购买该商品
	 */
	boolean isPurchased(Member member, Product product);

	/**
	 * 查看并更新点击数
	 * 
	 * @param id
	 *            ID
	 * @return 点击数
	 */
	long viewHits(Long id);

	/**
	 * 读取Excel商品
	 * 
	 * @param inp
	 * @return
	 */
	List<Product> readExcelProduct(InputStream inp, Goods gd);

	/**
	 * 获取导入EXCEL相关错误
	 * 
	 * @return
	 */
	String getInclusionErrList();

	/**
	 * 查找新品首发商品
	 * 
	 * @param productCategory
	 *            分类
	 * @param brand
	 *            品牌
	 * @param isNewFirst
	 *            是否为新品首发
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 纯萌值商品列表
	 */
	Page<Product> findIsNewFirstList(ProductCategory productCategory, Brand brand, Boolean isNewFirst,
			OrderType orderType, Pageable pageable);
	/**
	 * 通过ID、编号、全称查找新品首发商品
	 * 
	 * @param keyword
	 *            关键词
	 * @param suit
	 *            是否为套装商品
	 * @param count
	 *            数量
	 * @return 商品
	 */
	Page<Product> searchIsNewFirst(String keyword, Boolean isNewFirst, OrderType orderType, Pageable pageable);
	
	
	/**
	 * 根据商品ID取得商品列表，不包括套装商品
	 * 
	 * @param ids
	 * @return
	 */
	List<Product> findListNoSuit(Long... ids);
	
	/**
	 * 取得商品列表，不钖套装商品
	 * @param count
	 * @param orders
	 * @return
	 */
	List<Product> findListNoSuit(Integer count, List<Order> orders);
	
	/**
	 * 热榜
	 * @param count
	 * @param orders
	 * @return
	 */
	List<Product> findListBySaleCount(Integer count,String cacheRegion);
	/**
	 * 商户中心获取商品列表
	 * @param trade_id
	 */
	Page<Product> findPageByTradeId(Long tradeShopId,Pageable pageable,String sn,ProductCategory productCategory, Brand brand,Boolean isMarketable,OrderType orderType);
}