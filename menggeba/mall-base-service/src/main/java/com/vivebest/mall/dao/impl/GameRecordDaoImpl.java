/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.GameRecordDao;
import com.vivebest.mall.entity.GameRecord;

/**
 * Dao - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("gameRecordDaoImpl")
public class GameRecordDaoImpl extends BaseDaoImpl<GameRecord, Long> implements GameRecordDao {

	/**
	 * 获取个人游戏记录
	 * @param member
	 * @return
	 */
	@Override
	public GameRecord findByMember(String member,String name){
		if (member == null || name == null) {
			return null;
		}
		try{
			String jpql = "select gameRecord from GameRecord gameRecord where lower(gameRecord.member) = lower(:member) and lower(gameRecord.name) = lower(:name)";
			GameRecord gameRecord = entityManager.createQuery(jpql, GameRecord.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("member", member).setParameter("name", name).getSingleResult();
			return gameRecord;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	
	
}