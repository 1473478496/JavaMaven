package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.ws.foreignInterface.dao.IMessageContenDao;

@SuppressWarnings("unchecked")
@Repository
public class MessageContenDaoImpl  extends GenericBaseCommonDao implements IMessageContenDao {

	@Override
	public List<TSSmsTemplateEntity> getMsgContent(String hql, Object[] parameter) {
		return executeQuery(hql, parameter);
	}

}
