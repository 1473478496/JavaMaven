package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsCategoryDao;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.service.RightsCategoryService;

/**
 * RightsCategoryServiceImpl - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsCategoryServiceImpl")
public class RightsCategoryServiceImpl extends BaseServiceImpl<RightsCategory, Long> implements RightsCategoryService{

	@Resource(name = "rightsCategoryDaoImpl")
	private RightsCategoryDao rightsCategoryDao;

	@Resource(name = "rightsCategoryDaoImpl")
	public void setBaseDao(RightsCategoryDao rightsCategoryDao) {
		super.setBaseDao(rightsCategoryDao);
	}
	
	@Transactional(readOnly = true)
	public List<RightsCategory> findRoots() {
		
		return rightsCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public RightsCategory findByName(String name) {
		
		return rightsCategoryDao.findByName(name);
	}

	@Transactional(readOnly = true)
	public RightsCategory findById(int id) {
		
		return rightsCategoryDao.findById(id);
	}
	
	@Override
	public RightsCategory findById(Long id) {
		return rightsCategoryDao.findById(id);
	}

	@Override
	public List<RightsCategory> findAllRightsCategory() {
		return rightsCategoryDao.findAllRightsCategory();
	}

}
