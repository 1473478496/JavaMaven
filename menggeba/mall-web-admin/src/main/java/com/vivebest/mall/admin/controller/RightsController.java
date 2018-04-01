package com.vivebest.mall.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.RightOrderItem;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.entity.RightsCode;
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.service.GBMQYService;
import com.vivebest.mall.service.RightsBrandService;
import com.vivebest.mall.service.RightsCategoryService;
import com.vivebest.mall.service.RightsCodeService;
import com.vivebest.mall.service.RightsService;
import com.vivebest.mall.service.RightsTradeService;
/**
 * Controller - 权益商品
 * @author Administrator
 *
 */
@Controller("adminRightsController")
@RequestMapping("/admin/rights")
public class RightsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(RightsController.class);
	
	@Resource(name = "rightsServiceImpl")
	private RightsService rightsService;
	@Resource(name = "rightsCategoryServiceImpl")
	private RightsCategoryService rightsCategoryService;
	@Resource(name = "apiGBMQYServiceImpl")
	private GBMQYService gbmqyService;
	
	@Resource(name = "rightsCodeServiceImpl")
	private RightsCodeService rightsCodeService;
	
	@Resource(name = "rightsBrandServiceImpl")
	private RightsBrandService rightsBrandService;
	
	@Resource(name = "rightsTradeServiceImpl")
	private RightsTradeService rightsTradeService;

	@Value("${upload.dir}")
	private String uploadDir;
	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (rightsService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageabl,ModelMap model) {
		model.addAttribute("page", rightsService.findPage(pageabl));
		return "/admin/rights/list";
	}
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("rightCategoryTree", rightsCategoryService.findAll());
		model.addAttribute("rightsBrands", rightsBrandService.findAll());
		model.addAttribute("rightsTrades", rightsTradeService.findAllRightsTrade(true));
		model.addAttribute("types", RightsType.values());
		return "/admin/rights/add";
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
	
		return "/admin/rights/import";
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
			 
			try {
				List<RightsCode> rightslist = rightsCodeService.readExcelProduct(is);
				if (rightslist.size() > 0) {
					for (RightsCode rr:rightslist)
					{
						rightsCodeService.update(rr);
					}
					msg = SUCCESS_MESSAGE;
				} else {
					msg = ERROR_MESSAGE;
					//msg.setContent(productService.getInclusionErrList());
				}

			} catch (Exception ex) {
				msg = ERROR_MESSAGE;
				//msg.setContent(productService.getInclusionErrList());
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
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", RightsType.values());
		model.addAttribute("rightCategoryTree", rightsCategoryService.findAll());
		model.addAttribute("rightsBrands", rightsBrandService.findAll());
		model.addAttribute("rightsTrades", rightsTradeService.findAll());
		model.addAttribute("rights", rightsService.findById(id));
		return "/admin/rights/edit";
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		List<Rights> rightses = rightsService.findList(ids);
		if(rightses.size() == 0){
			return ERROR_MESSAGE;
		}
		for (Rights rights : rightses) {
			/*Set<RightsTrade> rightsTrades = rights.getRightsTrades();
			if (rightsTrades != null && !rightsTrades.isEmpty()) {
				return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
			}*/
			Set<RightOrderItem> orderItems = rights.getOrderItems();
			if (orderItems != null && !orderItems.isEmpty()) {
				return Message.error("存在下级订单，无法删除！");
			}
		}
		rightsService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Rights rights, Long rightsCategoryId, Long brandId, 
			Long[] rightsTradeIds,RedirectAttributes redirectAttributes){
		if(rightsTradeIds == null){
			addFlashMessage(redirectAttributes,Message.warn("admin.message.noTrade"));
			return "redirect:add.jhtml";
		}else{
			Set<RightsTrade> rightsTrades=new HashSet<RightsTrade>();
			for (int i=0;i<rightsTradeIds.length;i++){
				RightsTrade rightsTrade=rightsTradeService.find(rightsTradeIds[i]);
				rightsTrades.add(rightsTrade);
			}
			rights.setRightsTrades(rightsTrades);
		}
		if(brandId==null){
			addFlashMessage(redirectAttributes,Message.warn("admin.message.noBrand"));
			return "redirect:add.jhtml";
		}
		if(rights.getSn()==null){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = formatter.format(currentTime);
			rights.setSn(dateString);			
		}
	 
		RightsCategory rightsCategory=rightsCategoryService.find(rightsCategoryId);
		RightsBrand rightsBrand=rightsBrandService.find(brandId);

		 
		rights.setRightsCategory(rightsCategory);
		rights.setRightsBrand(rightsBrand);
		rightsService.save(rights);
		if(rights.getIsJicai()){
			Map<String, Object> map = gbmqyService.baseInfoSync(rights);
			if(map == null){
				addFlashMessage(redirectAttributes, Message.warn("权益基础数据同步失败"));
			}else{
				addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
			}
		}
		return "redirect:list.jhtml";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Rights rights, Long rightsCategoryId, Long rightsBrandId, Long[] rightsTradeIds, RedirectAttributes redirectAttributes) {
		
		Rights rights1=rightsService.find(rights.getId());
		rights.setHits(rights1.getHits());
		rights.setRightsTrades(rights1.getRightsTrades());
	 
		rights.setSn(rights1.getSn());		
		rights.setUnit(rights1.getUnit());
		rights.setTotalScore(rights1.getTotalScore());		
		rights.setRightsBrand(rightsBrandService.find(rightsBrandId));
		rights.setRightsCategory(rightsCategoryService.find(rightsCategoryId));
		if(rightsTradeIds == null){
			addFlashMessage(redirectAttributes,Message.warn("admin.message.noTrade"));
			return "redirect:add.jhtml";
		}else{
			Set<RightsTrade> rightsTrades=new HashSet<RightsTrade>();
			for (int i=0;i<rightsTradeIds.length;i++){
				RightsTrade rightsTrade=rightsTradeService.find(rightsTradeIds[i]);
				rightsTrades.add(rightsTrade);
			}
			rights.setRightsTrades(rightsTrades);
		}
		
		rightsService.update(rights);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 权益码生成页面
	 */
	@RequestMapping(value = "/build", method = RequestMethod.GET)
	public String build(Long rightsId, ModelMap model){
		model.addAttribute("rights", rightsService.findById(rightsId));
		return "/admin/rights/build";
	}
	
	/**
	 * 生成权益码
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Long rightsId, Integer count, ModelMap model, RedirectAttributes redirectAttributes){
		Rights rights = rightsService.find(rightsId);
		String code = "";
		String base = "0123456789";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
		try {
			Date initDate = format.parse("2015-06-06");
			Calendar initCalendar = Calendar.getInstance();
			Calendar currentCalendar = Calendar.getInstance();
			initCalendar.setTime(initDate);
			currentCalendar.setTime(new Date());
			int difNum = currentCalendar.get(Calendar.YEAR) - initCalendar.get(Calendar.YEAR);
			Date date = new Date();
			String dateStr = dateFormat.format(date);
			
			for (int i = 0; i < count; i++) {
				RightsCode rightsCode = new RightsCode();
				rightsCode.setRights(rights);
				rightsCode.setIsUsed(false);
				Random random = new Random();
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < 5; j++) {
				    int num = random.nextInt(base.length());
				    sb.append(base.charAt(num));
				}
				code = String.valueOf(difNum) + sb.toString() + dateStr + "80";
				rightsCode.setCode(code);
				rightsCodeService.save(rightsCode);
			}
			
		} catch (ParseException e) {
			logger.info(">>>>>Error："+e);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
}
