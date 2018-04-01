package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.repair.entity.BrandEntity;


public interface IBrandEntityDao {
	

	public List<BrandEntity>  getBrandEntityList(String hql,Object[] param);
	
}
