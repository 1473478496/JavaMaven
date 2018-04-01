package com.vivebest.mall.web.merchant.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeAdmin;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;
import com.vivebest.mall.merchant.service.TradeAdminService;
import com.vivebest.mall.merchant.service.TradeService;
import com.vivebest.mall.merchant.service.TradeShopService;
@Controller("tradeShopController")
@RequestMapping("/tradeShop")
public class TradeShopController  extends BaseController{
	
	private static final Logger logger = Logger.getLogger(TradeShopController.class);
	
	@Resource(name = "tradeServiceImpl")
	private TradeService tradeService;
	
	@Resource(name = "tradeShopServiceImpl")
	private TradeShopService tradeShopService;
	
	@Resource(name = "tradeAdminServiceImpl")
	private TradeAdminService tradeAdminService;
	
	
	public static final String FILE_PATH = "/resources/images/uploadImg/";
	// 店铺开启状态
	public static final int SHOP_OPEN_STATUS = 0;
	// 店铺关闭状态
	public static final int SHOP_CLOSE_STATUS = 1;
	
	
	/**
	 * 图片上传
	 * @throws ServiceException,IOException 
	 */
	@RequestMapping(value = "/upload_img", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> upload_img(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws ServiceException,IOException {
		
		Map<String, Object> json = new HashMap<String, Object>();
		
		try {
			// 获取上传图片服务器的跟路径
			String realPath = request.getSession().getServletContext().getRealPath(FILE_PATH);
			
			String oldName = file.getOriginalFilename();
			
			String newName = System.currentTimeMillis()+oldName.substring(oldName.lastIndexOf("."));
			//将上传队列的第一张图片写入到图片服务器下
			FileUtils.copyInputStreamToFile(file.getInputStream(),new File(realPath,newName));
			
			json.put("flag", SUCCESS_MESSAGE);
			
			json.put("image", FILE_PATH+newName);
			
			logger.info("The store logo uploaded successfully");
		} catch (Exception e) {
			
			json.put("flag", ERROR_MESSAGE);
			
			logger.error("The Store logo upload fails");
		}
		
		return json;
	}
	/**
	 * 保存店铺信息
	 * @param tradeShop 店铺信息
	 * @param trade_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	@RequestMapping(value = "/shopset_save",method=RequestMethod.POST)
	public String save_shopSetting(TradeShop tradeShop,String trade_id,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws ServiceException, IOException {
		// 如果进入店铺设置页面，则session中一定存在Trade对象
		// 判断表单中传入的店铺的ID是否存在
		// 拿到商户
		Trade trade = (Trade)request.getSession().getAttribute("trade");
		
		String address = tradeShop.getAddr().replace(",", "");
		
		tradeShop.setTrade(trade);
		
		tradeShop.setAddr(address);
		// 获取图片的路径
		tradeShop.setLogo_url(request.getParameter("image_1"));
				
		tradeShop.setApply_date(new Date());

		if(tradeShop.getId()!=null){
			TradeShop shop = tradeShopService.find(tradeShop.getId());
			shop.setAddr(address);
			shop.setLogo_url(tradeShop.getLogo_url());
			shop.setName(tradeShop.getName());
			shop.setBegin_worktime(tradeShop.getBegin_worktime());
			shop.setEnd_worktime(tradeShop.getEnd_worktime());
			shop.setPhone(tradeShop.getPhone());
			shop.setSearchKey(tradeShop.getSearchKey());
			shop.setCustomer_service(tradeShop.getCustomer_service());
			tradeShopService.update(shop);
		}else{
			tradeShop.setStatus(Status.close);
			tradeShop.setPlatStatus(PlatStatus.open);
			tradeShopService.save(tradeShop);
		}
/*//		Trade trade = tradeService.find(Long.valueOf(trade_id));
		Long id = tradeShopService.getIdentifier(tradeShop);
		// 根据存入的Id拿到刚存入的对象
		tradeShop = tradeShopService.find(id);
		// 将对象放回到页面
		model.put("tradeShop", tradeShop);*/
		
		
		return "redirect:/tradeShop/shop_setting.do";
	}
	
	/**
	 * 跳转到店铺设置页面
	 * @param tradeId
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	@RequestMapping(value = "/shop_setting")
	public String trade_setting(String tradeId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model,HttpSession session)throws ServiceException, IOException{
		TradeShop tradeShop = null;
		TradeAdmin admin = tradeAdminService.getCurrent();
		// 获取当前登入的商户ID
		Long id = admin.getTrade().getId();
		
		Trade trade =  tradeService.find(id);
		// 获取店铺信息
		Set<TradeShop> shop = trade.getTradeShops();
		// 如果店铺信息存在，则获取店铺信息
		Iterator<TradeShop> iterator = shop.iterator();
		if(iterator.hasNext()){
			tradeShop = iterator.next();
			model.put("tradeShop", tradeShop);
		}
		
		model.put("trade", trade);
		model.put("admin", admin);
		session.setAttribute("trade", trade);
		
		return "trade/shop_setting";
	}
	/**
	 * 跳转到店铺总览页面
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * 
	 * @return 视图名
	 */
	@RequestMapping(value = "/shop_scan")
	public String trade_scan(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,HttpSession session){
		// 根据用户的用户名，获取用户的id，进而获取商户的Id
	
		/*Principal principal = (Principal)session.getAttribute(TradeAdmin.class.getName());
		TradeAdmin tradeAdmin = tradeAdminService.findByUsername(principal.getUsername());*/
		TradeAdmin admin = tradeAdminService.find(1L);
		
		Trade trade = admin.getTrade();
		
		model.put("trade", trade);

		model.put("tradeAdmin", admin);
		
		return "trade/shop_index";
	}
	/**
	 * 开启店铺
	 * @param id 店铺ID
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/openShop",method=RequestMethod.GET)
	public String open_shop(String id,HttpServletRequest request,
			HttpServletResponse response, ModelMap model,HttpSession session){
		Long shopId = Long.parseLong(id);
		
		TradeShop tradeShop = tradeShopService.find(shopId);
		
		tradeShop.setStatus(Status.open);
		
		tradeShopService.update(tradeShop);
		
		return "redirect:/tradeShop/shop_setting.do";
	}
	/**
	 * 关闭店铺
	 * @param id 店铺ID
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="closeShop",method=RequestMethod.GET)
	public String close_shop(String id,HttpServletRequest request,
			HttpServletResponse response, ModelMap model,HttpSession session){
		Long shopId = Long.parseLong(id);
		
		TradeShop tradeShop = tradeShopService.find(shopId);
		
		tradeShop.setStatus(Status.close);
		
		tradeShopService.update(tradeShop);
		
		return "redirect:/tradeShop/shop_setting.do";
	}
}
