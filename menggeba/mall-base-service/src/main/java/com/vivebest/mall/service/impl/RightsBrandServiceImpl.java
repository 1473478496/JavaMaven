package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsBrandDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.service.RightsBrandService;

/**
 * RightsBrandServiceImpl - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsBrandServiceImpl")
public class RightsBrandServiceImpl extends BaseServiceImpl<RightsBrand, Long> implements RightsBrandService {

	
	@Resource(name = "rightsBrandDaoImpl")
	public void setBaseDao(RightsBrandDao rightsBrandDao) {
		super.setBaseDao(rightsBrandDao);
	}
	@Resource(name="rightsBrandDaoImpl")
	private RightsBrandDao rightsBrandDao;
	
	@Override
	public RightsBrand findById(Long id) {
		return rightsBrandDao.findById(id);
	}

	@Override
	public List<RightsBrand> findAllRightsBrand() {
		return rightsBrandDao.findAllRightsBrand();
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, RightsBrand rightsBrand, RightsCategory rightsCategory) {
		return rightsBrandDao.findPage(pageable, rightsBrand, rightsCategory);
	}

	@Override
	public Page<RightsBrand> findList(Pageable pageable, RightsType type,Long area,RightsBrand rightsBrand) {
		return rightsBrandDao.findList(pageable, type ,area,rightsBrand);
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, Member member) {
		return rightsBrandDao.findPage(pageable, member);
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, Long rightsCategoryId,
			Long rightsBrandId, Long areaId) {
		return rightsBrandDao.findPage(pageable, rightsCategoryId, rightsBrandId, areaId);
	}

}
