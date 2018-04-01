package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsDao;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.Rights.ConvertOrderType;
import com.vivebest.mall.entity.Rights.PriceOrderType;
import com.vivebest.mall.entity.Rights.PutAwayOrderType;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.service.RightsService;

/**
 * RightsServiceImpl - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsServiceImpl")
public class RightsServiceImpl extends BaseServiceImpl<Rights, Long> implements RightsService {

	@Resource(name="rightsDaoImpl")
	private RightsDao rightsDao;
	@Resource(name = "rightsDaoImpl")
	public void setBaseDao(RightsDao rightsDao) {
		super.setBaseDao(rightsDao);
	}
	
	@Override
	public Page<Rights> findPage(RightsCategory rightsCategory,RightsBrand rightsBrand, Pageable pageable, ConvertOrderType convertOrderType
			, PutAwayOrderType putAwayOrderType, PriceOrderType priceOrderType, String searchValue, RightsType rightsType) {
		return null;
		//rightsDao.findPage(rightsCategory, rightsBrand, pageable, convertOrderType, putAwayOrderType, priceOrderType, searchValue, rightsType);
	}

	@Override
	public Rights findById(Long id) {
		return rightsDao.find(id);
	}

	@Override
	public List<Rights> rightsHotList() {
		return rightsDao.rightsHotList();
	}
	
	 

	@Override
	public List<Rights> findByBrandId(Long rightsBrandId) {
		return rightsDao.findByBrandId(rightsBrandId);
	}

	@Override
	public List<Rights> rightsPopular(Boolean isHome, Boolean isPopularity) {
		return rightsDao.rightsPopular(isHome,isPopularity);
	}

	@Transactional(readOnly = true)
	public boolean snUnique(String previousSn, String currentSn) {
		if (StringUtils.equalsIgnoreCase(previousSn, currentSn)) {
			return true;
		} else {
			if (rightsDao.snExists(currentSn)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public Page<Rights> findList(Rights rights, Pageable pageable) {
		return rightsDao.findList(rights, pageable);
	}

	@Override
	public void setIsPopularity(Rights rights) {
		rightsDao.setIsPopularity(rights);
	}


}
