package cn.sunline.ws.foreignInterface.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.dao.IRepairpacketDao;
import cn.sunline.ws.foreignInterface.entity.RepairpacketInfo;
import cn.sunline.ws.foreignInterface.service.IGiveRepInfoSubmitService;

@Service
public class GiveRepInfoSubmitServiceImpl implements IGiveRepInfoSubmitService {
	@Autowired
	private IRepairpacketDao iRepairpacketDao;

	@Override
	public void savePacket(String frmNo, String reportNo, String responseXml,
			String requsetXml, String tradeType) {
		// 保存交互报文
		RepairpacketInfo repairpacketInfo = new RepairpacketInfo();
		repairpacketInfo.setFrmNo(frmNo);
		repairpacketInfo.setReprotNo(reportNo);
		repairpacketInfo.setRequestPacket(requsetXml);
		repairpacketInfo.setResponePacket(responseXml);
		repairpacketInfo.setTradeType(tradeType);
		repairpacketInfo.setCrtTm(new Date());
		iRepairpacketDao.addRPinfo(repairpacketInfo);
	}

}
