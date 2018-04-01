package cn.sunline.ws.foreignInterface.service;

import javax.jws.WebService;

/**
 * 送修-- webservices接口
 * 调度提交、查勘提交、核损通过信息告知
 */

@WebService(targetNamespace = "http://cn/sunline/repair/foreignInterface/IRepairService", name = "IRepairService")
public interface IRepairService {
	
	 public java.lang.String dispatchSubmit(java.lang.String req) throws Exception ;// 调度提交
	 
	 public java.lang.String surveySubmit(java.lang.String req) throws Exception;// 查勘提交
	 
	 public java.lang.String verifyLossSubmit(java.lang.String req) throws Exception ;// 核损通过信息告知
	 
	 public java.lang.String giveRepInfoSubmit(java.lang.String req) throws Exception ;// 返回修理信息提交

}
