package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.ws.foreignInterface.dao.IMessageStateDao;
import cn.sunline.ws.message.vo.MessageStateVo;

@SuppressWarnings("unchecked")
@Repository
public class MessageStateDaoImpl  extends GenericBaseCommonDao  implements IMessageStateDao {

	public void addMsgState(MessageStateVo msgStateVo) {
		saveOrUpdate(msgStateVo);
	}
	
	public List msgStateInfoList(String hql, Object[] params){
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
//			this.getSession().evict((MsgStateVo)list.get(0));
			return list;			
		}
		return null;
	}
	
	public boolean updateMsgStateInfo(MessageStateVo msgStateVo){
		try {
			updateEntitie(msgStateVo);
			return true ;
		} catch (Exception e) {
			return false ;
		}
	}
	
	public List msgRulesInfo(String hql, Object[] params){
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
//			this.getSession().evict((MsageEntity)list.get(0));
//			return (MsageEntity)list.get(0);	
			return list;
		}
		return null;
	}

}
