package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.MemberRankRigthsDao;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.entity.MemberRankRigths.RightsName;

/**
 * DaoImpl - 会员权益
 * 
 * @author ding.hy
 * @version 3.0
 */
@Repository("memberRankRigthsDaoImpl")
public class MemberRankRigthsDaoImpl extends BaseDaoImpl<MemberRankRigths, Long>implements MemberRankRigthsDao {


	@Override
	public List<MemberRankRigths> findByMemberRankId(Long memberRankId) {
		if (memberRankId == null) {
			return null;
		}
		String jpql = "select memberRankRigths from MemberRankRigths memberRankRigths where lower(memberRankRigths.memberRank) = lower(:memberRankId)";
		return entityManager.createQuery(jpql, MemberRankRigths.class).setFlushMode(FlushModeType.COMMIT).setParameter("memberRankId", memberRankId).getResultList();
	}

	@Override
	public MemberRankRigths findByRankAndName(Long memberRankId, RightsName rightsName) {
		if (memberRankId == null && rightsName == null) {
			return null;
		}
		try {
			String jpql = "select memberRankRigths from MemberRankRigths memberRankRigths where lower(memberRankRigths.memberRank) = lower(:memberRankId) and lower(memberRankRigths.rightsName) = lower(:rightsName)";
			return entityManager.createQuery(jpql, MemberRankRigths.class).setFlushMode(FlushModeType.COMMIT).setParameter("memberRankId", memberRankId).setParameter("rightsName", rightsName.ordinal()).getResultList().get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
