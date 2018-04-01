package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.entity.MemberRankRigths.RightsName;
/**
 * Service - 会员权益
 * 
 * @author ding.hy
 * @version 3.0
 */
public interface MemberRankRigthsService extends BaseService<MemberRankRigths, Long> {
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
	
	/**
	 * 保存或更新等级权益
	 * @param priceConcessions
	 * @param birthdayPrivilege
	 * @param customerService
	 * @param newTrialArea
	 */
	void updateMemberRankRigths (MemberRank memberRank, Long priceConcessions, Long[] birthdayPrivilege, Long customerService, Long newTrialArea);
}
