package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.MemberRankRigthsDao;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.entity.MemberRankRigths.RightsName;
import com.vivebest.mall.service.MemberRankRigthsService;

/**
 * ServiceImpl - 会员权益
 * 
 * @author ding.hy
 * @version 3.0
 */
@Service("memberRankRigthsServiceImpl")
public class MemberRankRigthsServiceImpl extends BaseServiceImpl<MemberRankRigths, Long>implements MemberRankRigthsService {

	@Resource(name = "memberRankRigthsDaoImpl")
	private MemberRankRigthsDao memberRankRigthsDao;

	@Resource(name = "memberRankRigthsDaoImpl")
	public void setBaseDao(MemberRankRigthsDao memberRankRigthsDao) {
		super.setBaseDao(memberRankRigthsDao);
	}

	@Override
	public List<MemberRankRigths> findByMemberRankId(Long memberRankId) {
		return memberRankRigthsDao.findByMemberRankId(memberRankId);
	}

	@Override
	public MemberRankRigths findByRankAndName(Long memberRankId, RightsName rightsName) {
		return memberRankRigthsDao.findByRankAndName(memberRankId, rightsName);
	}

	@Override
	public void updateMemberRankRigths(MemberRank memberRank, Long priceConcessions, Long[] birthdayPrivilege, Long customerService,
			Long newTrialArea) {
				String str = "";
				for (int i = 0 ; i < birthdayPrivilege.length ; i ++) {
					str += birthdayPrivilege[i];
				}
				Long bp = Long.parseLong(str);
				RightsName[] rightsNames = new RightsName[]{
						// 优惠比例
						RightsName.priceConcessions,
						// 专属客服
						RightsName.customerService,
						// 新品试用区
						RightsName.newTrialArea,
						// 生日特权
						RightsName.birthdayPrivilege,
						};
				Long[] rightsValues = new Long[]{
						// 优惠比例
						priceConcessions,
						// 专属客服
						customerService,
						// 新品试用区
						newTrialArea,
						// 生日特权
						bp,
						};
				MemberRankRigths mrr = null;
					try {
						for (int i = 0 ; i < rightsNames.length ; i ++) {
							mrr = findByRankAndName(memberRank.getId(), rightsNames[i]);
							if (mrr != null) {
								mrr.setMemberRank(memberRank);
								mrr.setRightsName(rightsNames[i]);
								mrr.setRightsValue(rightsValues[i]);
								mrr.setIsStatus(true);
								mrr.setName("1");
								update(mrr);
							} else {
								mrr = new MemberRankRigths();
								mrr.setMemberRank(memberRank);
								mrr.setRightsName(rightsNames[i]);
								mrr.setRightsValue(rightsValues[i]);
								mrr.setIsStatus(true);
								mrr.setName("1");
								save(mrr);
							}
					
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}
	
	

}
