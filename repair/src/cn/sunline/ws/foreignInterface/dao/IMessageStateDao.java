package cn.sunline.ws.foreignInterface.dao;



import java.util.List;

import cn.sunline.ws.message.vo.MessageStateVo;

public interface IMessageStateDao {

	public void addMsgState(MessageStateVo msgStateVo);
	
	public List msgStateInfoList(String hql, Object[] param);
	
	public boolean updateMsgStateInfo(MessageStateVo msgStateVo);
	
	public List msgRulesInfo(String hql, Object[] params);

}
