/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.GameRecord;

/**
 * Service - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface GameRecordService extends BaseService<GameRecord, Long> {

	public GameRecord findByMember(String member,String name);
	
	public GameRecord addGameScore(GameRecord gr);
}