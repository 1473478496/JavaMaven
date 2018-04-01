package com.vivebest.mall.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.TradeAdminDao;
import com.vivebest.mall.merchant.entity.TradeAdmin;
import com.vivebest.mall.merchant.entity.TradeRole;
import com.vivebest.mall.merchant.service.TradeAdminService;

 
@Service("tradeAdminServiceImpl")
public class TradeAdminServiceImpl extends BaseServiceImpl<TradeAdmin, Long> implements TradeAdminService {

	@Resource(name = "tradeAdminDaoImpl")
	private TradeAdminDao tradeAdminDao;

	
	@Resource(name = "tradeAdminDaoImpl")
	public void setBaseDao(TradeAdminDao tradeAdminDao) {
		super.setBaseDao(tradeAdminDao);
	}


	@Override
	public TradeAdmin findByUsername(String username) {
		return tradeAdminDao.findByUsername(username);
	}


	@Override
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		TradeAdmin admin = tradeAdminDao.find(id);
		if (admin != null) {
			for (TradeRole role : admin.getTradeRoles()) {
				authorities.addAll(role.getAuthorities());
			}
		}
		return authorities;
	}


	@Transactional(readOnly = true)
	public TradeAdmin getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(TradeAdmin.class.getName());
			if (principal != null) {
				return tradeAdminDao.find(principal.getId());
			}
		}
		return null;
	}


}
