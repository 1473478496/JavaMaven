package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.QuestionTransferDao;
import com.vivebest.mall.entity.QuestionTransfer;

@Repository("questionTransferDaoImpl")
public class QuestionTransferDaoImpl extends BaseDaoImpl<QuestionTransfer, Long> implements QuestionTransferDao{
	
	private static Logger logger = Logger.getLogger(QuestionTransferDaoImpl.class);

	@Override
	public List<QuestionTransfer> findListByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		String jpql = "select questtionTransfers from QuestionTransfer questtionTransfers where lower(questtionTransfers.mobile) = lower(:mobile)";
		try {
			return entityManager.createQuery(jpql, QuestionTransfer.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getResultList();
		} catch (NoResultException noResultException) {
			logger.info(">>>>>"+noResultException);
			return null;
		} catch (NonUniqueResultException nonUniqueResultException) {
			logger.info(">>>>>"+nonUniqueResultException);
			return null;
		}
	}

}
