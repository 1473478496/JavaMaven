package com.vivebest.mall.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.RightsCodeDao;
import com.vivebest.mall.dao.RightsDao;
import com.vivebest.mall.entity.RightOrder.RightsOrderStatus;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsCode;
import com.vivebest.mall.service.RightOrderService;
import com.vivebest.mall.service.RightsCodeService;

/**
 * RightsServiceImpl - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsCodeServiceImpl")
public class RightsCodeServiceImpl extends BaseServiceImpl<RightsCode, Long> implements RightsCodeService {

	private Logger logger = LoggerFactory.getLogger(RightsCodeServiceImpl.class);
	
	@Resource(name="rightsCodeDaoImpl")
	private RightsCodeDao rightsCodeDao;
	
	@Resource(name = "rightsOrderServiceImpl")
	private RightOrderService rightOrderService;
	
	
	@Resource(name="rightsDaoImpl")
	private RightsDao rightsDao;
	
	@Resource(name = "rightsCodeDaoImpl")
	public void setBaseDao(RightsCodeDao rightsCodeDao) {
		super.setBaseDao(rightsCodeDao);
	}
	
	private String inclusionErrList;
	
	@Override
	public Page<RightsCode> findList(RightsCode rightsCode, Pageable pageable) {
		return rightsCodeDao.findList(rightsCode, pageable);
	}
	@Override
	public RightsCode asignCode(Long rightsId) {
		List<RightsCode> list = rightsCodeDao.asignCode(rightsId, 1);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public Integer GetAvailableStock(Long rightsId, Integer num) {
		// TODO Auto-generated method stub
		List<RightsCode> list = rightsCodeDao.asignCode(rightsId, null);
		return list.size();
	}
	@Override
	public List<RightsCode> findlist(Long rightsOrderId) {
		// TODO Auto-generated method stub
		List<RightsCode> RightsCodelist=rightsCodeDao.findByRigthsOrderId(rightsOrderId);
		return RightsCodelist;
	}
	
	@Override
	public List<RightsCode> readExcelProduct(InputStream inp)
	{
		List<RightsCode> RightsCodeList = new ArrayList<RightsCode>();
		StringBuilder strErrAll = new StringBuilder();
		Map<Integer, String> mapIdentity = new HashMap<Integer, String>();
		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets
			StringBuilder eachLineDesc = new StringBuilder();

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i); // 获取行(row)对象
				if (row == null || row.getCell(0) == null) {
					continue;
				}
				String sn = null;
				if (row.getCell(0) != null) {

					Cell cell = row.getCell(0); // ForMat带E的情况
					if (cell != null)
						sn = ConvertCellStr(cell, cellStr);
					if (StringUtils.isNotEmpty(sn)) {
						if (mapIdentity.containsValue(sn)) {
							eachLineDesc.append("权益订单号重复;");
						}
						mapIdentity.put(i, sn);
					} else {
						// row为空的处理
						sheet.removeRow(row);
						continue;
					}

				}
				 
				RightsCode rightcode = new RightsCode();
				for (int j = 0; j < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j); // 获得单元格(cell)对象
              
					if (cell != null) {
						// 转换接收的单元格
						cellStr = ConvertCellStr(cell, cellStr);
						if (cellStr != null && !StringUtils.isEmpty(cellStr)) {
							try {
								rightcode=addingrightcode(j,rightcode,cellStr);
								// 将单元格的数据添加至一个对象
								cellStr = ""; // 清空变量值

							} catch (Exception ex) {
								//eachLineDesc.append(cellStr + "数据内容格式有误;");
							}
						}
					} else
						continue;

				}
				if (eachLineDesc.length() > 1) {
					strErrAll.append("第" + i + "行:");
					strErrAll.append(eachLineDesc);
					strErrAll.append("\r\n");
				}
				rightcode.setCreateDate(new Date());
				rightcode.setModifyDate(new Date());
				rightcode.setIsUsed(false);
				
				RightsCodeList.add(rightcode);
				eachLineDesc.delete(0, eachLineDesc.length());// 清空

				cellStr = null;
				
			}
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					this.setInclusionErrList(strErrAll.toString());
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

			}
		}
		
		return RightsCodeList;
	}
			
	
	
	
	 
	private RightsCode addingrightcode(int j, RightsCode rightcode,
			String cellStr) {
		// TODO Auto-generated method stub
		switch (j) {

		case 0:
			Rights rights=rightsDao.findByName(cellStr);
			rightcode.setRights(rights);
			break;
			
		case 1:
			rightcode.setCode(cellStr);
			break;
		}
		return rightcode;
	}
	/**
	 * 把单元格内的类型转换至String类型
	 */
	private String ConvertCellStr(Cell cell, String cellStr) {

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:

			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {

				// 读取日期格式
				cellStr = cell.getDateCellValue().toString();

			} else {

				// 读取数字
				cellStr = String.valueOf(cell.getNumericCellValue());
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			// 读取公式

			DecimalFormat df = new DecimalFormat("0");

			cellStr = df.format(cell.getNumericCellValue());

			break;
		}
		return cellStr;
	}
	
 
	
	
	public String getInclusionErrList() {
		return inclusionErrList;
	}

	public void setInclusionErrList(String err) {
		this.inclusionErrList = err;
	}
	
	@Override
	public Message validateCode(String code) {
		logger.info("rightCode--->"+code);
		RightsCode rcode = rightsCodeDao.findByCode(code);
		if(rcode != null){
			rcode.setIsUsed(true);
			rcode.getRightOrder().setRightsOrderStatus(RightsOrderStatus.completed);
			super.update(rcode);
			logger.info("---->"+rcode.getRightOrder().getRightsOrderStatus());
			return Message.success("权益核销成功", rcode);
		}
		return Message.error("找不到该权益", "");
	}
	

}
