/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.GameRecordDao;
import com.vivebest.mall.entity.GameRecord;
import com.vivebest.mall.service.GameRecordService;

/**
 * Service - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("gameRecordServiceImpl")
public class GameRecordServiceImpl extends BaseServiceImpl<GameRecord, Long> implements GameRecordService {

	@Resource(name = "gameRecordDaoImpl")
	private GameRecordDao gameRecordDao;
	
	@Resource(name = "gameRecordDaoImpl")
	public void setBaseDao(GameRecordDao gameRecordDao) {
		super.setBaseDao(gameRecordDao);
	}

	@Override
	@Transactional
	public void save(GameRecord gameRecord) {
		
		GameRecord gr = gameRecordDao.findByMember(gameRecord.getMember(),gameRecord.getName());
		if(gr==null){
			super.save(gameRecord);
		}
	}
	
	@Override
	public GameRecord findByMember(String member,String name) {
		return gameRecordDao.findByMember(member,name);
	}
	
	@Override
	public GameRecord addGameScore(GameRecord gameRecord){
		GameRecord gr = findByMember(gameRecord.getMember(),gameRecord.getName());
		if(gr !=null){
			gr.setScore(gr.getScore()+gameRecord.getScore());
			gr.setLose(gr.getLose()+gameRecord.getLose());
			gr.setPeace(gr.getPeace()+gameRecord.getPeace());
			return super.update(gr);
		}
		return null;
	}
	

}