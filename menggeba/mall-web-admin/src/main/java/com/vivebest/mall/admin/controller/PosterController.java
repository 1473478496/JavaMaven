package com.vivebest.mall.admin.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.FileInfo.FileType;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.service.FileService;
import com.vivebest.mall.entity.Poster;
import com.vivebest.mall.service.AdPositionService;
import com.vivebest.mall.service.AdService;
import com.vivebest.mall.service.PosterService;

/**
 * Controller - secKill 整点秒杀
 * 
 * @author chen.liang
 */
@Controller("adminPosterController")
@RequestMapping("/admin/poster")
public class PosterController extends BaseController {
	//private static Logger logger = Logger.getLogger(PosterController.class);
	
	@Resource(name="posterServiceImpl")
	private PosterService posterService;
	
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;
	
	@Resource(name = "adServiceImpl")
	private AdService adService;
	
	@Resource(name="fileServiceImpl")
	private FileService fileService;
	
	/**
	 * 列表
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Pageable pageable, ModelMap model)
			throws UnsupportedEncodingException {
		model.addAttribute("page", posterService.findPage(pageable));
		return "/admin/poster/list";
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("ads",adService.findAll());
		return "/admin/poster/add";
	}

	/**
	 * 保存
	 * 
	 * @param special
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam MultipartFile file,Poster poster, RedirectAttributes redirectAttributes) {

		
		if(poster.getName() == null){
			return ERROR_VIEW;
		}
		if(poster.getImgUrl() == null){
			return ERROR_VIEW;
		}
		if(file == null || "".equals(file)){
			return ERROR_VIEW;
		}
		String path = "";
		path = fileService.upload(FileType.file, file, true);
		poster.setUrl(path);
		posterService.save(poster);
		return "redirect:list.jhtml";
	}



	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {	
		Poster poster = posterService.find(id);
		if(poster == null){
			return ERROR_VIEW;
		}
		model.addAttribute("ads",adService.findAll());
		model.addAttribute("poster", poster);
		return "/admin/poster/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestParam MultipartFile file,Poster poster, RedirectAttributes redirectAttributes) {
		
		if(poster.getName() == null){
			return ERROR_VIEW;
		}
		if(poster.getImgUrl() == null){
			return ERROR_VIEW;
		}
		if(file.getSize()>0){
			String path = "";
			path = fileService.upload(FileType.file, file, true);
			poster.setUrl(path);
		}
		posterService.update(poster);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		posterService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	
	
	
	
}
