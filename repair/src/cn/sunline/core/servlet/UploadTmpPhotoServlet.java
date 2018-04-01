package cn.sunline.core.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.sunline.core.util.SpringContextUtil;
import cn.sunline.repair.service.DangerServiceI;
import cn.sunline.repair.service.impl.DangerServiceImpl;
import cn.sunline.web.system.service.SystemService;
import cn.sunline.ws.foreignInterface.dao.IRepairMainDao;

public class UploadTmpPhotoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DangerServiceImpl.class);
	private static String uploadPath = null;// 上传文件的目录
	private static String tempPath = null; // 临时文件目录
	private static File uploadFile = null;
	private static File tempPathFile = null;
	private static int sizeThreshold = 1024 * 2000;
	private static int sizeMax = 50 * 1024 * 1024;
	private static String id;

	private SystemService systemService;
	private IRepairMainDao repairMainDao;
	private DangerServiceI dangerService;
	private JdbcTemplate jdbcTemplate;

	@Override
	public void init() throws ServletException {

		// TODO Auto-generated method stub
		tempPath = "c:/myfiel";
		tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}

		if (null == systemService) {
			systemService = SpringContextUtil.getBean("systemService");
		}
		if (null == repairMainDao) {
			repairMainDao = SpringContextUtil.getBean("repairMainDaoImpl");
		}
		if (null == dangerService) {
			dangerService = SpringContextUtil.getBean("dangerService");
		}
		if (null == jdbcTemplate) {
			jdbcTemplate = SpringContextUtil.getBean("jdbcTemplate");
		}
		id = UUID.randomUUID().toString().replaceAll("-", "");
	}

	// 初始化

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)

	throws ServletException, IOException {
		id = UUID.randomUUID().toString().replace("-", "");

		PrintWriter out = response.getWriter();
		boolean flag = true;
		// 检查输入请求是否为multipart表单数据。
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 若果是的话
		if (isMultipart) {
			/** 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。 **/
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(sizeThreshold); // 设置缓冲区大小，这里是4kb
				factory.setRepository(tempPathFile);// 设置缓冲区目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
				upload.setSizeMax(sizeMax);// 设置最大文件尺寸
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> itr = items.iterator();// 所有的表单项
				// 保存文件
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();// 循环获得每个表单项
					if (!item.isFormField()) {// 如果是文件类型
						String name = item.getName();// 获得文件名 包括路径啊
						if (name != null) {
							File fullFile = new File(item.getName());
							// 获取该上传流在表单中name的属性值<input type="file"
							// name="mac">取出来就是mac
							String reportNo = request.getParameter("reportNo");
							String nameA = reportNo;
							// ！！！！！！！！！
							// 达到根据不同的上传框上传到不同的路径，这里把存储路径直接弄成了和mac一样了，大家可以根据自己的要求随意改
							uploadPath = "c:/repair/" + nameA;
							uploadFile = new File(uploadPath);
							if (!uploadFile.exists()) {
								uploadFile.mkdirs();
							}
							// 如果某个上传框没有选择文件上传就直接执行else
							if (!fullFile.getName().equals("")) {
								File savedFile = new File(uploadPath,fullFile.getName());
								item.write(savedFile);
								BufferedImage bufImg = ImageIO.read(new File(uploadPath, fullFile.getName()));
								String sql = "insert into RP_MAINTENANCE_REMARK (ID,IMGAGEPATH,REPORTNO_ID,CRT_TM) values(?,?,?,?)";
								jdbcTemplate.update(sql, new Object[] { id, savedFile.toString(), nameA, new Date() });
								String viewId = request.getParameter("viewId");
								Map<String, Object> fileNames = new HashMap<String, Object>();
								fileNames.put("imgagePath", uploadFile.toString());
								fileNames.put("viewId", viewId);
								JsonConfig jsonConfig = new JsonConfig();
								jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
								JSONObject json = JSONObject.fromObject(fileNames, jsonConfig);
								out.print(json.toString());
								// out.print("^" + bufImg.getHeight() + "^"+
								// bufImg.getWidth());
								logger.info("----"+json+"-----");
								logger.info(bufImg.getHeight() + "---"
										+ bufImg.getWidth() + "---" + bufImg);
								logger.info("upload a file for " + nameA
										+ " successfully" + savedFile);
							} else {
								logger.info("no file upload for " + nameA);
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				logger.info("no file upload for ");
			} catch (FileUploadException e) {
				flag = false;
				logger.info("error occured when uploading a file ");
				e.printStackTrace();
			} catch (Exception e) {
				flag = false;
				logger.info("error occured!! when uploading a file ");
				e.printStackTrace();
			}
		} else {
			flag = false;
			logger.info("the enctype must be multipart/form-data");
			System.out.println("the enctype must be multipart/form-data");
		}
	}
}