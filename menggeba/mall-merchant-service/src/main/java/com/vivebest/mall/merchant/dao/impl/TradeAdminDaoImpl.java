package com.vivebest.mall.merchant.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.TradeAdminDao;
import com.vivebest.mall.merchant.entity.TradeAdmin;

@Repository("tradeAdminDaoImpl")
public class TradeAdminDaoImpl extends BaseDaoImpl<TradeAdmin, Long> implements TradeAdminDao {
	
	private static Logger logger = Logger.getLogger(TradeAdminDaoImpl.class);
	
	public TradeAdmin findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select tradeAdmin from TradeAdmin tradeAdmin where lower(tradeAdmin.username) = lower(:username)";
			return entityManager.createQuery(jpql, TradeAdmin.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		}  catch (NoResultException nre) {
			logger.info(">>>>>"+nre);
			return null;
		} catch (NonUniqueResultException nure) {
			logger.info(">>>>>"+nure);
			return null;
		}
	}

}
