/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.GameRecord;

/**
 * Dao - 游戏记录
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface GameRecordDao extends BaseDao<GameRecord, Long> {
	/**
	 * 获取个人游戏记录
	 * @param member
	 * @return
	 */
	public GameRecord findByMember(String member,String name);
}