package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoItemDao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;

@Component
public class ExtintorInspecaoItemManagerImpl extends GenericManagerImpl<ExtintorInspecaoItem, ExtintorInspecaoItemDao> implements ExtintorInspecaoItemManager
{
	@Autowired
	ExtintorInspecaoItemManagerImpl(ExtintorInspecaoItemDao extintorInspecaoItemDao) {
		setDao(extintorInspecaoItemDao);
	}

}