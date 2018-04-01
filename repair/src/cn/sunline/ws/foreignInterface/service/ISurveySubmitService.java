package cn.sunline.ws.foreignInterface.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
public interface ISurveySubmitService {
	public void savePacket(List<RepairMainInfo> rCInfoList,String responseXml, String requsetXml, String tradeType);
}
