package com.vivebest.mall.dao.impl;

 

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.PointActosDao;
import com.vivebest.mall.entity.PointActors;
 
@Repository("pointActosDaoImpl")
public class PointActosDaoImpl extends BaseDaoImpl<PointActors, Long> implements PointActosDao {

	private static Logger logger = Logger.getLogger(PointActosDaoImpl.class);
	@Override
	public PointActors findTel(String mobile) {
		String jpql = "select pointActors from PointActors pointActors where pointActors.actorTel= :actorTel"  ;
		try {
			return entityManager.createQuery(jpql, PointActors.class).setFlushMode(FlushModeType.COMMIT).setParameter("actorTel", mobile).getSingleResult();
		} catch (NoResultException nre) {
			logger.info(">>>>>"+nre);
			return null;
		} catch (NonUniqueResultException nure) {
			logger.info(">>>>>"+nure);
			return null;
		}
	}
	
	
	public Boolean isReceive(String tel) {
		if (tel == null) {
			return false;
		}
		String jpql = "select count(*) from PointActors pointActors where  pointActors.actorTel = :actorTel";
	 
		Long count;
		try {
			count = entityManager.createQuery(jpql, Long.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("actorTel", tel).getSingleResult();
			return count > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
