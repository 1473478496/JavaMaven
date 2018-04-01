package com.vivebest.mall.dao.impl;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ChangesItemDao;
import com.vivebest.mall.entity.ChangesItem;
@Repository("changesItemDaoImpl")
public class ChangesItemDaoImpl extends BaseDaoImpl<ChangesItem, Long> implements ChangesItemDao {

}
