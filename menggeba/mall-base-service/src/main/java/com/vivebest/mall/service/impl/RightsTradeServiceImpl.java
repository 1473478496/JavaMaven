package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsTradeDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.service.RightsTradeService;

@Service("rightsTradeServiceImpl")
public class RightsTradeServiceImpl extends BaseServiceImpl<RightsTrade, Long> implements RightsTradeService {

	@Resource(name = "rightsTradeDaoImpl")
	private RightsTradeDao rightsTradeDao;

	@Resource(name = "rightsTradeDaoImpl")
	public void setBaseDao(RightsTradeDao rightsTradeDao) {
		super.setBaseDao(rightsTradeDao);
	}
	
	@Override
	public RightsTrade findByName(String name) {
		
		return rightsTradeDao.findByName(name);
	}

	@Override
	public List<RightsTrade> findAllRightsTrade(boolean limit) {
		return rightsTradeDao.findAllRightsTrade(limit);
	}

	@Transactional(readOnly = true)
	public boolean rloginUnique(String previousRlogin, String rlogin) {
		if (StringUtils.equalsIgnoreCase(previousRlogin, rlogin)) {
			return true;
		} else {
			if (rightsTradeDao.snExists(rlogin)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public Page<RightsTrade> findPage(Pageable pageable, String areaName, String brandName) {
		return rightsTradeDao.findPage(pageable, areaName, brandName);
	}

	@Override
	public Page<RightsTrade> findPage(Pageable pageable, Rights rights,
			Area area) {
		return rightsTradeDao.findPage(pageable, rights, area);
	}

	

}
