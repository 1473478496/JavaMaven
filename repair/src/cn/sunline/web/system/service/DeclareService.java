package cn.sunline.web.system.service;

import java.util.List;


import cn.sunline.core.common.service.CommonService;
import cn.sunline.web.system.pojo.base.TSAttachment;

/**
 * 
 * @author  张代浩
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
