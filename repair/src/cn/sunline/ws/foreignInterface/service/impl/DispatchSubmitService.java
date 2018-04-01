package cn.sunline.ws.foreignInterface.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.dao.IRepairpacketDao;
import cn.sunline.ws.foreignInterface.entity.RepairpacketInfo;
import cn.sunline.ws.foreignInterface.service.IDispatchSubmitService;

@Service
public class DispatchSubmitService implements IDispatchSubmitService {

	@Autowired
	private IRepairpacketDao repairpacketDao;

	@Override
	public void savePacket(String frmNo, String reportNo, String responseXml, String requsetXml, String tradeType) {
		// 保存交互报文
		RepairpacketInfo rpinfo = new RepairpacketInfo();
		rpinfo.setFrmNo(frmNo); // 车架号
		rpinfo.setReprotNo(reportNo); // 报案号
		rpinfo.setRequestPacket(requsetXml); // 请求报文
		rpinfo.setResponePacket(responseXml); // 返回报文
		rpinfo.setTradeType(tradeType);// 交易类型
		rpinfo.setCrtTm(new Date()); // 交易时间
		repairpacketDao.addRPinfo(rpinfo);
	}
}
