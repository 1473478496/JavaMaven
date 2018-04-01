package com.vivebest.mall.dao;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.BorderMan;

public interface BorderManDao extends BaseDao<BorderMan, Long> {
	/**
	 * 查找边民注册审核申请分页
	 * @param checkState 申请状态
	 * @param pageable 分页信息
	 * @return
	 */
	Page<BorderMan> findPage(int checkState, Pageable pageable);
	/**
	 * 根据边民证件编号查询
	 * 
	 */
	BorderMan findByTradeId(Long bmNum);
}
