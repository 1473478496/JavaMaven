package cn.sunline.web.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sunline.core.common.service.impl.CommonServiceImpl;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.web.system.pojo.base.DynamicDataSourceEntity;
import cn.sunline.web.system.service.DynamicDataSourceServiceI;


@Service("dynamicDataSourceService")
@Transactional
public class DynamicDataSourceServiceImpl extends CommonServiceImpl implements DynamicDataSourceServiceI {

	/**初始化数据库信息，TOMCAT启动时直接加入到内存中**/
	public List<DynamicDataSourceEntity> initDynamicDataSource() {
		ResourceUtil.dynamicDataSourceMap.clear();

		List<DynamicDataSourceEntity> dynamicSourceEntityList = this.commonDao.loadAll(DynamicDataSourceEntity.class);

		for (DynamicDataSourceEntity dynamicSourceEntity : dynamicSourceEntityList) {
			ResourceUtil.dynamicDataSourceMap.put(dynamicSourceEntity.getDbKey(), dynamicSourceEntity);
		}
		return dynamicSourceEntityList;
	}

	public static DynamicDataSourceEntity getDbSourceEntityByKey(String dbKey) {
		DynamicDataSourceEntity dynamicDataSourceEntity = ResourceUtil.dynamicDataSourceMap.get(dbKey);

		return dynamicDataSourceEntity;
	}

	public void refleshCache() {
		initDynamicDataSource();
	}

	//add-begin--Author:luobaoli  Date:20150620 for：增加通过数据源Key获取数据源Type
	@Override

	public DynamicDataSourceEntity getDynamicDataSourceEntityForDbKey(String dbKey){
		List<DynamicDataSourceEntity> dynamicDataSourceEntitys = commonDao.findHql("from DynamicDataSourceEntity where dbKey = ?", dbKey);
		if(dynamicDataSourceEntitys.size()>0)
			return dynamicDataSourceEntitys.get(0);
		return null;
	}

	//add-end--Author:luobaoli  Date:20150620 for：增加通过数据源Key获取数据源Type

}