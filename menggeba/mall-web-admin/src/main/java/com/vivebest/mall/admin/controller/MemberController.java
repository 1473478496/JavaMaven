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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivebest.mall.core.common.CommonAttributes;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.entity.BaseEntity.Save;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.core.util.ExportUtil;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Member.Gender;
import com.vivebest.mall.entity.MemberAttribute;
import com.vivebest.mall.entity.MemberAttribute.Type;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.AreaService;
import com.vivebest.mall.service.MemberAttributeService;
import com.vivebest.mall.service.MemberRankService;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.SnService;

/**
 * Controller - 会员
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

	private static Logger logger = Logger.getLogger(MemberController.class);

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	@Value("${upload.dir}")
	private String uploadDir;

	@Value("${member.queryBudAccBal.url}")
	private String balance_url;

	@Value("${member.registerBudAccBal.url}")
	private String register_url;

	@Value("${member.MengTopUp.url}")
	private String mengtoup_url;
	
	@Value("${member.queryTotal.url}")
	private String total_url;

	@Resource(name = "snServiceImpl")
	private SnService snService;

	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody boolean checkEmail(String previousEmail, String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (memberService.emailUnique(previousEmail, email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查看
	 * 
	 * @throws ServiceException
	 * @throws IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model, HttpServletRequest request) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		model.addAttribute("member", memberService.find(id));
		String sn = memberService.find(id).getSn();
		model.addAttribute("accBalList", getBudAccBal(sn, balance_url));
		return "/admin/member/view";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "/admin/member/add";
	}

	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String importMember(ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "/admin/member/import";
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public void fileUpLoad(@RequestParam MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException { // 上传后记录的文件...
																						// Message

		Message msg = ERROR_MESSAGE;
		String uploadPath = uploadDir + uploadFile.getOriginalFilename();
		File imageFile = new File(uploadPath);

		uploadFile.transferTo(imageFile);
		// 业务处理
		if (imageFile.exists()) {

			InputStream is = new FileInputStream(imageFile);
			try {

				List<Member> memberlist = memberService.readExcelMember(is);
				if (memberlist.size() > 0) {
					for (Member mem : memberlist) {
						mem.setRegisterIp(request.getRemoteAddr());

						Map<String, Object> ReturnStatus = null;
						if (StringUtils.isEmpty(mem.getSn()) || mem.getSn() == null) // 随机生成用户号给到电子帐户注册
						{

							mem.setUsername(mem.getMobile()); // 用m手机号当作用户名
							mem.setSn(snService.generate(Sn.Type.member)); // 创建MemberSN
							ReturnStatus = registerBudAccBal(mem, register_url);
							if (!ReturnStatus.get("txStatus").equals("00")) {
								msg = ERROR_MESSAGE;
								msg.setContent("注册电子账户失败:" + ReturnStatus.get("errorMsg").toString());
								break;
							}

						}
						// 调用积分引擎赠送萌值
						ReturnStatus = mengUpAccBal(mem, mengtoup_url);
						if (ReturnStatus.get("txStatus").equals("00")) {
							memberService.save(mem);
							msg = SUCCESS_MESSAGE;
						} else {
							msg = ERROR_MESSAGE;
							msg.setContent("赠送萌值失败:" + ReturnStatus.get("errorMsg").toString());
							break;
						}

					}

				} else {
					msg = ERROR_MESSAGE;
					msg.setContent(memberService.getInclusionErrList());

				}

			} catch (Exception ex) {
				msg = ERROR_MESSAGE;
				msg.setContent(memberService.getInclusionErrList());

			}

		}
		responseOutWithJson(response, msg);

	}

	// 生成随机串
	private static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
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
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Member member, Long memberRankId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member, Save.class)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (member.getUsername().length() < setting.getUsernameMinLength()
				|| member.getUsername().length() > setting.getUsernameMaxLength()) {
			return ERROR_VIEW;
		}
		if (member.getPassword().length() < setting.getPasswordMinLength()
				|| member.getPassword().length() > setting.getPasswordMaxLength()) {
			return ERROR_VIEW;
		}
		if (memberService.usernameDisabled(member.getUsername())
				|| memberService.usernameExists(member.getUsername())) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && memberService.emailExists(member.getEmail())) {
			return ERROR_VIEW;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList()) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address
					|| memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone
					|| memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text
					|| memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter)
							? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setAmount(new BigDecimal(0));
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(null);
		member.setLoginDate(null);
		member.setSafeKey(null);
		member.setCart(null);
		member.setOrders(null);
		member.setDeposits(null);
		member.setPayments(null);
		member.setCouponCodes(null);
		member.setReceivers(null);
		member.setReviews(null);
		member.setConsultations(null);
		member.setFavoriteProducts(null);
		member.setProductNotifies(null);
		member.setInMessages(null);
		member.setOutMessages(null);
		memberService.save(member, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		model.addAttribute("member", memberService.find(id));
		model.addAttribute("accBalList", getBudAccBal(memberService.find(id).getSn(), balance_url));
		return "/admin/member/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Member member, Long memberRankId, Integer modifyPoint, BigDecimal modifyBalance,
			String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (member.getPassword() != null && (member.getPassword().length() < setting.getPasswordMinLength()
				|| member.getPassword().length() > setting.getPasswordMaxLength())) {
			return ERROR_VIEW;
		}
		Member pMember = memberService.find(member.getId());

		if (pMember == null) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(pMember.getEmail(), member.getEmail())) {
			return ERROR_VIEW;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList()) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address
					|| memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone
					|| memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text
					|| memberAttribute.getType() == Type.hobby || memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter)
							? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(pMember.getPassword());
		} else {
			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		}
		if (pMember.getIsLocked() && !member.getIsLocked()) {
			member.setLoginFailureCount(0);
			member.setLockedDate(null);
		} else {
			member.setIsLocked(pMember.getIsLocked());
			member.setLoginFailureCount(pMember.getLoginFailureCount());
			member.setLockedDate(pMember.getLockedDate());
		}

		BeanUtils.copyProperties(member, pMember,
				new String[] { "username", "point", "amount", "balance", "registerIp", "loginIp", "mobile", "loginDate",
						"safeKey", "cart", "orders", "deposits", "payments", "couponCodes", "receivers", "reviews",
						"consultations", "favoriteProducts", "productNotifies", "inMessages", "outMessages" });
		memberService.update(pMember, modifyPoint, modifyBalance, depositMemo, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findAll());
		model.addAttribute("page", memberService.findPage(pageable));
		return "/admin/member/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(new BigDecimal(0)) > 0) {
					return Message.error("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 调用积分引擎借口获取蒙值余额
	 * 
	 * @param username
	 *            会员名
	 * @param txCode
	 * @return
	 */
	private Map<String, Object> getBudAccBal(String sn, String txCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merCustNo", sn);
		params.put("acceptBizNo", this.getAcceptBizNo());
		String resMsg = null;
		try {
			resMsg = HttpClientUtil.postJson(txCode, params, "UTF-8");
		} catch (JsonProcessingException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("获取积分失败：", e);
			return null;
		}

		// 处理返回结果
		Map<String, Object> resMap = null;
		try {
			resMap = new ObjectMapper().readValue(resMsg, Map.class);
		} catch (JsonParseException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (JsonMappingException e) {
			logger.error("JSON映射失败：", e);
			return null;
		} catch (IOException e) {
			logger.error("获取蒙值失败：", e);
			return null;
		}
		List<Object> resList = (List<Object>) resMap.get("accBalList");

		Map<String, Object> temMap = null;
		if (resList != null) {
			Iterator<Object> it = resList.iterator();
			while (it.hasNext()) {
				temMap = (Map<String, Object>) it.next();
			}
		}
		return temMap;
	}

	/**
	 * 调用积分引擎消费
	 * 
	 * @param username
	 *            会员名
	 * @param txCode
	 * @return
	 */
	private Map<String, Object> mengUpAccBal(Member mem, String txCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merCustNo", mem.getSn());
		params.put("mobile", mem.getMobile());

		params.put("custType", "P");
		params.put("bizType", "01");

		params.put("txType", "A0");

		params.put("merOrderId", "nicihome_up_" + getRandomString(6));

		params.put("event", "101");
		params.put("ruleType", "2");

		params.put("txIntegral", mem.getPoint());

		params.put("acceptBizNo", this.getAcceptBizNo());

		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));

		params.put("remark", "赠送萌值");

		String resMsg = null;
		try {
			resMsg = HttpClientUtil.postJson(txCode, params, "UTF-8");
		} catch (JsonProcessingException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("赠送萌值：", e);
			return null;
		}

		// 处理返回结果
		Map<String, Object> resMap = null;
		try {
			resMap = new ObjectMapper().readValue(resMsg, Map.class);
		} catch (JsonParseException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (JsonMappingException e) {
			logger.error("JSON映射失败：", e);
			return null;
		} catch (IOException e) {
			logger.error("赠送萌值失败：", e);
			return null;
		}

		return resMap;
	}

	/**
	 * 调用积分引擎注册借口注册账户 调用生产接口
	 * 
	 * @param username
	 *            会员名
	 * @param txCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> registerBudAccBal(Member mem, String txCode) {
		Map<String, Object> params = new HashMap<String, Object>();

		
		params.put("custName", mem.getSn());
		params.put("merCustNo", mem.getSn());
		params.put("mobile", mem.getMobile());
		params.put("custType", "P");
		params.put("merOrderId", "nicihome_reg_" + getRandomString(10));
		params.put("event", "101");
		params.put("ruleType", "2");
		params.put("acceptBizNo", this.getAcceptBizNo());
     	params.put("credType", "0");
		params.put("credNo", "");	
		params.put("realName", "");
		params.put("proPhone", "");
	 		 
		params.put("txIntegral", "0");
		
		String resMsg = null;
		try {
			resMsg = HttpClientUtil.postJson(txCode, params, "UTF-8");
		} catch (JsonProcessingException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("注册电子账户失败：", e);
			return null;
		}

		// 处理返回结果
		Map<String, Object> resMap = null;
		try {
			resMap = new ObjectMapper().readValue(resMsg, Map.class);
		} catch (JsonParseException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (JsonMappingException e) {
			logger.error("JSON映射失败：", e);
			return null;
		} catch (IOException e) {
			logger.error("注册电子账户失败：", e);
			return null;
		}

		return resMap;
	}

	/**
	 * 导出数据
	 */
	@RequestMapping(value = "/exportData", method = RequestMethod.POST)
	public void exportData(Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
		//Page<Member> page = memberService.findPage(pageable);
		List<Member> Members = memberService.findBySearchProperty(pageable);
		Setting setting = SettingUtils.get();
		try {
			String[] headers = { SpringUtils.getMessage("Member.username", setting.getSiteName()),
					SpringUtils.getMessage("Member.memberRank.name", setting.getSiteName()),
					SpringUtils.getMessage("Member.mobile", setting.getSiteName()),
					SpringUtils.getMessage("Member.name", setting.getSiteName()),
					SpringUtils.getMessage("Member.gender", setting.getSiteName()),
					SpringUtils.getMessage("Member.birth", setting.getSiteName()),
					SpringUtils.getMessage("Member.email", setting.getSiteName()),
					SpringUtils.getMessage("Member.area.fullName", setting.getSiteName()),
					SpringUtils.getMessage("Member.area.name", setting.getSiteName()),
					SpringUtils.getMessage("Member.address", setting.getSiteName()),
					SpringUtils.getMessage("Member.phone", setting.getSiteName()),
					SpringUtils.getMessage("Member.amount", setting.getSiteName()),
					SpringUtils.getMessage("Member.point", setting.getSiteName()),
					SpringUtils.getMessage("Member.createDate", setting.getSiteName()),
					SpringUtils.getMessage("Member.registerIp", setting.getSiteName()),
					SpringUtils.getMessage("Member.loginDate", setting.getSiteName()),
					SpringUtils.getMessage("Member.history.point", setting.getSiteName()),
					SpringUtils.getMessage("Member.point.balance", setting.getSiteName()),
					SpringUtils.getMessage("Member.total.consumption", setting.getSiteName())};
			ExportUtil<Member> ex = new ExportUtil<Member>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
			String filedisplay = SpringUtils.getMessage("Member.shippingExportName", setting.getSiteName())
					+ sdf.format(new Date()) + ".xls";
			String title = SpringUtils.getMessage("Member.shippingExportName", setting.getSiteName());

			memberExportExcel(request, response, filedisplay, title, headers, Members, "yyyy-MM-dd");
		} catch (Exception e) {
			logger.error("会员导出出错》》》》》》》》。" + e);
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

	private void memberExportExcel(HttpServletRequest request, HttpServletResponse response, String filedisplay,
			String title, String[] headers, Collection<Member> dataset, String pattern) throws Exception {
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
		Iterator<Member> it = dataset.iterator();
		int index = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		Setting setting = SettingUtils.get();
		while (it.hasNext()) {
			Member t = it.next();
			if (t != null) {
				// 订单编号 会员 下单时间 产品名称 产品数量 型号 订单金额 会员姓名 出生年月日 联系方式 所属省 所属市
				// 详细地址 收货人
				// 收货人电话 收货人地址 订单状态 支付状态 配送状态
				try {
					index++;
					row = sheet.createRow(index);

					setCellValues(workbook, row, style2, t.getUsername(), 0);

					if (t.getMemberRank() != null)
						setCellValues(workbook, row, style2, t.getMemberRank().getName(), 1);
					else
						setCellValues(workbook, row, style2, "", 1);

					setCellValues(workbook, row, style2, t.getMobile(), 2);
					if(t.getName()!=null)
					setCellValues(workbook, row, style2, t.getName(), 3);
					else
						setCellValues(workbook, row, style2, "", 3);
					if( t.getGender()!=null)
					setCellValues(workbook, row, style2,
							SpringUtils.getMessage("Member.Gender." + t.getGender().toString(), setting.getSiteName()),
							4);
					else
						setCellValues(workbook, row, style2, "", 4);
					if(t.getBirth()!=null){
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
					setCellValues(workbook, row, style2, sdf.format(t.getBirth()), 5);
				}else
						setCellValues(workbook, row, style2, "", 5);
					if(t.getEmail()!=null)
					setCellValues(workbook, row, style2, t.getEmail(), 6);
					else
						setCellValues(workbook, row, style2, "", 6);
					if (t.getArea() != null) {
						setCellValues(workbook, row, style2, t.getArea().getFullName(), 7);
						setCellValues(workbook, row, style2, t.getArea().getName(), 8);
					}else{
						setCellValues(workbook, row, style2, "", 7);
						setCellValues(workbook, row, style2, "", 8);
					}
					if(t.getAddress()!=null)
					setCellValues(workbook, row, style2, t.getAddress(), 9);
					else
						setCellValues(workbook, row, style2, "", 9);
					if(t.getPhone()!=null)
						setCellValues(workbook, row, style2, t.getPhone(), 10);
					else
						setCellValues(workbook, row, style2, "", 10);
					if (t.getAmount() != null)
						setCellValues(workbook, row, style2, "￥" + df.format(t.getAmount()), 11);
					else
						setCellValues(workbook, row, style2, "￥" + df.format(0), 11);
					if (t.getPoint()!=null) {
						setCellValues(workbook, row, style2, t.getPoint()+"积分", 12);
					}else{
						setCellValues(workbook, row, style2, "0积分", 12);
					}
					
					setCellValues(workbook, row, style2, DateUtil.dateToString(t.getCreateDate(), "yyyyMMddhhmmss"), 13); //注册时间
					
					setCellValues(workbook, row, style2, t.getRegisterIp(), 14); //注册IP
					
					if(t.getLoginDate() != null)
						setCellValues(workbook, row, style2, DateUtil.dateToString(t.getLoginDate()), 15);
					else
						setCellValues(workbook, row, style2, "", 15);
					
					//获取积分余额
					String pricePoint = String.valueOf(getBudAccBal(t.getSn(), balance_url).get("totalBal"));
					//获取消费总额
					String consumption = String.valueOf(point(t, "virAccountOutProcess").get("count"));
					
					pricePoint = pricePoint.substring(0, pricePoint.lastIndexOf("."));
					
					if(consumption != null && pricePoint != null)
						setCellValues(workbook, row, style2, String.valueOf(Integer.valueOf(pricePoint) + Integer.valueOf(consumption))+"积分", 16); //历史积分总额
					
					setCellValues(workbook, row, style2, pricePoint+"积分", 17);//积分总额
					
					if (t.getAmount() != null)
						setCellValues(workbook, row, style2, "￥" + df.format(t.getAmount()) + "+" + consumption+"积分", 18);//消费总额
					else
						setCellValues(workbook, row, style2, "￥" + df.format(0) + "+" + consumption+"积分", 18);
					
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
	 * 获取积分消费总额
	 * @param username 会员名
	 * @param txCode
	 * @param url
	 * @return
	 */
	private Map<String, Object> point(Member mem, String txCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merCustNo", mem.getSn());
		params.put("txCode", txCode);
		
		String resMsg = null;
		try {
			resMsg = HttpClientUtil.postJson(total_url, params, "UTF-8");
		} catch (JsonProcessingException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("赠送萌值：", e);
			return null;
		}

		// 处理返回结果
		Map<String, Object> resMap = null;
		try {
			resMap = new ObjectMapper().readValue(resMsg, Map.class);
		} catch (JsonParseException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (JsonMappingException e) {
			logger.error("JSON映射失败：", e);
			return null;
		} catch (IOException e) {
			logger.error("赠送萌值失败：", e);
			return null;
		}

		return resMap;
	}
}