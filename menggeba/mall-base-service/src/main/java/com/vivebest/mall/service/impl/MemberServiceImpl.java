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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.core.util.MD5;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.dao.DepositDao;
import com.vivebest.mall.dao.MemberDao;
import com.vivebest.mall.entity.APIMessage;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Deposit;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Member.Gender;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.service.AreaService;
import com.vivebest.mall.service.MemberRankService;
import com.vivebest.mall.service.MemberService;

/**
 * Service - 会员
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long>implements MemberService {
	
	private static Logger logger = Logger.getLogger(MemberServiceImpl.class);

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;

	@Resource(name = "memberDaoImpl")
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}

	private String inclusionErrList;

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		Assert.hasText(username);
		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberDao.emailExists(email);
	}
	
	

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (memberDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void save(Member member, Admin operator) {
		Assert.notNull(member);
		memberDao.persist(member);
		if (member.getBalance().compareTo(new BigDecimal(0)) > 0) {
			Deposit deposit = new Deposit();
			deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.memberRecharge);
			deposit.setCredit(member.getBalance());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMember(member);
			depositDao.persist(deposit);
		}
	}

	public void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo,
			Admin operator) {
		Assert.notNull(member);

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);

		if (modifyPoint != null && modifyPoint != 0 && member.getPoint() + modifyPoint >= 0) {
			member.setPoint(member.getPoint() + modifyPoint);
		}

		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0
				&& member.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) >= 0) {
			member.setBalance(member.getBalance().add(modifyBalance));
			Deposit deposit = new Deposit();
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0) {
				deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.memberRecharge);
				deposit.setCredit(modifyBalance);
				deposit.setDebit(new BigDecimal(0));
			} else {
				deposit.setType(operator != null ? Deposit.Type.adminChargeback : Deposit.Type.memberPayment);
				deposit.setCredit(new BigDecimal(0));
				deposit.setDebit(modifyBalance);
			}
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMemo(depositMemo);
			deposit.setMember(member);
			depositDao.persist(deposit);
		}
		memberDao.merge(member);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return memberDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public List<Member> findListByMobile(String mobile) {
		return memberDao.findListByMobile(mobile);
	}

	@Transactional(readOnly = true)
	public boolean mobileExists(String mobile) {
		return memberDao.mobileExists(mobile);
	}

	@Transactional(readOnly = true)
	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		return memberDao.findPurchaseList(beginDate, endDate, count);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memberDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	public List<Member> readExcelMember(InputStream inp) {

		// TODO Auto-generated method stub
		List<Member> MembertList = new ArrayList<Member>();
		StringBuilder strErrAll = new StringBuilder();

		Map<Integer, String> mapIdentity = new HashMap<Integer, String>();

		try {
			String cellStr = null;
			StringBuilder eachLineDesc = new StringBuilder();

			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheet

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i); // 获取行(row)对象

				if (row == null) {
					// row为空的话,不处理
					continue;
				}

				String mobile = null;
				if (row.getCell(0) != null) {

					Cell cell = row.getCell(0); //
					if (cell != null) {
						mobile = ConvertCellStr(cell, cellStr);

						if (StringUtils.isNotEmpty(mobile)) {
							if (mapIdentity.containsValue(mobile)) {
								eachLineDesc.append("手机号码重复;");
							}
							mapIdentity.put(i, mobile);
						} else {
							// row为空的处理
							sheet.removeRow(row);
							continue;
						}

					}
				}

				// 先行根据mobile来确定Member;
				List<Member> meblist = memberService.findListByMobile(mobile);
				Member member = null;
				if (!meblist.isEmpty())
					member = meblist.get(0);
				if (member == null) {
					member = new Member();
					BigDecimal decialbanlce = new BigDecimal("0.00");
					member.setBalance(decialbanlce);
					member.setIsEnabled(true);
					member.setIsLocked(false);
					member.setIsImported(true);
					member.setPassword("1qax@WSX");
					member.setLoginFailureCount(0);
					Date dtNow = new Date();
					member.setCreateDate(dtNow);
					member.setModifyDate(dtNow);
				}

				StringBuilder AreaPath = new StringBuilder();

				for (int j = 0; j < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j); // 获得单元格(cell)对象

					if (cell != null) {
						// 转换接收的单元格
						cellStr = ConvertCellStr(cell, cellStr);

						if (cellStr != "" && cellStr != null)
							try {
								addingMember(j, member, cellStr, AreaPath);
								cellStr = ""; // 清空变量值
							} catch (Exception ex) {
								eachLineDesc.append(cellStr + "数据内容格式有误;");
							}
						else
							continue;
						// 将单元格的数据添加至一个对象

					} else
						continue;

				}
				// 处理完以后最后处理AreaPath

				if (AreaPath.length() > 1) {

					Area area = areaService.findByFullName(AreaPath.toString());
					member.setArea(area);

				}
				if (member.getMobile() == null) {
					eachLineDesc.append("手机号码为空;");
				}
				if (member.getAmount() == null) {
					eachLineDesc.append("消费金额为空;");
				}
				if (member.getPoint() == null) {
					eachLineDesc.append("赠送萌值为空;");
				}
				if (eachLineDesc.length() > 1) {
					strErrAll.append("第" + i + "行:");
					strErrAll.append(eachLineDesc);
					strErrAll.append("\r\n");
				}

				MembertList.add(member);
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
					mapIdentity.clear();
					inp.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

			}
		}
		return MembertList;
	}

	@Override
	public String getInclusionErrList() {
		return inclusionErrList;
	}

	public void setInclusionErrList(String err) {
		this.inclusionErrList = err;
	}

	private Member addingMember(int j, Member member, String cellStr, StringBuilder AreaPath) {
		switch (j) {
		case 0: // 手机号码
			member.setMobile(cellStr);
			break;

		case 1: // 会员姓名
			member.setName(cellStr);
			break;
		case 2: // 性别
			member.setGender(cellStr.equals("男") ? Gender.male : Gender.female);
			break;
		case 3: // 出生日期

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date daBirth = sdf.parse(cellStr);
				member.setBirth(daBirth);
			} catch (ParseException e) {
			}

			break;
		case 4: // 邮箱
			member.setEmail(cellStr);
			break;
		case 5: // 所在省份
			AreaPath.append(cellStr);
			break;
		case 6: // 所在城市
			AreaPath.append(cellStr);
			break;
		case 7: // 所在区
			AreaPath.append(cellStr);
			break;
		case 8: // 地址
			member.setAddress(AreaPath.toString() + cellStr);
			break;
		case 9: // 电话
			member.setPhone(cellStr);
			break;
		case 10: // 已消费金额

			BigDecimal bigAmount = new BigDecimal(cellStr);
			member.setAmount(bigAmount);
			MemberRank mrank = memberRankService.findByAmount(bigAmount);
			member.setMemberRank(mrank);

			break;
		case 11: // 赠送萌值
			member.setPoint(new Long(new BigDecimal(cellStr).longValue()));
			break;

		}
		return member;

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
				DecimalFormat df = new DecimalFormat("#");// 转换成整型
				cellStr = df.format(cell.getNumericCellValue());

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

	@Transactional(readOnly = true)
	public boolean wxOpenIdExists(String wxOpenId) {
		return memberDao.wxOpenIdExists(wxOpenId);
	}

	@Transactional(readOnly = true)
	public Member findByWxOpenId(String wxOpenId) {
		return memberDao.findByWxOpenId(wxOpenId);
	}

	@Transactional(readOnly = true)
	public Member findUserBySn(String usersn) {
		return memberDao.findUserBySn(usersn);
	}
	
	public List<Member>findBySearchProperty(Pageable pageable){
		return memberDao.findBySearchProperty(pageable);
	}
	
	@Override
	public Member findByMobile(String moblie) {
		return memberDao.findByMobile(moblie);
	}

	@Override
	public void consumeCount(Member m, String paidAmt) {
		logger.info("[消费金额统计：]"+m.getMobile());
		BigDecimal consume = new BigDecimal(paidAmt);
		BigDecimal b = m.getAmount();
		m.setAmount(b.add(consume));
		logger.info("[消费金额统计：]"+m.getAmount());
		m.setMemberRank(memberRankService.findByAmount(m.getAmount()));
		super.update(m);
	}

	@Override
	public APIMessage findByToken(String token) {
		logger.info("[会员信息接口]：" + token);
		Member member = memberDao.findByToken(token);
		APIMessage api = new APIMessage();
		if(member != null){
			long mtime = member.getModifyDate().getTime();
			long ctime = new Date().getTime();
			if((ctime - mtime)/(1000 * 60)>30){
				logger.info("[会员信息接口:]token失效");
			}else{
				api.setStatusCode("0");
				api.setUserId(member.getSn());
				api.setUserMobile(member.getMobile());
				api.setUserName(member.getUsername());
				api.setUserKey(member.getToken());
				api.setUserScore(member.getPoint());
				return api;
			}
		}
		logger.info("[会员信息接口]：用户不存在");
		api.setStatusCode(APIMessage.Error.UNKNOW_ERR.getCode());
		api.setErrorMsg(APIMessage.Error.UNKNOW_ERR.getDesc());
		return api;
	}

	@Override
	public String createToken() {
		int random = new java.util.Random().nextInt(900)+100;
		String securit = UUID.randomUUID().toString()+""+random;
		return MD5.sign(securit, "mgb_caipiao", "utf-8");
	}
	
	
}