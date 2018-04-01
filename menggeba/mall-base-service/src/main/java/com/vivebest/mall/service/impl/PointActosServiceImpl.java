package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.impl.PointActosDaoImpl;
import com.vivebest.mall.entity.PointActors;
import com.vivebest.mall.service.PointActosService;

@Service("pointActosServiceImpl")
public class PointActosServiceImpl  extends BaseServiceImpl<PointActors, Long> implements PointActosService {


	@Resource(name = "pointActosDaoImpl")
	private PointActosDaoImpl pointActosDao;
	
	
	@Override
	public PointActors findTel(String mobile) {
		// TODO Auto-generated method stub
		return pointActosDao.findTel(mobile);
	}

	@Override
	public Boolean newActo(Integer point, String mobile) {
		// TODO Auto-generated method stub
		Boolean bln =  pointActosDao.isReceive(mobile);
		if(bln) //超过限额，就不允许再领取
		{
			 return false;
		}
		PointActors pointentity=new PointActors();
		pointentity.setActor(mobile);
		pointentity.setActorTel(mobile);
		pointentity.setPoint(point);
		pointActosDao.persist(pointentity);
		return true;
		
	}
	

}
