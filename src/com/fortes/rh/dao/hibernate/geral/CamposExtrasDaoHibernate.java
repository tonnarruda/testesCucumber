package com.fortes.rh.dao.hibernate.geral;

import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.model.geral.CamposExtras;

@Component
public class CamposExtrasDaoHibernate extends GenericDaoHibernate<CamposExtras> implements CamposExtrasDao
{
}
