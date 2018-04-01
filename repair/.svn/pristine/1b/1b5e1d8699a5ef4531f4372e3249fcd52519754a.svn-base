package cn.sunline.ws.foreignInterface.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.constants.RepairConstants;
import cn.sunline.ws.foreignInterface.dao.IMessageMainInfoDao;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.foreignInterface.service.IWsMessageService;
import cn.sunline.ws.message.vo.MessageMainInfoVo;
@Service
public class WsMessageServiceImpl implements IWsMessageService {
	@Autowired
	private IMessageMainInfoDao messageMaindao;
	
	@Override
	public void saveMsgMianInfo(RepairMainInfo repairMainInfo) {//	// 保存数据到短信主表
		String frmNo = repairMainInfo.getFrmNo();
		String reportNo = repairMainInfo.getReportNo();
		String hql = "FROM MessageMainInfoVo where  frmNo= ? and reportNo=?";
		MessageMainInfoVo msgMainInfoVo = messageMaindao.getMsmInfo(hql, new Object[] { frmNo,reportNo });
		if (msgMainInfoVo == null || msgMainInfoVo.getId() == null) {
			MessageMainInfoVo vo = new MessageMainInfoVo();
			vo.setRepairSerialNo(repairMainInfo.getRepairSerialNo());			
			vo.setIssuDepartment(repairMainInfo.getClaimDepartment());
			vo.setPlateNo(repairMainInfo.getPlateNo());
			vo.setFrmNo(repairMainInfo.getFrmNo());
			vo.setReportNo(repairMainInfo.getReportNo());
			vo.setVehicleType(repairMainInfo.getVehicleType());
			vo.setVehicleUsage(repairMainInfo.getVehicleUsage());
			vo.setDealerNo(repairMainInfo.getRepairNo());
			vo.setPolicyDealerNo(repairMainInfo.getPolicyRepairNo());
			int random = (int) (Math.random() * 5);
			vo.setRandom(String.valueOf(random));
			vo.setCrtTm(new Date());
			vo.setUpdTm(new Date());
			vo.setMsgState(RepairConstants.MSGSENDSTATE_0);//短信发送状态初始化
			messageMaindao.addMsmInfo(vo);
		}else{// 有记录 更新信息
			msgMainInfoVo.setMsgState(RepairConstants.MSGSENDSTATE_0);//短信发送状态初始化);
			msgMainInfoVo.setUpdTm(new Date());
			messageMaindao.updateMsmInfo(msgMainInfoVo);
		}
	
	}

	@Override
	public void updateMsmInfo(RepairMainInfo repairMainInfo) {	//此时主要欧式发送定损员的短信
		String frmNo = repairMainInfo.getFrmNo();
		String reportNo = repairMainInfo.getReportNo();
		String hql = "FROM MessageMainInfoVo where  frmNo= ? and reportNo=?";
		MessageMainInfoVo vo = messageMaindao.getMsmInfo(hql, new Object[] { frmNo, reportNo });
		if(vo != null && vo.getId() != null) {
			vo.setMsgState(RepairConstants.MSGSENDSTATE_0);//短信发送状态初始化);
			vo.setUpdTm(new Date());
			messageMaindao.updateMsmInfo(vo);
		}
	}

}
