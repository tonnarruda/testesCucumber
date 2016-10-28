package com.fortes.rh.dao.hibernate.sesmt;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoItemDao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;

@Component
public class ExtintorInspecaoItemDaoHibernate extends GenericDaoHibernate<ExtintorInspecaoItem> implements ExtintorInspecaoItemDao
{
}