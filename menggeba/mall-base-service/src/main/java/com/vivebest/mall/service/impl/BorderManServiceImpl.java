package com.vivebest.mall.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.BorderManDao;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.BorderMan;
import com.vivebest.mall.service.BorderManService;

@Service("borderManServiceImpl")
public class BorderManServiceImpl extends BaseServiceImpl<BorderMan, Long>
		implements BorderManService {
	private static Logger logger = Logger.getLogger(BorderManServiceImpl.class);
	@Resource(name = "borderManDaoImpl")
	private BorderManDao borderManDao;

	@Resource(name = "borderManDaoImpl")
	public void setBaseDao(BorderManDao borderManDao) {
		super.setBaseDao(borderManDao);
	}

	@Override
	@Transactional
	// @CacheEvict(value = "borderMan", allEntries = true)
	public void save(BorderMan borderMan) {
		// TODO Auto-generated method stub
		super.save(borderMan);
	}

	@Override
	public void approve(BorderMan borderMan, Admin admin, String auditDesc) {
		logger.info("开始设置用户相关内容");
		Assert.notNull(borderMan);
		borderMan.setCheckState(new BorderMan().getCheckState());
		borderMan.setCheckPerson(admin.getUsername());
		borderMan.setCheckDate(new Date());
		borderMan.setComment(auditDesc);
		borderManDao.merge(borderMan);
		borderManDao.flush();
		logger.info("设置结束。。。");

	}

	@Override
	public Page<BorderMan> findPage(int checkStatus, Pageable pageable) {
		return borderManDao.findPage(checkStatus, pageable);
	}

	@Override
	public BorderMan findByTradeId(Long bmNum) {
		// TODO Auto-generated method stub
		return borderManDao.findByTradeId(bmNum);
	}

}
