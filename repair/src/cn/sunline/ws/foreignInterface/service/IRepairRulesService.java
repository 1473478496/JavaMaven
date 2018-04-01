package cn.sunline.ws.foreignInterface.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;

public interface IRepairRulesService {
	///送修规则：1、承保规则 2、就近规则
	public String repairRuel(RepairMainInfo rpairMainfo);
	
	///权重系数规则
	public String repairRulesWeighting(RepairMainInfo rpairMainInfo , List<String> repairNos,String repairHql,Object[] parameter, String flag);
	
	//设置所使用过的策略
	public void setRepairRule(RepairMainInfo rpairMainInfo , String repairRule);
}
