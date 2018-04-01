package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.repair.entity.TSSmsTemplateEntity;

public interface IMessageContenDao {

	public List<TSSmsTemplateEntity> getMsgContent(String hql, Object[] parameter);

}
