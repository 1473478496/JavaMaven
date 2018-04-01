package com.vivebest.mall.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsCodeDao;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsCode;

/**
 * RightsDaoImpl - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsCodeDaoImpl")
public class RightsCodeDaoImpl extends BaseDaoImpl<RightsCode, Long> implements RightsCodeDao {

	@Override
	public RightsCode findById(Long id) {
		if(id != null){
			return entityManager.find(RightsCode.class, id);
		}
		return null;
	}

	@Override
	public Page<RightsCode> findList(RightsCode rightsCode, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsCode> criteriaQuery = criteriaBuilder.createQuery(RightsCode.class);
		Root<RightsCode> root = criteriaQuery.from(RightsCode.class);
		criteriaQuery.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		//根据用户id查询权益
		if(!StringUtils.isEmpty(rightsCode.getMember())){
			if(!StringUtils.isEmpty(rightsCode.getMember().getId())){
				Predicate p = criteriaBuilder.equal(root.get("member").get("id"), rightsCode.getMember().getId());
				predicates.add(p);
			}
		}
		
		//根据用户id查询权益
		if(!StringUtils.isEmpty(rightsCode.getIsUsed())){
			Predicate p = criteriaBuilder.equal(root.get("isUsed"), rightsCode.getIsUsed());
			predicates.add(p);
		}
		
		
		if(predicates.size()>0){
			criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate").as(Date.class)));
		
		
		return super.findPage(criteriaQuery, pageable);
	}
	
	/**
	 * id 查询单权益对象
	 */
	@Override
	public List<RightsCode> asignCode(Long rightsId,Integer num) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsCode> criteriaQuery = criteriaBuilder.createQuery(RightsCode.class);
		Root<RightsCode> root = criteriaQuery.from(RightsCode.class);
		criteriaQuery.select(root);
		Predicate p1 = criteriaBuilder.isNull(root.get("member").get("id").as(Long.class));
		Predicate p2 = criteriaBuilder.equal(root.get("isUsed").as(Boolean.class),false);
		Predicate p3 = criteriaBuilder.isNull(root.get("rightOrder").get("id").as(Long.class));
		Join<RightsCode,Rights> join  = root.join(root.getModel().getSingularAttribute("rights",Rights.class),JoinType.LEFT);
		Predicate p4 = criteriaBuilder.equal(join.get("id").as(Long.class),rightsId);
		
		criteriaQuery.where(criteriaBuilder.and(p1,p2,p3),p4);
		return super.findList(criteriaQuery, 0, num, null, null);
	}
	

   /**
    * 	根据权益订单取得权益码
    */
	public List<RightsCode> findByRigthsOrderId(Long id){
		String hsql="select rightcode from RightsCode rightcode where lower(rightcode.rightOrder.id)=lower(:id)";
		List<RightsCode>rightcodes=entityManager.createQuery(hsql, RightsCode.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", id).getResultList();
		if (null != rightcodes && !rightcodes.isEmpty()) {
			return rightcodes;
		}else{
			return null;
		}
	}

@Override
public RightsCode findByCode(String code) {
	String hsql="select rightcode from RightsCode rightcode where lower(rightcode.code)=lower(:code)";
	try{
		return entityManager.createQuery(hsql, RightsCode.class).setFlushMode(FlushModeType.COMMIT).setParameter("code", code).getSingleResult();
	}catch(NoResultException e){
		return null;
	}
}
	
	
	

}
