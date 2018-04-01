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
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.dao.ReviewDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Review;
import com.vivebest.mall.entity.Review.Type;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.ReviewService;
import com.vivebest.mall.service.StaticService;

/**
 * Service - 评论
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("reviewServiceImpl")
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements
		ReviewService {

	@Resource(name = "reviewDaoImpl")
	private ReviewDao reviewDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "reviewDaoImpl")
	public void setBaseDao(ReviewDao reviewDao) {
		super.setBaseDao(reviewDao);
	}

	private String inclusionErrList;

	@Transactional(readOnly = true)
	public List<Review> findList(Member member, Product product, Type type,
			Boolean isShow, Integer count, List<Filter> filters,
			List<Order> orders) {
		return reviewDao.findList(member, product, type, isShow, count,
				filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable("review")
	public List<Review> findList(Member member, Product product, Type type,
			Boolean isShow, Integer count, List<Filter> filters,
			List<Order> orders, String cacheRegion) {
		return reviewDao.findList(member, product, type, isShow, count,
				filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Review> findPage(Member member, Product product, Type type,
			Boolean isShow, Pageable pageable) {
		return reviewDao.findPage(member, product, type, isShow, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Product product, Type type, Boolean isShow) {
		return reviewDao.count(member, product, type, isShow);
	}

	@Transactional(readOnly = true)
	public boolean isReviewed(Member member, Product product) {
		return reviewDao.isReviewed(member, product);
	}

	@Transactional(readOnly = true)
	public boolean isReviewed(Member member, Product product, String sn) {
		return reviewDao.isReviewed(member, product, sn);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public void save(Review review) {
		super.save(review);
		Product product = review.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public Review update(Review review) {
		Review pReview = super.update(review);
		Product product = pReview.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public Review update(Review review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public void delete(Review review) {
		if (review != null) {
			super.delete(review);
			Product product = review.getProduct();
			if (product != null) {
				reviewDao.flush();
				long totalScore = reviewDao.calculateTotalScore(product);
				long scoreCount = reviewDao.calculateScoreCount(product);
				product.setTotalScore(totalScore);
				product.setScoreCount(scoreCount);
				productDao.merge(product);
				reviewDao.flush();
				staticService.build(product);
			}
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review",
			"consultation" }, allEntries = true)
	public void saveAll(List<Review> reviewList) {
		for (Review review : reviewList) {
			this.save(review);
		}
	}

	@Override
	public List<Review> readExcelProduct(InputStream inp) {

		List<Review> reviewList = new ArrayList<Review>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			for (int s = 0; s < wb.getNumberOfSheets(); s++) {
				Sheet sheet = wb.getSheetAt(s);
				String Name = wb.getSheetName(s);
				Product product = productDao.findBySn(Name);
				if (product == null)
					continue;
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {

					Row row = sheet.getRow(i); // 获取行(row)对象
					if (row == null || row.getCell(0) == null) {
						continue;
					}
					String sn = null;
					if (row.getCell(3) != null) {

						Cell cell = row.getCell(3); // ForMat带E的情况

						if (cell != null) {
							DecimalFormat df = new DecimalFormat("0");
							sn = df.format(cell.getNumericCellValue());
						}
						if (StringUtils.isEmpty(sn)) {
							// row为空的处理
							sheet.removeRow(row);
							continue;
						}

					}

					Review review = new Review();
					Member mm = memberService.findByUsername(sn);
					if (mm != null) {
						String headerImage = String
								.format("resources/nicihomewap/img/common_nici/myInfo1(%s).png",
										new Random().nextInt(9) + 1);
						mm.setHeaderImage(headerImage);
					}
					review.setMember(mm);
					review.setProduct(product);
					review.setSn(product.getSn());
					review.setIp("0:0:0:0:0:0:0:1");
					review.setIsShow(true);
					review.setCustScore(0);
					review.setProdScore(0);
					review.setTranScore(0);
					review.setScore(0);

					for (int j = 0; j < row.getLastCellNum(); j++) {

						Cell cell = row.getCell(j); // 获得单元格(cell)对象

						if (cell != null) {
							// 转换接收的单元格
							try {
								review = addingreview(j, review, cell);
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						} else
							continue;

					}
					reviewList.add(review);
				}
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

			}
		}

		return reviewList;
	}

	private Review addingreview(int j, Review review, Cell cell) {
		// TODO Auto-generated method stub
		switch (j) {

		case 0:

			review.setContent(cell.getStringCellValue()); // 评论内容
			break;

		case 1:
			review.setCreateDate(cell.getDateCellValue()); // 评论日期
			review.setTempDate(cell.getDateCellValue());
			break;

		case 4:
			review.setScore(new BigDecimal(cell.getNumericCellValue())
					.intValue());

			break;
		}
		return review;
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

}