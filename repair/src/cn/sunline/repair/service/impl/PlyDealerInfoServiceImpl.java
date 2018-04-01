package cn.sunline.repair.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sunline.core.common.service.impl.CommonServiceImpl;
import cn.sunline.repair.service.DealerInfoServiceI;
import cn.sunline.repair.service.PlyDealerInfoServiceI;



@Service("plyDealerInfoServiceI")
@Transactional
public class PlyDealerInfoServiceImpl extends CommonServiceImpl implements PlyDealerInfoServiceI {
	
}