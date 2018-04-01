package cn.sunline.repair.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sunline.core.common.service.impl.CommonServiceImpl;
import cn.sunline.repair.service.MonitorServiceI;



@Service("monitorService")
@Transactional
public class MonitorServiceImpl extends CommonServiceImpl implements MonitorServiceI {
	
}