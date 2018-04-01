package com.vivebest.mall.core.util;

import java.io.BufferedInputStream;
 
import java.io.File;
import java.io.FileInputStream;
 
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
 
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class FileWriter {

	private static Logger logger = Logger.getLogger(FileWriter.class);
	public void downLoad(String filePath, HttpServletResponse response,
			boolean isOnLine) throws Exception {
		
		logger.info("downloadfile begin:"+filePath);
		File f = new File(filePath);

		if (!f.exists()) {
			logger.error("downloadfile error: File not found!");
			response.sendError(404, "File not found!");
			return;
		}

		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		// 在线打开方式
		if (isOnLine) {
			URL u = new URL(filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename="
					+ toUTF8(f.getName()));
			// 文件名应该编码成UTF-8
		}
		// 纯下载方式
		else {
			response.setContentType("application/x-msdownload");
			String filedisplay = URLEncoder.encode(f.getName(), "UTF-8"); 
	 		response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
		}

		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		out.flush();
		br.close();
		out.close();

	}

	// UTF-8编码
	private String toUTF8(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

}
