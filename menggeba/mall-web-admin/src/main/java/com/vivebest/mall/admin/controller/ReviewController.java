/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Review;
import com.vivebest.mall.entity.Review.Type;
import com.vivebest.mall.service.ReviewService;

/**
 * Controller - 评论
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminReviewController")
@RequestMapping("/admin/review")
public class ReviewController extends BaseController {

	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;

	@Value("${upload.dir}")
	private String uploadDir;

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("review", reviewService.find(id));
		return "/admin/review/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long id,
			@RequestParam(defaultValue = "false") Boolean isShow,
			RedirectAttributes redirectAttributes) {
		Review review = reviewService.find(id);
		if (review == null) {
			return ERROR_VIEW;
		}
		review.setIsShow(isShow);
		reviewService.update(review);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Type type, Pageable pageable, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("types", Type.values());
		model.addAttribute("page",
				reviewService.findPage(null, null, type, null, pageable));
		return "/admin/review/list";
	}

	/**
	 * 调转到导入页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String listimport(Long id, ModelMap model) {

		return "/admin/review/import";
	}

	/**
	 * 导入Excel
	 * 
	 * @param uploadFile
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	/**
	 * 
	 * @param uploadFile
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void fileUpLoad(@RequestParam MultipartFile uploadFile,
			HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		// 上传后记录的文件...
		Message msg = ERROR_MESSAGE;
		String uploadPath = uploadDir + uploadFile.getOriginalFilename();
		File imageFile = new File(uploadPath);

		uploadFile.transferTo(imageFile);
		// 业务处理
		if (imageFile.exists()) {

			InputStream is = new FileInputStream(imageFile);

			try {
				List<Review> review = reviewService.readExcelProduct(is);
				reviewService.saveAll(review);
				msg = SUCCESS_MESSAGE;

			} catch (Exception ex) {
				msg = ERROR_MESSAGE;
				// msg.setContent(productService.getInclusionErrList());
			}
		}
		responseOutWithJson(response, msg);
		return;
	}

	private void responseOutWithJson(HttpServletResponse response,
			Object responseObject) throws JsonProcessingException {
		// 将实体对象转换为JSON Object转换
		ObjectMapper mapper = new ObjectMapper();
		String responseJSONObject = mapper.writeValueAsString(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		reviewService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}