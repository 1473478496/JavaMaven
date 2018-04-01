package cn.sunline.ws.foreignInterface.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.dao.IRepairpacketDao;
import cn.sunline.ws.foreignInterface.entity.DealearInfo;
import cn.sunline.ws.foreignInterface.entity.RepairpacketInfo;
import cn.sunline.ws.foreignInterface.service.IVerifyLossSubmitService;

@Service
public class VerifyLossSubmitServiceImpl implements IVerifyLossSubmitService {

	@Autowired
	private IRepairpacketDao repairpacketDao;

	@Override
	public void savePacket(List<DealearInfo> deInfoList, String responseXml,
			String requsetXml, String tradeType) {

		for (DealearInfo deInfo : deInfoList) {
			RepairpacketInfo rpinfo = new RepairpacketInfo();
			rpinfo.setFrmNo(deInfo.getFrmNo()); // 车架号
			rpinfo.setReprotNo(deInfo.getReportNo()); // 报案号
			rpinfo.setRequestPacket(requsetXml); // 请求报文
			rpinfo.setResponePacket(responseXml); // 返回报文
			rpinfo.setTradeType(tradeType);// 交易类型
			rpinfo.setCrtTm(new Date()); // 交易时间
			repairpacketDao.addRPinfo(rpinfo);
		}

	}

	@Override
	public String validate(DealearInfo deInfo) {
		if (null == deInfo.getFrmNo() || "".equals(deInfo.getFrmNo())) {
			return "车架号不能为空";
		}
		return "";
	}
}
