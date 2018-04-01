package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.entity.MemberRankRigths.RightsName;

/**
 * Dao - 会员权益
 * 
 * @author ding.hy
 * @version 3.0
 */
public interface MemberRankRigthsDao extends BaseDao<MemberRankRigths, Long> {
	
	/**
	 * 根据会员等级查找对应的权益
	 * @param memberRankId
	 * @return
	 */
	List<MemberRankRigths> findByMemberRankId (Long memberRankId);
	
	/**
	 * 根据会员等级与与对应权益名查找权益
	 * @param memberRankId
	 * @param name
	 * @return
	 */
	MemberRankRigths findByRankAndName (Long memberRankId, RightsName rightsName );
}
