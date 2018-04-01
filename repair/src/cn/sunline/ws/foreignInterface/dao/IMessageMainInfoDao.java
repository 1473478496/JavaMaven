package cn.sunline.ws.foreignInterface.dao;

import java.util.List;

import cn.sunline.ws.message.vo.MessageMainInfoVo;

public interface IMessageMainInfoDao {

	public List<MessageMainInfoVo> getMsgMainList(String hql, Object[] parameter);

	public void addMsmInfo(MessageMainInfoVo msmInfo);

	public MessageMainInfoVo getMsmInfo(String hql, Object[] param);

	public boolean updateMsmInfo(MessageMainInfoVo msmInfo);

}
