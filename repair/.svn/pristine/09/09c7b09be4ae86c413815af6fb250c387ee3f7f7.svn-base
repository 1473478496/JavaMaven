package cn.sunline.ws.foreignInterface.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.sunline.core.common.dao.impl.GenericBaseCommonDao;
import cn.sunline.ws.foreignInterface.dao.IMessageMainInfoDao;
import cn.sunline.ws.message.vo.MessageMainInfoVo;

@SuppressWarnings("unchecked")
@Repository
@Service("msgMainInfoDaoImpl")
public class MessageMainInfoDaoImpl extends GenericBaseCommonDao  implements IMessageMainInfoDao{
	public List<MessageMainInfoVo> getMsgMainList(String hql, Object[] parameter) {
		return executeQueryMaxSize(hql, parameter);
	}

	@Override
	public void addMsmInfo(MessageMainInfoVo msmInfo) {
		saveOrUpdate(msmInfo);
		
	}

	@Override
	public MessageMainInfoVo getMsmInfo(String hql, Object[] params) {
		List list = executeQuery(hql, params);
		if(list != null && list.size() > 0){
			this.getSession().evict((MessageMainInfoVo)list.get(0));
			return (MessageMainInfoVo)list.get(0);			
		}
		return null;
	}

	@Override
	public boolean updateMsmInfo(MessageMainInfoVo msmInfo) {
		try {
			updateEntitie(msmInfo);
			return true ;
		} catch (Exception e) {
			return false ;
		}
	}

}
