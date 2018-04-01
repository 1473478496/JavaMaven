package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.TradeProductsDao;
import com.vivebest.mall.entity.TradeProducts;
/**
 * 
 * 商户商品分类Dao实现类
 * @author Terry
 *
 */
@Repository("tradeProductsDaoImpl")
public class TradeProductsDaoImpl extends BaseDaoImpl<TradeProducts,Long> implements TradeProductsDao {

	/**
	 * 根据商户id查询列表
	 * 
	 */
	public List<Long> findList(Long tradeId) {
	    if(tradeId==null){
	    	return null;
	    }
	    String jpql="select product_category_id from TradeProducts tradeProducts where lower(tradeProducts.trade_id) = lower(:tradeId)";
		try{
			return entityManager.createQuery(jpql,Long.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("tradeId", tradeId).getResultList();
		}catch (NoResultException e){
	     return  null ;
		}
	}
}
