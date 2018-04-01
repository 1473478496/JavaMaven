/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivebest.mall.core.common.FileInfo.FileType;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.service.FileService;
import com.vivebest.mall.core.util.FileWriter;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.entity.Attribute;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.entity.Goods;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.Parameter;
import com.vivebest.mall.entity.ParameterGroup;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Product.OrderType;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.entity.ProductImage;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Specification;
import com.vivebest.mall.entity.SpecificationValue;
import com.vivebest.mall.entity.Tag;
import com.vivebest.mall.entity.Tag.Type;
import com.vivebest.mall.entity.TieInSale;
import com.vivebest.mall.entity.TieInSaleTitle;
import com.vivebest.mall.service.BrandService;
import com.vivebest.mall.service.GoodsService;
import com.vivebest.mall.service.MemberRankService;
import com.vivebest.mall.service.ProductCategoryService;
import com.vivebest.mall.service.ProductImageService;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.PromotionService;
import com.vivebest.mall.service.SpecificationService;
import com.vivebest.mall.service.SpecificationValueService;
import com.vivebest.mall.service.TagService;
import com.vivebest.mall.service.TieInSaleCatetoryService;
import com.vivebest.mall.service.TieInSaleService;
import com.vivebest.mall.service.TieInSaleTitleService;

/**
 * Controller - 商品
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	private static Logger logger = Logger.getLogger(ProductController.class);
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@Value("${upload.dir}")
	private String uploadDir;

	@Value("${upload.template}")
	private String uploadTemplate;

	@Resource(name = "tieInSaleServiceImpl")
	private TieInSaleService tieInSaleService;
	@Resource(name = "tieInSaleTitleServiceImpl")
	private TieInSaleTitleService tieInSaleTitleService;
	@Resource(name = "tieInSaleCatetoryServiceImpl")
	private TieInSaleCatetoryService tieInSaleCatetoryService;
	@Resource(name = "adminTieInSaleController")
	private TieInSaleController tieInSaleController;

	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (productService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取参数组
	 */
	@RequestMapping(value = "/parameter_groups", method = RequestMethod.GET)
	public @ResponseBody Set<ParameterGroup> parameterGroups(Long id) {
		ProductCategory productCategory = productCategoryService.find(id);
		return productCategory.getParameterGroups();
	}

	/**
	 * 获取属性
	 */
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public @ResponseBody Set<Attribute> attributes(Long id) {
		ProductCategory productCategory = productCategoryService.find(id);
		return productCategory.getAttributes();
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		return "/admin/product/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product, Long productCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Boolean suit = false;
		for (Iterator<ProductImage> iterator = product.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		product.setProductCategory(productCategoryService.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && productService.snExists(product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}
		product.setSuit(suit);
		product.setFullName(null);
		product.setAllocatedStock(0);
		product.setScore(0F);
		product.setTotalScore(0L);
		product.setScoreCount(0L);
		product.setHits(0L);
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

		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}

		for (ParameterGroup parameterGroup : product.getProductCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}

		for (Attribute attribute : product.getProductCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}

		Goods goods = new Goods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								product.setGoods(goods);
								product.setSpecifications(new HashSet<Specification>());
								product.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(product);
							} else {
								Product specificationProduct = new Product();
								BeanUtils.copyProperties(product, specificationProduct);
								specificationProduct.setId(null);
								specificationProduct.setCreateDate(null);
								specificationProduct.setModifyDate(null);
								specificationProduct.setSn(null);
								specificationProduct.setFullName(null);
								specificationProduct.setAllocatedStock(0);
								specificationProduct.setIsList(false);
								specificationProduct.setScore(0F);
								specificationProduct.setTotalScore(0L);
								specificationProduct.setScoreCount(0L);
								specificationProduct.setHits(0L);
								specificationProduct.setWeekHits(0L);
								specificationProduct.setMonthHits(0L);
								specificationProduct.setSales(0L);
								specificationProduct.setWeekSales(0L);
								specificationProduct.setMonthSales(0L);
								specificationProduct.setWeekHitsDate(new Date());
								specificationProduct.setMonthHitsDate(new Date());
								specificationProduct.setWeekSalesDate(new Date());
								specificationProduct.setMonthSalesDate(new Date());
								specificationProduct.setGoods(goods);
								specificationProduct.setReviews(null);
								specificationProduct.setConsultations(null);
								specificationProduct.setFavoriteMembers(null);
								specificationProduct.setSpecifications(new HashSet<Specification>());
								specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
								specificationProduct.setPromotions(null);
								specificationProduct.setCartItems(null);
								specificationProduct.setOrderItems(null);
								specificationProduct.setGiftItems(null);
								specificationProduct.setProductNotifies(null);
								products.add(specificationProduct);
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService
								.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(specification);
						specificationProduct.getSpecificationValues().add(specificationValue);
					}
				}
			}
		} else {
			product.setGoods(goods);
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			products.add(product);
		}
		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.save(goods);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("product", productService.find(id));
		tieInSaleController.list(id, model);
		tieInSaleController.tieInSaleList(id, model);
		return "/admin/product/edit";
	}

	/**
	 * 调转到导入页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String listimport(Long id, ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		return "/admin/product/import";
	}

	/**
	 * 导入Excel
	 * 
	 * @param uploadFile
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	/**
	 * 导入EXCEL因为jquery.upload.js不支持@ResponseBody格式，故换这个模式输出JSON
	 * 
	 * @param uploadFile
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void fileUpLoad(@RequestParam MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException {
		// 上传后记录的文件...
		Message msg = ERROR_MESSAGE;
		String uploadPath = uploadDir + uploadFile.getOriginalFilename();
		File imageFile = new File(uploadPath);

		uploadFile.transferTo(imageFile);
		// 业务处理
		if (imageFile.exists()) {

			InputStream is = new FileInputStream(imageFile);
			// Goods goods = new Goods();
			try {
				List<Product> productlist = productService.readExcelProduct(is, null);
				logger.info("=============="+productlist.size() );
				if (productlist.size() > 0) {
					msg = SUCCESS_MESSAGE;
					logger.info("=============="+productService.getInclusionErrList());
					if(!org.springframework.util.StringUtils.isEmpty(productService.getInclusionErrList())){
						msg = ERROR_MESSAGE;
						msg.setContent(productService.getInclusionErrList());
					}
				} else {
					msg = ERROR_MESSAGE;
					msg.setContent(productService.getInclusionErrList());
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				msg = ERROR_MESSAGE;
				msg.setContent(productService.getInclusionErrList());
			}
		}
		responseOutWithJson(response, msg);
		return;
	}

	private void responseOutWithJson(HttpServletResponse response, Object responseObject)
			throws JsonProcessingException {
		// 将实体对象转换为JSON Object转换
		ObjectMapper mapper = new ObjectMapper();
		String responseJSONObject = mapper.writeValueAsString(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 导出页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	// btnFile对应页面的name属性
	public void fileExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 业务处理
		if (uploadTemplate != null) {
			// 7. 输出流

			FileWriter FW = new FileWriter();
			FW.downLoad(uploadTemplate, response, false);

		}
		return;

	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, Long productCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds,
			Long[] specificationProductIds, String[] titleCate1, Long[] productIds2, String[] titleCate3,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// Boolean suit=false;
		product.setSuit(false);
		product.setProductCategory(productCategoryService.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		Product pProduct = productService.find(product.getId());
		if (pProduct == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && !productService.snUnique(pProduct.getSn(), product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}

		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}

		for (ParameterGroup parameterGroup : product.getProductCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}

		for (Attribute attribute : product.getProductCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}

		Goods goods = pProduct.getGoods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								BeanUtils.copyProperties(product, pProduct,
										new String[] { "id", "createDate", "modifyDate", "fullName", "allocatedStock",
												"score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits",
												"sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate",
												"weekSalesDate", "monthSalesDate", "goods", "reviews", "consultations",
												"favoriteMembers", "specifications", "specificationValues",
												"promotions", "cartItems", "orderItems", "giftItems",
												"productNotifies" });
								pProduct.setSpecifications(new HashSet<Specification>());
								pProduct.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(pProduct);
							} else {
								if (specificationProductIds != null && j < specificationProductIds.length) {
									Product specificationProduct = productService.find(specificationProductIds[j]);
									if (specificationProduct == null || (specificationProduct.getGoods() != null
											&& !specificationProduct.getGoods().equals(goods))) {
										return ERROR_VIEW;
									}
									specificationProduct.setSpecifications(new HashSet<Specification>());
									specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
									products.add(specificationProduct);
								} else {
									Product specificationProduct = new Product();
									BeanUtils.copyProperties(product, specificationProduct);
									specificationProduct.setId(null);
									specificationProduct.setCreateDate(null);
									specificationProduct.setModifyDate(null);
									specificationProduct.setSn(null);
									specificationProduct.setFullName(null);
									specificationProduct.setAllocatedStock(0);
									specificationProduct.setIsList(false);
									specificationProduct.setScore(0F);
									specificationProduct.setTotalScore(0L);
									specificationProduct.setScoreCount(0L);
									specificationProduct.setHits(0L);
									specificationProduct.setWeekHits(0L);
									specificationProduct.setMonthHits(0L);
									specificationProduct.setSales(0L);
									specificationProduct.setWeekSales(0L);
									specificationProduct.setMonthSales(0L);
									specificationProduct.setWeekHitsDate(new Date());
									specificationProduct.setMonthHitsDate(new Date());
									specificationProduct.setWeekSalesDate(new Date());
									specificationProduct.setMonthSalesDate(new Date());
									specificationProduct.setGoods(goods);
									specificationProduct.setReviews(null);
									specificationProduct.setConsultations(null);
									specificationProduct.setFavoriteMembers(null);
									specificationProduct.setSpecifications(new HashSet<Specification>());
									specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
									specificationProduct.setPromotions(null);
									specificationProduct.setCartItems(null);
									specificationProduct.setOrderItems(null);
									specificationProduct.setGiftItems(null);
									specificationProduct.setProductNotifies(null);
									products.add(specificationProduct);
								}
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService
								.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(specification);
						specificationProduct.getSpecificationValues().add(specificationValue);
					}
				}
			}
		} else {
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			BeanUtils.copyProperties(product, pProduct,
					new String[] { "id", "createDate", "modifyDate", "fullName", "allocatedStock", "score",
							"totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales",
							"monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "goods",
							"reviews", "consultations", "favoriteMembers", "promotions", "cartItems", "orderItems",
							"giftItems", "productNotifies" });
			products.add(pProduct);
		}

		// 商品搭配组合 b ding.hy
		// 清掉原来的商品搭配销售
		List<TieInSale> tis = tieInSaleService.findByProductId(product.getId());
		if (tis != null) {
			Long[] ids = new Long[tis.size()];
			for (int i = 0; i < tis.size(); i++) {
				if (tis.get(i).getProductSuit() == null) {
					ids[i] = tis.get(i).getId();
				}
			}
			tieInSaleService.delete(ids);
		}

		if (titleCate1 != null || productIds2 != null || titleCate3 != null) {
			if (titleCate1 != null) {
				for (String str : titleCate1) {
					// 推荐配件标题
					List<TieInSaleTitle> tsts = tieInSaleTitleService.findByCatetoryTitle(1l,
							str.substring(0, str.indexOf("_")));
					// 判断 标题存在则不保存，不存在时执行保存标题
					if (tsts.size() == 0) {
						TieInSaleTitle tie = new TieInSaleTitle();
						tie.setTieInSaleCatetory(tieInSaleCatetoryService.find(1l));
						tie.setTitle(str.substring(0, str.indexOf("_")));
						tieInSaleTitleService.save(tie);
					}

					// 搭配销售
					TieInSale tieInSale = new TieInSale();
					tieInSale.setProduct(product);
					tieInSale.setTieInSaleTitle(
							tieInSaleTitleService.findByCatetoryTitle(1l, str.substring(0, str.indexOf("_"))).get(0));
					tieInSale.setTelIn(productService.find(Long.parseLong(str.substring(str.indexOf("_") + 1))));
					tieInSaleService.save(tieInSale);
				}
			}

			// 最佳组合商品
			if (productIds2 != null) {
				for (Long id : productIds2) {
					// 自由组合空标题
					List<TieInSaleTitle> tsts = tieInSaleTitleService.findByCatetoryTitle(3l, "");
					// 判断 标题存在则不保存，不存在时执行保存标题
					if (tsts.size() == 0) {
						TieInSaleTitle tie = new TieInSaleTitle();
						tie.setTieInSaleCatetory(tieInSaleCatetoryService.find(3l));
						tie.setTitle("");
						tieInSaleTitleService.save(tie);
					}
					// 搭配销售
					TieInSale tieInSale = new TieInSale();
					tieInSale.setProduct(product);
					tieInSale.setTelIn(productService.find(id));
					tieInSale.setTieInSaleTitle(tieInSaleTitleService.findByCatetoryTitle(3l, "").get(0));
					tieInSaleService.save(tieInSale);
				}
			}
		}
		// 商品搭配组合 e
		// tieInSaleController.addTieInSales(productSuit, proId, productId3,
		// tieInSaleTitle);

		//goods.getProducts().clear();

		// goods.getProducts().addAll(products);
		Goods goodsSave = new Goods();
		List<Product> products2 = new ArrayList<Product>();
		List<Product> products3 = new ArrayList<Product>();
		int jjj = 0;
		for (Product product2 : products) {
			if (specificationProductIds != null && jjj < specificationProductIds.length) {
				products2.add(product2);
			} else {
				products3.add(product2);
			}
			jjj++;
		}
		if (products2.size() > 0) {
			goods.getProducts().addAll(products2);
			goodsService.update(goods);
		}

		if (products3.size() > 0) {
			goodsSave.getProducts().addAll(products3);
			goodsService.save(goodsSave);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, Long brandId, Long promotionId, Long tagId, Boolean isStore,
			Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean isNewFirst, Pageable pageable, ModelMap model) {
		Boolean flag = false;
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagId);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("promotions", promotionService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("brandId", brandId);
		model.addAttribute("promotionId", promotionId);
		model.addAttribute("tagId", tagId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("isList", isList);
		model.addAttribute("isTop", isTop);
		model.addAttribute("isGift", isGift);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("isStockAlert", isStockAlert);
		model.addAttribute("isNewFirst", isNewFirst);
		model.addAttribute("flag", flag);

		// return "/admin/product/list_new";
		model.addAttribute("page",
				productService.findPage(productCategory, brand, promotion, tags, isStore, null, null, null,
						isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, isNewFirst, OrderType.dateDesc,
						pageable, flag));

		return "/admin/product/list";
	}

	/**
	 * 导出数据
	 */
	@RequestMapping(value = "/exportData", method = RequestMethod.POST)
	public void exportData(Long productCategoryId, Long brandId, Long promotionId, Long tagId, Boolean isStore,
			Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
		Boolean flag = false;
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagId);

		List<Product> proudctList = productService.findList(productCategory, brand, promotion, tags, null, null, null,
				isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, OrderType.dateDesc, null, null,
				pageable.getOrders(), flag);

		String[] headers = { "商品编号", "商品分类", "商品二级分类", "名称", "销售价", "所需萌值", "成本价", "市场价", "单位", "重量", "库存", "库存备注",
				"赠送萌值", "品牌", "标签", "是否上架", "是否列出", "是否置顶", "是否赠品", "是否新品首发", "备注", "搜索关键词", "页面标题", "页面关键词", "页面描述"

		};

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CANADA);
		String filedisplay = "产品列表" + sdf.format(new Date()) + ".xls";
		String title = "产品列表";

		try {
			productExportExcel(request, response, filedisplay, title, headers, proudctList, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void productExportExcel(HttpServletRequest request, HttpServletResponse response, String filedisplay,
			String title, String[] headers, Collection<Product> dataset, String pattern) throws Exception {
		// 声明一个工作薄
		Workbook workbook = new HSSFWorkbook();

		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		CellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		Font font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行
		Row row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<Product> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			Product t = it.next();
			if (t != null) {

				try {
					index++;

					row = sheet.createRow(index);

					setCellValues(workbook, row, style2, t.getSn(), 0); // 商品编号

					if (t.getProductCategory() != null && t.getProductCategory().getParent() != null) {
						setCellValues(workbook, row, style2, t.getProductCategory().getParent().getName(), 1);// 商品分类

						setCellValues(workbook, row, style2, t.getProductCategory().getName(), 2); // 商品二级分类
					} else if (t.getProductCategory() != null) {
						setCellValues(workbook, row, style2, t.getProductCategory().getName(), 1); // 商品一级分类

						setCellValues(workbook, row, style2, "", 2); // 商品二级分类
					}

					setCellValues(workbook, row, style2, t.getName(), 3); //
					setCellValues(workbook, row, style2, t.getPrice() == null ? "" : t.getPrice().toString(), 4);
					setCellValues(workbook, row, style2, t.getPricePoint() == null ? "" : t.getPricePoint().toString(),
							5);
					setCellValues(workbook, row, style2, t.getCost() == null ? "" : t.getCost().toString(), 6);
					setCellValues(workbook, row, style2,
							t.getMarketPrice() == null ? "" : t.getMarketPrice().toString(), 7);
					setCellValues(workbook, row, style2, t.getUnit() == null ? "" : t.getUnit().toString(), 8);

					setCellValues(workbook, row, style2, t.getWeight() == null ? "" : t.getWeight().toString(), 9);
					setCellValues(workbook, row, style2, t.getStock() == null ? "" : t.getStock().toString(), 10);
					setCellValues(workbook, row, style2, t.getStockMemo(), 11);
					setCellValues(workbook, row, style2, t.getPoint() == null ? "" : t.getPoint().toString(), 12);

					setCellValues(workbook, row, style2, t.getBrand() == null ? "" : t.getBrand().getName(), 13);
					setCellValues(workbook, row, style2, "", 14);
					setCellValues(workbook, row, style2, t.getIsMarketable() == true ? "是" : "否", 15);
					setCellValues(workbook, row, style2, t.getIsList() == true ? "是" : "否", 16);
					setCellValues(workbook, row, style2, t.getIsTop() == true ? "是" : "否", 17);
					setCellValues(workbook, row, style2, t.getIsGift() == true ? "是" : "否", 18);
					setCellValues(workbook, row, style2, t.getIsNewFirst() == true ? "是" : "否", 19);
					setCellValues(workbook, row, style2, t.getMemo(), 20);
					setCellValues(workbook, row, style2, t.getKeyword(), 21);
					setCellValues(workbook, row, style2, t.getSeoTitle(), 22);
					setCellValues(workbook, row, style2, t.getSeoKeywords(), 23);
					setCellValues(workbook, row, style2, t.getSeoDescription(), 24);

				} catch (SecurityException e) {
					logger.error(e);
				} catch (Exception e) {
					logger.error(e);
				}

			}
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/x-msdownload");
		// filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filedisplay.getBytes(), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}

	/**
	 * 利用正则表达式判断textValue是否全部由数字组成
	 * 
	 * @param workbook
	 * @param cell
	 * @param textValue
	 */
	private void setCellValues(Workbook workbook, Row row, CellStyle style2, String textValue, int sn) {
		// 利用正则表达式判断textValue是否全部由数字组成
		Cell cell = row.createCell(sn);
		cell.setCellStyle(style2);
		if (textValue != null) {
			Pattern p = Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher = p.matcher(textValue);
			if (matcher.matches()) {
				// 是数字当作double处理
				cell.setCellValue(Double.parseDouble(textValue));
			} else {
				HSSFRichTextString richString = new HSSFRichTextString(textValue);
				Font font3 = workbook.createFont();
				font3.setColor(HSSFColor.BLUE.index);
				richString.applyFont(font3);
				cell.setCellValue(richString);
			}
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		productService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 计算默认市场价
	 * 
	 * @param price
	 *            价格
	 */
	private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultMarketPriceScale = setting.getDefaultMarketPriceScale();
		return setting.setScale(price.multiply(new BigDecimal(defaultMarketPriceScale.toString())));
	}

	/**
	 * 计算默认积分
	 * 
	 * @param price
	 *            价格
	 */
	private long calculateDefaultPoint(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultPointScale = setting.getDefaultPointScale();
		return price.multiply(new BigDecimal(defaultPointScale.toString())).longValue();
	}

	/**
	 * 独立商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				map.put("price", product.getPrice());
				map.put("pricePoint", product.getPricePoint());
				data.add(map);
			}
		}
		return data;
	}

}