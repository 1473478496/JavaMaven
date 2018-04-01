package com.vivebest.mall.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.QuestionnaireAnswerDao;
import com.vivebest.mall.entity.QuestionnareAnswer;


@Repository("questionnaireAnswerDaoImpl")
public class QuestionnaireAnswerDaoImpl extends BaseDaoImpl<QuestionnareAnswer, Long> implements QuestionnaireAnswerDao {
	public QuestionnareAnswer findByMobileAndName(String mobile,String name) {
		if (mobile == null || name ==null) {
			return null;
		}
		String jpql = "select questionnareAnswer from QuestionnareAnswer questionnareAnswer where lower(questionnareAnswer.mobile) = lower(:mobile) and lower(questionnareAnswer.name) = lower(:name) and to_days(create_date) = to_days(now())";
		try {
			TypedQuery<QuestionnareAnswer> query = entityManager.createQuery(jpql, QuestionnareAnswer.class);
			query.setFlushMode(FlushModeType.COMMIT);
			query.setParameter("mobile", mobile);
			query.setParameter("name", name);
			return query.getSingleResult(); 
		} catch (NoResultException e) {
			return null;
		}
	}
} 
