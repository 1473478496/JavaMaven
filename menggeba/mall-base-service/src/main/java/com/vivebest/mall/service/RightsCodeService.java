package com.vivebest.mall.service;

import java.io.InputStream;
import java.util.List;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.RightsCode;

public interface RightsCodeService extends BaseService<RightsCode, Long>{
	
	/**
	 * 权益码列表
	 * @param rights
	 * @param pageable
	 * @return
	 */
	Page<RightsCode> findList(RightsCode rightsCode, Pageable pageable);
	
	
	/**
	 * 根据权益订单号获取权益码
	 * @param rightsOrderId
	 * @return
	 */
	List<RightsCode> findlist(Long rightsOrderId);
	
	/**
	 * 分配权益码
	 * @param rightsId
	 * @param num
	 * @return
	 */
	RightsCode asignCode(Long rightsId) ;
	
	/**
	 * 权益核销状态变更
	 * @param id
	 * @return
	 */
	Message validateCode(String code) ;
	
	
	/**
	 * 获取有效库存
	 * @param rightsId
	 * @param num
	 * @return
	 */
	Integer GetAvailableStock(Long rightsId,Integer num);
	
	/**
	 * 导入权益code
	 * @param inp
	 * @return
	 */
    List<RightsCode> readExcelProduct(InputStream inp);
	
}
