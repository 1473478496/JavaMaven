/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.service.CacheService;

import freemarker.template.TemplateModelException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

/**
 * Service - 缓存
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	@CacheEvict(value = { "setting", "authorization", "logConfig", "template", "shipping", "area", "seo", "adPosition", "memberAttribute", "navigation", "tag", "friendLink", "brand", "article", "articleCategory", "product", "productCategory", "review", "consultation", "promotion" }, allEntries = true)
	public void clear() {
		reloadableResourceBundleMessageSource.clearCache();
		try {
			freeMarkerConfigurer.getConfiguration().setSharedVariable("setting", SettingUtils.get());
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
	}

}