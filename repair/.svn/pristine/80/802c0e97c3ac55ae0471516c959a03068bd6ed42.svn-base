package cn.sunline.ws.foreignInterface.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.dao.IRepairpacketDao;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.foreignInterface.entity.RepairpacketInfo;
import cn.sunline.ws.foreignInterface.service.ISurveySubmitService;
@Service
public class SurveySubmitServiceImpl implements ISurveySubmitService {
	@Autowired
	private IRepairpacketDao repairpacketDao;
	@Override
	public void savePacket(List<RepairMainInfo> rCInfoList, String responseXml, String requsetXml, String tradeType) {

		RepairpacketInfo rpinfo = new RepairpacketInfo();
		for (RepairMainInfo rcInfo : rCInfoList) {
			rpinfo.setFrmNo(rcInfo.getFrmNo()); // 车架号
			rpinfo.setReprotNo(rcInfo.getReportNo()); // 报案号
			rpinfo.setRequestPacket(requsetXml); // 请求报文
			rpinfo.setResponePacket(responseXml); // 返回报文
			rpinfo.setTradeType(tradeType);// 交易类型
			rpinfo.setCrtTm(new Date()); // 交易时间
			repairpacketDao.addRPinfo(rpinfo);
		}
	
	}

}
