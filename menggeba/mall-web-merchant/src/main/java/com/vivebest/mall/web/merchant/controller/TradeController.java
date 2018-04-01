package com.vivebest.mall.web.merchant.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.entity.TradeProducts;
import com.vivebest.mall.merchant.entity.ListEntity;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeBanks;
import com.vivebest.mall.merchant.entity.TradeProxy;
import com.vivebest.mall.merchant.service.TradeBanksService;
import com.vivebest.mall.merchant.service.TradeProxyService;
import com.vivebest.mall.merchant.service.TradeService;
import com.vivebest.mall.service.ProductCategoryService;
import com.vivebest.mall.service.TradeProductsService;
/**
 * 商品入驻
 * @author du
 *
 */

@Controller("tradeController")
@RequestMapping("/trade")
public class TradeController extends BaseController {
		
			@Resource(name = "tradeServiceImpl")
			private TradeService tradeService;
			
			@Resource(name = "tradeProxyServiceImpl")
			private TradeProxyService tradeProxyService;
			
			@Resource(name = "productCategoryServiceImpl")
			private	ProductCategoryService productCategoryService;
			
			@Resource(name = "tradeBanksServiceImpl")
			private	TradeBanksService tradeBanksService;
			
			@Resource(name = "tradeProductsServiceImpl")
			private	TradeProductsService tradeProductsService;
			
			public static final String FILE_PATH = "/resources/images/uploadImg/";
		
			/**
			 * 跳转  新增商户入驻 页面
			 * 
			 */
			@RequestMapping(value = "/shop_apply")
			public String shopTrade(ModelMap model) throws ServiceException, IOException {
					//查询商品分类
					List<ProductCategory> productCategoryList=productCategoryService.categoryFindRoots();
					model.put("lists", productCategoryList);
				return "/trade/shop_apply";
			}
			
			
			/**
			 * 保存商品入驻信息
			 * 
			 * @param trade 商户
			 * @param tradeProxy 代理人
			 * @param tradeBanks 开户银行
			 * @param model
			 * @return
			 * @throws ServiceException
			 * @throws IOException
			 */
			@RequestMapping(value = "/trade_save",method = {RequestMethod.POST})
			@ResponseBody
			public String trade_save(Long[] dd,ListEntity listEntity,HttpServletRequest request, ModelMap model) throws ServiceException, IOException {
					Trade trade=null;
						if(null!=listEntity){
					     trade=listEntity.getTrade();
					     //组织机构营业证照片  写入数据库字段
					     trade.setGroup_photo(request.getParameter("image_1"));
				
					     //法人身份证招片  写入数据库字段
					    trade.setCert_photos(request.getParameter("apply_1"));
					     trade.setApply_date(new Date());
					     	//保存商户信息
							tradeService.save(trade);
									
							//银行开户  多个
							TradeBanks banks=listEntity.getBanks();
					  		List<TradeBanks> tradeBank=listEntity.getTradeBanks();
					  				if(null!=tradeBank){
					  			tradeBank.add(banks);
					  				for (TradeBanks tradeBanks : tradeBank) {
					  					tradeBanks.setTrade(trade);
					  						tradeBanksService.save(tradeBanks);
							} 
					  	}
					  	//代理人信息 多个		
					  	List<TradeProxy> tradeProxys=listEntity.getTradeProxy();
					  		if(null!=tradeProxys){
					  			for (TradeProxy tradeProxy : tradeProxys) {
					  				tradeProxy.setTrade(trade);
					  					tradeProxyService.save(tradeProxy);
					  				}
					  		}
						}
					
				    /**
				     * 保存商户商品分类
				     */
				    if(null!=dd&&dd.length>0){
				    for (int i = 0; i < dd.length; i++) {
					    TradeProducts products=new TradeProducts();
					    products.setCreateDate(new Date());
					    products.setModifyDate(new Date());
					    products.setProduct_category_id(dd[i]);
					    products.setTrade_id(trade.getId());
					    tradeProductsService.save(products);
					}
				  }
					
				    return "/trade/apply_info.do";
				}
	
	
				/**
				 * 商户信息
				 * 
				 * @param tradeId 商户id
				 * @param request
				 * @param response
				 * @param model
				 * @param session
				 */
				@RequestMapping(value = "/apply_info")
				public String trade_info(String tradeId, ModelMap model,HttpSession session)
						throws ServiceException, IOException {
			
					Long id = Long.parseLong(tradeId != null ? tradeId : "116");
					//根据商户id查询
					Trade trade = tradeService.findByTradeId(id);
					
					//获取商家商品分类
					List<Long> x=tradeProductsService.findList(id);
					List<ProductCategory> categoryList=new ArrayList<ProductCategory>();
					for (Long long1 : x) {
						ProductCategory category=productCategoryService.find(long1);
						categoryList.add(category);
					}
					//获取商家 开户银行信息  多个
					Set<TradeBanks> tradeBanksSet=trade.getTradeBanks();
						model.addAttribute("tradeBanksSet", tradeBanksSet);
					//获取商家代理人信息  多个
					Set<TradeProxy> tradeProxySet=trade.getTradeProxy();
					model.addAttribute("tradeProxySet", tradeProxySet);
					model.addAttribute("categorylist", categoryList);
					model.addAttribute("trade", trade);
					session.setAttribute("trade", trade);
					
					return "/trade/shoperinfo";
				}
				
	
	
				/**
				 * 商户信息 维护
				 * @param banksId 银行开户Id
				 * @param proxyId 代理人ID
				 * @param tradeId  商户ID
				 * @param listEntity 商户大类
				 * @param model
				 */
				@RequestMapping(value = "/apply_update")
				public String trade_update(Long banksId,Long proxyId,Long tradeId,ListEntity listEntity)
						throws ServiceException, IOException {
					TradeBanks banks=tradeBanksService.find(banksId);
					if(null!=banks){
							tradeBanksService.delete(banks);
						}
					TradeProxy proxy=tradeProxyService.find(proxyId);
						if(null!=proxy){
							tradeProxyService.delete(proxy);
						}
					
			     if(null!=listEntity){
					Trade trade=tradeService.find(tradeId);
					
					List<TradeBanks> tradeBank=listEntity.getTradeBanks();
						if(null!=tradeBank){
						for (TradeBanks tradeBanks : tradeBank) {
								tradeBanks.setTrade(trade);
								tradeBanksService.save(tradeBanks);
							} 
						}
						
				    List<TradeProxy> tradeProxys=listEntity.getTradeProxy();
							if(null!=tradeProxys){
								for (TradeProxy tradeProxy : tradeProxys) {
									tradeProxy.setTrade(trade);
									tradeProxyService.save(tradeProxy);
								}
						}
					}
					return "redirect:/trade/apply_info.do";
				}
						
				
			/**
			 * 图片上传
			 * @throws ServiceException,IOException 
			 */
			@RequestMapping(value = "/upload_img", method = RequestMethod.POST)
			public @ResponseBody
			Map<String, Object> upload_img(HttpServletRequest request,@RequestParam("file") MultipartFile[] file) throws ServiceException,IOException {
				Map<String, Object> json = new HashMap<String, Object>();
				try {
					for (int i = 0; i < file.length; i++) {
						// 获取上传图片服务器的跟路径
						String realPath = request.getSession().getServletContext().getRealPath(FILE_PATH);
						//将上传队列的第一张图片写入到图片服务器下
							FileUtils.copyInputStreamToFile(file[i].getInputStream(),new File(realPath,file[i].getOriginalFilename()));
							json.put("flag", SUCCESS_MESSAGE);
							json.put("image", FILE_PATH+file[i].getOriginalFilename());
					}
				} catch (Exception e) {
					json.put("flag", ERROR_MESSAGE);
				}
				return json;
			}
	}
