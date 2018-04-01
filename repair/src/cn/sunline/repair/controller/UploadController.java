package cn.sunline.repair.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.repair.entity.MaintenanceRemarkEntity;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;
import cn.sunline.web.system.util.MyPropertyConfigurer;

@Controller
@RequestMapping("/uploadController")
public class UploadController extends BaseController {

	private final Logger logger = LoggerFactory
			.getLogger(UploadController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private MyPropertyConfigurer myPropertyConfigurer;

	@RequestMapping(value = "/upload")
	public ModelAndView upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "files", required = false) MultipartFile files)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		try {
			String policyNo = request.getParameter("policyNo");
			logger.info("报案号：{}", policyNo);
			String reportNo = request.getParameter("reportNo");
			logger.info("报案号：{}", reportNo);
			TSUser user = ResourceUtil.getSessionUserName();
			String filePath = myPropertyConfigurer.getProperty("imgAgePath");
			String path = filePath + File.separator + reportNo;
			logger.info("保存文件路径：{}", filePath);
			String fileName = files.getOriginalFilename();
			logger.info("上传文件名称：{}", fileName);
			File saveFile = new File(path + File.separator + fileName);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			logger.info("========开始保存文件路径========");
			files.transferTo(saveFile);
			logger.info("========保存文件成功========");
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String sql = "INSERT INTO RP_MAINTENANCE_REMARK (ID,IMGAGEPATH,POLICYNO_ID,REPORTNO_ID,CRT_TM,CRT_CODE) VALUES(?,?,?,?,?,?)";
			Object obj[] = new Object[] { id, saveFile.toString(), policyNo,
					reportNo, new Date(),user.getUserName() };
			logger.info("文件上传保存记录参数：{}", obj);
			systemService.executeSql(sql, obj);
			logger.info("文件上传参数保存成功");
			mav.addObject("status", "success");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			mav.addObject("status", "error");
			mav.addObject("msg", e.getMessage());
		}
		mav.setViewName("system/repair/danger/actualDanger");
		return mav;
	}

	@RequestMapping("/showImage")
	public ModelAndView showImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		ModelAndView mav = new ModelAndView();
		try {
			String reportNo = request.getParameter("reportNo");
			logger.info("报案号：{}", reportNo);
			String sql = "FROM MaintenanceRemarkEntity WHERE REPORTNO_ID='"
					+ reportNo + "'";
			List<Object> objList = systemService.findByQueryString(sql);// 存放图片路径的List
			mav.addObject("objList", objList);
		} catch (Exception e) {
			logger.error("图片信息查询异常：{}", e.getMessage());
			e.printStackTrace();
		}
		mav.setViewName("system/repair/danger");
		return mav;
	}

	/**
	 * 文件预览
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			String tableId = request.getParameter("id");
			logger.info("id：{}"+tableId);
			String sql = "FROM MaintenanceRemarkEntity WHERE ID='" + tableId
					+ "'";
			List<Object> objList = systemService.findByQueryString(sql);// 存放图片路径的List
			if (CollectionUtils.isNotEmpty(objList)) {
				MaintenanceRemarkEntity info = (MaintenanceRemarkEntity) objList.get(0);
				File file = new File(info.getImgagePath());
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + file.getName());
				inputStream = new FileInputStream(file);
				os = response.getOutputStream();
				byte[] b = new byte[2048];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (null != os) {
				os.close();
			}
			if (null != inputStream) {
				inputStream.close();
			}
		}
	}

	/**
	 * 图片删除
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "delImgAgeData")
	public @ResponseBody
	Map<String, Object> delImgAgeData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "";
		String imgIds = request.getParameter("imgIds");
		String img[] = imgIds.split(",");// 逗号间隔
		try {
			if (!"".equals(img) && img.length > 0 && null != img) {
				for (String mg : img) {
					// 根据图片ID值删除
					sql = "delete from RP_MAINTENANCE_REMARK where id=?";
					systemService.executeSql(sql, new Object[] { mg });
				}
				result.put("status", "success");
			} else {
				result.put("status", "error");
				result.put("msg", "未获取到图片数据信息");
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
