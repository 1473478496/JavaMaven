package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.GuessYouLikeDao;
import com.vivebest.mall.entity.GuessYouLike;
import com.vivebest.mall.service.GuessYouLikeService;

/**
 * Service - 首页猜你喜欢
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("guessYouLikeServiceImpl")
public class GuessYouLikeServiceImpl extends BaseServiceImpl<GuessYouLike, Long> implements GuessYouLikeService {

	@Resource(name = "guessYouLikeDaoImpl")
	private GuessYouLikeDao guessYouLikeDao;
	
	@Resource(name = "guessYouLikeDaoImpl")
	public void setBaseDao(GuessYouLikeDao guessYouLikeDao){
		super.setBaseDao(guessYouLikeDao);
	}

	@Override
	public boolean checkProduct(Long productId) {
		return guessYouLikeDao.checkProduct(productId);
	}
	
	@Override
	public List<GuessYouLike> findList(Integer count, List<Filter> filters,
			List<Order> orders) {
		return guessYouLikeDao.findList(count, filters, orders);
	}
	
}
