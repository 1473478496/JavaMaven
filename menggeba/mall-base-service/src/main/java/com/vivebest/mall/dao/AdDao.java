/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Ad;

/**
 * Dao - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface AdDao extends BaseDao<Ad, Long> {
    private String abc;

    String getAbc() {
        return abc;
    }

    void setAbc(String abc) {
        AdDao.abc = abc;
    }
}
